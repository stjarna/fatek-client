package cz.stjarna.fatek.connectivity;

import lombok.Getter;

import static com.google.common.base.Preconditions.checkNotNull;

@Getter
public abstract class AbstractConnection implements IConnection {

    private final String host;
    private final int port;
	private final int stationId;

    protected AbstractConnection(final ConnectionParams connParams) {
        checkNotNull(connParams, "Connection parameters cannot be null");
    	host = connParams.getHost();
    	port = connParams.getPort();
    	stationId = connParams.getStationId();
    }

}
