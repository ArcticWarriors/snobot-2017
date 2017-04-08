package com.snobot2017.test.utilities;

import org.junit.After;
import org.junit.Before;

import com.snobot.simulator.DefaultDataAccessorFactory;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot.simulator.wrapper_accessors.SimulatorDataAccessor.SnobotLogLevel;

import edu.wpi.first.wpilibj.RobotBase;

public class BaseUnitTest
{
    protected static final double DOUBLE_EPSILON = .00001;
    private static boolean INITIALIZED = false;

    @Before
    public void setup()
    {
        if (!INITIALIZED)
        {
            DefaultDataAccessorFactory.initalize();
            RobotBase.initializeHardwareConfiguration();
            DataAccessorFactory.getInstance().getSimulatorDataAccessor().setLogLevel(SnobotLogLevel.DEBUG);

            INITIALIZED = true;
        }
        DataAccessorFactory.getInstance().getSimulatorDataAccessor().reset();
    }

    @After
    public void cleanup()
    {
        System.out.println("Starting to shutdown NetworkTables...");
        // NetworkTableInstance.getDefault().stopServer();
        System.out.println("Shutdown");
    }
}
