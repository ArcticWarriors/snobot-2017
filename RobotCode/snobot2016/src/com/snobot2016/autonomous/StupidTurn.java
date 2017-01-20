package com.snobot2016.autonomous;

import com.snobot2016.drivetrain.IDriveTrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Tells the robot to turn for a specified amount of time.
 * 
 * @author Alec/Andrew
 *
 */
public class StupidTurn extends Command
{
    private IDriveTrain mDriveTrain;
    private double mSpeed;
    private double mTime;
    private Timer mTimer;

    /**
     * Creates a new StupidTurn Command.
     * 
     * @param aDriveTrain
     *            The robot's DriveTrain.
     * @param aSpeed
     *            The desired speed (-1 - 1).
     * @param aTime
     *            . The desired amount of time for the robot to turn.
     */
    public StupidTurn(IDriveTrain aDriveTrain, double aSpeed, double aTime)
    {
        mDriveTrain = aDriveTrain;
        mSpeed = aSpeed;
        mTime = aTime;
        mTimer = new Timer();
    }

    /**
     * Starts the timer.
     */
    @Override
    protected void initialize()
    {
        mTimer.start();
    }

    /**
     * Sets the robot's turning speed.
     */
    @Override
    protected void execute()
    {
        mDriveTrain.setLeftRightSpeed(mSpeed, -mSpeed);
    }

    /**
     * Checks if the robot has been turning for the specified amount of time.
     */
    @Override
    protected boolean isFinished()
    {
        if (mTimer.get() >= mTime)
        {
            return true;
        }
        return false;
    }

    /**
     * Stops the robot and the timer.
     */
    @Override
    protected void end()
    {
        mDriveTrain.stop();
        mTimer.stop();

    }

    @Override
    protected void interrupted()
    {

    }

}
