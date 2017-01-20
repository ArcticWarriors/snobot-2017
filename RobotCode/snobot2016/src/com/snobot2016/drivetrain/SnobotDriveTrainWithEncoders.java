package com.snobot2016.drivetrain;

import com.snobot.xlib.Logger;
import com.snobot2016.Properties2016;
import com.snobot2016.joystick.IDriverJoystick;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * 
 * @author Team 174
 * 
 * The drive train encoders, so that we can see how far we have gone
 *
 */
public class SnobotDriveTrainWithEncoders extends ASnobotDrivetrain
{

    private final Encoder mLeftEncoder;
    private final Encoder mRightEncoder;

    /**
    * @param aLeftMotor
    *                  The First Left Motor
    * @param aLeftMotorB
    *                   The Second Left Motor
    * @param aRightMotor
    *                   The First Right Motor
    * @param aRightMotorB
    *                    The Second Right Motor
    * @param aDriverJoyStick
    *                       The Driver Joystick
    * @param aLogger
    *               The Logger
    *               
     */
    public SnobotDriveTrainWithEncoders(
    		SpeedController aLeftMotor, 
    		SpeedController aLeftMotorB, 
    		SpeedController aRightMotor, 
    		SpeedController aRightMotorB, 
    		Encoder aLeftEncoder, 
    		Encoder aRightEncoder,
 IDriverJoystick aDriverJoyStick, Logger aLogger)
    {
        super(aLeftMotor, aLeftMotorB, aRightMotor, aRightMotorB, aDriverJoyStick, aLogger);

        mLeftEncoder = aLeftEncoder;
        mRightEncoder = aRightEncoder;
        
        mLeftEncoder.setDistancePerPulse(Properties2016.sLEFT_ENCODER_DIST_PER_PULSE.getValue());
        mRightEncoder.setDistancePerPulse(Properties2016.sRIGHT_ENCODER_DIST_PER_PULSE.getValue());

    }

    @Override
    public double getRightEncoderDistance()
    {
        return mRightEncoder.getDistance();
    }

    @Override
    public double getLeftEncoderDistance()
    {
        return mLeftEncoder.getDistance();
    }

    @Override
    public void resetEncoders()
    {
        mLeftEncoder.reset();
        mRightEncoder.reset();
    }

}
