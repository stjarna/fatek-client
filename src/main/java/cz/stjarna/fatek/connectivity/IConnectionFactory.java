package cz.stjarna.fatek.connectivity;

import java.io.IOException;

public interface IConnectionFactory {
	
	IConnection createConnection() throws IOException;

}
