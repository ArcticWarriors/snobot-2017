package com.snobot2017.drivetrain;

import com.ctre.CANTalon;
import com.snobot.lib.Logger;
import com.snobot2017.Properties2017;
import com.snobot2017.autologger.AutoLogger;
import com.snobot2017.joystick.IDriverJoystick;

public class SnobotCanDriveTrain extends ASnobotDrivetrain<CANTalon>
{
    public SnobotCanDriveTrain(
            CANTalon aLeftMotorA, 
            CANTalon aLeftMotorB, 
            CANTalon aRightMotorA, 
            CANTalon aRightMotorB, 
            IDriverJoystick aDriverJoystick, 
            Logger aLogger, 
            AutoLogger aAutoLogger)
    {
        super(aLeftMotorA, aLeftMotorB, aRightMotorA, aRightMotorB, aDriverJoystick, aLogger, aAutoLogger);
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