package cz.stjarna.fatek.connectivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static com.google.common.base.Preconditions.checkNotNull;

public class TCPConnection extends AbstractConnection {

	private final Socket socket;
	 
	public TCPConnection(final ConnectionParams connParams) throws IOException {
        super(connParams);
        checkNotNull(connParams, "Connection parameters cannot be null");
        this.socket = new Socket(getHost(), getPort());
    }

    @Override
	public InputStream getInputStream() throws IOException {
		return socket.getInputStream();
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		return socket.getOutputStream();
	}

	@Override
	public boolean isConnected() {
		return !socket.isClosed() && socket.isConnected();
	}

	@Override
	public void close() throws IOException {
		socket.close();
	}

}
