package com.snobot.lib;

import java.util.ArrayList;
import java.util.List;

import com.snobot.lib.logging.ILogger;
import com.snobot.lib.logging.Logger;
import com.snobot.lib.modules.IControllableModule;
import com.snobot.lib.modules.ILoggableModule;
import com.snobot.lib.modules.ISmartDashboardUpdaterModule;
import com.snobot.lib.modules.ISubsystem;
import com.snobot.lib.modules.IUpdateableModule;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;

public abstract class ASnobot extends IterativeRobot implements ISubsystem
{

    private List<IUpdateableModule> mUpdateableModules;
    private List<IControllableModule> mControllableModules;
    private List<ILoggableModule> mLoggableModules;
    private List<ISmartDashboardUpdaterModule> mSmartDashboardModules;

    private Logger mLogger;

    // Autonomous
    private CommandGroup mAutonCommand;

    public ASnobot()
    {
        mUpdateableModules = new ArrayList<>();
        mControllableModules = new ArrayList<>();
        mLoggableModules = new ArrayList<>();
        mSmartDashboardModules = new ArrayList<>();

        mLogger = new Logger();
    }

    protected void addModule(Object aModule)
    {
        if (aModule instanceof IUpdateableModule)
        {
            mUpdateableModules.add((IUpdateableModule) aModule);
        }
        if (aModule instanceof IControllableModule)
        {
            mControllableModules.add((IControllableModule) aModule);
        }
        if (aModule instanceof ILoggableModule)
        {
            mLoggableModules.add((ILoggableModule) aModule);
        }
        if (aModule instanceof ISmartDashboardUpdaterModule)
        {
            mSmartDashboardModules.add((ISmartDashboardUpdaterModule) aModule);
        }

    }

    /**
     * This function is called periodically during autonomous
     */
    @Override
    public void autonomousPeriodic()
    {
        Scheduler.getInstance().run();

        update();
        updateSmartDashboard();
        updateLog();

    }

    @Override
    public void autonomousInit()
    {
        mAutonCommand = createAutonomousCommand();

        if (mAutonCommand != null)
        {
            mAutonCommand.start();
        }
    }

    @Override
    public void teleopInit()
    {
        if (mAutonCommand != null)
        {
            mAutonCommand.cancel();
            Scheduler.getInstance().run();
        }
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
    public void teleopPeriodic()
    {
        update();
        control();
        updateSmartDashboard();
        updateLog();
    }

    @Override
    public void disabledInit()
    {
        PropertyManager.saveIfUpdated();
        mLogger.flush();
    }

    @Override
    public void disabledPeriodic()
    {
        updateSmartDashboard();
    }

    @Override
    public void initializeLogHeaders()
    {
        mLogger.initializeLogger();
        for (ILoggableModule iSubsystem : mLoggableModules)
        {
            iSubsystem.initializeLogHeaders();
        }
        mLogger.endHeader();
        mAutonCommand = createAutonomousCommand();
    }

    @Override
    public void update()
    {
        for (IUpdateableModule iSubsystem : mUpdateableModules)
        {
            iSubsystem.update();

        }
    }

    @Override
    public void control()
    {
        for (IControllableModule iSubsystem : mControllableModules)
        {
            iSubsystem.control();
        }
    }

    @Override
    public void updateLog()
    {
        if (mLogger.logNow())
        {
            mLogger.startRow();

            for (ILoggableModule iSubsystem : mLoggableModules)
            {
                iSubsystem.updateLog();
            }
            mLogger.endRow();
        }

    }

    @Override
    public void updateSmartDashboard()
    {
        for (ISmartDashboardUpdaterModule iSubsystem : mSmartDashboardModules)
        {
            iSubsystem.updateSmartDashboard();
        }
    }

    @Override
    public void stop()
    {
        for (IControllableModule iSubsystem : mControllableModules)
        {
            iSubsystem.stop();
        }
    }

    /**
     * This function is called periodically during test mode
     */
    @Override
    public void testPeriodic()
    {

    }

    protected ILogger getLogger()
    {
        return mLogger;
    }

    protected abstract CommandGroup createAutonomousCommand();

}