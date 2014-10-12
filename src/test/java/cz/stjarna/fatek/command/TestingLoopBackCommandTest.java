package cz.stjarna.fatek.command;

import cz.stjarna.fatek.command.response.Response;
import cz.stjarna.fatek.enums.CommandEnum;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestingLoopBackCommandTest {

    @Test
    public void testGetRequestData() throws Exception {
        TestingLoopBackCommand command = new TestingLoopBackCommand("ABCDEFG");
        String requestData  = command.getRequestData();
        assertEquals("Request data does not match", "ABCDEFG", requestData);
    }

    @Test
    public void testGetResponseData() throws Exception {
        TestingLoopBackCommand command = new TestingLoopBackCommand("ABCDEFG");
        Response response = new Response(99, CommandEnum.READ_FROM_DISCRETE_REGISTER.getCode(), 0x00, null, "ABCDEFG".getBytes());
        String result = command.getResponseData(response);
        assertNotNull("Result cannot be null", result);
        assertEquals("Expected value does not match", "ABCDEFG", result);
    }
}