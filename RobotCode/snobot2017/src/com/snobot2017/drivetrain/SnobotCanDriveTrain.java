package com.snobot2017.drivetrain;

import com.ctre.CANTalon;
import com.snobot.lib.Logger;
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
        mRightMotorDistance = mRightMotor.getEncPosition();
        mLeftMotorDistance = mLeftMotor.getEncPosition();
    }

    @Override
    public void resetEncoders()
    {
        mRightMotor.setEncPosition(0);
        mLeftMotor.setEncPosition(0);
    }

}