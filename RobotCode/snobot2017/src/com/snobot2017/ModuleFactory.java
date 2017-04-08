package com.snobot2017;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.snobot.lib.logging.ILogger;
import com.snobot2017.climbing.Climbing;
import com.snobot2017.climbing.IClimbing;
import com.snobot2017.drivetrain.IDriveTrain;
import com.snobot2017.drivetrain.SnobotCanDriveTrain;
import com.snobot2017.drivetrain.SnobotDriveTrain;
import com.snobot2017.gearboss.IGearBoss;
import com.snobot2017.gearboss.SnobotGearBoss;
import com.snobot2017.joystick.IDriverJoystick;
import com.snobot2017.joystick.IOperatorJoystick;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;

public class ModuleFactory
{

    public IDriveTrain createDrivetrain(IDriverJoystick driverJoystick, ILogger logger)
    {
        return createDrivetrain(driverJoystick, logger, PortMappings2017.sUSE_CAN_DRIVETRAIN);
    }

    public IDriveTrain createDrivetrain(IDriverJoystick driverJoystick, ILogger logger, boolean aUseCan)
    {
        IDriveTrain output;

        // Drive Train
        if (aUseCan)
        {
            CANTalon driveLeftMotorA = new CANTalon(PortMappings2017.sDRIVE_CAN_LEFT_A_PORT);
            CANTalon driveLeftMotorB = new CANTalon(PortMappings2017.sDRIVE_CAN_LEFT_B_PORT);
            driveLeftMotorA.setFeedbackDevice(FeedbackDevice.QuadEncoder);
            driveLeftMotorB.changeControlMode(TalonControlMode.Follower);
            driveLeftMotorB.set(driveLeftMotorA.getDeviceID());

            CANTalon driveRightMotorA = new CANTalon(PortMappings2017.sDRIVE_CAN_RIGHT_A_PORT);
            CANTalon driveRightMotorB = new CANTalon(PortMappings2017.sDRIVE_CAN_RIGHT_B_PORT);
            driveRightMotorA.setFeedbackDevice(FeedbackDevice.QuadEncoder);
            driveRightMotorB.changeControlMode(TalonControlMode.Follower);
            driveRightMotorB.set(driveRightMotorA.getDeviceID());

            output = new SnobotCanDriveTrain(driveLeftMotorA, driveRightMotorA, driverJoystick, logger);
        }
        else
        {
            SpeedController driveLeftMotor = new VictorSP(PortMappings2017.sDRIVE_PWM_LEFT_A_PORT);
            SpeedController driveRightMotor = new VictorSP(PortMappings2017.sDRIVE_PWM_RIGHT_A_PORT);
            Encoder leftDriveEncoder = new Encoder(PortMappings2017.sLEFT_DRIVE_ENCODER_PORT_A, PortMappings2017.sLEFT_DRIVE_ENCODER_PORT_B);
            Encoder rightDriveEncoder = new Encoder(PortMappings2017.sRIGHT_DRIVE_ENCODER_PORT_A, PortMappings2017.sRIGHT_DRIVE_ENCODER_PORT_B);

            output = new SnobotDriveTrain(driveLeftMotor, driveRightMotor, leftDriveEncoder, rightDriveEncoder, driverJoystick, logger);
        }

        return output;
    }

    public IClimbing createClimber(IOperatorJoystick operatorJoystick, ILogger logger)
    {
        SpeedController climbingMotor = new VictorSP(PortMappings2017.sCLIMB_PWM_PORT);
        return new Climbing(climbingMotor, logger, operatorJoystick);
    }

    public IGearBoss createGearBoss(IOperatorJoystick operatorJoystick, ILogger logger)
    {
        DoubleSolenoid gearSolonoid = new DoubleSolenoid(PortMappings2017.sGEARBOSS_SOLENOID_CHANNEL_A,
                PortMappings2017.sGEARBOSS_SOLENOID_CHANNEL_B);

        return new SnobotGearBoss(gearSolonoid, operatorJoystick, logger);
    }
}
