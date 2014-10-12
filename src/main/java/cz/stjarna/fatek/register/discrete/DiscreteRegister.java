package cz.stjarna.fatek.register.discrete;

import cz.stjarna.fatek.register.AbstractRegister;
import cz.stjarna.fatek.register.IRegisterInfo;

import static com.google.common.base.Preconditions.checkNotNull;


public class DiscreteRegister extends AbstractRegister {

    protected DiscreteRegister(final IRegisterInfo registerInfo, final int address) {
        super(registerInfo, address);
        checkNotNull(registerInfo, "Register info cannot be null");
    }

    @Override
    public boolean isDiscrete() {
        return true;
    }
    
}
