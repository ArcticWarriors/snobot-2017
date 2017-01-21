package com.snobot2017;

import java.text.SimpleDateFormat;

import com.snobot.lib.ASnobot;
import com.snobot2017.autonomous.AutonomousFactory;
import com.snobot2017.drivetrain.IDriveTrain;
import com.snobot2017.drivetrain.SnobotDriveTrain;
import com.snobot2017.joystick.IDriverJoystick;
import com.snobot2017.joystick.SnobotDriveXbaxJoystick;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class Snobot2017 extends ASnobot
{
    // Robot Subystems
    private IDriveTrain mDriveTrain;

    // Autonomous
    private AutonomousFactory mAutonFactory;

    // Joystick
    private IDriverJoystick mDriverJoystick;

    public Snobot2017()
    {
        super(new SimpleDateFormat("yyyyMMdd_hhmmssSSS"), Properties2017.sLOG_COUNT.getValue(), Properties2017.sLOG_FILE_PATH.getValue());

        // Drivetrain
        Encoder rightDriveEncoder;
        Encoder leftDriveEncoder;
        SpeedController rightDriveMotor;
        SpeedController leftDriveMotor;

        // Autonomous
        mAutonFactory = new AutonomousFactory(this);
        
        //Joystick
        Joystick driverXbax = new Joystick(0);
        mDriverJoystick = new SnobotDriveXbaxJoystick(driverXbax); 
        mSubsystems.add(mDriverJoystick);
        
        //Drive Train
        SpeedController mFrontLeftMotor = new Talon(1);
        SpeedController mRearLeftMotor = new Talon(2);
        SpeedController mFrontRightMotor = new Talon(3);
        SpeedController mRearRightMotor = new Talon(4);
        Encoder mLeftDriveEncoder = new Encoder(1, 2);
        Encoder mRightDriveEncoder = new Encoder(3, 4);
        
        
        
        mDriveTrain = new SnobotDriveTrain(mFrontLeftMotor, mRearLeftMotor, mFrontRightMotor, mRearRightMotor, mDriverJoystick, mLogger, mLeftDriveEncoder, mRightDriveEncoder);
        mSubsystems.add(mDriveTrain);
        
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
