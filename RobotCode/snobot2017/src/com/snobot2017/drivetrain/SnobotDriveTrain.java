package com.snobot2017.drivetrain;

import com.snobot.lib.Logger;
import com.snobot2017.Properties2017;
import com.snobot2017.autologger.AutoLogger;
import com.snobot2017.joystick.IDriverJoystick;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * Drivetrain using normal speed controllers and encoders
 * 
 * @author jbnol
 *
 */
public class SnobotDriveTrain extends ASnobotDrivetrain<SpeedController>
{
    private final Encoder mLeftDriveEncoder;
    private final Encoder mRightDriveEncoder;

    public SnobotDriveTrain(
            SpeedController aLeftMotor, 
            SpeedController aRightMotor, 
            Encoder aLeftDriveEncoder, 
            Encoder aRightDriveEncoder,
            IDriverJoystick aDriverJoystick, 
            Logger aLogger)
    {
        super(aLeftMotor, null, aRightMotor, null, aDriverJoystick, aLogger);
        mLeftDriveEncoder = aLeftDriveEncoder;
        mRightDriveEncoder = aRightDriveEncoder;

        mLeftDriveEncoder.setDistancePerPulse(Properties2017.sLEFT_ENCODER_DIST_PER_PULSE.getValue());
        mRightDriveEncoder.setDistancePerPulse(Properties2017.sRIGHT_ENCODER_DIST_PER_PULSE.getValue());
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