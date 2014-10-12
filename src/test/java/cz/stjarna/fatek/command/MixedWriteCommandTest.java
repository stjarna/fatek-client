package cz.stjarna.fatek.command;

import com.google.common.collect.Lists;
import cz.stjarna.fatek.register.data.DRRegister;
import cz.stjarna.fatek.register.discrete.WMRegister;
import cz.stjarna.fatek.register.discrete.YRegister;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MixedWriteCommandTest {

    @Test
    public void testGetRequestData() throws Exception {
        MixedWriteCommand command = new MixedWriteCommand(Lists.newArrayList(new YRegister(0), new YRegister(1), new WMRegister(8), new DRRegister(2)), Lists.newArrayList(0x1L, 0x0L, 0x5555L, 0xFFL));
        String requestData  = command.getRequestData();
        assertEquals("Request data does not match", "04Y00001Y00010WM00085555DR00002000000FF", requestData);
    }

}