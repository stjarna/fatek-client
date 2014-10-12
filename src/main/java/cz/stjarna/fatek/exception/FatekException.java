package cz.stjarna.fatek.exception;

import static com.google.common.base.Preconditions.checkNotNull;

public class FatekException extends Exception {
	
    public FatekException(final String message) {
        super(message);
        checkNotNull(message, "Message cannot be null");
    }

    public FatekException(final String message, final Object... args) {
        super(String.format(message, args));
        checkNotNull(message, "Message cannot be null");
    }

    public FatekException(final String message, final Throwable throwable) {
        super(message, throwable);
        checkNotNull(message, "Message cannot be null");
        checkNotNull(throwable, "Throwable instance cannot be null");
    }
	
    public FatekException(final Exception e) {
        super(e);
        checkNotNull(e, "Exception instance cannot be null");
    }

    public FatekException() {
        super();
    }

}
