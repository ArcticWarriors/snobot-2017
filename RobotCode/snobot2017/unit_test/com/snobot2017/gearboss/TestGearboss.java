package com.snobot2017.gearboss;

import org.junit.Assert;
import org.junit.Test;

import com.snobot.lib.logging.ILogger;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;
import com.snobot2017.ModuleFactory;
import com.snobot2017.joystick.IOperatorJoystick;
import com.snobot2017.test.mocks.MockLogger;
import com.snobot2017.test.mocks.MockOperatorJoystick;
import com.snobot2017.test.utilities.BaseModuleTest;

public class TestGearboss extends BaseModuleTest
{

    @Test
    public void testSolenoid()
    {
        IOperatorJoystick opJoy = new MockOperatorJoystick();
        ILogger logger = new MockLogger();

        IGearBoss gearBoss = new ModuleFactory().createGearBoss(opJoy, logger);
        initializeModule(gearBoss);
        Assert.assertFalse(gearBoss.isGearUp());

        Assert.assertEquals("Solenoid 0", DataAccessorFactory.getInstance().getSolenoidAccessor().getName(0));

        gearBoss.moveGearHigh();
        runTeleopLoopNoControl(gearBoss);
        Assert.assertTrue(gearBoss.isGearUp());

        gearBoss.moveGearLow();
        runTeleopLoopNoControl(gearBoss);
        Assert.assertFalse(gearBoss.isGearUp());
    }
}
