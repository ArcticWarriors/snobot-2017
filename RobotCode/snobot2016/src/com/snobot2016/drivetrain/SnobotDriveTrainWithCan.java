package com.snobot2016.drivetrain;

import com.snobot.xlib.Logger;
import com.snobot2016.Properties2016;
import com.snobot2016.joystick.IDriverJoystick;

import edu.wpi.first.wpilibj.CANTalon;

/**
 * 
 * @author Team 174
 * 
 * The drive train with can motor controllers
 *
 */
public class SnobotDriveTrainWithCan extends ASnobotDrivetrain
{
    /**
     * 
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
    public SnobotDriveTrainWithCan(
            CANTalon aLeftMotor, 
    		CANTalon aLeftMotorB, 
    		CANTalon aRightMotor, 
    		CANTalon aRightMotorB, 
            IDriverJoystick aDriverJoyStick, Logger aLogger)
    {
        super(aLeftMotor, aLeftMotorB, aRightMotor, aRightMotorB, aDriverJoyStick, aLogger);
    }

    @Override
    public double getRightEncoderDistance()
    {
        return ((CANTalon) mRightMotor).getEncPosition() * Properties2016.sRIGHT_ENCODER_DIST_PER_PULSE.getValue();
    }

    @Override
    public double getLeftEncoderDistance()
    {
        return ((CANTalon) mLeftMotor).getEncPosition() * Properties2016.sLEFT_ENCODER_DIST_PER_PULSE.getValue();
    }

    @Override
    public void resetEncoders()
    {
        ((CANTalon) mLeftMotor).setEncPosition(0);
        ((CANTalon) mRightMotor).setEncPosition(0);
    }

}
