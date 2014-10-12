package cz.stjarna.fatek.connectivity;

import java.io.IOException;

public final class TCPConnectionPool extends AbstractObjectPool<TCPConnection> {

    public TCPConnectionPool(final ConnectionParams params) throws IOException {
        super(params);
    }

    public PooledTCPConnection getConnection() throws IOException {
        final TCPConnection connection = borrowObject();
        PooledTCPConnection pooledTCPConnection = null;
        if (connection != null) {
            pooledTCPConnection = new PooledTCPConnection(this, connection);
        }
        return pooledTCPConnection;
    }

    @Override
    protected TCPConnection createObject(final ConnectionParams connectionParams) throws IOException {
        return new TCPConnection(connectionParams);
    }

    @Override
    protected void destroyObject(final TCPConnection object) throws IOException {
        object.close();
    }
}
