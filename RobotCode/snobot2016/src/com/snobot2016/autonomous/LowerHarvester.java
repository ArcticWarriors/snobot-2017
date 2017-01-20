package com.snobot2016.autonomous;

import com.snobot2016.harvester.IHarvester;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This class will run the pivot motor to lower the harvester system.
 * 
 * @author avdonle
 *
 */
public class LowerHarvester extends Command
{
    private Timer mTimer;
    private double mTime;
    private IHarvester mHarvester;

    /**
     * Initializes class variables using outside parameters.
     * 
     * @param aTime
     *            Time that defines when the pivot motor will be turned on.
     * @param aHarvester
     *            Takes in IHarvester functionality.
     */
    public LowerHarvester(double aTime, IHarvester aHarvester)
    {
        mTimer = new Timer();
        mTime = aTime;
        mHarvester = aHarvester;
    }

    /**
     * Initializes the class' timer.
     */
    @Override
    protected void initialize()
    {
        mTimer.start();
    }

    /**
     * Starts the lowering function if within time.
     */
    @Override
    protected void execute()
    {
        if (mTimer.get() < mTime)
        {
            mHarvester.lowerHarvester();
        }
    }

    /**
     * Returns true or false if the pivot motor is running or not.
     * 
     * @return Pivot motor running or not.
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
     * Stops the pivot motor when called.
     */
    @Override
    protected void end()
    {
        mHarvester.stop();
    }

    @Override
    protected void interrupted()
    {

    }
}
