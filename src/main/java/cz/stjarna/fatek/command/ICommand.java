package cz.stjarna.fatek.command;

import cz.stjarna.fatek.connectivity.IConnection;
import cz.stjarna.fatek.exception.FatekException;

public interface ICommand<RESULT_TYPE> {

    RESULT_TYPE execute(IConnection connection) throws FatekException;
}
