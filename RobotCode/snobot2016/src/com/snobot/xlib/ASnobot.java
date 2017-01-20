package com.snobot.xlib;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

public abstract class ASnobot extends IterativeRobot implements ISubsystem
{

    protected List<ISubsystem> mSubsystems;

    protected Logger mLogger;
    private SimpleDateFormat mLogDateFormat;

    public ASnobot(SimpleDateFormat aLogFormat)
    {
        mSubsystems = new ArrayList<>();
        mLogDateFormat = aLogFormat;

        String headerDate = mLogDateFormat.format(new Date());
        mLogger = new Logger(headerDate);
    }

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        this.init();
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
        String logDate = mLogDateFormat.format(new Date());
        if (mLogger.logNow())
        {
            mLogger.startLogEntry(logDate);

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

}