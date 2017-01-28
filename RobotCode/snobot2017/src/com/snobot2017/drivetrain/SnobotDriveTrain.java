package com.snobot2017.drivetrain;

import com.snobot.lib.Logger;
import com.snobot2017.autologger.AutoLogger;
import com.snobot2017.joystick.IDriverJoystick;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

public class SnobotDriveTrain extends ASnobotDrivetrain
{
    private final Encoder mLeftDriveEncoder;
    private final Encoder mRightDriveEncoder;

    public SnobotDriveTrain(
            SpeedController aLeftMotor, 
            SpeedController aRightMotor, 
            Encoder aLeftDriveEncoder, 
            Encoder aRightDriveEncoder,
            IDriverJoystick aDriverJoystick, 
            Logger aLogger, 
            AutoLogger aAutoLogger)
    {
        super(aLeftMotor, null, aRightMotor, null, aDriverJoystick, aLogger, aAutoLogger);
        mLeftDriveEncoder = aLeftDriveEncoder;
        mRightDriveEncoder = aRightDriveEncoder;
    }

    @Override
    public void update()
    {
        mLeftMotorDistance = mLeftDriveEncoder.getDistance();
        mRightMotorDistance = mRightDriveEncoder.getDistance();
    }

    @Override
    public void resetEncoders()
    {
        mLeftDriveEncoder.reset();
        mRightDriveEncoder.reset();
    }

}