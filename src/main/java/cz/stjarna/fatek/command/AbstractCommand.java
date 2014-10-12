package cz.stjarna.fatek.command;

import cz.stjarna.fatek.command.builder.CommandBuilder;
import cz.stjarna.fatek.command.builder.CommandResponseBuilder;
import cz.stjarna.fatek.command.response.Response;
import cz.stjarna.fatek.connectivity.IConnection;
import cz.stjarna.fatek.enums.CommandEnum;
import cz.stjarna.fatek.exception.FatekException;
import cz.stjarna.fatek.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

import static com.google.common.base.Preconditions.checkNotNull;
import static cz.stjarna.fatek.util.CommonConstants.END_OF_TEXT;
import static cz.stjarna.fatek.util.CommonConstants.START_OF_TEXT;

@Slf4j
public abstract class AbstractCommand<RESULT_TYPE> implements ICommand<RESULT_TYPE> {

    public abstract CommandEnum getCommandCode();
    public abstract String getRequestData();
    public abstract RESULT_TYPE getResponseData(Response response) throws IOException;

    @Override
    public RESULT_TYPE execute(final IConnection connection) throws FatekException {
        checkNotNull(connection, "Connection cannot be null");
        log.debug("Going to send command");
        sendData(connection);
        log.info("Command has been sent");
        log.debug("Waiting for response");
        final Response response = new CommandResponseBuilder(connection.getStationId(), getCommandCode(), receiveData(connection)).build();
        log.info("Received message {} ", CommonUtils.convertByteArrayToHumanReadableString(response.getMessageByteArray()));
        log.debug("Going to process response payload");

        RESULT_TYPE result;
        try {
            result = getResponseData(response);
        } catch (IOException e) {
            throw new FatekException("Error while reading data from response", e);
        }

        return result;
    }

    private void sendData(final IConnection connection) throws FatekException {
        try {
            final byte[] data = new CommandBuilder(connection.getStationId())
                    .commandCode(getCommandCode())
                    .data(getRequestData())
                    .build();
            log.info("Sending message {}", CommonUtils.convertByteArrayToHumanReadableString(data));
            writeToOutputStreamAndFlush(connection, data);
        } catch (UnsupportedEncodingException e) {
            throw new FatekException(String.format("Data cannot be converted to byte array"), e);
        } catch (IOException e) {
            throw new FatekException("Unable to execute command", e);
        }
    }

    private byte[] receiveData(final IConnection connection) throws FatekException {
        try {
            final InputStream inputStream = connection.getInputStream();
            int data = inputStream.read();

            if (START_OF_TEXT != data) {
                throw new FatekException("Response was not received");
            }

            final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            buffer.write(data);

            while ((data = inputStream.read()) != END_OF_TEXT) {
                if (data < 0) {
                    throw new FatekException("End of stream not expected");
                }
                buffer.write(data);
            }
            buffer.write(data);
            return buffer.toByteArray();
        } catch (IOException e) {
            throw new FatekException("Unable to read response", e);
        }
    }

    private void writeToOutputStreamAndFlush(final IConnection connection, final byte[] data) throws IOException {
        log.debug("Writing data to output stream");
        final OutputStream outputStream = connection.getOutputStream();
        outputStream.write(data);
        outputStream.flush();
    }
}