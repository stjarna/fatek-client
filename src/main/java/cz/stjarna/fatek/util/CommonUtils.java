package cz.stjarna.fatek.util;

import cz.stjarna.fatek.enums.RegisterLengthEnum;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.google.common.base.Preconditions.checkNotNull;
import static cz.stjarna.fatek.util.CommonConstants.*;

public class CommonUtils {

    public static String convertByteArrayToHumanReadableString(final byte[] bytes) {
        checkNotNull(bytes, "Byte array cannot be null");
        final StringBuilder buffer = new StringBuilder();

        return bytesToIntStream(bytes).mapToObj(b -> {
            switch (b) {
                case START_OF_TEXT:
                    return START_OF_TEXT_STRING + SEPARATOR;
                case END_OF_TEXT:
                    return SEPARATOR + END_OF_TEXT_STRING;
                default:
                    return "" + (char) b;
            }
        }).collect(Collectors.joining(""));
    }

    public static long convertBinaryStringToLong(final String binaryExpression) {
        checkNotNull(binaryExpression, "Value cannot be null");
        return Long.parseLong(binaryExpression, CommonConstants.RADIX_BIN);
    }

    public static IntStream bytesToIntStream(byte[] bytes) {
        return IntStream.range(0, bytes.length)
                .map(i -> bytes[i] & RegisterLengthEnum.BYTE.getMask());
    }
}
