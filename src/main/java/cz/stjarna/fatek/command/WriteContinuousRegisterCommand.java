package cz.stjarna.fatek.command;

import cz.stjarna.fatek.enums.CommandEnum;
import cz.stjarna.fatek.enums.RegisterLengthEnum;
import cz.stjarna.fatek.exception.FatekException;
import cz.stjarna.fatek.exception.FatekRuntimeException;
import cz.stjarna.fatek.register.AbstractRegister;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class WriteContinuousRegisterCommand extends AbstractWriteCommand {

	private final AbstractRegister offsetRegister;
	private final int registersCount;
    private final List<Long> valueList;

	public WriteContinuousRegisterCommand(final AbstractRegister offsetRegister, final int registersCount, final List<Long> valueList) throws FatekException {
        checkNotNull(offsetRegister, "Offset register cannot be null");
        checkNotNull(valueList, "Value list cannot be null");
		this.offsetRegister = offsetRegister;
		this.registersCount = registersCount;
        this.valueList = valueList;
	}

	@Override
	public CommandEnum getCommandCode() {
		return CommandEnum.WRITE_TO_CONTINUOUS_REGISTER;
	}

	@Override
	public String getRequestData() {
        final StringBuilder builder = new StringBuilder()
                .append(String.format(RegisterLengthEnum.BYTE.getFormat(), registersCount == 256 ? 0 : registersCount & RegisterLengthEnum.BYTE.getMask()))
                .append(offsetRegister.toString());

        for (final Long value : valueList) {
            builder.append(convertLongToFormattedStringBasedOnRegisterLength(value, offsetRegister.getRegisterLength()));
        }

        return builder.toString();
	}

    private String convertLongToFormattedStringBasedOnRegisterLength(final long value, final RegisterLengthEnum registerLength) {
        checkNotNull(offsetRegister, "Register length cannot be null");
        String format = null;
        switch (registerLength) {
            case NIBBLE: format = RegisterLengthEnum.NIBBLE.getFormat();
                break;
            case BYTE: format = RegisterLengthEnum.BYTE.getFormat();
                break;
            case WORD: format = RegisterLengthEnum.WORD.getFormat();
                break;
            case DWORD: format = RegisterLengthEnum.DWORD.getFormat();
                break;
            default: throw new FatekRuntimeException("Unsupported register length");
        }

        return String.format(format, value);
    }

}
