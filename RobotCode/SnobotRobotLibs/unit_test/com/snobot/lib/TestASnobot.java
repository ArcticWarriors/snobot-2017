package com.snobot.lib;

import org.junit.Test;

import com.snobot.lib.modules.IControllableModule;
import com.snobot.lib.modules.ILoggableModule;
import com.snobot.lib.modules.ISmartDashboardUpdaterModule;
import com.snobot.lib.modules.IUpdateableModule;
import com.snobot2017.test.utilities.BaseTest;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class TestASnobot extends BaseTest
{

    private class TestLoggableModule implements ILoggableModule
    {

        @Override
        public void initializeLogHeaders()
        {

        }

        @Override
        public void updateLog()
        {

        }

    }

    private class TestSDModule implements ISmartDashboardUpdaterModule
    {

        @Override
        public void updateSmartDashboard()
        {

        }

    }

    private class TestControllable implements IControllableModule
    {

        @Override
        public void control()
        {

        }

        @Override
        public void stop()
        {

        }
    }

    private class TestUpdatable implements IUpdateableModule
    {

        @Override
        public void update()
        {

        }
    }
    
    private class TestSnobot extends ASnobot
    {
        public TestSnobot()
        {
            TestLoggableModule loggable = new TestLoggableModule();
            addModule(loggable);

            TestSDModule sd = new TestSDModule();
            addModule(sd);

            TestControllable control = new TestControllable();
            addModule(control);

            TestUpdatable updatable = new TestUpdatable();
            addModule(updatable);
        }

        @Override
        protected CommandGroup createAutonomousCommand()
        {
            return null;
        }

    }

    @Test
    public void testASnobot()
    {
        TestSnobot snobot = new TestSnobot();

        snobot.initializeLogHeaders();
        snobot.disabledInit();
        snobot.disabledPeriodic();
        snobot.autonomousInit();
        snobot.autonomousPeriodic();
        snobot.teleopInit();
        snobot.teleopPeriodic();
        snobot.testInit();
        snobot.testPeriodic();
        snobot.stop();
        snobot.getLogger();
    }

}
