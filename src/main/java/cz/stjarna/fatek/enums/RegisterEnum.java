package cz.stjarna.fatek.enums;

import cz.stjarna.fatek.register.IRegisterInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static cz.stjarna.fatek.enums.RegisterLengthEnum.NIBBLE;
import static cz.stjarna.fatek.enums.RegisterLengthEnum.WORD;
import static cz.stjarna.fatek.enums.RegisterLengthEnum.DWORD;

@AllArgsConstructor
@Getter
public enum RegisterEnum implements IRegisterInfo {
	// discrete ones
        X(4, NIBBLE),
        Y(4, NIBBLE),
        M(4, NIBBLE),
        S(4, NIBBLE),
        T(4, NIBBLE),
        C(4, NIBBLE),
        WX(4, WORD),
        WY(4, WORD),
        WM(4, WORD),
        WS(4, WORD),
        WT(4, WORD),
        WC(4, WORD),
        DWX(4, DWORD),
        DWY(4, DWORD),
        DWM(4, DWORD),
        DWS(4, DWORD),
        DWT(4, DWORD),
        DWC(4, DWORD),
    // data ones
        RT(4, WORD),
        RC(4, WORD),
        R(5, WORD),
        D(5, WORD),
        DRT(4, DWORD),
        DRC(4, DWORD),
        DR(5, DWORD),
        DD(5, DWORD);

	private final int maxNumericAddressLength;

    private final RegisterLengthEnum registerLength;

    @Override
    public String getName() {
        return name();
    }

	@Override
    public String toString() {
        return name();
    }

}
