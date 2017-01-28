package cz.stjarna.fatek.command;

import cz.stjarna.fatek.enums.CommandEnum;
import cz.stjarna.fatek.enums.RunStopControlEnum;
import cz.stjarna.fatek.exception.FatekException;

import java.util.function.Function;

import static com.google.common.base.Preconditions.checkNotNull;

public class ControlCommand extends AbstractCommand<Void> {

    private final RunStopControlEnum runStopControl;

	public ControlCommand(final RunStopControlEnum runStopControl) throws FatekException {
        checkNotNull(runStopControl, "RunStopControl code cannot be null");
		this.runStopControl = runStopControl;
	}

	@Override
	public CommandEnum getCommandCode() {
		return CommandEnum.CONTROL;
	}

	@Override
	public String getRequestData() {
		return String.valueOf(runStopControl.getValue());
	}

	@Override
	public Function<byte[], Void> getResultFunction() {
		return (byte[] payload) -> null;
	}
}
