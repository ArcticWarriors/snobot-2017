package com.snobot2017;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.snobot.lib.ASnobot;
import com.snobot2017.autologger.AutoLogger;
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

    // Logger
    private AutoLogger mAutoLogger;
    private DateFormat mAutoLogDateFormat;

    public Snobot2017()
    {
        super(new SimpleDateFormat("yyyyMMdd_hhmmssSSS"), Properties2017.sLOG_COUNT.getValue(), Properties2017.sLOG_FILE_PATH.getValue());

        mAutoLogDateFormat = new SimpleDateFormat("yyyyMMdd_hhmmssSSS");
        String headerDate = mAutoLogDateFormat.format(new Date());
        mAutoLogger = new AutoLogger(headerDate, Properties2017.sAUTO_LOG_COUNT.getValue(), Properties2017.sAUTO_LOG_FILE_PATH.getValue());
        // Drivetrain
        Encoder rightDriveEncoder;
        Encoder leftDriveEncoder;
        SpeedController rightDriveMotor;
        SpeedController leftDriveMotor;

        // Autonomous
        mAutonFactory = new AutonomousFactory(this);

        // Joystick
        Joystick driverXbax = new Joystick(0);
        mDriverJoystick = new SnobotDriveXbaxJoystick(driverXbax);
        mSubsystems.add(mDriverJoystick);

        // Drive Train
        SpeedController mFrontLeftMotor = new Talon(PortMappings2017.sDRIVE_PWM_LEFT_A_PORT);
        SpeedController mRearLeftMotor = new Talon(PortMappings2017.sDRIVE_PWM_LEFT_B_PORT);
        SpeedController mFrontRightMotor = new Talon(PortMappings2017.sDRIVE_PWM_RIGHT_A_PORT);
        SpeedController mRearRightMotor = new Talon(PortMappings2017.sDRIVE_PWM_RIGHT_B_PORT);
        Encoder mLeftDriveEncoder = new Encoder(PortMappings2017.sLEFT_DRIVE_ENCODER_PORT_A, PortMappings2017.sLEFT_DRIVE_ENCODER_PORT_B);
        Encoder mRightDriveEncoder = new Encoder(PortMappings2017.sRIGHT_DRIVE_ENCODER_PORT_A, PortMappings2017.sRIGHT_DRIVE_ENCODER_PORT_B);

        mDriveTrain = new SnobotDriveTrain(mFrontLeftMotor, mRearLeftMotor, mFrontRightMotor, mRearRightMotor, mDriverJoystick, mLogger,
                mLeftDriveEncoder, mRightDriveEncoder, mAutoLogger);
        mSubsystems.add(mDriveTrain);

    }

    public void init()
    {
        mAutoLogger.init();
        super.init();
        mAutoLogger.endHeader();

    }

    public void updateLog()
    {
        super.updateLog();
        String logDate = mAutoLogDateFormat.format(new Date());
        if (mAutoLogger.logNow())
        {
            mAutoLogger.startLogEntry(logDate);

            mAutoLogger.endLogger();
        }
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
