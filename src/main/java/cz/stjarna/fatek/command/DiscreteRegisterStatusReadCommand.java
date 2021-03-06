package cz.stjarna.fatek.command;

import cz.stjarna.fatek.enums.CommandEnum;
import cz.stjarna.fatek.enums.RegisterLengthEnum;
import cz.stjarna.fatek.exception.FatekException;
import cz.stjarna.fatek.register.discrete.DiscreteRegister;

public class DiscreteRegisterStatusReadCommand extends AbstractReadCommand {

	public DiscreteRegisterStatusReadCommand(final DiscreteRegister offsetRegister, final int registersCount) throws FatekException {
        super(offsetRegister, registersCount);
	}

	@Override
	public CommandEnum getCommandCode() {
		return CommandEnum.READ_DISCRETE_REGISTER_STATUS;
	}

	@Override
	public String getRequestData() {
        return new StringBuilder()
                .append(String.format(RegisterLengthEnum.BYTE.getFormat(), registersCount == 256 ? 0 : registersCount & RegisterLengthEnum.BYTE.getMask()))
                .append(offsetRegister.toString()).toString();
	}

}
