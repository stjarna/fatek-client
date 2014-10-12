package cz.stjarna.fatek.util;

import static com.google.common.base.Preconditions.checkNotNull;
import static cz.stjarna.fatek.util.CommonConstants.*;

public class CommonUtils {

    public static String convertByteArrayToHumanReadableString(final byte[] bytes) {
        checkNotNull(bytes, "Byte array cannot be null");
        final StringBuilder buffer = new StringBuilder();
        for (final byte b : bytes) {
            switch (b) {
                case START_OF_TEXT:
                    buffer.append(START_OF_TEXT_STRING + SEPARATOR);
                    break;
                case END_OF_TEXT:
                    buffer.append(SEPARATOR + END_OF_TEXT_STRING);
                    break;
                default:
                    buffer.append((char) b);
            }
        }
        return buffer.toString();
    }

    public static long convertBinaryStringToLong(final String binaryExpression) {
        checkNotNull(binaryExpression, "Value cannot be null");
        return Long.parseLong(binaryExpression, CommonConstants.RADIX_BIN);
    }
}
