package com.snobot2017.drivetrain;

import com.ctre.CANTalon;
import com.snobot.lib.Logger;
import com.snobot2017.autologger.AutoLogger;
import com.snobot2017.joystick.IDriverJoystick;

import edu.wpi.first.wpilibj.Encoder;

public class SnobotCanDriveTrain extends ASnobotDrivetrain
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
        // TODO: PJ - Need to look at least year
    }

    @Override
    public void resetEncoders()
    {
        // TODO: PJ - Need to look at least year
    }

}