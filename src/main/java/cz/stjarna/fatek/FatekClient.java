package cz.stjarna.fatek;

import cz.stjarna.fatek.command.ICommand;
import cz.stjarna.fatek.connectivity.ConnectionParams;
import cz.stjarna.fatek.connectivity.IConnection;
import cz.stjarna.fatek.connectivity.IConnectionFactory;
import cz.stjarna.fatek.connectivity.TCPConnectionFactory;
import cz.stjarna.fatek.enums.ProtocolEnum;
import cz.stjarna.fatek.exception.FatekException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.google.common.base.Preconditions.*;

@Slf4j
public class FatekClient {

    protected IConnection connection;
    protected ConnectionParams connectionParams;

    public FatekClient(final String uri) throws URISyntaxException, FatekException {
        this(new URI(uri));
        checkNotNull(uri, "Url cannot be null");
    }

    public FatekClient(final URI uri) throws FatekException {
        checkNotNull(uri, "Url cannot be null");
        init(uri);
    }

    private void init(final URI uri) throws FatekException {
        try {
            connectionParams = new ConnectionParams(uri);
        } catch (FatekException e) {
            throw new FatekException("URL could not be properly processed", e);
        }
    }

    private IConnectionFactory resolveConnectionFactory(final ProtocolEnum protocol) throws IOException {
        checkNotNull(protocol, "Protocol cannot be null");
        checkArgument(ProtocolEnum.TCP == protocol, "The only supported protocol is TCP");
        return new TCPConnectionFactory(connectionParams);
    }

    public void connect() throws IOException {
        connection = resolveConnectionFactory(connectionParams.getProtocol()).createConnection();
    }

    public void disconnect() throws FatekException {
        if (connection != null && connection.isConnected()) {
            try {
                connection.close();
            } catch (IOException e) {
                throw new FatekException("Unable to disconnect the connection", e);
            }
        }
    }

    public <RESULT_TYPE> RESULT_TYPE executeCommand(final ICommand<RESULT_TYPE> command) throws FatekException {
        checkNotNull(command, "Command cannot be null");
        checkState(connection != null, "There must be an active connection. Connect to Fatek device first.");
        return command.execute(connection);
    }
}
