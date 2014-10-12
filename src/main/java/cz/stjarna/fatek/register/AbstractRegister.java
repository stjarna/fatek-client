package cz.stjarna.fatek.register;

import cz.stjarna.fatek.enums.RegisterLengthEnum;
import cz.stjarna.fatek.util.CommonConstants;
import lombok.Getter;

import java.util.Arrays;

import static com.google.common.base.Preconditions.checkArgument;
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

    public long readFromMixedByteArray(final byte[] byteArray, final int index) {
        checkNotNull(byteArray, "Byte array cannot be null");
        final int bufferLength = registerLength.getNumberOfNibbles();
        checkArgument(byteArray.length >= index+bufferLength, "Invalid index, data cannot be read due to being out of range");
        final int from = index;
        final int to = from + bufferLength;
        final byte[] buffer = Arrays.copyOfRange(byteArray, from, to);
        return Long.parseLong(new String(buffer), CommonConstants.RADIX_HEX);
    }

    public long readFromUniformByteArray(final byte[] byteArray, final int index) {
        checkNotNull(byteArray, "Byte array cannot be null");
        final int bufferLength = registerLength.getNumberOfNibbles();
        checkArgument(byteArray.length >= index*bufferLength, "Invalid index, data cannot be read due to being out of range");
        final int from = index * bufferLength;
        final int to = from + bufferLength;
        final byte[] buffer = Arrays.copyOfRange(byteArray, from, to);
        return Long.parseLong(new String(buffer), CommonConstants.RADIX_HEX);
    }

    public String write(final long value) {
        return String.format(registerLength.getFormat(), value & registerLength.getMask());
    }
}
