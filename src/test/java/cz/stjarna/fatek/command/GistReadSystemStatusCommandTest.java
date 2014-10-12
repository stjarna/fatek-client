package cz.stjarna.fatek.command;

import cz.stjarna.fatek.command.response.GistSystemStatus;
import cz.stjarna.fatek.command.response.Response;
import cz.stjarna.fatek.enums.CommandEnum;
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
        Response response = new Response(99, CommandEnum.GIST_READ_SYSTEM_STATUS.getCode(), 0x00, null, "290000".getBytes());
        GistSystemStatus result = command.getResponseData(response);
        assertNotNull("Result cannot be null", result);
        assertTrue("Run flag must be TRUE", result.isRun());
        assertTrue("Set Id flag must be TRUE", result.isSetId());
        assertTrue("Use ROM pack flag must be TRUE", result.isUseRomPack());
    }
}