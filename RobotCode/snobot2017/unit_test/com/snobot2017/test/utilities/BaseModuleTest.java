package com.snobot2017.test.utilities;

import com.snobot.lib.modules.IControllableModule;
import com.snobot.lib.modules.ILoggableModule;
import com.snobot.lib.modules.ISmartDashboardUpdaterModule;
import com.snobot.lib.modules.IUpdateableModule;

public class BaseModuleTest extends BaseUnitTest
{

    protected void initializeModule(Object aModule)
    {
        if (aModule instanceof ILoggableModule)
        {
            ((ILoggableModule) aModule).initializeLogHeaders();
        }
    }

    protected void runTeleopLoop(Object aModule)
    {
        if (aModule instanceof IUpdateableModule)
        {
            ((IUpdateableModule) aModule).update();
        }
        if (aModule instanceof IControllableModule)
        {
            ((IControllableModule) aModule).control();
        }
        if (aModule instanceof ILoggableModule)
        {
            ((ILoggableModule) aModule).updateLog();
        }
        if (aModule instanceof ISmartDashboardUpdaterModule)
        {
            ((ISmartDashboardUpdaterModule) aModule).updateSmartDashboard();
        }
    }

    protected void runTeleopLoopNoControl(Object aModule)
    {
        if (aModule instanceof IUpdateableModule)
        {
            ((IUpdateableModule) aModule).update();
        }
        if (aModule instanceof ILoggableModule)
        {
            ((ILoggableModule) aModule).updateLog();
        }
        if (aModule instanceof ISmartDashboardUpdaterModule)
        {
            ((ISmartDashboardUpdaterModule) aModule).updateSmartDashboard();
        }
    }
}
