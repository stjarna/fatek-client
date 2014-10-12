package cz.stjarna.fatek.command.builder;

import cz.stjarna.fatek.enums.CommandEnum;
import cz.stjarna.fatek.enums.RegisterLengthEnum;
import cz.stjarna.fatek.register.discrete.XRegister;
import cz.stjarna.fatek.register.discrete.YRegister;
import cz.stjarna.fatek.util.CommonUtils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommandBuilderTest {

    private String getTestRequestDataForReadFromDiscreteRegisterX() {
        return new StringBuilder()
                .append(String.format(RegisterLengthEnum.BYTE.getFormat(), 1 & RegisterLengthEnum.BYTE.getMask()))
                .append(new XRegister(8).toString())
                .toString();
    }

    private String getTestRequestDataForReadFromDiscreteRegisterY() {
        return new StringBuilder()
                .append(String.format(RegisterLengthEnum.BYTE.getFormat(), 10 & RegisterLengthEnum.BYTE.getMask()))
                .append(new YRegister(8).toString())
                .toString();
    }

    @Test
    public void testReadFromDiscreteRegisterX() throws Exception {
        CommandBuilder commandBuilder = new CommandBuilder(99);

        byte[] commandData = commandBuilder.commandCode(CommandEnum.READ_FROM_DISCRETE_REGISTER)
                .data(getTestRequestDataForReadFromDiscreteRegisterX())
                .build();

        assertEquals("Expected command data not matched", "STX|634401X000881|ETX", CommonUtils.convertByteArrayToHumanReadableString(commandData));
    }

    @Test
    public void testReadFromDiscreteRegisterY() throws Exception {
        CommandBuilder commandBuilder = new CommandBuilder(99);

        byte[] commandData = commandBuilder.commandCode(CommandEnum.READ_FROM_DISCRETE_REGISTER)
                .data(getTestRequestDataForReadFromDiscreteRegisterY())
                .build();

        assertEquals("Expected command data not matched", "STX|63440AY000892|ETX", CommonUtils.convertByteArrayToHumanReadableString(commandData));
    }
}