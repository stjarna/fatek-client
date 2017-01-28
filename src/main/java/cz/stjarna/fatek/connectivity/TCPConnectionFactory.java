package cz.stjarna.fatek.connectivity;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

public class TCPConnectionFactory implements IConnectionFactory {

    private final ConnectionParams connectionParams;

    public TCPConnectionFactory(final ConnectionParams connectionParams) throws IOException {
        checkNotNull(connectionParams, "Connection parameters cannot be null");
        this.connectionParams = connectionParams;
    }

	@Override
	public TCPConnection createConnection() throws IOException {
		return new TCPConnection(connectionParams);
	}

}
