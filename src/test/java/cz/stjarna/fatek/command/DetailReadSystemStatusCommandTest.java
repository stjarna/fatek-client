package cz.stjarna.fatek.command;

import cz.stjarna.fatek.command.response.DetailedSystemStatus;
import org.junit.Test;

import static org.junit.Assert.*;

public class DetailReadSystemStatusCommandTest {

    @Test
    public void testGetRequestData() throws Exception {
        DetailReadSystemStatusCommand command = new DetailReadSystemStatusCommand();
        String requestData  = command.getRequestData();
        assertNull("Request data must be null", requestData);
    }

    @Test
    public void testGetResponseData() throws Exception {
        DetailReadSystemStatusCommand command = new DetailReadSystemStatusCommand();
        DetailedSystemStatus result = command.getResultFunction().apply("210102408000010001000064006407D203E800001F88100001000100".getBytes());
        assertNotNull("Result cannot be null", result);

        assertTrue("Run flag must be TRUE", result.isRun());
        assertTrue("Battery low flag must be FALSE", !result.isBatteryLow());
        assertTrue("Ladder checksum error flag must be FALSE", !result.isLadderChecksumError());
        assertTrue("Use ROM pack flag must be FALSE", !result.isUseRomPack());
        assertTrue("WDT Time out flag must be FALSE", !result.isWDTTimeout());
        assertTrue("Set Id flag must be TRUE", result.isSetId());
        assertTrue("Urgent stop flag must be FALSE", !result.isUrgentStop());

        assertEquals("Main unit type mismatch", 0x01, result.getMainUnitType());

        assertEquals("Main unit IO Points mismatch", 0x02, result.getMainUnitIOPoints());

        assertEquals("OS version mismatch", 0x40, result.getOSVersion());

        assertEquals("Ladder size mismatch", 0x8000, result.getLadderSize());

        assertEquals("Discrete input size mismatch", 0x0100, result.getDiscreteInput());

        assertEquals("Discrete output size mismatch", 0x0100, result.getDiscreteOutput());

        assertEquals("Analog input size mismatch", 0x0064, result.getAnalogInput());

        assertEquals("Analog output size mismatch", 0x0064, result.getAnalogOutput());

        assertEquals("MRelay size mismatch", 0x07D2, result.getMRelay());

        assertEquals("SRelay size mismatch", 0x03E8, result.getSRelay());

        assertEquals("LRelay size mismatch", 0x0000, result.getLRelay());

        assertEquals("RRelay size mismatch", 0x1F88, result.getRRelay());

        assertEquals("DRelay size mismatch", 0x1000, result.getDRelay());

        assertEquals("Timer size mismatch", 0x0100, result.getTimer());

        assertEquals("Counter size mismatch", 0x0100, result.getCounter());
    }
}