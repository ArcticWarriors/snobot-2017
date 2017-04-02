package com.snobot2017.autonomous.path;

import com.snobot.lib.motion_profile.ISetpointIterator;
import com.snobot2017.Properties2017;
import com.snobot2017.drivetrain.IDriveTrain;
import com.snobot2017.positioner.IPositioner;

import edu.wpi.first.wpilibj.command.Command;

/**
 * uses the motion profile to follow a straight path
 * 
 * @author Andrew
 *
 */
public class DriveStraightPath extends Command
{
    protected IDriveTrain mDriveTrain;
    protected IPositioner mPositioner;
    private PathFollower mPathFollower;
    private double mStartDistance;

    /**
     * 
     * @param aDriveTrain
     *            The robot's drive train
     * @param aPositioner
     *            The robot's positioner
     * @param aSetpointIterator
     *            A setpoint iterator for the path follower
     */
    public DriveStraightPath(IDriveTrain aDriveTrain, IPositioner aPositioner, ISetpointIterator aSetpointIterator)
    {
        this(aDriveTrain, aPositioner, aSetpointIterator, 
                Properties2017.sDRIVE_PATH_KP.getValue(), 
                Properties2017.sDRIVE_PATH_KV.getValue(), 
                Properties2017.sDRIVE_PATH_KA.getValue());
    }

    public DriveStraightPath(IDriveTrain aDriveTrain, IPositioner aPositioner, ISetpointIterator aSetpointIterator,
            double aKp, double aKv, double aKa)
    {
        mDriveTrain = aDriveTrain;
        mPositioner = aPositioner;

        mPathFollower = new PathFollower(aSetpointIterator, aKv, aKa, aKp);
    }

    @Override
    protected void initialize()
    {
        mStartDistance = mPositioner.getTotalDistance();
        mPathFollower.init();
    }

    protected double calculatePathSpeed()
    {
        double curPos = mPositioner.getTotalDistance() - mStartDistance;
        double motorSpeed = mPathFollower.calcMotorSpeed(curPos);

        return motorSpeed;
    }

    @Override
    protected void execute()
    {
        double motorSpeed = calculatePathSpeed();
        mDriveTrain.setLeftRightSpeed(motorSpeed, motorSpeed);
    }

    @Override
    protected boolean isFinished()
    {
        return mPathFollower.isFinished();
    }

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
