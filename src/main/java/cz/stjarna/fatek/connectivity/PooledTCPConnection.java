package cz.stjarna.fatek.connectivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicBoolean;

public class PooledTCPConnection implements IConnection {

    private final TCPConnectionPool connectionPool;
    private final TCPConnection connection;
    private final AtomicBoolean returnedToPool = new AtomicBoolean(false);

    public PooledTCPConnection(final TCPConnectionPool connectionPool, final TCPConnection connection) throws IOException {
        this.connection = connection;
        this.connectionPool = connectionPool;
    }

    @Override
    public int getStationId() {
        return connection.getStationId();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return connection.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return connection.getOutputStream();
    }

    @Override
    public boolean isConnected() {
        return connection.isConnected();
    }

    @Override
    public void close() throws IOException {
        if (returnedToPool.compareAndSet(false, true)) {
            this.connectionPool.returnObject(connection);
        } else {
            throw new IllegalStateException("Connection has been already returned");
        }
    }
}
