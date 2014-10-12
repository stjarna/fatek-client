package cz.stjarna.fatek.command;

import cz.stjarna.fatek.command.response.Response;
import cz.stjarna.fatek.register.discrete.DiscreteRegister;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class AbstractReadCommand extends AbstractCommand<List<Long>> {

    protected final DiscreteRegister offsetRegister;
    protected final int registersCount;
    protected List<Long> result;

    public AbstractReadCommand(final DiscreteRegister offsetRegister, final int registersCount) {
        checkNotNull(offsetRegister, "Offset register cannot be null");
        this.offsetRegister = offsetRegister;
        this.registersCount = registersCount;
    }

    @Override
	public List<Long> getResponseData(final Response response) {
        checkNotNull(response, "Response cannot be null");
        final byte[] payload = response.getPayload();
        result = new ArrayList<Long>(registersCount);

		for (int i = 0; i < registersCount; i++) {
			result.add(offsetRegister.readFromUniformByteArray(payload, i));
		}

        return result;
	}
}
