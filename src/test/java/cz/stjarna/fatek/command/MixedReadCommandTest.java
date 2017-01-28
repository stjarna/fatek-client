package cz.stjarna.fatek.command;

import cz.stjarna.fatek.register.data.RRegister;
import cz.stjarna.fatek.register.discrete.DWMRegister;
import cz.stjarna.fatek.register.discrete.YRegister;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MixedReadCommandTest {

    @Test
    public void testGetRequestData() throws Exception {
        MixedReadCommand command = new MixedReadCommand(new RRegister(1), new YRegister(9), new DWMRegister(0));
        String requestData  = command.getRequestData();
        assertEquals("Request data does not match", "03R00001Y0009DWM0000", requestData);
    }

    @Test
    public void testGetResponseData() throws Exception {
        MixedReadCommand command = new MixedReadCommand(new RRegister(1), new YRegister(9), new DWMRegister(0));
        List<Long> result = command.getResultFunction().apply("5C341003547BA".getBytes());
        assertNotNull("Result cannot be null", result);
        assertEquals("Size does not match", 3, result.size());
        assertEquals("Value does not match", 0x5C34L, result.get(0).longValue());
        assertEquals("Value does not match", 1L, result.get(1).longValue());
        assertEquals("Value does not match", 0x3547BAL, result.get(2).longValue());
    }
}