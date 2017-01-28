package cz.stjarna.fatek.command;

import cz.stjarna.fatek.command.response.GistSystemStatus;
import org.junit.Test;

import static org.junit.Assert.*;

public class GistReadSystemStatusCommandTest {

    @Test
    public void testGetRequestData() throws Exception {
        GistReadSystemStatusCommand command = new GistReadSystemStatusCommand();
        String requestData  = command.getRequestData();
        assertNull("Request data must be null", requestData);
    }

    @Test
    public void testGetResponseData() throws Exception {
        GistReadSystemStatusCommand command = new GistReadSystemStatusCommand();
        GistSystemStatus result = command.getResultFunction().apply("290000".getBytes());
        assertNotNull("Result cannot be null", result);
        assertTrue("Run flag must be TRUE", result.isRun());
        assertTrue("Set Id flag must be TRUE", result.isSetId());
        assertTrue("Use ROM pack flag must be TRUE", result.isUseRomPack());
    }
}