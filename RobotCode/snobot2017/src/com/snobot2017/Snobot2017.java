package com.snobot2017;

import java.text.SimpleDateFormat;
import java.util.logging.LogManager;

import com.ctre.CANTalon;
import com.snobot.lib.ASnobot;
import com.snobot.lib.logging.ILogger;
import com.snobot.lib.logging.LogFormatter;
import com.snobot2017.SnobotActor.ISnobotActor;
import com.snobot2017.SnobotActor.SnobotActor;
import com.snobot2017.autologger.AutoLogger;
import com.snobot2017.autonomous.AutonomousFactory;
import com.snobot2017.climbing.Climbing;
import com.snobot2017.climbing.IClimbing;
import com.snobot2017.drivetrain.IDriveTrain;
import com.snobot2017.drivetrain.SnobotCanDriveTrain;
import com.snobot2017.drivetrain.SnobotDriveTrain;
import com.snobot2017.fuel_pooper.FuelPooper;
import com.snobot2017.fuel_pooper.IFuelPooper;
import com.snobot2017.gearboss.IGearBoss;
import com.snobot2017.gearboss.SnobotGearBoss;
import com.snobot2017.joystick.SnobotDriveXbaxJoystick;
import com.snobot2017.joystick.SnobotOperatorXbaxJoystick;
import com.snobot2017.light_manager.LightManager;
import com.snobot2017.positioner.IPositioner;
import com.snobot2017.positioner.Positioner;
import com.snobot2017.vision.VisionManager;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedController;
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
    private LightManager mLightManager;

    // Logger
    private AutoLogger mAutoLogger;

    // Sphincter
    private IFuelPooper mPooper;

    // SnobotActor
    private ISnobotActor mSnobotActor;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        LogManager.getLogManager().getLogger("").getHandlers()[0].setFormatter(new LogFormatter());

        ILogger logger = getLogger();

        ////////////////////////////////////////////////////////////
        // Initialize joysticks
        ////////////////////////////////////////////////////////////
        Joystick driverJoystickRaw = new Joystick(0);
        Joystick operatorJoystickRaw = new Joystick(1);

        SnobotDriveXbaxJoystick driverJoystick = new SnobotDriveXbaxJoystick(driverJoystickRaw, logger, mAutonFactory);
        addModule(driverJoystick);

        SnobotOperatorXbaxJoystick operatorJoystick = new SnobotOperatorXbaxJoystick(operatorJoystickRaw, logger);
        addModule(operatorJoystick);

        ////////////////////////////////////////////////////////////
        // Initialize subsystems
        ////////////////////////////////////////////////////////////

        // Drive Train
        boolean useCan = false;
        if (useCan)
        {
            CANTalon driveLeftMotorA = new CANTalon(PortMappings2017.sDRIVE_CAN_LEFT_A_PORT);
            CANTalon driveLeftMotorB = new CANTalon(PortMappings2017.sDRIVE_CAN_LEFT_B_PORT);
            CANTalon driveRightMotorA = new CANTalon(PortMappings2017.sDRIVE_CAN_RIGHT_A_PORT);
            CANTalon driveRightMotorB = new CANTalon(PortMappings2017.sDRIVE_CAN_RIGHT_B_PORT);

            mDriveTrain = new SnobotCanDriveTrain(driveLeftMotorA, driveLeftMotorB, driveRightMotorA, driveRightMotorB, driverJoystick, logger);
        }
        else
        {
            SpeedController driveLeftMotor = new VictorSP(PortMappings2017.sDRIVE_PWM_LEFT_A_PORT);
            SpeedController driveRightMotor = new VictorSP(PortMappings2017.sDRIVE_PWM_RIGHT_A_PORT);
            Encoder leftDriveEncoder = new Encoder(PortMappings2017.sLEFT_DRIVE_ENCODER_PORT_A, PortMappings2017.sLEFT_DRIVE_ENCODER_PORT_B);
            Encoder rightDriveEncoder = new Encoder(PortMappings2017.sRIGHT_DRIVE_ENCODER_PORT_A, PortMappings2017.sRIGHT_DRIVE_ENCODER_PORT_B);

            mDriveTrain = new SnobotDriveTrain(driveLeftMotor, driveRightMotor, leftDriveEncoder, rightDriveEncoder, driverJoystick, logger);
        }
        addModule(mDriveTrain);

        // Climbing
        SpeedController climbingMotor = new VictorSP(PortMappings2017.sCLIMB_PWM_PORT);
        mClimber = new Climbing(climbingMotor, logger, operatorJoystick);
        addModule(mClimber);

        // GearBoss
        DoubleSolenoid gearSolonoid = new DoubleSolenoid(
                PortMappings2017.sGEARBOSS_SOLENOID_CHANNEL_A,
                PortMappings2017.sGEARBOSS_SOLENOID_CHANNEL_B);
        mGearBoss = new SnobotGearBoss(gearSolonoid, operatorJoystick, logger);
        addModule(mGearBoss);

        // Positioner
        Gyro gyro = new ADXRS450_Gyro();
        mPositioner = new Positioner(gyro, mDriveTrain, logger);
        addModule(mPositioner);

        // SnobotActor
        mSnobotActor = new SnobotActor(mDriveTrain, mPositioner);
        addModule(mSnobotActor);
        mDriveTrain.setSnobotActor(mSnobotActor);

        // Vision
        mVisionManager = new VisionManager(mPositioner, mSnobotActor, driverJoystick);
        addModule(mVisionManager);

        // LED Manager
        Relay greenLight = new Relay(PortMappings2017.sRELAY_GREEN_LED);
        Relay blueLight = new Relay(PortMappings2017.sRELAY_BLUE_LED);
        mLightManager = new LightManager(operatorJoystick, mSnobotActor, mVisionManager, greenLight, blueLight);
        addModule(mLightManager);

        // Sphincter
        DoubleSolenoid rightSphincter = new DoubleSolenoid(2, 3);
        DoubleSolenoid leftSphincter = new DoubleSolenoid(4, 5);
        mPooper = new FuelPooper(operatorJoystick, rightSphincter, leftSphincter, logger);
        addModule(mPooper);

        // Autolog
        mAutoLogger = new AutoLogger(Properties2017.sAUTO_LOG_FILE_PATH.getValue(), driverJoystick, mDriveTrain);
        addModule(mAutoLogger);

        ////////////////////////////////////////////////////////////
        // Initialize Autonomous
        ////////////////////////////////////////////////////////////
        mAutonFactory = new AutonomousFactory(this, driverJoystick);

        // Call last
        logger.startLogging(new SimpleDateFormat("yyyyMMdd_hhmmssSSS"),
                Properties2017.sLOG_FILE_PATH.getValue());
        initializeLogHeaders();
    }

    @Override
    public void teleopInit()
    {
        super.teleopInit();
        mSnobotActor.cancelAction();
    }
    
    @Override
    public void autonomousInit()
    {
        super.autonomousInit();
        mGearBoss.moveGearHigh();
    }
    
    @Override
    protected CommandGroup createAutonomousCommand()
    {
        return mAutonFactory.createAutonMode();
    }

    /**
     * Returns the class that controls the robots drivetrain
     * 
     * @return The robots drive train
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

    /**
     * Returns the SnobotActor
     * 
     * @return ISnobotActor
     */
    public ISnobotActor getSnobotActor()
    {
        return mSnobotActor;
    }

    public VisionManager getVisionManager()
    {
        return mVisionManager;
    }
}
