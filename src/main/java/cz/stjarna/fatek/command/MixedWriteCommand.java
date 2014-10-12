package cz.stjarna.fatek.command;

import cz.stjarna.fatek.command.response.Response;
import cz.stjarna.fatek.enums.CommandEnum;
import cz.stjarna.fatek.enums.RegisterLengthEnum;
import cz.stjarna.fatek.exception.FatekException;
import cz.stjarna.fatek.register.AbstractRegister;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class MixedWriteCommand extends AbstractCommand<Void> {

    private final List<AbstractRegister> registers;
    private final List<Long> values;

    public MixedWriteCommand(final List<AbstractRegister> registers, final List<Long> values) throws FatekException {
        checkNotNull(registers, "Register list cannot be null");
        checkNotNull(values, "Value list cannot be null");
        this.registers = registers;
        this.values = values;
    }

    @Override
    public CommandEnum getCommandCode() {
        return CommandEnum.MIXED_WRITE;
    }

    @Override
    public String getRequestData(){
        final StringBuilder builder = new StringBuilder()
                .append(String.format(RegisterLengthEnum.BYTE.getFormat(), registers.size() == 256 ? 0 : registers.size() & RegisterLengthEnum.BYTE.getMask()));

        for (int i = 0; i < registers.size(); i++) {
            builder.append(registers.get(i).toString())
                    .append(registers.get(i).write(values.get(i)));
        }

        return builder.toString();
    }

    @Override
    public Void getResponseData(final Response response) {
        return null;
    }
}
