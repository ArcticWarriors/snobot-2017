package com.snobot2017;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogManager;

import com.ctre.CANTalon;
import com.snobot.lib.ASnobot;
import com.snobot.lib.LogFormatter;
import com.snobot2017.autologger.AutoLogger;
import com.snobot2017.autonomous.AutonomousFactory;
import com.snobot2017.climbing.Climbing;
import com.snobot2017.climbing.IClimbing;
import com.snobot2017.drivetrain.IDriveTrain;
import com.snobot2017.drivetrain.SnobotCanDriveTrain;
import com.snobot2017.drivetrain.SnobotDriveTrain;
import com.snobot2017.gearboss.IGearBoss;
import com.snobot2017.gearboss.SnobotGearBoss;
import com.snobot2017.joystick.IDriverJoystick;
import com.snobot2017.joystick.SnobotDriveXbaxJoystick;
import com.snobot2017.joystick.SnobotOperatorXbaxJoystick;
import com.snobot2017.positioner.IPositioner;
import com.snobot2017.positioner.Positioner;
import com.snobot2017.vision.VisionManager;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class Snobot2017 extends ASnobot
{
    // Robot Subsystems
    private IDriveTrain mDriveTrain;
    private IPositioner mPositioner;

    // Autonomous
    private AutonomousFactory mAutonFactory;

    // Climb
    private IClimbing mClimber;

    // GearBoss
    private IGearBoss mGearBoss;

    // Vision
    private VisionManager mVisionManager;

    // Logger
    private AutoLogger mAutoLogger;
    private DateFormat mAutoLogDateFormat;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        LogManager.getLogManager().getLogger("").getHandlers()[0].setFormatter(new LogFormatter());



        // Joystick
        Joystick driverJoystickRaw = new Joystick(0);
        Joystick operatorJoystickRaw = new Joystick(1);

        IDriverJoystick driverJoystick = new SnobotDriveXbaxJoystick(driverJoystickRaw, mLogger);
        mSubsystems.add(driverJoystick);

        SnobotOperatorXbaxJoystick operatorJoystick = new SnobotOperatorXbaxJoystick(operatorJoystickRaw, mLogger);
        mSubsystems.add(operatorJoystick);
        
        //autolog
        mAutoLogDateFormat = new SimpleDateFormat("yyyyMMdd_hhmmssSSS");
        String headerDate = mAutoLogDateFormat.format(new Date());
        mAutoLogger = new AutoLogger(headerDate, Properties2017.sAUTO_LOG_COUNT.getValue(), Properties2017.sAUTO_LOG_FILE_PATH.getValue(), driverJoystickRaw, mDriveTrain);
        mSubsystems.add(mAutoLogger);
        
        // Drive Train
        boolean useCan = false;
        if (useCan)
        {
            CANTalon driveLeftMotorA = new CANTalon(PortMappings2017.sDRIVE_CAN_LEFT_A_PORT);
            CANTalon driveLeftMotorB = new CANTalon(PortMappings2017.sDRIVE_CAN_LEFT_B_PORT);
            CANTalon driveRightMotorA = new CANTalon(PortMappings2017.sDRIVE_CAN_RIGHT_A_PORT);
            CANTalon driveRightMotorB = new CANTalon(PortMappings2017.sDRIVE_CAN_RIGHT_B_PORT);

            mDriveTrain = new SnobotCanDriveTrain(
                    driveLeftMotorA, 
                    driveLeftMotorB, 
                    driveRightMotorA, 
                    driveRightMotorB, 
                    driverJoystick, 
                    mLogger,
                    mAutoLogger);
        }
        else
        {
            SpeedController driveLeftMotor = new VictorSP(PortMappings2017.sDRIVE_PWM_LEFT_A_PORT);
            SpeedController driveRightMotor = new VictorSP(PortMappings2017.sDRIVE_PWM_RIGHT_A_PORT);
            Encoder leftDriveEncoder = new Encoder(PortMappings2017.sLEFT_DRIVE_ENCODER_PORT_A, PortMappings2017.sLEFT_DRIVE_ENCODER_PORT_B);
            Encoder rightDriveEncoder = new Encoder(PortMappings2017.sRIGHT_DRIVE_ENCODER_PORT_A, PortMappings2017.sRIGHT_DRIVE_ENCODER_PORT_B);
    
            mDriveTrain = new SnobotDriveTrain(
                    driveLeftMotor, 
                    driveRightMotor,
                    leftDriveEncoder, 
                    rightDriveEncoder, 
                    driverJoystick, 
                    mLogger,
                    mAutoLogger);
        }
        mSubsystems.add(mDriveTrain);

        // Climbing
        SpeedController climbingMotor = new VictorSP(PortMappings2017.sCLIMB_PWM_PORT);
        mClimber = new Climbing(climbingMotor, mLogger, operatorJoystick);
        mSubsystems.add(mClimber);

        // GearBoss
        DoubleSolenoid gearSolonoid = new DoubleSolenoid(PortMappings2017.sGEARBOSS_SOLENOID_CHANNEL_A, PortMappings2017.sGEARBOSS_SOLENOID_CHANNEL_B);
        mGearBoss = new SnobotGearBoss(gearSolonoid, operatorJoystick, mLogger);
        mSubsystems.add(mGearBoss);

        // Vision
        mVisionManager = new VisionManager(operatorJoystick);
        mSubsystems.add(mVisionManager);

        // Positioner
        Gyro gyro = new ADXRS450_Gyro();
        mPositioner = new Positioner(gyro, mDriveTrain, mLogger);
        mSubsystems.add(mPositioner);

        // Autonomous
        mAutonFactory = new AutonomousFactory(this);

        // Call last
        mLogger.startLogging(
                new SimpleDateFormat("yyyyMMdd_hhmmssSSS"), 
                Properties2017.sLOG_COUNT.getValue(),
                Properties2017.sLOG_FILE_PATH.getValue());
        init();
    }
    		
    @Override
    public void init()
    {
        super.init();
    }
    
    PowerDistributionPanel pdp = new PowerDistributionPanel();
    
    @Override
    public void update()
    {
    	super.update();
    	System.out.println(pdp.getCurrent(13));
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
    
    /**
     * Returns the IGearBoss for the robot
     * 
     * @return IGearBoss
     */
    public IGearBoss getGearBoss()
    {
        return mGearBoss;
    }

    /**
     * Returns the Robots position
     * 
     * @return IPositioner
     */
    public IPositioner getPositioner()
    {
        return mPositioner;
    }
}
