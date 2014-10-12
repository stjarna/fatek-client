package cz.stjarna.fatek;

import cz.stjarna.fatek.command.*;
import cz.stjarna.fatek.command.builder.CommandBuilderTest;
import cz.stjarna.fatek.command.builder.CommandResponseBuilderTest;
import cz.stjarna.fatek.connectivity.ConnectionParamsTest;
import junit.framework.TestCase;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ConnectionParamsTest.class,
                CommandBuilderTest.class,
                CommandResponseBuilderTest.class,
                ReadDiscreteRegisterCommandTest.class,
                WriteDiscreteRegisterCommandTest.class,
                GistReadSystemStatusCommandTest.class,
                ControlCommandTest.class,
                SingleDiscreteControlCommandTest.class,
                ReadDiscreteRegisterStatusCommandTest.class,
                ReadContinuousRegisterCommandTest.class,
                WriteContinuousRegisterCommandTest.class,
                MixedReadCommandTest.class,
                MixedWriteCommandTest.class,
                TestingLoopBackCommandTest.class
})
public class FatekDriverTestSuite extends TestCase {
}
