package cz.stjarna.fatek.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommandEnum {
    GIST_READ_SYSTEM_STATUS((byte)0x40),
    CONTROL((byte)0x41),
    SINGLE_DISCRETE_CONTROL((byte)0x42),
    READ_DISCRETE_REGISTER_STATUS((byte)0x43),
	READ_FROM_DISCRETE_REGISTER((byte)0x44),
    WRITE_TO_DISCRETE_REGISTER((byte)0x45),
    READ_FROM_CONTINUOUS_REGISTER((byte)0x46),
    WRITE_TO_CONTINUOUS_REGISTER((byte)0x47),
    MIXED_READ((byte)0x48),
    MIXED_WRITE((byte)0x49),
    TESTING_LOOP_BACK((byte)0x4E),
    DETAIL_READ_SYSTEM_STATUS((byte)0x53);

	private byte code;
}
