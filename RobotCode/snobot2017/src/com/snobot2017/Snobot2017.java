package com.snobot2017;

import java.text.SimpleDateFormat;

import com.snobot.lib.ASnobot;
import com.snobot2017.autonomous.AutonomousFactory;
import com.snobot2017.drivetrain.IDriveTrain;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class Snobot2017 extends ASnobot
{
    // Robot Subystems
    private IDriveTrain mDrivetrain;

    // Autonomous
    private AutonomousFactory mAutonFactory;


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
        return this.mDrivetrain;
    }
}
