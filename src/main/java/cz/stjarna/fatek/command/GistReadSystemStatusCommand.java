package cz.stjarna.fatek.command;

import cz.stjarna.fatek.command.response.GistSystemStatus;
import cz.stjarna.fatek.enums.CommandEnum;

import java.util.function.Function;

public class GistReadSystemStatusCommand extends AbstractSystemStatusCommand<GistSystemStatus> {

    @Override
    public CommandEnum getCommandCode() {
        return CommandEnum.GIST_READ_SYSTEM_STATUS;
    }

    @Override
    public String getRequestData() {
        return null;
    }

    @Override
    public Function<byte[], GistSystemStatus> getResultFunction() {
        return (byte[] payload) -> new GistSystemStatus(readByteFromByteArray(payload, 0), readByteFromByteArray(payload, 2), readByteFromByteArray(payload, 4));
    }
}
