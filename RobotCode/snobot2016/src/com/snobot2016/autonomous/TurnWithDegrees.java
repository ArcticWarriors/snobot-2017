package com.snobot2016.autonomous;

import com.snobot.xlib.Utilities;
import com.snobot2016.drivetrain.IDriveTrain;
import com.snobot2016.positioner.IPositioner;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Tells the robot to turn to a specified orientation.
 * 
 * @author Alec/Andrew
 *
 */
public class TurnWithDegrees extends Command
{
    private final IDriveTrain mDriveTrain;
    private final IPositioner mPositioner;
    private final double mAngleToTurn;
    private final double mSpeed;
    private final double mDeadband;
    private double mEndAngle;
    private boolean mFinished;

    /**
     * Creates a new TurnWithDegrees Command with a default deadband.
     * 
     * @param aDriveTrain
     *            The robot's DriveTrain.
     * @param aPositioner
     *            The robot's Positioner
     * @param aTurnDegrees
     *            The desired final orientation (-180 - 180).
     * @param aSpeed
     *            The desired speed (-1 - 1).
     */
    public TurnWithDegrees(IDriveTrain aDriveTrain, IPositioner aPositioner, double aTurnDegrees, double aSpeed)
    {
        this(aDriveTrain, aPositioner, aTurnDegrees, aSpeed, 5);
    }

    /**
     * Creates a new TurnWithDegrees Command.
     * 
     * @param aDriveTrain
     *            The robot's DriveTrain.
     * @param aPositioner
     *            The robot's Positioner
     * @param aTurnDegrees
     *            The desired final orientation.
     * @param aSpeed
     *            The desired speed (-1 - 1).
     * @param aDeadband
     *            Defines the stop-zone of the robot (>0).
     */
    public TurnWithDegrees(IDriveTrain aDriveTrain, IPositioner aPositioner, double aTurnDegrees, double aSpeed, double aDeadband)
    {
        mDriveTrain = aDriveTrain;
        mPositioner = aPositioner;
        mAngleToTurn = aTurnDegrees;
        mSpeed = aSpeed;
        mDeadband = aDeadband;
    }

    /**
     * Calculates the robot's final orientation.
     */
    @Override
    protected void initialize()
    {
        mEndAngle = mAngleToTurn + mPositioner.getOrientationDegrees();
        mFinished = false;
    }

    /**
     * Turns the robot towards the desired orientation.
     */
    @Override
    protected void execute()
    {
        double error = mPositioner.getOrientationDegrees() - mEndAngle;

        // turn left
        if (error > mDeadband)
        {
            mDriveTrain.setLeftRightSpeed(-mSpeed, mSpeed);
        }
        // turn right
        else if (error < -mDeadband)
        {
            mDriveTrain.setLeftRightSpeed(mSpeed, -mSpeed);
        }
        else
        {
            mDriveTrain.stop();
            mFinished = true;
        }

    }

    /**
     * Checks if the robot is finished turning.
     */
    @Override
    protected boolean isFinished()
    {
        return mFinished;
    }

    /**
     * Stops the robot.
     */
    @Override
    protected void end()
    {
        mDriveTrain.setLeftRightSpeed(0, 0);
    }

    @Override
    protected void interrupted()
    {

    }

}
