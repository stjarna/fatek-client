package cz.stjarna.fatek.command;

import cz.stjarna.fatek.command.response.Response;
import cz.stjarna.fatek.enums.CommandEnum;
import cz.stjarna.fatek.register.discrete.YRegister;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ReadDiscreteRegisterCommandTest {

    @Test
    public void testGetRequestData() throws Exception {
        DiscreteRegisterReadCommand command = new DiscreteRegisterReadCommand(new YRegister(8), 10);
        String requestData  = command.getRequestData();
        assertEquals("Request data does not match", "0AY0008", requestData);
    }

    @Test
    public void testGetResponseData() throws Exception {
        DiscreteRegisterReadCommand command = new DiscreteRegisterReadCommand(new YRegister(8), 10);
        Response response = new Response(99, CommandEnum.READ_FROM_DISCRETE_REGISTER.getCode(), 0x00, null, "1000000000".getBytes());
        List<Long> result = command.getResponseData(response);
        assertNotNull("Result cannot be null", result);
        assertEquals("Size does not match", 10, result.size());
        assertEquals("First element must be TRUE", new Long(1L), result.get(0));
        for (int i = 1; i < result.size(); i++) {
            assertEquals(String.format("Element with index %d must be FALSE", i), new Long(0L), result.get(i));
        }
    }
}