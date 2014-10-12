package cz.stjarna.fatek.connectivity;

import cz.stjarna.fatek.enums.ProtocolEnum;
import cz.stjarna.fatek.exception.FatekException;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;


public class ConnectionParamsTest {

    @Test
    public void testProperlyEncodedParams() throws FatekException, URISyntaxException {
        ConnectionParams connParams = new ConnectionParams(new URI("tcp://192.168.1.120?slaveStationId=1&param1=abc&param2=a%20b"));
        assertEquals("Parameter slaveStationId does not match ", "1", connParams.getParam("slaveStationId"));
        assertEquals("Parameter param1 does not match", "abc", connParams.getParam("param1"));
        assertEquals("Parameter param2 does not match", "a b",  connParams.getParam("param2"));
    }


    @Test
    public void testGetProtocol() throws Exception {
        ConnectionParams connParams = new ConnectionParams(new URI("tcp://192.168.1.120?slaveStationId=1&param1=abc&param2=a%20b"));
        assertEquals("Protocol does not match", connParams.getProtocol(), ProtocolEnum.TCP);
    }

    @Test
    public void testGetHost() throws Exception {
        ConnectionParams connParams = new ConnectionParams(new URI("tcp://192.168.1.120?slaveStationId=1&param1=abc&param2=a%20b"));
        assertEquals("Host does not match", connParams.getHost(), "192.168.1.120");
    }

    @Test
    public void testGetDefaultPort() throws Exception {
        ConnectionParams connParams = new ConnectionParams(new URI("tcp://192.168.1.120?slaveStationId=1&param1=abc&param2=a%20b"));
        assertEquals("Default port does not match", connParams.getPort(), 500);
    }

    @Test
    public void testGetExplicitPort() throws Exception {
        ConnectionParams connParams = new ConnectionParams(new URI("tcp://192.168.1.120:555?slaveStationId=1&param1=abc&param2=a%20b"));
        assertEquals("Explicit port does not match", connParams.getPort(), 555);
    }

    @Test
    public void testGetStationId() throws Exception {
        ConnectionParams connParams = new ConnectionParams(new URI("tcp://192.168.1.120:555?slaveStationId=1&param1=abc&param2=a%20b"));
        assertEquals("Parameter slaveStationId does not match",connParams.getStationId(), 1);
    }
}