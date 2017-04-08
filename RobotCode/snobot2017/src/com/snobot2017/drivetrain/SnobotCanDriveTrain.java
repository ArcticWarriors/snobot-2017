package com.snobot2017.drivetrain;

import com.ctre.CANTalon;
import com.snobot.lib.logging.ILogger;
import com.snobot2017.Properties2017;
import com.snobot2017.joystick.IDriverJoystick;

/**
 * Drivetrain using CAN controllers
 *
 * @author Jeff
 */
public class SnobotCanDriveTrain extends ASnobotDrivetrain<CANTalon>
{
    public SnobotCanDriveTrain(
            CANTalon aLeftMotor, CANTalon aRightMotor,
            IDriverJoystick aDriverJoystick, 
            ILogger aLogger)
    {
        super(aLeftMotor, aRightMotor, aDriverJoystick, aLogger);
    }

    @Override
    public void update()
    {
        mRightMotorDistance = mRightMotor.getEncPosition() * Properties2017.sRIGHT_ENCODER_DIST_PER_PULSE.getValue();
        mLeftMotorDistance = mLeftMotor.getEncPosition() * Properties2017.sLEFT_ENCODER_DIST_PER_PULSE.getValue();
    }

    @Override
    public void resetEncoders()
    {
        mRightMotor.setEncPosition(0);
        mLeftMotor.setEncPosition(0);
    }

}