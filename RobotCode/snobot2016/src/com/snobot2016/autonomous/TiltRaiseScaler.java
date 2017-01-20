package com.snobot2016.autonomous;

import com.snobot2016.scaling.IScaling;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This class controls the tilt of the scaling mechanism during autonomous.
 * 
 * @author avdonle
 *
 */
public class TiltRaiseScaler extends Command
{
    private Timer mTimer;
    private double mTime;
    private IScaling mScaling;

    /**
     * Passes values to local variables.
     * 
     * @param aTime
     *            Set time controlling when the tilt motor runs.
     * @param aScaling
     *            Gets mScaling functionality.
     */
    public TiltRaiseScaler(double aTime, IScaling aScaling)
    {
        mTimer = new Timer();
        mTime = aTime;
        mScaling = aScaling;
    }

    /**
     * Starts local timer.
     */
    @Override
    protected void initialize()
    {
        mTimer.start();
    }

    /**
     * Calls the "TiltRaise" method if within the time given.
     */
    @Override
    protected void execute()
    {
        if (mTimer.get() < mTime)
        {
            mScaling.raiseScaleTiltMechanism();
        }
    }

    /**
     * Informs on if the tilt motor is running.
     * 
     * @return Tilt motor running or not.
     */
    @Override
    protected boolean isFinished()
    {
        if (mTimer.get() > mTime)
        {
            return true;
        }
        return false;
    }

    /**
     * Stops the tilt motor.
     */
    @Override
    protected void end()
    {
        mScaling.stop();
    }

    @Override
    protected void interrupted()
    {

    }

}
