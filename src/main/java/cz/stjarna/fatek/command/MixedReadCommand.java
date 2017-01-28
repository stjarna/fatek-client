package cz.stjarna.fatek.command;

import com.google.common.base.Joiner;
import cz.stjarna.fatek.enums.CommandEnum;
import cz.stjarna.fatek.enums.RegisterLengthEnum;
import cz.stjarna.fatek.exception.FatekException;
import cz.stjarna.fatek.register.AbstractRegister;
import cz.stjarna.fatek.util.CommonConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class MixedReadCommand extends AbstractCommand<List<Long>> {

	private final AbstractRegister[] registers;

	public MixedReadCommand(final AbstractRegister... registers) throws FatekException {
        checkNotNull(registers, "Register array cannot be null");
        checkArgument(registers.length <= 64, "Total count of registers is 64 at maximum");
		this.registers = registers;
	}

	@Override
	public CommandEnum getCommandCode() {
		return CommandEnum.MIXED_READ;
	}

	@Override
	public String getRequestData() {
        return new StringBuilder()
                    .append(String.format(RegisterLengthEnum.BYTE.getFormat(), registers.length))
                    .append(Joiner.on("").join(registers)).toString();
	}

    public Function<byte[],List<Long>> getResultFunction() {
	    return (byte[] payload) -> {
            final List<Long> result = new ArrayList<Long>(registers.length);
            int offset = 0;
            RegisterLengthEnum registerLength;
            for (int i = 0; i < registers.length; i++) {
                registerLength = registers[i].getRegisterLength();
                result.add(readFromMixedByteArray(payload, offset, registers[i].getRegisterLength().getNumberOfNibbles()));
                offset += registerLength.getNumberOfNibbles();
            }
            return result;
        };
    }

    private long readFromMixedByteArray(final byte[] byteArray, final int index, final int bufferLength) {
        checkNotNull(byteArray, "Byte array cannot be null");
        checkArgument(byteArray.length >= index+bufferLength, "Invalid index, data cannot be read due to being out of range");
        final int from = index;
        final int to = from + bufferLength;
        final byte[] buffer = Arrays.copyOfRange(byteArray, from, to);
        return Long.parseLong(new String(buffer), CommonConstants.RADIX_HEX);
    }
}
