package com.snobot.test.utilities;

import org.junit.After;
import org.junit.Before;

import com.snobot.simulator.DefaultDataAccessorFactory;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.simulator.wrapper_accessors.SimulatorDataAccessor.SnobotLogLevel;

import edu.wpi.first.wpilibj.RobotBase;

public class BaseSimulatorTest
{
    private static boolean INITIALIZED = false;

    @Before
    public void setup()
    {
        if (!INITIALIZED)
        {
            DefaultDataAccessorFactory.initalize();
            DataAccessorFactory.getInstance().getSimulatorDataAccessor().setLogLevel(SnobotLogLevel.DEBUG);

            INITIALIZED = true;
        }

        DataAccessorFactory.getInstance().getSimulatorDataAccessor().reset();
        RobotBase.initializeHardwareConfiguration();
    }

    @After
    public void cleanup()
    {

    }
}
