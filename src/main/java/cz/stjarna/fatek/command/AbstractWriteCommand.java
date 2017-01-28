package cz.stjarna.fatek.command;

import java.util.function.Function;

public abstract class AbstractWriteCommand extends AbstractCommand<Void> {

    @Override
    public Function<byte[], Void> getResultFunction() {
        return (byte[] payload) -> null;
    }
}
