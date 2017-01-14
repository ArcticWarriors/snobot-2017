package com.snobot2016;

import java.text.SimpleDateFormat;

import com.snobot.xlib.ASnobot;
import com.snobot2016.autonomous.AutonFactory;
import com.snobot2016.camera.Camera;
import com.snobot2016.drivetrain.IDriveTrain;
import com.snobot2016.drivetrain.SnobotDriveTrainWithCan;
import com.snobot2016.drivetrain.SnobotDriveTrainWithEncoders;
import com.snobot2016.harvester.Harvester;
import com.snobot2016.harvester.IHarvester;
import com.snobot2016.joystick.SnobotDriveXboxJoystick;
import com.snobot2016.joystick.SnobotOperatorJoystick;
import com.snobot2016.light.Light;
import com.snobot2016.positioner.IPositioner;
import com.snobot2016.positioner.Positioner;
import com.snobot2016.positioner.SimplePositioner;
import com.snobot2016.scaling.IScaling;
import com.snobot2016.scaling.Scaling;

import edu.wpi.first.wpilibj.ADXL362;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.vision.AxisCamera;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Snobot extends ASnobot
{
    // Drivetrain
    private IDriveTrain mDrivetrain;
    private IScaling mScaling;
    private IHarvester mHarvester;
    private IPositioner mSnobotPositioner;
    private Camera mCamera;

    // Autonomous
    private CommandGroup mAutonCommand;
    private AutonFactory mAutonFactory;

    public Snobot()
    {
        super(new SimpleDateFormat("yyyyMMdd_hhmmssSSS"));

        // Raw Joysticks
        Joystick mRawDriverJoystick = new Joystick(Properties2016.sDRIVER_JOYSTICK_PORT);
        // Joystick mRawDriverJoystick2 = new
        // Joystick(Properties2016.sDRIVER_JOYSTICK_PORT2);
        Joystick mRawOperatorJoystick = new Joystick(Properties2016.sOPERATOR_JOYSTICK_PORT);

        // Our Joysticks
        SnobotDriveXboxJoystick mDriverXbox = new SnobotDriveXboxJoystick(mRawDriverJoystick);
        SnobotOperatorJoystick mOperatorJoystick = new SnobotOperatorJoystick(mRawOperatorJoystick);
        // mDriverFlightStick = new SnobotDriveFlightStick(mRawDriverJoystick,
        // mRawDriverJoystick2);
        // mArcadeJoystick = new SnobotDriveArcadeJoystick(mRawDriverJoystick);
        // mJoystickFactory = new SnobotDriveJoystickFactory(mDriverXbox,
        // mDriverFlightStick, mArcadeJoystick, mLogger);
        mSubsystems.add(mDriverXbox);
        mSubsystems.add(mOperatorJoystick);
        // mSubsystems.add(mDriverFlightStick);
        // mSubsystems.add(mArcadeJoystick);
        // mSubsystems.add(mJoystickFactory);

        // Drive train
        if (Properties2016.sIS_REAL_ROBOT)
        {
            CANTalon leftMotorA = new CANTalon(Properties2016.sDRIVE_CAN_LEFT_A_PORT);
            CANTalon leftMotorB = new CANTalon(Properties2016.sDRIVE_CAN_LEFT_B_PORT);
            CANTalon rightMotorA = new CANTalon(Properties2016.sDRIVE_CAN_RIGHT_A_PORT);
            CANTalon rightMotorB = new CANTalon(Properties2016.sDRIVE_CAN_RIGHT_B_PORT);
            mDrivetrain = new SnobotDriveTrainWithCan(leftMotorA, leftMotorB, rightMotorA, rightMotorB, mDriverXbox, mLogger);
            mSubsystems.add(mDrivetrain);
        }
        else
        {
            Encoder leftDriveEncoder = new Encoder(Properties2016.sLEFT_DRIVE_ENCODER_PORT_A, Properties2016.sLEFT_DRIVE_ENCODER_PORT_B);
            Encoder rightDriveEncoder = new Encoder(Properties2016.sRIGHT_DRIVE_ENCODER_PORT_A, Properties2016.sRIGHT_DRIVE_ENCODER_PORT_B);
            Talon driveLeftMotor = new Talon(Properties2016.sDRIVER_LEFT_MOTOR_PORT);
            Talon driveRightMotor = new Talon(Properties2016.sDRIVER_RIGHT_MOTOR_PORT);
            mDrivetrain = new SnobotDriveTrainWithEncoders(driveLeftMotor, null, driveRightMotor, null, leftDriveEncoder, rightDriveEncoder,
                    mDriverXbox, mLogger);
            mSubsystems.add(mDrivetrain);
        }

        // Scaling
        Talon mScaleMoveMotor = new Talon(Properties2016.sSCALE_MOVE_MOTOR_PORT);
        Talon mScaleTiltMotor = new Talon(Properties2016.sSCALE_TILT_MOTOR_PORT);
        AnalogInput mScalePot = new AnalogInput(Properties2016.sSCALE_POT_PORT);
        AnalogInput mExtensionPot = new AnalogInput(Properties2016.sEXTENSION_POT_PORT);
        mScaling = new Scaling(mScaleMoveMotor, mScaleTiltMotor, mOperatorJoystick, mLogger, mScalePot, mExtensionPot);
        mSubsystems.add(mScaling);

        // Harvester
        Talon mHarvesterPivotMotor = new Talon(Properties2016.sHARVESTER_PIVOT_MOTOR_PORT);
        Talon mHarvesterRollerMotor = new Talon(Properties2016.sHARVESTER_ROLLER_MOTOR_PORT);
        AnalogInput mHarvesterPot = new AnalogInput(Properties2016.sHARVESTER_POT_PORT);
        mHarvester = new Harvester(mHarvesterRollerMotor, mHarvesterPivotMotor, mOperatorJoystick, mLogger, mHarvesterPot);
        mSubsystems.add(mHarvester);

        // Camera
        Relay mCameraRelay = new Relay(Properties2016.sLIGHT_RELAY);
        Light mCameraLight = new Light(mCameraRelay, mLogger);
        mSubsystems.add(mCameraLight);

        if (Properties2016.sENABLE_CAMERA.getValue())
        {
            System.out.println("Enabling camera");
            AxisCamera mAxisCamera = new AxisCamera(Properties2016.sCAMERA_HOST_IP.getValue());
            mAxisCamera.writeBrightness(10);
            mCamera = new Camera(mAxisCamera);

        }
        else
        {
            System.out.println("Not enabling camera");
        }

        // Positioner
        Gyro mGyro;
        if (Properties2016.sUSE_SPI_GYRO)
        {
            mGyro = new ADXRS450_Gyro();
        }
        else
        {
            mGyro = new AnalogGyro(Properties2016.sGYRO_SENSOR_PORT);
        }
        if (Properties2016.sUSE_IMU_POSITIONER)
        {
            // ADXL362 mAccelerometer = new ADXL362(Accelerometer.Range.k2G);
            // mSnobotPositioner = new IMUPositioner(mGyro, mAccelerometer,
            // mDrivetrain, mLogger);

            ADXL362 mAccelerometer = new ADXL362(Accelerometer.Range.k2G);
            mSnobotPositioner = new Positioner(mGyro, mDrivetrain, mLogger, mAccelerometer);
        }
        else
        {
            mSnobotPositioner = new SimplePositioner(mGyro, mDrivetrain, mLogger);
        }
        mSubsystems.add(mSnobotPositioner);

        // Autonomous
        mAutonFactory = new AutonFactory(this.getPositioner(), this);
    }

    @Override
    public void robotInit()
    {
        init();
    }

    @Override
    public void autonomousInit()
    {
        mAutonCommand = mAutonFactory.buildAnAuton();
        mAutonCommand.start();
    }

    public void autonomousPeriodic()
    {
        Timer.delay(.005);
        super.autonomousPeriodic();
    }

    @Override
    public void teleopInit()
    {
        if (mAutonCommand != null)
        {
            mAutonCommand.cancel();
            Scheduler.getInstance().run();
        }
    }

    public IDriveTrain getDriveTrain()
    {
        return this.mDrivetrain;
    }

    public IPositioner getPositioner()
    {
        return this.mSnobotPositioner;
    }

    public IHarvester getHarvester()
    {
        return this.mHarvester;
    }

    public IScaling getScaling()
    {
        return this.mScaling;
    }
}
