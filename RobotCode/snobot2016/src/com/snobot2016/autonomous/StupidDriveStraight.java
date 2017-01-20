package com.snobot2016.autonomous;

import com.snobot2016.drivetrain.IDriveTrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Tells the robot to drive straight for a given length of time but doesn't
 * account for veering off course.
 * 
 * @author Alec/Andrew
 *
 */
public class StupidDriveStraight extends Command
{
    private Timer mTimer;
    private IDriveTrain mDriveTrain;
    private double mTime;
    private double mSpeed;

    /**
     * Creates a new StupidDriveStraight Command
     * 
     * @param aDriveTrain
     *            The robot's DriveTrain.
     * @param aTime
     *            The length of time the robot is supposed to drive straight.
     * @param aSpeed
     *            The desired speed (-1 - 1).
     */
    public StupidDriveStraight(IDriveTrain aDriveTrain, double aTime, double aSpeed)
    {
        mTimer = new Timer();
        mTime = aTime;
        mDriveTrain = aDriveTrain;
        mSpeed = aSpeed;
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
     * Sets the robot's motor speed equal to the given speed.
     */
    @Override
    protected void execute()
    {
        mDriveTrain.setLeftRightSpeed(mSpeed, mSpeed);

    }

    /**
     * Checks if the robot has been driving for the given amount of time.
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
     * Stops the timer and the robot.
     */
    @Override
    protected void end()
    {
        mTimer.stop();
        mDriveTrain.setLeftRightSpeed(0, 0);

    }

    @Override
    protected void interrupted()
    {
    }

}
