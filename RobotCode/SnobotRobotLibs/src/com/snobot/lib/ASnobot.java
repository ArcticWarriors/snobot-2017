package com.snobot.lib;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;

public abstract class ASnobot extends IterativeRobot implements ISubsystem
{

    protected List<ISubsystem> mSubsystems;

    protected Logger mLogger;

    // Autonomous
    private CommandGroup mAutonCommand;

    public ASnobot()
    {
        mSubsystems = new ArrayList<>();
        mLogger = new Logger();
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
    public void init()
    {
        mLogger.init();
        for (ISubsystem iSubsystem : mSubsystems)
        {
            iSubsystem.init();
        }
        mLogger.endHeader();
    }

    @Override
    public void update()
    {
        for (ISubsystem iSubsystem : mSubsystems)
        {
            iSubsystem.update();

        }
    }

    @Override
    public void control()
    {
        for (ISubsystem iSubsystem : mSubsystems)
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

            for (ISubsystem iSubsystem : mSubsystems)
            {
                iSubsystem.updateLog();
            }
            mLogger.endLogger();
        }

    }

    @Override
    public void updateSmartDashboard()
    {
        for (ISubsystem iSubsystem : mSubsystems)
        {
            iSubsystem.updateSmartDashboard();
        }
    }

    @Override
    public void rereadPreferences()
    {
        for (ISubsystem iSubsystem : mSubsystems)
        {
            iSubsystem.rereadPreferences();
        }
    }

    @Override
    public void stop()
    {
        for (ISubsystem iSubsystem : mSubsystems)
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

    protected abstract CommandGroup createAutonomousCommand();

}