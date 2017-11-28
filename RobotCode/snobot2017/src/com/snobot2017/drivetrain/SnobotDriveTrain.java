package com.snobot2017.drivetrain;

import com.snobot.lib.logging.ILogger;
import com.snobot2017.Properties2017;
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
            ILogger aLogger)
    {
        super(aLeftMotor, aRightMotor, aDriverJoystick, aLogger);
        mLeftDriveEncoder = aLeftDriveEncoder;
        mRightDriveEncoder = aRightDriveEncoder;

        mLeftDriveEncoder.setDistancePerPulse(Properties2017.sLEFT_ENCODER_DIST_PER_PULSE.getValue());
        mRightDriveEncoder.setDistancePerPulse(Properties2017.sRIGHT_ENCODER_DIST_PER_PULSE.getValue());
    }

    
    @Override
    public void update()
    {
        mLeftDriveEncoder.setDistancePerPulse(Properties2017.sLEFT_ENCODER_DIST_PER_PULSE.getValue());
        mRightDriveEncoder.setDistancePerPulse(Properties2017.sRIGHT_ENCODER_DIST_PER_PULSE.getValue());
        
        mLeftMotorDistance = mLeftDriveEncoder.getDistance();
        mRightMotorDistance = mRightDriveEncoder.getDistance();
        mRightEncoderRaw = mRightDriveEncoder.getRaw();
        mLeftEncoderRaw = mLeftDriveEncoder.getRaw();
    }


    @Override
    public void resetEncoders()
    {
        mLeftDriveEncoder.reset();
        mRightDriveEncoder.reset();
    }

}