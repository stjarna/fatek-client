package cz.stjarna.fatek.command;

import cz.stjarna.fatek.enums.CommandEnum;
import cz.stjarna.fatek.enums.RunningCodeControlEnum;
import cz.stjarna.fatek.exception.FatekException;
import cz.stjarna.fatek.register.discrete.DiscreteRegister;

import java.util.function.Function;

import static com.google.common.base.Preconditions.checkNotNull;

public class SingleDiscreteControlCommand extends AbstractCommand<Void> {

    private final DiscreteRegister offsetRegister;
    private final RunningCodeControlEnum runningControl;

    public SingleDiscreteControlCommand(final DiscreteRegister offsetRegister, final RunningCodeControlEnum runningControl) throws FatekException {
        checkNotNull(offsetRegister, "Offset register cannot be null");
        checkNotNull(runningControl, "Running code control cannot be null");
        this.offsetRegister = offsetRegister;
        this.runningControl = runningControl;
    }

    @Override
    public CommandEnum getCommandCode() {
        return CommandEnum.SINGLE_DISCRETE_CONTROL;
    }

    @Override
    public String getRequestData() {
        return new StringBuilder()
                .append(runningControl.getValue())
                .append(offsetRegister.toString()).toString();
    }

    @Override
    public Function<byte[], Void> getResultFunction() {
        return (byte[] payload) -> null;
    }
}
