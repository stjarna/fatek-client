package cz.stjarna.fatek.command.builder;

import cz.stjarna.fatek.exception.FatekException;

public interface IBuilder<RESULT_TYPE> {

    RESULT_TYPE build() throws FatekException;
    
}
