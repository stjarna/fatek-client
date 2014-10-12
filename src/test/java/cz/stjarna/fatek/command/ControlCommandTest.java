package cz.stjarna.fatek.command;

import cz.stjarna.fatek.enums.RunStopControlEnum;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ControlCommandTest {

    @Test
    public void testGetRequestDataForRun() throws Exception {
        ControlCommand command = new ControlCommand(RunStopControlEnum.RUN);
        String requestData  = command.getRequestData();
        assertEquals("Request data does not match", "1", requestData);
    }

    @Test
    public void testGetRequestDataForStop() throws Exception {
        ControlCommand command = new ControlCommand(RunStopControlEnum.STOP);
        String requestData  = command.getRequestData();
        assertEquals("Request data does not match", "0", requestData);
    }

}