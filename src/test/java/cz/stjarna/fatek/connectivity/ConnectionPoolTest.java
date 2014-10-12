package cz.stjarna.fatek.connectivity;

import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.*;

public class ConnectionPoolTest {

    @Test
    public void testDetailReadSystemStatusOverTCPConnection() throws Exception {
        ConnectionParams connectionParams = new ConnectionParams(new URI("tcp://192.168.1.120:500?slaveStationId=99&checkInterval=3000"));
        TCPConnectionPool connectionPool = new TCPConnectionPool(connectionParams);
        PooledTCPConnection conn1 = connectionPool.getConnection();
        assertNotNull(conn1);
        PooledTCPConnection conn2 = connectionPool.getConnection();
        assertNotNull(conn2);
        PooledTCPConnection conn3 = connectionPool.getConnection();
        assertNotNull(conn3);
        PooledTCPConnection conn4 = connectionPool.getConnection();
        assertNull(conn4);
        PooledTCPConnection conn5 = connectionPool.getConnection();
        assertNull(conn5);
        conn1.close();
        conn2.close();
        conn3.close();
        Thread.sleep(4000);
        PooledTCPConnection conn8 = connectionPool.getConnection();
        assertNotNull(conn8);
        PooledTCPConnection conn9 = connectionPool.getConnection();
        assertNotNull(conn9);
        PooledTCPConnection conn10 = connectionPool.getConnection();
        assertNotNull(conn10);
        conn8.close();
        conn9.close();
        conn10.close();
        try {
            conn10.close();
        } catch (IllegalStateException ise) {
            assertTrue("Connection cannot be closed twice", true);
        }
    }
}
