package cz.stjarna.fatek.register.data;

import cz.stjarna.fatek.register.AbstractRegister;
import cz.stjarna.fatek.register.IRegisterInfo;

import static com.google.common.base.Preconditions.checkNotNull;


public class DataRegister extends AbstractRegister {

    protected DataRegister(final IRegisterInfo registerInfo, final int address) {
        super(registerInfo, address);
        checkNotNull(registerInfo, "Register info cannot be null");
    }

    @Override
    public boolean isDiscrete() {
        return false;
    }
    
}
