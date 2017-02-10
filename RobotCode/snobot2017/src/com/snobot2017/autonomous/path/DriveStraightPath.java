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
    private IDriveTrain mDriveTrain;
    private IPositioner mPositioner;
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
        mDriveTrain = aDriveTrain;
        mPositioner = aPositioner;

        double kP = Properties2017.sDRIVE_PATH_KP.getValue();
        // double kD = Properties2017.sDRIVE_PATH_KD.getValue();
        double kVelocity = Properties2017.sDRIVE_PATH_KV.getValue();
        double kAccel = Properties2017.sDRIVE_PATH_KA.getValue();

        mPathFollower = new PathFollower(aSetpointIterator, kVelocity, kAccel, kP);
    }

    @Override
    protected void initialize()
    {
        mStartDistance = mPositioner.getTotalDistance();
        mPathFollower.init();
    }

    @Override
    protected void execute()
    {
        double curPos = mPositioner.getTotalDistance() - mStartDistance;
        double motorSpeed = mPathFollower.calcMotorSpeed(curPos);
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
