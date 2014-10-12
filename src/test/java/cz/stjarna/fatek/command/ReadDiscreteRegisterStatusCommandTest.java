package cz.stjarna.fatek.command;

import cz.stjarna.fatek.command.response.Response;
import cz.stjarna.fatek.enums.CommandEnum;
import cz.stjarna.fatek.register.discrete.XRegister;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ReadDiscreteRegisterStatusCommandTest {

    @Test
    public void testGetRequestData() throws Exception {
        DiscreteRegisterStatusReadCommand command = new DiscreteRegisterStatusReadCommand(new XRegister(8), 5);
        String requestData  = command.getRequestData();
        assertEquals("Request data does not match", "05X0008", requestData);
    }

    @Test
    public void testGetResponseData() throws Exception {
        DiscreteRegisterStatusReadCommand command = new DiscreteRegisterStatusReadCommand(new XRegister(8), 5);
        Response response = new Response(99, CommandEnum.READ_DISCRETE_REGISTER_STATUS.getCode(), 0x00, null, "11111".getBytes());
        List<Long> result = command.getResponseData(response);
        assertNotNull("Result cannot be null", result);
        assertEquals("Size does not match", 5, result.size());
        for (int i = 0; i < result.size(); i++) {
            assertEquals(String.format("Element with index %d must be TRUE", i), new Long(1L), result.get(i));
        }
    }
}