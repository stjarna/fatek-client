package cz.stjarna.fatek.exception;

import lombok.Getter;

public class FatekErrorCodeException extends FatekException {

    @Getter
    private final int errorCode;

    public FatekErrorCodeException(final String message, final int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

}
