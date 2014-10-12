package cz.stjarna.fatek.command;

import cz.stjarna.fatek.command.response.GistSystemStatus;
import cz.stjarna.fatek.command.response.Response;
import cz.stjarna.fatek.enums.CommandEnum;

import static com.google.common.base.Preconditions.checkNotNull;

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
	public GistSystemStatus getResponseData(final Response response) {
        checkNotNull(response, "Response cannot be null");
        return new GistSystemStatus(readByteFromByteArray(response.getPayload(), 0), readByteFromByteArray(response.getPayload(), 2), readByteFromByteArray(response.getPayload(), 4));
	}

}
