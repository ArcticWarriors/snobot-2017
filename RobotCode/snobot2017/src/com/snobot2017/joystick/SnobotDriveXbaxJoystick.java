package com.snobot2017.joystick;

import com.snobot.lib.Utilities;
import com.snobot.lib.logging.ILogger;
import com.snobot.lib.ui.LatchedButton;
import com.snobot.lib.ui.ToggleButton;
import com.snobot.lib.ui.XboxButtonMap;
import com.snobot2017.SmartDashBoardNames;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SnobotDriveXbaxJoystick implements IDriverJoystick, IAutoLoggerJoystick, IVisionJoystick
{
	private static final double sJOYSTICK_DEADBAND = .032;
    private ILogger mLogger;
    private Joystick mJoystick;

    // Driving Stuff
    private double mLeftSpeed;
    private double mRightSpeed;

    // Snobot Actor Stuff
    private boolean mDriveToPeg;
    private boolean mDriveSmoothlyToPosition;
    private ToggleButton mDriveToPegToggleButton;
    private ToggleButton mDriveSmoothlyToPositionToggleButton;

    // App stuff
    private LatchedButton mSwitchAppViewLatcher;
    private LatchedButton mSwitchToFrontCameraLatcher;
    private LatchedButton mSwitchToRearCameraLatcher;
    private LatchedButton mRestartAppLatcher;
    private boolean mSwitchAppView;
    private boolean mSwitchToFrontCamera;
    private boolean mSwitchToRearCamera;
    private boolean mRestartApp;

    public SnobotDriveXbaxJoystick(Joystick aJoystick, ILogger aLogger)
    {

        mJoystick = aJoystick;
        mLogger = aLogger;

        mSwitchAppViewLatcher = new LatchedButton();
        mSwitchToFrontCameraLatcher = new LatchedButton();
        mSwitchToRearCameraLatcher = new LatchedButton();
        mRestartAppLatcher = new LatchedButton();
        mDriveToPegToggleButton = new ToggleButton();
        mDriveSmoothlyToPositionToggleButton = new ToggleButton();
    }

    @Override
    public void initializeLogHeaders()
    {
        mLogger.addHeader("XbaxLeftJoystickSpeed");
        mLogger.addHeader("XbaxRightJoystickSpeed");
    }

    @Override
    public void update()
    {
        // Drivetrain
        mRightSpeed = -Utilities.stopInDeadband(mJoystick.getRawAxis(XboxButtonMap.RIGHT_Y_AXIS), sJOYSTICK_DEADBAND);;
        mLeftSpeed = -Utilities.stopInDeadband(mJoystick.getRawAxis(XboxButtonMap.LEFT_Y_AXIS), sJOYSTICK_DEADBAND);;

        // App
        mSwitchAppView = mSwitchAppViewLatcher.update(mJoystick.getPOV() == 90);
        mSwitchToFrontCamera = mSwitchToFrontCameraLatcher.update(mJoystick.getPOV() == 0);
        mSwitchToRearCamera = mSwitchToRearCameraLatcher.update(mJoystick.getPOV() == 180);
        mRestartApp = mRestartAppLatcher.update(mJoystick.getPOV() == 270);
        
//        System.out.println("X " + mJoystick.getPOV() + ", " + mSwitchToFrontCamera + ", " + mSwitchToRearCamera);

        // SnobotActor Stuff
        mDriveToPeg = mDriveToPegToggleButton.update(mJoystick.getRawButton(XboxButtonMap.A_BUTTON));
        mDriveSmoothlyToPosition = mDriveSmoothlyToPositionToggleButton.update(mJoystick.getRawButton(XboxButtonMap.Y_BUTTON));

    }

    @Override
    public void updateSmartDashboard()
    {
        SmartDashboard.putNumber(SmartDashBoardNames.sLEFT_XBAX_JOYSTICK_SPEED, mLeftSpeed);
        SmartDashboard.putNumber(SmartDashBoardNames.sRIGHT_XBAX_JOYSTICK_SPEED, mRightSpeed);
//        SmartDashboard.putNumber(SmartDashBoardNames.sAUTON_NUM, mAutonFactory.autonMode());
    }

    @Override
    public void updateLog()
    {
        mLogger.updateLogger(mLeftSpeed);
        mLogger.updateLogger(mRightSpeed);
    }

    @Override
    public double getRightSpeed()
    {

        return mRightSpeed;
    }

    @Override
    public double getLeftSpeed()
    {

        return mLeftSpeed;
    }

    @Override
    public boolean isRecording()
    {
        return false;
    }

    @Override
    public boolean iterateAppView()
    {
        return mSwitchAppView;
    }

    @Override
    public boolean switchToFrontCamera()
    {
        return mSwitchToFrontCamera;
    }

    @Override
    public boolean switchToRearCamera()
    {
        return mSwitchToRearCamera;
    }

    @Override
    public boolean restartApp()
    {
        return mRestartApp;
    }

    @Override
    public boolean driveToPositionInSteps()
    {
        return mDriveToPeg;
    }

    @Override
    public boolean driveSmoothlyToPosition()
    {
        return mDriveSmoothlyToPosition;
    }

    @Override
    public void turnOffActions()
    {
        // If the drive to peg is true then we need to reset it.
        if (mDriveToPeg)
        {
            mDriveToPeg = mDriveToPegToggleButton.update(true);
        }
        if (mDriveSmoothlyToPosition)
        {
            mDriveSmoothlyToPosition = mDriveSmoothlyToPositionToggleButton.update(true);
        }
    }

}
