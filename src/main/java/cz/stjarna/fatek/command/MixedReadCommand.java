package cz.stjarna.fatek.command;

import com.google.common.base.Joiner;
import cz.stjarna.fatek.command.response.Response;
import cz.stjarna.fatek.enums.CommandEnum;
import cz.stjarna.fatek.enums.RegisterLengthEnum;
import cz.stjarna.fatek.exception.FatekException;
import cz.stjarna.fatek.register.AbstractRegister;

import java.util.ArrayList;
import java.util.List;

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

    @Override
	public List<Long> getResponseData(final Response response) {
        checkNotNull(response, "Response cannot be null");
        final byte[] payload = response.getPayload();
        final List<Long> result = new ArrayList<Long>(registers.length);
        int offset = 0;
        RegisterLengthEnum registerLength;
        for (int i = 0; i < registers.length; i++) {
            registerLength = registers[i].getRegisterLength();
            result.add(registers[i].readFromMixedByteArray(payload, offset));
            offset += registerLength.getNumberOfNibbles();
		}
        return result;
	}
}
