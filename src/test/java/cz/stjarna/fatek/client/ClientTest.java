package cz.stjarna.fatek.client;

import com.google.common.collect.Lists;
import cz.stjarna.fatek.FatekClient;
import cz.stjarna.fatek.command.*;
import cz.stjarna.fatek.command.response.DetailedSystemStatus;
import cz.stjarna.fatek.command.response.GistSystemStatus;
import cz.stjarna.fatek.enums.RunStopControlEnum;
import cz.stjarna.fatek.enums.RunningCodeControlEnum;
import cz.stjarna.fatek.register.data.DRRegister;
import cz.stjarna.fatek.register.data.RRegister;
import cz.stjarna.fatek.register.discrete.DWYRegister;
import cz.stjarna.fatek.register.discrete.WMRegister;
import cz.stjarna.fatek.register.discrete.XRegister;
import cz.stjarna.fatek.register.discrete.YRegister;
import cz.stjarna.fatek.util.CommonConstants;
import org.junit.*;

import java.util.List;

import static cz.stjarna.fatek.util.CommonUtils.convertBinaryStringToLong;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class ClientTest {

    private static FatekClient client;

    @BeforeClass
    public static void tearUpGlobal() throws Exception {
        client = new FatekClient("tcp://192.168.1.120:500?slaveStationId=99");
    }

    @Before
    public void tearUp() throws Exception {
        client.connect();
    }

    @After
    public void tearDown() throws Exception {
        client.disconnect();
    }

    @Test
    public void testDiscreteRead() throws Exception {
        List<Long> results = client.executeCommand(new DiscreteRegisterReadCommand(new YRegister(8), 10));
        for (Long result : results) {
            System.out.println(result);
        }
    }

    @Test
    public void testDiscreteWrite() throws Exception {
        client.executeCommand(new WriteDiscreteRegisterCommand(new YRegister(9), 4, Lists.newArrayList(1L, 1L, 1L, 1L)));
    }

    @Test
    public void testGistReadSystemStatusAndControl() throws Exception {
        client.executeCommand(new ControlCommand(RunStopControlEnum.STOP));

        GistSystemStatus status = client.executeCommand(new GistReadSystemStatusCommand());
        assertEquals("Fatek device is in STOP mode", false, status.isRun());

        client.executeCommand(new ControlCommand(RunStopControlEnum.RUN));

        status = client.executeCommand(new GistReadSystemStatusCommand());
        assertEquals("Fatek device is in RUN mode", true, status.isRun());
    }

    @Test
    public void testSingleDiscreteControl() throws Exception {
        client.executeCommand(new SingleDiscreteControlCommand(new XRegister(8), RunningCodeControlEnum.RESET));
    }

    @Test
    public void testReadDiscreteRegisterStatus() throws Exception {
        for (int i = 0; i < 3; i++) {
            client.executeCommand(new SingleDiscreteControlCommand(new XRegister(i), RunningCodeControlEnum.DISABLE));
        }

        List<Long> results =  client.executeCommand(new DiscreteRegisterStatusReadCommand(new XRegister(0), 3));
        assertEquals("Result list size does not match", 3, results.size());
        for (Long result : results) {
            assertEquals("Result must be TRUE", 1l, result.longValue());
        }

        for (int i = 0; i < 3; i++) {
            client.executeCommand(new SingleDiscreteControlCommand(new XRegister(i), RunningCodeControlEnum.ENABLE));
        }

        results = client.executeCommand(new DiscreteRegisterStatusReadCommand(new XRegister(0), 3));
        assertEquals("Result list size does not match", 3, results.size());
        for (Long result : results) {
            assertNotSame("Result must be FALSE", 1l, result.longValue());
        }
    }

	@Test
	public void testContinuousReadRRegister() throws Exception {
        List<Long> result = client.executeCommand(new ReadContinuousRegisterCommand(new RRegister(0), 11));
        for (Long number : result) {
            System.out.println(number);
        }
	}

    @Test
    public void testContinousWriteOver() throws Exception {
        client.executeCommand(new WriteContinuousRegisterCommand(new DWYRegister(0), 1, Lists.newArrayList(convertBinaryStringToLong("11111111111111111111111111111111"))));
    }

	@Test
	public void testContinuousReadDWYRegister() throws Exception {
        List<Long> result = client.executeCommand(new ReadContinuousRegisterCommand(new DWYRegister(0), 1));
        for (Long number : result) {
            System.out.println(number);
        }
	}

    @Test
    public void testMixedReadAndWrite() throws Exception {
        client.executeCommand(new MixedWriteCommand(Lists.newArrayList(new YRegister(0), new YRegister(1), new WMRegister(8), new DRRegister(2)),
                Lists.newArrayList(convertBinaryStringToLong("0000000000000001"),
                        convertBinaryStringToLong("0000000000000000"),
                        convertBinaryStringToLong("101010101010101"),
                        convertBinaryStringToLong("0000000011111111"))));

        List<Long> result = client.executeCommand(new MixedReadCommand(new YRegister(0), new YRegister(1), new WMRegister(8), new DRRegister(2)));
        for (Long value : result) {
            System.out.println(value);
        }
    }

    @Test
    public void testLoopBack() throws Exception {
        String result = client.executeCommand(new TestingLoopBackCommand("ABCDEFGH"));
        System.out.println(result);
    }

    @Test
    public void testDetailReadSystemStatus() throws Exception {
        DetailedSystemStatus result = client.executeCommand(new DetailReadSystemStatusCommand());
        System.out.println(result);
    }
}
