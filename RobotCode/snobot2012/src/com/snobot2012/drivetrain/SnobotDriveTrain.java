package com.snobot2012.drivetrain;

import com.snobot.lib.modules.ISubsystem;
import com.snobot2012.SmartDashboardNames;
import com.snobot2012.ui.DriverJoystick;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Sets up specific snobot drive train
 * 
 * @author Ayush/Ammar
 *
 */
public class SnobotDriveTrain implements ISubsystem
{

    private SpeedController mSpeedControllerLeft;
    private SpeedController mSpeedControllerRight;
    private RobotDrive mRobotDrive;
    private DriverJoystick mJoystick;
    
    /**
     * Takes 2 speed controllers and joy stick arguments
     * 
     * @param aSpeedControllerLeft
     *            Argument for left Speed Controller
     * @param aSpeedControllerRight
     *            Argument for right Speed Controller
     * @param aDriverJoystick
     *            Argument Driver Joy stick
     */
    public SnobotDriveTrain(
    		SpeedController aSpeedControllerLeft, 
    		SpeedController aSpeedControllerRight,
            DriverJoystick aDriverJoystick)
    {
        mSpeedControllerLeft = aSpeedControllerLeft;
        mSpeedControllerRight = aSpeedControllerRight;
        mRobotDrive = new RobotDrive(mSpeedControllerLeft, mSpeedControllerRight);
        mJoystick = aDriverJoystick;

        mRobotDrive.setSafetyEnabled(false);
    }

    @Override
    public void initializeLogHeaders()
    {

    }

    @Override
    public void update()
    {

    }

    @Override
    public void control()
    {
        mRobotDrive.setLeftRightMotorOutputs(mJoystick.getLeftThrottle(), mJoystick.getRightThrottle());
    }

    @Override
    public void updateSmartDashboard()
    {
        SmartDashboard.putNumber(SmartDashboardNames.LEFT_DRIVE_SPEED, mSpeedControllerLeft.get());
        SmartDashboard.putNumber(SmartDashboardNames.RIGHT_DRIVE_SPEED, mSpeedControllerRight.get());
    }

    @Override
    public void updateLog()
    {
    }

    @Override
    public void stop()
    {
        mRobotDrive.setLeftRightMotorOutputs(0, 0);
    }
}
