package com.snobot2016.autonomous;

import com.snobot2016.drivetrain.IDriveTrain;
import com.snobot2016.positioner.IPositioner;

import edu.wpi.first.wpilibj.command.Command;

/**
 * A command that causes the robot to drive straight for a given distance and
 * speed but doesn't account for veering off course.
 * 
 * @author Alec/Andrew
 *
 */
public class DriveStraightADistance extends Command
{
    private IDriveTrain mDriveTrain;
    private IPositioner mPositioner;
    private double mDistance;
    private double mSpeed;
    private double mStartDistance;

    /**
     * Creates a new DriveStraightADistance Command.
     * 
     * @param aDriveTrain
     *            The robot's DriveTrain.
     * @param aPositioner
     *            The robot's Positioner.
     * @param aDistance
     *            The requested distance.
     * @param aSpeed
     *            The requested speed (-1 - 1).
     */
    public DriveStraightADistance(IDriveTrain aDriveTrain, IPositioner aPositioner, double aDistance, double aSpeed)
    {
        mDriveTrain = aDriveTrain;
        mPositioner = aPositioner;
        mDistance = aDistance;
        mSpeed = aSpeed;
    }

    /**
     * Sets mStartDistance equal to the total distance the robot has already
     * traveled.
     */
    @Override
    protected void initialize()
    {
        mStartDistance = mPositioner.getTotalDistance();
    }

    /**
     * Sets the robot's motor speed.
     */
    @Override
    protected void execute()
    {
        mDriveTrain.setLeftRightSpeed(mSpeed, mSpeed);

    }

    /**
     * Checks if the robot has gone the distance requested.
     */
    @Override
    protected boolean isFinished()
    {
        if (mDistance > 0)
        {
            return ((mStartDistance + mDistance) <= mPositioner.getTotalDistance());
        }
        else if (mDistance < 0)
        {
            return ((mStartDistance + mDistance) >= mPositioner.getTotalDistance());
        }
        else
        {
            return true;
        }

    }

    /**
     * Stops the robot.
     */
    @Override
    protected void end()
    {
        mDriveTrain.stop();
    }

    @Override
    protected void interrupted()
    {

    }

}
