package com.snobot2017;

import java.text.SimpleDateFormat;

import com.snobot.lib.ASnobot;
import com.snobot2017.autonomous.AutonomousFactory;
import com.snobot2017.climbing.Climbing;
import com.snobot2017.climbing.IClimbing;
import com.snobot2017.drivetrain.IDriveTrain;
import com.snobot2017.drivetrain.SnobotDriveTrain;
import com.snobot2017.gearboss.IGearBoss;
import com.snobot2017.gearboss.SnobotGearBoss;
import com.snobot2017.joystick.IDriverJoystick;
import com.snobot2017.joystick.IOperatorJoystick;
import com.snobot2017.joystick.SnobotDriveXbaxJoystick;
import com.snobot2017.joystick.SnobotOperatorXbaxJoystick;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class Snobot2017 extends ASnobot
{
    // Robot Subystems
    private IDriveTrain mDriveTrain;

    // Autonomous
    private AutonomousFactory mAutonFactory;

    // Climb
    private IClimbing mClimber;

    // GearBoss
    private IGearBoss mGearBoss;

    public Snobot2017()
    {
        super(new SimpleDateFormat("yyyyMMdd_hhmmssSSS"), Properties2017.sLOG_COUNT.getValue(), Properties2017.sLOG_FILE_PATH.getValue());

        // Autonomous
        mAutonFactory = new AutonomousFactory(this);

        // Joystick
        Joystick driverJostickRaw = new Joystick(0);
        Joystick operatorJoystickRaw = new Joystick(1);

        IDriverJoystick mDriverJoystick = new SnobotDriveXbaxJoystick(driverJostickRaw);
        mSubsystems.add(mDriverJoystick);

        IOperatorJoystick operatorJoystick = new SnobotOperatorXbaxJoystick(operatorJoystickRaw);
        mSubsystems.add(operatorJoystick);

        // Drive Train
        SpeedController driveLeftMotorA = new Talon(PortMappings2017.sDRIVE_PWM_LEFT_A_PORT);
        SpeedController driveLeftMotorB = new Talon(PortMappings2017.sDRIVE_PWM_LEFT_B_PORT);
        SpeedController driveRightMotorA = new Talon(PortMappings2017.sDRIVE_PWM_RIGHT_A_PORT);
        SpeedController driveRightMotorB = new Talon(PortMappings2017.sDRIVE_PWM_RIGHT_B_PORT);
        Encoder mLeftDriveEncoder = new Encoder(PortMappings2017.sLEFT_DRIVE_ENCODER_PORT_A, PortMappings2017.sLEFT_DRIVE_ENCODER_PORT_B);
        Encoder mRightDriveEncoder = new Encoder(PortMappings2017.sRIGHT_DRIVE_ENCODER_PORT_A, PortMappings2017.sRIGHT_DRIVE_ENCODER_PORT_B);

        mDriveTrain = new SnobotDriveTrain(
                driveLeftMotorA, 
                driveLeftMotorB, 
                driveRightMotorA, 
                driveRightMotorB, 
                mDriverJoystick, 
                mLogger,
                mLeftDriveEncoder, 
                mRightDriveEncoder);
        mSubsystems.add(mDriveTrain);

        // Climbing
        SpeedController climbingMotor = new Talon(PortMappings2017.sCLIMB_PWM_PORT);
        mClimber = new Climbing(climbingMotor, mLogger, operatorJoystick);
        mSubsystems.add(mClimber);

        // GearsBoss
        Solenoid gearSolonoid = new Solenoid(PortMappings2017.sGEARBOSS_SOLENOID_CHANNEL);
        mGearBoss = new SnobotGearBoss(gearSolonoid, operatorJoystick);
        mSubsystems.add(mGearBoss);
    }

    @Override
    protected CommandGroup createAutonomousCommand()
    {
        return mAutonFactory.createAutonMode();
    }

    /**
     * Returns the class that controls the robots drivetrain
     * 
     * @return The robots drivetrain
     */
    public IDriveTrain getDriveTrain()
    {
        return this.mDriveTrain;
    }
}
