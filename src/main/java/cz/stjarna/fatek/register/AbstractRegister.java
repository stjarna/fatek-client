package cz.stjarna.fatek.register;

import cz.stjarna.fatek.enums.RegisterLengthEnum;
import lombok.Getter;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class AbstractRegister {

    @Getter
	private final String name;
    @Getter
    private final int address;
    @Getter
    private final RegisterLengthEnum registerLength;
    @Getter
    private final int numericAddressLength;

    protected AbstractRegister(final IRegisterInfo registerInfo, final int address) {
        checkNotNull(registerInfo, "Register info cannot be null");
        this.name = registerInfo.getName();
        this.registerLength = registerInfo.getRegisterLength();
        this.numericAddressLength = registerInfo.getMaxNumericAddressLength();
        this.address = address;
    }

    @Override
    public String toString() {
        return String.format("%s%0" + numericAddressLength + "d", name, address);
    }
    
    public abstract boolean isDiscrete();

    public String write(final long value) {
        return String.format(registerLength.getFormat(), value & registerLength.getMask());
    }
}
