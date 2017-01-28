package cz.stjarna.fatek.command.builder;

import cz.stjarna.fatek.command.AbstractCommand;
import cz.stjarna.fatek.command.response.Response;
import cz.stjarna.fatek.enums.CommandEnum;
import cz.stjarna.fatek.enums.ErrorCodeEnum;
import cz.stjarna.fatek.exception.FatekErrorCodeException;
import cz.stjarna.fatek.exception.FatekException;
import cz.stjarna.fatek.util.CommonConstants;
import cz.stjarna.fatek.util.LRCUtils;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class CommandResponseBuilder<RESULT_TYPE> implements IBuilder<Response<RESULT_TYPE>> {

    public static final String MESSAGE_MUST_BE_READ_FIRST = "Response must be read first";

    public static final int OFFSET_STATION_ID = 1;
    public static final int OFFSET_COMMAND_CODE = 3;
    public static final int OFFSET_ERROR_CODE = 5;
    public static final int OFFSET_DATA = 6;
    private final int expectedSlaveStationId;
    private final CommandEnum expectedCommandCode;
    private final Function<byte[], RESULT_TYPE> resultFunction;

    private AbstractCommand command;
    private int stationId;
    private int commandCode;
    private int errorCode;
    private int checkSum;
    private byte[] payload;
    private final byte[] messageByteArray;

    public CommandResponseBuilder(final int slaveStationId, final CommandEnum commandCode, final byte[] receivedData, final Function<byte[], RESULT_TYPE> resultFunction) {
        checkNotNull(commandCode, "Command code cannot be null");
        checkNotNull(receivedData, "Received data cannot be null");
        this.messageByteArray = receivedData.clone();
        this.expectedSlaveStationId = slaveStationId;
        this.expectedCommandCode = commandCode;
        this.resultFunction = resultFunction;
    }

    private CommandResponseBuilder readStationId() throws FatekException {
        checkNotNull(messageByteArray, MESSAGE_MUST_BE_READ_FIRST);
        final ParsedValue parsedValue = new ParsedValue(messageByteArray, OFFSET_STATION_ID, 2);
        stationId = parsedValue.getIntData();
        log.debug("Station Id parsed: {}", parsedValue);
        return this;
    }

    private CommandResponseBuilder readCommandCode() throws FatekException {
        checkNotNull(messageByteArray, MESSAGE_MUST_BE_READ_FIRST);
        final ParsedValue parsedValue = new ParsedValue(messageByteArray, OFFSET_COMMAND_CODE, 2);
        commandCode = parsedValue.getIntData();
        log.debug("Command code parsed: {}", parsedValue);
        return this;
    }

    private CommandResponseBuilder readErrorCode() throws FatekException {
        checkNotNull(messageByteArray, MESSAGE_MUST_BE_READ_FIRST);
        if (isTestingLoopBackCommand()) {
            log.debug("Testing loop back does not come with any error code. Nothing to parse.");
        } else {
            final ParsedValue parsedValue = new ParsedValue(messageByteArray, OFFSET_ERROR_CODE, 1);
            errorCode = parsedValue.getIntData();
            log.debug("Error code parsed: {}", parsedValue);

        }
        return this;
    }

    private CommandResponseBuilder readChecksum() throws FatekException {
        checkNotNull(messageByteArray, MESSAGE_MUST_BE_READ_FIRST);
        final ParsedValue parsedValue = new ParsedValue(messageByteArray, messageByteArray.length - 3, 2);
        checkSum = parsedValue.getIntData();
        log.debug("Checksum parsed: {}", parsedValue);
        return this;
    }

    private CommandResponseBuilder readData() throws FatekException {
        checkNotNull(messageByteArray, MESSAGE_MUST_BE_READ_FIRST);
        int offset = OFFSET_DATA;
        if (isTestingLoopBackCommand()) {
            // no status code in case of loop back so we can start reading one byte earlier
            offset--;
        }

        final ParsedValue parsedValue = new ParsedValue(messageByteArray, offset, messageByteArray.length - 3 - offset);
        payload = parsedValue.getByteArrayData();
        log.debug("Data parsed: {}", parsedValue);
        return this;
    }

    private CommandResponseBuilder verifyResponse() throws FatekException {
        checkNotNull(messageByteArray, MESSAGE_MUST_BE_READ_FIRST);
        log.debug("Going to verify parse response data");
        verifySlaveStationId();
        verifyCommandCode();
        verifyErrorCode();
        verifyChecksum();
        log.info("Response verified successfully");
        return this;
    }

    private void verifyChecksum() throws FatekException {
        final int computedChecksum = LRCUtils.countLRC(Arrays.copyOfRange(messageByteArray, 0, messageByteArray.length - 3));
        if (checkSum == computedChecksum) {
            log.debug("Checksum OK");
        } else {
            throw new FatekException(String.format("Received data's LRC (%02XH) does not match received LRC (%02XH)", computedChecksum, checkSum));
        }
    }

    private void verifyErrorCode() throws FatekErrorCodeException {
        if (!isTestingLoopBackCommand()) {
            // loop back test does not have any status
            if (errorCode == 0) {
                log.debug("Error code OK");
            } else {
                throw new FatekErrorCodeException(String.format("Command processed with %s", ErrorCodeEnum.get(errorCode).toString()), errorCode);
            }
        }
    }

    private void verifyCommandCode() throws FatekException {
        if (expectedCommandCode.getCode() == commandCode) {
            log.debug("Command code OK");
        } else {
            throw new FatekException("Received command code (%02XH) does not match command code (%02XH) from an initial request", commandCode, command.getCommandCode().getCode());
        }
    }

    private void verifySlaveStationId() throws FatekException {
        if (expectedSlaveStationId == stationId) {
            log.debug("Slave station id OK");
        } else {
            throw new FatekException("Received slave station id (%02XH) does not match slave station id (%02XH) from an initial request", stationId, expectedSlaveStationId);
        }
    }

    private boolean isTestingLoopBackCommand() {
        return CommandEnum.TESTING_LOOP_BACK == expectedCommandCode;
    }

    @Override
    public Response build() throws FatekException {
        checkNotNull(messageByteArray, MESSAGE_MUST_BE_READ_FIRST);
        readStationId()
                .readCommandCode()
                .readErrorCode()
                .readData()
                .readChecksum()
                .verifyResponse();
        return new Response(stationId, commandCode, checkSum, messageByteArray, payload, extractResult());
    }

    private RESULT_TYPE extractResult() {
        RESULT_TYPE result = null;
        if (resultFunction != null) {
            result = resultFunction.apply(this.payload);
            log.debug("Extracted result {}", result);
        }
        return result;
    }

    @ToString
    class ParsedValue {
        @Getter
        private final byte[] byteArrayData;
        @Getter
        private final String stringData;

        ParsedValue(final byte[] input, final int offset, final int length) {
            this.byteArrayData = Arrays.copyOfRange(input, offset, offset + length);
            stringData = new String(this.byteArrayData);
        }

        public int getIntData() {
            return Integer.parseInt(stringData, CommonConstants.RADIX_HEX);
        }
    }
}
