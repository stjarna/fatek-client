package cz.stjarna.fatek.command;

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
        String result = command.getResultFunction().apply("ABCDEFG".getBytes());
        assertNotNull("Result cannot be null", result);
        assertEquals("Expected value does not match", "ABCDEFG", result);
    }
}