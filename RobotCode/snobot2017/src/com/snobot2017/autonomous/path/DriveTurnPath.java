package com.snobot2017.autonomous.path;

import com.snobot.lib.motion_profile.ISetpointIterator;
import com.snobot2017.Properties2017;
import com.snobot2017.drivetrain.IDriveTrain;
import com.snobot2017.positioner.IPositioner;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Uses the path follower to turn with a motion profile
 * 
 * @author Andrew
 *
 */
public class DriveTurnPath extends Command
{
    private IDriveTrain mDriveTrain;
    private IPositioner mPositioner;
    private PathFollower mPathFollower;

    double mStartAngle;

    /**
     * 
     * @param aDriveTrain
     *            The robot's drive train
     * @param aPositioner
     *            The robot's positioner
     * @param aSetpointIterator
     *            A setpoint iterator for the path follower
     */
    public DriveTurnPath(IDriveTrain aDriveTrain, IPositioner aPositioner, ISetpointIterator aSetpointIterator)
    {
        mDriveTrain = aDriveTrain;
        mPositioner = aPositioner;

        double kP = Properties2017.sTURN_PATH_KP.getValue();
        // double kD = Properties2016.sTURN_PATH_KD.getValue();
        double kVelocity = Properties2017.sTURN_PATH_KV.getValue();
        double kAccel = Properties2017.sTURN_PATH_KA.getValue();

        mPathFollower = new PathFollower(aSetpointIterator, kVelocity, kAccel, kP);
    }

    @Override
    protected void initialize()
    {
        mStartAngle = mPositioner.getOrientationDegrees();
        mPathFollower.init();
    }

    @Override
    protected void execute()
    {
        double curAngle = mPositioner.getOrientationDegrees() - mStartAngle;
        double motorSpeed = mPathFollower.calcMotorSpeed(curAngle);
        mDriveTrain.setLeftRightSpeed(motorSpeed, -motorSpeed);
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
