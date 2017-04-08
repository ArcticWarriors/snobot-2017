package com.snobot2017.drivetrain;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.snobot.lib.logging.ILogger;
import com.snobot2017.ModuleFactory;
import com.snobot2017.joystick.IDriverJoystick;
import com.snobot2017.test.mocks.MockDriverJoystick;
import com.snobot2017.test.mocks.MockLogger;
import com.snobot2017.test.utilities.BaseModuleTest;

@RunWith(value = Parameterized.class)
public class TestDrivetrain extends BaseModuleTest
{
    private final boolean mUseCan;

    @Parameters()
    public static Collection<Boolean> data()
    {
        return Arrays.asList(false);
    }

    public TestDrivetrain(boolean aUseCan)
    {
        mUseCan = aUseCan;
    }

    @Test
    public void testDrivetrain()
    {
        IDriverJoystick driverJoy = new MockDriverJoystick();
        ILogger logger = new MockLogger();

        
        IDriveTrain drivetrain = new ModuleFactory().createDrivetrain(driverJoy, logger, mUseCan);
        initializeModule(drivetrain);

        drivetrain.setLeftRightSpeed(.5, -.75);
        runTeleopLoopNoControl(drivetrain);
        Assert.assertEquals(drivetrain.getLeftMotorSpeed(), .5, DOUBLE_EPSILON);
        Assert.assertEquals(drivetrain.getRightMotorSpeed(), -.75, DOUBLE_EPSILON);

        drivetrain.stop();
        runTeleopLoopNoControl(drivetrain);
        Assert.assertEquals(drivetrain.getLeftMotorSpeed(), 0, DOUBLE_EPSILON);
        Assert.assertEquals(drivetrain.getRightMotorSpeed(), 0, DOUBLE_EPSILON);
    }
}
