package cz.stjarna.fatek.command;

import com.google.common.collect.Lists;
import cz.stjarna.fatek.register.discrete.YRegister;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WriteDiscreteRegisterCommandTest {

    @Test
    public void testGetRequestData() throws Exception {
        WriteDiscreteRegisterCommand command = new WriteDiscreteRegisterCommand(new YRegister(9), 4, Lists.newArrayList(1L, 1L, 1L, 1L));
        String requestData  = command.getRequestData();
        assertEquals("Request data does not match", "04Y00091111", requestData);
    }

}