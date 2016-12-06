
package com.snobot.teaching;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
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
    // Outputs
    private SpeedController mTestMotor1;
    private SpeedController mTestMotor2;
    private DigitalOutput mDigitalOutput;
    private Solenoid mSolenoid;
    private Relay mRelay;

    // Inputs
    private Joystick mJoystick1;
    private Encoder mEncoder;

    // Utilities
    private Timer mTimer;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        mTestMotor1 = new Talon(0);
        mTestMotor2 = new Jaguar(1);
        mSolenoid = new Solenoid(0);
        mRelay = new Relay(0);
        mEncoder = new Encoder(4, 5);
        mDigitalOutput = new DigitalOutput(0);
        mJoystick1 = new Joystick(0);
        mTimer = new Timer();
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

        if (mJoystick1.getRawButton(1))
        {
            mTestMotor1.set(.5);
        }
        else
        {
            mTestMotor1.set(0);
        }

        SmartDashboard.putNumber("Motor 1", mTestMotor1.get());
    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic()
    {

    }
    
}
