package com.snobot2017.test.utilities;

import com.snobot.lib.modules.IUpdateableModule;
import com.snobot.simulator.wrapper_accessors.DataAccessorFactory;

import edu.wpi.first.wpilibj.command.Scheduler;

public class BaseAutonModeTest extends BaseUnitTest
{
    protected void runCommand(Object aModule, double aTimeout)
    {
        runCommand(aModule, aTimeout, .02);
    }

    protected void runCommand(Object aModule, double aTimeout, double aUpdatePeriod)
    {
        double update_frequency = 1 / aUpdatePeriod;

        for (int i = 0; i < update_frequency * aTimeout; ++i)
        {
            Scheduler.getInstance().run();
            DataAccessorFactory.getInstance().getSimulatorDataAccessor().updateSimulatorComponents(aUpdatePeriod);
            DataAccessorFactory.getInstance().getSimulatorDataAccessor().waitForNextUpdateLoop(aUpdatePeriod);

            if (aModule instanceof IUpdateableModule)
            {
                ((IUpdateableModule) aModule).update();
            }
        }
    }
}
