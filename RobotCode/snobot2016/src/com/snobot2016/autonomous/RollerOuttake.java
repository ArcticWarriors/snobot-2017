package com.snobot2016.autonomous;

import com.snobot2016.harvester.IHarvester;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This class will run the roller motor in a direction to dispense a ball during
 * the autonomous period.
 * 
 * @author avdonle
 *
 */
public class RollerOuttake extends Command
{
    private Timer mTimer;
    private double mTime;
    private IHarvester mHarvester;

    /**
     * Initializes class variables using outside parameters.
     * 
     * @param aTime
     *            Time that defines when the roller will be turned on.
     * @param aHarvester
     *            Takes in IHarvester functionality.
     */
    public RollerOuttake(double aTime, IHarvester aHarvester)
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
     * Starts the roll in function if within time.
     */
    @Override
    protected void execute()
    {
        if (mTimer.get() < mTime)
        {
            mHarvester.rollOut();
        }
    }

    /**
     * Gives a true false as to if the roller is running or not.
     * 
     * @return Roller running or not.
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
     * Shut off function to stop the roller motors.
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
