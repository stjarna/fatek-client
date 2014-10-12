package cz.stjarna.fatek.command;

import cz.stjarna.fatek.command.response.Response;
import cz.stjarna.fatek.enums.CommandEnum;
import cz.stjarna.fatek.enums.RegisterLengthEnum;
import cz.stjarna.fatek.exception.FatekException;
import cz.stjarna.fatek.register.AbstractRegister;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class ReadContinuousRegisterCommand extends AbstractCommand<List<Long>> {

	private final AbstractRegister offsetRegister;
	private final int registersCount;
	private List<Long> result;

	public ReadContinuousRegisterCommand(final AbstractRegister offsetRegister, final int registersCount) throws FatekException {
        checkNotNull(offsetRegister, "Offset register cannot be null");
		this.offsetRegister = offsetRegister;
		this.registersCount = registersCount;
	}

	@Override
	public CommandEnum getCommandCode() {
		return CommandEnum.READ_FROM_CONTINUOUS_REGISTER;
	}

	@Override
	public String getRequestData() {
        return new StringBuilder()
                .append(String.format(RegisterLengthEnum.BYTE.getFormat(), registersCount == 256 ? 0 : registersCount & RegisterLengthEnum.BYTE.getMask()))
                .append(offsetRegister.toString()).toString();
	}

    @Override
	public List<Long> getResponseData(final Response response) throws IOException {
        checkNotNull(response, "Response cannot be null");
        final byte[] payload = response.getPayload();
        result = new ArrayList<Long>(registersCount);

    	for (int i = 0; i < registersCount; i++) {
            result.add(offsetRegister.readFromUniformByteArray(payload, i));
		}

        return result;
	}

}
