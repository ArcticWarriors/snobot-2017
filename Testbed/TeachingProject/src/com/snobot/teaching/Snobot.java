
package com.snobot.teaching;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Snobot extends IterativeRobot
{
	//////////////////////////////////////
    // Outputs
	//////////////////////////////////////

	// PWM
    private SpeedController mTestMotor1;
    private SpeedController mTestMotor2;
	private Servo mServo;

	// Digital IO
    private DigitalOutput mDigitalOutput;

	// Solenoid
    private Solenoid mSolenoid;

	// Relay
    private Relay mRelay;

	//////////////////////////////////////
    // Inputs
	//////////////////////////////////////

	// Joysticks
    private Joystick mJoystick1;
    private XboxController mJoystick2;

	// Digital IO
    private Encoder mRightEncoder;
    private Encoder mLeftEncoder;
	private Ultrasonic mUltrasonic;

	// Analog IO
    private Gyro mAnalogGryo;
    private Gyro mSpiGryo;
    private Potentiometer mPotentiometer;

    // Utilities
    private PowerDistributionPanel mPDP;
    private Timer mTimer;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
		// PWM's
        mTestMotor1 = new Talon(0);
        mTestMotor2 = new Jaguar(1);
		mServo = new Servo(2);

		// Digital IO
        mDigitalOutput = new DigitalOutput(0);
		mRightEncoder = new Encoder(4, 5);
		mLeftEncoder = new Encoder(1, 2);
		mUltrasonic = new Ultrasonic(7, 6);

		// Analog IO
		mAnalogGryo = new AnalogGyro(0);
		mPotentiometer = new AnalogPotentiometer(1);

		// Solenoid
        mSolenoid = new Solenoid(0);

		// Relay
        mRelay = new Relay(0);

		// Joysticks
        mJoystick1 = new Joystick(0);
        mJoystick2 = new XboxController(1);

		// SPI
        mSpiGryo = new ADXRS450_Gyro();

		// Utilities
        mTimer = new Timer();
        mPDP = new PowerDistributionPanel();

		Preferences.getInstance().putDouble("Motor One Speed", .5);
    }

    public void autonomousInit()
    {
        mTimer.start();
    }

    public void autonomousPeriodic()
    {
        if (mTimer.get() < 2)
        {
            mRelay.set(Value.kForward);
        }
        else if (mTimer.get() < 4)
        {
            mRelay.set(Value.kReverse);
        }
        else
        {
            mRelay.set(Value.kOn);
        }
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic()
    {
		double speed = Preferences.getInstance().getDouble("Motor One Speed", .5);

        if (mJoystick1.getRawButton(1))
        {
			mTestMotor1.set(speed);
        }
        else
        {
            mTestMotor1.set(0);
        }

        SmartDashboard.putNumber("Motor 1", mTestMotor1.get());
        SmartDashboard.putNumber("Analog Angle", mAnalogGryo.getAngle());
        SmartDashboard.putNumber("SPI Angle", mSpiGryo.getAngle());
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic()
    {

    }
    
}
