package cz.stjarna.fatek.command.builder;

import cz.stjarna.fatek.enums.CommandEnum;
import cz.stjarna.fatek.enums.RegisterLengthEnum;
import cz.stjarna.fatek.exception.FatekException;
import cz.stjarna.fatek.util.CommonConstants;
import cz.stjarna.fatek.util.LRCUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static cz.stjarna.fatek.util.CommonConstants.ENCONDING_ASCII;


@Slf4j
public class CommandBuilder implements IBuilder<byte[]> {

    private final ByteArrayOutputStream buffer;
    private byte[] payload;
    private CommandEnum command;
    private int slaveStationId;

    public static CommandBuilder builder() {
        return new CommandBuilder();
    }

    private CommandBuilder() {
        buffer = new ByteArrayOutputStream();
    }

    public CommandBuilder slaveStationId(final int slaveStationId) {
        checkArgument(slaveStationId > 0, "Slave id must be provided");
        log.debug("Adding slave id {}", slaveStationId);
        this.slaveStationId = slaveStationId;
        return this;
    }

    public CommandBuilder commandCode(final CommandEnum command) {
        checkNotNull(command, "Command code cannot be null");
        log.debug("Adding command code {}", command);
        this.command = command;
        return this;
    }

    public CommandBuilder data(final String stringData) throws FatekException {
        log.debug("Adding data {}", stringData);
        try {
            payload = convertString2ByteArray(stringData);
            return this;
        } catch (IOException e) {
            throw new FatekException("Could not write data", e);
        }
    }

    @Override
    public byte[] build() throws FatekException {
        try {
            buffer.write(CommonConstants.START_OF_TEXT);
            buffer.write(convertByte2ByteArray(slaveStationId));
            buffer.write(convertByte2ByteArray(command.getCode()));
            buffer.write(payload);
            buffer.write(convertByte2ByteArray(LRCUtils.countLRC(payload)));
            buffer.write(CommonConstants.END_OF_TEXT);
            return buffer.toByteArray();
        } catch (IOException e) {
            throw new FatekException("Unable to build request", e);
        }
    }

    private byte[] convertByte2ByteArray(final int value) throws UnsupportedEncodingException {
        return String.format(RegisterLengthEnum.BYTE.getFormat(), value & RegisterLengthEnum.BYTE.getMask()).getBytes(ENCONDING_ASCII);
    }

    private byte[] convertString2ByteArray(final String value) throws UnsupportedEncodingException {
        byte[] result;
        if (value == null) {
            result = new byte[0];
        } else {
            result = String.format(value).getBytes(ENCONDING_ASCII);
        }
        return result;
    }
}
