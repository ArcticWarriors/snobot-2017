package com.snobot2017.drivetrain;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.snobot.lib.logging.ILogger;
import com.snobot2017.Properties2017;
import com.snobot2017.joystick.IDriverJoystick;

/**
 * Drivetrain using CAN controllers
 *
 * @author Jeff
 */
public class SnobotCanDriveTrain extends ASnobotDrivetrain<WPI_TalonSRX>
{
    public SnobotCanDriveTrain(WPI_TalonSRX aLeftMotorA, WPI_TalonSRX aLeftMotorB, WPI_TalonSRX aRightMotorA, WPI_TalonSRX aRightMotorB,
            IDriverJoystick aDriverJoystick, ILogger aLogger)
    {
        super(aLeftMotorA, aRightMotorA, aDriverJoystick, aLogger);

        aLeftMotorA.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
        aRightMotorA.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);

        aLeftMotorB.follow(aLeftMotorA);
        aRightMotorB.follow(aRightMotorA);
    }

    @Override
    public void update()
    {
        mRightMotorDistance = mRightMotor.getSelectedSensorPosition(0) * Properties2017.sRIGHT_ENCODER_DIST_PER_PULSE.getValue();
        mLeftMotorDistance = mLeftMotor.getSelectedSensorPosition(0) * Properties2017.sLEFT_ENCODER_DIST_PER_PULSE.getValue();
    }

    @Override
    public void resetEncoders()
    {
        mRightMotor.setSelectedSensorPosition(0, 0, 0);
        mLeftMotor.setSelectedSensorPosition(0, 0, 0);
    }

}