package com.snobot2017.autonomous.path;

import com.snobot.lib.motion_profile.ISetpointIterator;
import com.snobot2017.Properties2017;
import com.snobot2017.drivetrain.IDriveTrain;
import com.snobot2017.positioner.IPositioner;

/**
 * uses the motion profile to follow a straight path
 * 
 * @author Andrew
 *
 */
public class DriveStraightPathWithGyro extends DriveStraightPath
{
    private double mStartAngle;

    /**
     * 
     * @param aDriveTrain
     *            The robot's drive train
     * @param aPositioner
     *            The robot's positioner
     * @param aSetpointIterator
     *            A setpoint iterator for the path follower
     */
    public DriveStraightPathWithGyro(IDriveTrain aDriveTrain, IPositioner aPositioner, ISetpointIterator aSetpointIterator)
    {
        super(aDriveTrain, aPositioner, aSetpointIterator);
    }

    @Override
    protected void initialize()
    {
        mStartAngle = mPositioner.getOrientationDegrees();

        super.initialize();
    }

    @Override
    protected void execute()
    {
        double motorSpeed = calculatePathSpeed();

        double angleError = mPositioner.getOrientationDegrees() - mStartAngle;
        double angleKP = Properties2017.sDRIVE_PATH_WITH_GYRO_KP.getValue();
        double addMorePower = angleKP * angleError;

        mDriveTrain.setLeftRightSpeed((motorSpeed - addMorePower), (motorSpeed + addMorePower));
    }
}
