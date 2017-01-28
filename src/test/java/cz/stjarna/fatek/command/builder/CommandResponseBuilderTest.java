package cz.stjarna.fatek.command.builder;

import cz.stjarna.fatek.command.response.Response;
import cz.stjarna.fatek.enums.CommandEnum;
import cz.stjarna.fatek.util.CommonUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CommandResponseBuilderTest {

    @Test
    public void testReadFromDiscreteRegisterY() throws Exception {
        Response response = new CommandResponseBuilder(99, CommandEnum.READ_FROM_DISCRETE_REGISTER, "\u0002634401000000000E4\u0003".getBytes(), null).build();
        assertNotNull("Response cannot be null", response);
        assertEquals("Slave station does not match", 99, response.getSlaveStationId());
        assertEquals("Command code does not match", 0x44, response.getCommandCode());
        assertEquals("Payload does not match", "1000000000", CommonUtils.convertByteArrayToHumanReadableString(response.getPayload()));
    }
}