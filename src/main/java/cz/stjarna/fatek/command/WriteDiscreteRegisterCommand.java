package cz.stjarna.fatek.command;

import cz.stjarna.fatek.enums.CommandEnum;
import cz.stjarna.fatek.enums.RegisterLengthEnum;
import cz.stjarna.fatek.exception.FatekException;
import cz.stjarna.fatek.register.discrete.DiscreteRegister;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class WriteDiscreteRegisterCommand extends AbstractWriteCommand {

	private final DiscreteRegister offsetRegister;
	private final int registersCount;
    private final List<Long> statusList;

	public WriteDiscreteRegisterCommand(final DiscreteRegister offsetRegister, final int registersCount, final List<Long> statusList) throws FatekException {
        checkNotNull(offsetRegister, "Offser register cannot be null");
        checkNotNull(statusList, "Status list cannot be null");
		this.offsetRegister = offsetRegister;
		this.registersCount = registersCount;
        this.statusList = statusList;
	}

	@Override
	public CommandEnum getCommandCode() {
		return CommandEnum.WRITE_TO_DISCRETE_REGISTER;
	}

	@Override
	public String getRequestData() {
        final StringBuilder builder = new StringBuilder()
                .append(String.format(RegisterLengthEnum.BYTE.getFormat(), registersCount == 256 ? 0 : registersCount & RegisterLengthEnum.BYTE.getMask()))
                .append(offsetRegister.toString());

        for (final Long value : statusList) {
            builder.append(value > 0 ? 1 : 0);
        }

        return builder.toString();
	}
}
