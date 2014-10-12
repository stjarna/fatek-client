package cz.stjarna.fatek.connectivity;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

public class TCPConnectionFactory implements IConnectionFactory {

    private final TCPConnectionPool connectionPool;

    public TCPConnectionFactory(final ConnectionParams connParams) throws IOException {
        checkNotNull(connParams, "Connection parameters cannot be null");
        connectionPool = new TCPConnectionPool(connParams);
    }

	@Override
	public PooledTCPConnection createConnection() throws IOException {
		return new PooledTCPConnection(connectionPool, connectionPool.borrowObject());
	}

}
