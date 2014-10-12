package cz.stjarna.fatek.command;

import com.google.common.collect.Lists;
import cz.stjarna.fatek.register.discrete.WYRegister;
import org.junit.Test;

import static cz.stjarna.fatek.util.CommonUtils.convertBinaryStringToLong;
import static org.junit.Assert.assertEquals;

public class WriteContinuousRegisterCommandTest {

    @Test
    public void testGetRequestData() throws Exception {
        WriteContinuousRegisterCommand command = new WriteContinuousRegisterCommand(new WYRegister(8), 2, Lists.newArrayList(convertBinaryStringToLong("1010101010101010"), convertBinaryStringToLong("101010101010101")));

        String requestData  = command.getRequestData();
        assertEquals("Request data does not match", "02WY0008AAAA5555", requestData);
    }

}