package cz.stjarna.fatek.command;

import cz.stjarna.fatek.command.response.Response;
import cz.stjarna.fatek.enums.CommandEnum;
import cz.stjarna.fatek.register.data.RRegister;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ReadContinuousRegisterCommandTest {

    @Test
    public void testGetRequestData() throws Exception {
        ReadContinuousRegisterCommand command = new ReadContinuousRegisterCommand(new RRegister(12), 3);
        String requestData  = command.getRequestData();
        assertEquals("Request data does not match", "03R00012", requestData);
    }

    @Test
    public void testGetResponseData() throws Exception {
        ReadContinuousRegisterCommand command = new ReadContinuousRegisterCommand(new RRegister(12), 3);
        List<Long> result = command.getResultFunction().apply("10A57FC40001".getBytes());
        assertNotNull("Result cannot be null", result);
        assertEquals("Size does not match", 3, result.size());

        assertEquals(String.format("Element with index %d must be TRUE", 0), new Long(4261), result.get(0));
        assertEquals(String.format("Element with index %d must be TRUE", 1), new Long(32708), result.get(1));
        assertEquals(String.format("Element with index %d must be TRUE", 1), new Long(1), result.get(2));
    }
}