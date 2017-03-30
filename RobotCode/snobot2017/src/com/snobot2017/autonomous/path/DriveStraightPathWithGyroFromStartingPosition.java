package com.snobot2017.autonomous.path;

import com.snobot.lib.motion_profile.ISetpointIterator;
import com.snobot.lib.motion_profile.PathConfig;
import com.snobot.lib.motion_profile.StaticSetpointIterator;
import com.snobot2017.Properties2017;
import com.snobot2017.autonomous.AutonomousFactory.StartingPositions;
import com.snobot2017.drivetrain.IDriveTrain;
import com.snobot2017.positioner.IPositioner;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * uses the motion profile to follow a straight path
 * 
 * @author Andrew
 *
 */
public class DriveStraightPathWithGyroFromStartingPosition extends Command
{
    private IDriveTrain mDriveTrain;
    private IPositioner mPositioner;
    private PathFollower mPathFollower;
    private double mStartAngle;
    private double mStartDistance;
    protected SendableChooser<StartingPositions> mPositionChooser;

    /**
     * 
     * @param aDriveTrain
     *            The robot's drive train
     * @param aPositioner
     *            The robot's positioner
     * @param aExpectedDt
     * @param aSetpointIterator
     *            A setpoint iterator for the path follower
     */
    public DriveStraightPathWithGyroFromStartingPosition(IDriveTrain aDriveTrain, IPositioner aPositioner, StartingPositions aStartPosition,
            double aMaxVelocity, double aMaxAccelleration, double aExpectedDt, double aRedLeft, double aRedRight, double aRedMiddle, double aBlueLeft,
            double aBlueRight, double aBlueMiddle)
    {
        mDriveTrain = aDriveTrain;
        mPositioner = aPositioner;

        double kP = Properties2017.sDRIVE_PATH_KP.getValue();
        // double kD = Properties2017.sDRIVE_PATH_KD.getValue();
        double kVelocity = Properties2017.sDRIVE_PATH_KV.getValue();
        double kAccel = Properties2017.sDRIVE_PATH_KA.getValue();

        double endpoint = 0;

        switch (aStartPosition)
        {
        case RedLeft:
            endpoint = aRedLeft;
            break;
        case RedRight:
            endpoint = aRedRight;
            break;
        case BlueRight:
            endpoint = aBlueRight;
            break;
        case BlueLeft:
            endpoint = aBlueLeft;
            break;
        case RedMiddle:
            endpoint = aRedMiddle;
            break;
        case BlueMiddle:
            endpoint = aBlueMiddle;
            break;
        // Intended fall through - do nothing in these unexpected cases
        case Origin:
        default:
            break;
        }

        PathConfig dudePathConfig = new PathConfig(endpoint, aMaxVelocity, aMaxAccelleration, aExpectedDt);
        ISetpointIterator dudeSetpointIterator = new StaticSetpointIterator(dudePathConfig);
        mPathFollower = new PathFollower(dudeSetpointIterator, kVelocity, kAccel, kP);
    }

    @Override
    protected void initialize()
    {
        mStartDistance = mPositioner.getTotalDistance();
        mStartAngle = mPositioner.getOrientationDegrees();
        mPathFollower.init();
    }

    @Override
    protected void execute()
    {
        double curPos = mPositioner.getTotalDistance() - mStartDistance;
        double motorSpeed = mPathFollower.calcMotorSpeed(curPos);
        double angleError = mPositioner.getOrientationDegrees() - mStartAngle;
        double angleKP = Properties2017.sDRIVE_PATH_WITH_GYRO_KP.getValue();
        double addMorePower = angleKP * angleError;
        mDriveTrain.setLeftRightSpeed((motorSpeed - addMorePower), (motorSpeed + addMorePower));
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
