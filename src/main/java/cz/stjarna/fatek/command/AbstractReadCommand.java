package cz.stjarna.fatek.command;

import cz.stjarna.fatek.register.discrete.DiscreteRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class AbstractReadCommand extends AbstractCommand<List<Long>> {

    protected final DiscreteRegister offsetRegister;
    protected final int registersCount;

    public AbstractReadCommand(final DiscreteRegister offsetRegister, final int registersCount) {
        checkNotNull(offsetRegister, "Offset register cannot be null");
        this.offsetRegister = offsetRegister;
        this.registersCount = registersCount;
    }

    @Override
    public Function<byte[], List<Long>> getResultFunction() {
        return (byte[] payload) -> {
            List<Long> result = new ArrayList<>(registersCount);
            for (int i = 0; i < registersCount; i++) {
                result.add(readFromUniformByteArray(payload, i, offsetRegister.getRegisterLength().getNumberOfNibbles()));
            }
            return result;
        };
    }
}
