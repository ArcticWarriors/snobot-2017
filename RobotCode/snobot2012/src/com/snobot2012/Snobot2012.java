
package com.snobot2012;

import com.snobot.lib.ASnobot;
import com.snobot.lib.PropertyManager;
import com.snobot2012.drivetrain.SnobotDriveTrain;
import com.snobot2012.intake.SnobotIntake;
import com.snobot2012.shooter.SnobotShooter;
import com.snobot2012.ui.DriverJoystick;
import com.snobot2012.ui.OperatorJoystick;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Snobot2012 extends ASnobot
{

	//Shooter
	private SpeedController mShooterMotor;
	private Solenoid mShooterSolenoid; 
	private SnobotShooter mShooter;
	
	//Drive Train
	private SpeedController mLeftMotor;
	private SpeedController mRightMotor;
	private SnobotDriveTrain mDriveTrain;
	
    // Intake
    private SpeedController mUpperIntakeMotor;
    private SpeedController mLowerIntakeMotor;
    private SnobotIntake mIntake;

    // UI
    private Joystick mShooterJoystick;
    private Joystick mDriveJoystick;
    private DriverJoystick mDriverController;
    private OperatorJoystick mOperatorController;

    /**
     * This function is run when the robot is first started up and should be used for any initialization code.
     */
    @Override
    public void robotInit()
    {
        // UI
        mShooterJoystick = new Joystick(PortMap.sOPERATOR_JOYSTICK_PORT);
        mDriveJoystick = new Joystick(PortMap.sDRIVER_JOYSTICK_PORT);
        mDriverController = new DriverJoystick(mDriveJoystick);
        mOperatorController = new OperatorJoystick(mShooterJoystick);

    	//Shooter
        mShooterMotor = new Talon(PortMap.sSHOOTER_MOTOR);
        mShooterSolenoid = new Solenoid(PortMap.sSHOOTER_PISTON);
        mShooter = new SnobotShooter(mShooterMotor, mShooterSolenoid, mOperatorController);
    	
    	//Drive Train
        mLeftMotor = new Talon(PortMap.sLEFT_DRIVE_MOTOR);
        mRightMotor = new Talon(PortMap.sRIGHT_DRIVE_MOTOR);
        mDriveTrain = new SnobotDriveTrain(mLeftMotor, mRightMotor, mDriverController);
    	
        // Intake
        mUpperIntakeMotor = new Talon(PortMap.sUPPER_INTAKE_MOTOR);
        mLowerIntakeMotor = new Talon(PortMap.sLOWER_INTAKE_MOTOR);
        mIntake = new SnobotIntake(mLowerIntakeMotor, mUpperIntakeMotor, mOperatorController);

        addModule(mDriveTrain);
        addModule(mShooter);
        addModule(mIntake);

        initializeLogHeaders();

        // Now all the preferences should be loaded, make sure they are all
        // saved
        PropertyManager.saveIfUpdated();
    }

    @Override
    public void updateLog()
    {
        // Nothing to do....
    }

    @Override
    protected CommandGroup createAutonomousCommand()
    {
        return null;
    }
    
}
