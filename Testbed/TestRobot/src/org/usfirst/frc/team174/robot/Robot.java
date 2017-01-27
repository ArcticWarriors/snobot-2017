package org.usfirst.frc.team174.robot;

import com.ctre.CANTalon;
import com.snobot.lib.ui.XboxButtonMap;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

    private SpeedController driveLeftA;
    private SpeedController driveLeftB;
    private SpeedController driveRightA;
    private SpeedController driveRightB;

    private SpeedController climberMotorA;
    private SpeedController climberMotorB;

    private RobotDrive drive;

    private Joystick joystick;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {

        driveLeftA = new CANTalon(2);
        driveLeftB = new CANTalon(1);
        driveRightA = new CANTalon(3);
        driveRightB = new CANTalon(4);

        climberMotorA = new Talon(1);
        climberMotorB = new Talon(2);

        drive = new RobotDrive(driveLeftA, driveLeftB, driveRightA, driveRightB);

        joystick = new Joystick(0);

        SmartDashboard.putNumber("SlowClimber", .5);
        SmartDashboard.putNumber("FastClimber", 1);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {

	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {

	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {

        drive.setLeftRightMotorOutputs(-joystick.getRawAxis(XboxButtonMap.LEFT_Y_AXIS), -joystick.getRawAxis(XboxButtonMap.RIGHT_Y_AXIS));

        double motorSpeed = 0;

        if (joystick.getRawButton(XboxButtonMap.Y_BUTTON))
        {
            motorSpeed = SmartDashboard.getNumber("FastClimber", 1);
        }
        else if (joystick.getRawButton(XboxButtonMap.A_BUTTON))
        {
            motorSpeed = SmartDashboard.getNumber("SlowClimber", .5);
        }

        System.out.println(motorSpeed);
        climberMotorA.set(-motorSpeed);
        climberMotorB.set(-motorSpeed);
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

