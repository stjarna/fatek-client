package cz.stjarna.fatek.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RunStopControlEnum {
    RUN((byte)1),
    STOP((byte)0);

	private byte value;
}
