package cz.stjarna.fatek.register;

import cz.stjarna.fatek.enums.RegisterLengthEnum;

public interface IRegisterInfo {

    String getName();
    int getMaxNumericAddressLength();
    RegisterLengthEnum getRegisterLength();

}
