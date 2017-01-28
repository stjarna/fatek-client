package cz.stjarna.fatek.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum RunningCodeControlEnum {
    DISABLE((byte)1),
    ENABLE((byte)2),
    SET((byte)3),
    RESET((byte)4);

    @Getter
	private byte value;
}
