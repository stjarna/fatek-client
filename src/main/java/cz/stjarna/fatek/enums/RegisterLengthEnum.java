package cz.stjarna.fatek.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RegisterLengthEnum {

    NIBBLE(4, "%X", 0x0F),
	BYTE(8, "%02X", 0xFF),
	WORD(16, "%04X", 0xFFFF),
	DWORD(32, "%08X", 0xFFFFFFFF);

	private int length;

    private String format;

    private int mask;
	
    @Override
    public String toString() {
        return name();
    }

    public int getNumberOfNibbles() {
        return length / 4;
    }

}
