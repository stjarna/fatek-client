package cz.stjarna.fatek.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommandEnum {
    GIST_READ_SYSTEM_STATUS(0x40),
    CONTROL(0x41),
    SINGLE_DISCRETE_CONTROL(0x42),
    READ_DISCRETE_REGISTER_STATUS(0x43),
	READ_FROM_DISCRETE_REGISTER(0x44),
    WRITE_TO_DISCRETE_REGISTER(0x45),
    READ_FROM_CONTINUOUS_REGISTER(0x46),
    WRITE_TO_CONTINUOUS_REGISTER(0x47),
    MIXED_READ(0x48),
    MIXED_WRITE(0x49),
    TESTING_LOOP_BACK(0x4E),
    DETAIL_READ_SYSTEM_STATUS(0x53);

	private int code;

}
