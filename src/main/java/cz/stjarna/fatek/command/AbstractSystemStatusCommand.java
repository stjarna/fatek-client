package cz.stjarna.fatek.command;

import cz.stjarna.fatek.enums.RegisterLengthEnum;
import cz.stjarna.fatek.util.CommonConstants;

import java.util.Arrays;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public abstract class AbstractSystemStatusCommand<TYPE> extends AbstractCommand<TYPE> {

    public AbstractSystemStatusCommand() {
        super();
    }

    protected int readByteFromByteArray(final byte[] byteArray, final int offset) {
        checkNotNull(byteArray, "Byte array cannot be null");
        checkArgument(byteArray.length >= offset + 2, "Invalid offset " + offset + ", byte cannot be read due to being out of range.");
        checkArgument(offset >= 0, "Offset cannot be negative");
        final byte[] buffer = Arrays.copyOfRange(byteArray, offset, offset + 2);
        return Integer.parseInt(new String(buffer), CommonConstants.RADIX_HEX) & RegisterLengthEnum.BYTE.getMask();
    }
}
