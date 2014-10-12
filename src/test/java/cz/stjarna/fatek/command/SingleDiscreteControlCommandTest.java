package cz.stjarna.fatek.command;

import cz.stjarna.fatek.enums.RunningCodeControlEnum;
import cz.stjarna.fatek.register.discrete.XRegister;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SingleDiscreteControlCommandTest {

    @Test
    public void testGetRequestDataForDisable() throws Exception {
        SingleDiscreteControlCommand command = new SingleDiscreteControlCommand(new XRegister(1), RunningCodeControlEnum.DISABLE);
        String requestData  = command.getRequestData();
        assertEquals("Request data does not match", "1X0001", requestData);
    }

    @Test
    public void testGetRequestDataForEnable() throws Exception {
        SingleDiscreteControlCommand command = new SingleDiscreteControlCommand(new XRegister(1), RunningCodeControlEnum.ENABLE);
        String requestData  = command.getRequestData();
        assertEquals("Request data does not match", "2X0001", requestData);
    }

    @Test
    public void testGetRequestDataForSet() throws Exception {
        SingleDiscreteControlCommand command = new SingleDiscreteControlCommand(new XRegister(1), RunningCodeControlEnum.SET);
        String requestData  = command.getRequestData();
        assertEquals("Request data does not match", "3X0001", requestData);
    }

    @Test
    public void testGetRequestDataForReset() throws Exception {
        SingleDiscreteControlCommand command = new SingleDiscreteControlCommand(new XRegister(1), RunningCodeControlEnum.RESET);
        String requestData  = command.getRequestData();
        assertEquals("Request data does not match", "4X0001", requestData);
    }
}