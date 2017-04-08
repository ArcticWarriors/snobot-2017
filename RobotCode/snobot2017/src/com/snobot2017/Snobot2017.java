package com.snobot2017;

import java.text.SimpleDateFormat;
import java.util.logging.LogManager;

import com.snobot.lib.ASnobot;
import com.snobot.lib.logging.ILogger;
import com.snobot.lib.logging.LogFormatter;
import com.snobot2017.SnobotActor.ISnobotActor;
import com.snobot2017.SnobotActor.SnobotActor;
import com.snobot2017.autologger.AutoLogger;
import com.snobot2017.autonomous.AutonomousFactory;
import com.snobot2017.climbing.IClimbing;
import com.snobot2017.drivetrain.IDriveTrain;
import com.snobot2017.gearboss.IGearBoss;
import com.snobot2017.joystick.SnobotDriveXbaxJoystick;
import com.snobot2017.joystick.SnobotOperatorXbaxJoystick;
import com.snobot2017.light_manager.LightManager;
import com.snobot2017.positioner.IPositioner;
import com.snobot2017.positioner.Positioner;
import com.snobot2017.vision.VisionManager;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class Snobot2017 extends ASnobot implements ISnobot2017
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

    // SnobotActor
    private ISnobotActor mSnobotActor;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        ModuleFactory moduleFactory = new ModuleFactory();
        LogManager.getLogManager().getLogger("").getHandlers()[0].setFormatter(new LogFormatter());

        ILogger logger = getLogger();

        ////////////////////////////////////////////////////////////
        // Initialize joysticks
        ////////////////////////////////////////////////////////////
        Joystick driverJoystickRaw = new Joystick(0);
        Joystick operatorJoystickRaw = new Joystick(1);

        SnobotDriveXbaxJoystick driverJoystick = new SnobotDriveXbaxJoystick(driverJoystickRaw, logger);
        addModule(driverJoystick);

        SnobotOperatorXbaxJoystick operatorJoystick = new SnobotOperatorXbaxJoystick(operatorJoystickRaw, logger);
        addModule(operatorJoystick);

        ////////////////////////////////////////////////////////////
        // Initialize subsystems
        ////////////////////////////////////////////////////////////
        mDriveTrain = moduleFactory.createDrivetrain(driverJoystick, logger);
        addModule(mDriveTrain);

        // Climbing
        mClimber = moduleFactory.createClimber(operatorJoystick, logger);
        addModule(mClimber);

        // GearBoss
        mGearBoss = moduleFactory.createGearBoss(operatorJoystick, logger);
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
        mVisionManager = new VisionManager(mPositioner, mSnobotActor, driverJoystick, this);
        addModule(mVisionManager);

        // LED Manager
        Relay greenLight = new Relay(PortMappings2017.sRELAY_GREEN_LED);
        Relay blueLight = new Relay(PortMappings2017.sRELAY_BLUE_LED);
        mLightManager = new LightManager(operatorJoystick, mSnobotActor, mVisionManager, greenLight, blueLight);
        addModule(mLightManager);

        // Autolog
        mAutoLogger = new AutoLogger(Properties2017.sAUTO_LOG_FILE_PATH.getValue(), driverJoystick, mDriveTrain);
        addModule(mAutoLogger);

        ////////////////////////////////////////////////////////////
        // Initialize Autonomous
        ////////////////////////////////////////////////////////////
        mAutonFactory = new AutonomousFactory(this, driverJoystick);

        // Call last
        logger.startLogging(new SimpleDateFormat("yyyyMMdd_hhmmssSSS"), Properties2017.sLOG_FILE_PATH.getValue());
        initializeLogHeaders();
    }

    @Override
    public void teleopInit()
    {
        super.teleopInit();
        mSnobotActor.cancelAction();
        mGearBoss.moveGearHigh();
        mVisionManager.startRecordingImages("tele");
    }

    @Override
    public void autonomousInit()
    {
        super.autonomousInit();
        mGearBoss.moveGearHigh();
        mVisionManager.startRecordingImages("auto");
    }

    @Override
    public void disabledInit()
    {
        super.disabledInit();
        mVisionManager.stopRecordingImages();
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
