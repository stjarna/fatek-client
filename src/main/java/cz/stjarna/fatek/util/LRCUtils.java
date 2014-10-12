package cz.stjarna.fatek.util;

import static com.google.common.base.Preconditions.checkNotNull;

public class LRCUtils {

    /**
     * Computes longitudinal redundancy check (LRC) used when we send as well as receive messages to/from Fatek device.
     *
     * @param data data we compute LRC for
     * @return LRC for given data of a given length
     */
    public static int countLRC(final byte[] data) {
        checkNotNull(data, "Byte array cannot be null");
        return countLRC(data, data.length);
    }

    /**
     * Computes longitudinal redundancy check (LRC) used when we send as well as receive messages to/from Fatek device.
     *
     * @param data data we compute LRC for
     * @param length given length of data to loop over
     * @return LRC for given data of a given length
     */
    public static int countLRC(final byte[] data, final int length) {
        checkNotNull(data, "Byte array cannot be null");
        int lrc = 0;
        for (int i = 0; i < length; i++) {
            lrc += data[i] & 0xff;
            lrc &= 0xff;
        }
        return lrc;
    }
}
