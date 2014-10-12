package cz.stjarna.fatek.command;

import cz.stjarna.fatek.command.response.Response;

public abstract class AbstractWriteCommand extends AbstractCommand<Void> {

    @Override
    public Void getResponseData(final Response response) {
        return null;
    }
}
