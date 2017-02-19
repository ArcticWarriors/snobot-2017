package com.snobot2017.joystick;

import com.snobot.lib.logging.ILogger;
import com.snobot.lib.ui.LatchedButton;
import com.snobot.lib.ui.ToggleButton;
import com.snobot.lib.ui.XboxButtonMap;
import com.snobot2017.SmartDashBoardNames;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The operator joystick class
 * 
 * @author ayush
 *
 */
public class SnobotOperatorXbaxJoystick implements IOperatorJoystick, IVisionJoystick
{
    private Joystick mJoystick;
    private boolean mClimb;
    private boolean mCatch;
    private ILogger mLogger;

    // Gear Boss
    private GearBossPositions mGearBossPos;

    // Sphincter
    private boolean mSphincterIsOpen;

    // App stuff
    private LatchedButton mSwitchAppViewLatcher;
    private LatchedButton mSwitchToFrontCameraLatcher;
    private LatchedButton mSwitchToRearCameraLatcher;
    private LatchedButton mRestartAppLatcher;
    private boolean mSwitchAppView;
    private boolean mSwitchToFrontCamera;
    private boolean mSwitchToRearCamera;
    private boolean mRestartApp;

    // Relay
    private LatchedButton mToggleGreenLight;
    private boolean mGreenRelayOn = true;

    private LatchedButton mToggleBlueLight;
    private boolean mBlueRelayOn = true;

    // Snobot Actor Stuff
    private boolean mDriveToPeg;
    private ToggleButton mDriveToPegToggleButton;
    private boolean mDriveSmoothlyToPosition;
    private ToggleButton mDriveSmoothlyToPositionToggleButton;

    public SnobotOperatorXbaxJoystick(Joystick aJoystick, ILogger aLogger)
    {
        mJoystick = aJoystick;

        mSwitchAppViewLatcher = new LatchedButton();
        mSwitchToFrontCameraLatcher = new LatchedButton();
        mSwitchToRearCameraLatcher = new LatchedButton();
        mRestartAppLatcher = new LatchedButton();
        mToggleGreenLight = new LatchedButton();
        mToggleBlueLight = new LatchedButton();
        mLogger = aLogger;
        mDriveToPegToggleButton = new ToggleButton();
        mDriveSmoothlyToPositionToggleButton = new ToggleButton();
    }

    @Override
    public void initializeLogHeaders()
    {
        mLogger.addHeader("Climb");
        mLogger.addHeader("Catch");
        mLogger.addHeader("GearBossPosition");
    }

    @Override
    public void update()
    {
        // Gear Boss
        if (mJoystick.getRawButton(XboxButtonMap.B_BUTTON))
        {
            mGearBossPos = GearBossPositions.UP;
            mJoystick.setRumble(RumbleType.kLeftRumble, 0);
            mJoystick.setRumble(RumbleType.kRightRumble, 0);
        }
        else if (mJoystick.getRawButton(XboxButtonMap.A_BUTTON))
        {
            mGearBossPos = GearBossPositions.DOWN;
            mJoystick.setRumble(RumbleType.kLeftRumble, 1);
            mJoystick.setRumble(RumbleType.kRightRumble, 1);
        }
        else
        {
            mGearBossPos = GearBossPositions.NONE;
        }

        // Climb
        mClimb = mJoystick.getRawButton(XboxButtonMap.RB_BUTTON);
        mCatch = mJoystick.getRawButton(XboxButtonMap.LB_BUTTON);

        // Sphincter
        mSphincterIsOpen = mJoystick.getRawAxis(XboxButtonMap.LEFT_Y_AXIS) > .5;

        // Light
        if (mToggleGreenLight.update(mJoystick.getRawButton(XboxButtonMap.START_BUTTON)))
        {
            mGreenRelayOn = !mGreenRelayOn;
        }

        if (mToggleBlueLight.update(mJoystick.getRawButton(XboxButtonMap.BACK_BUTTON)))
        {
            mBlueRelayOn = !mBlueRelayOn;
        }

        // App
        mSwitchAppView = mSwitchAppViewLatcher.update(mJoystick.getRawButton(XboxButtonMap.A_BUTTON));
        mSwitchToFrontCamera = mSwitchToFrontCameraLatcher.update(mJoystick.getRawButton(XboxButtonMap.X_BUTTON));
        mSwitchToRearCamera = mSwitchToRearCameraLatcher.update(mJoystick.getRawButton(XboxButtonMap.Y_BUTTON));
        mRestartApp = mRestartAppLatcher.update(mJoystick.getRawButton(XboxButtonMap.B_BUTTON));

        // SnobotActor Stuff
        mDriveToPeg = mDriveToPegToggleButton.update(mJoystick.getRawButton(XboxButtonMap.START_BUTTON));
        mDriveSmoothlyToPosition = mDriveSmoothlyToPositionToggleButton.update(mJoystick.getRawButton(XboxButtonMap.BACK_BUTTON));
        // System.out.println("A BTN " +
        // mJoystick.getRawButton(XboxButtonMap.A_BUTTON));
        // System.out.println("B BTN " +
        // mJoystick.getRawButton(XboxButtonMap.B_BUTTON));
        // System.out.println("X BTN " +
        // mJoystick.getRawButton(XboxButtonMap.X_BUTTON));
        // System.out.println("Y BTN " +
        // mJoystick.getRawButton(XboxButtonMap.Y_BUTTON));
        // System.out.println("LB BTN " +
        // mJoystick.getRawButton(XboxButtonMap.LB_BUTTON));
        // System.out.println("RB BTN " +
        // mJoystick.getRawButton(XboxButtonMap.RB_BUTTON));
        // System.out.println("L3 BTN " +
        // mJoystick.getRawButton(XboxButtonMap.L3_BUTTON));
        // System.out.println("R3 BTN " +
        // mJoystick.getRawButton(XboxButtonMap.R3_BUTTON));
        // System.out.println("Start " +
        // mJoystick.getRawButton(XboxButtonMap.START_BUTTON));
        // System.out.println("Back " +
        // mJoystick.getRawButton(XboxButtonMap.BACK_BUTTON));
        //
        // System.out.println("LY " +
        // mJoystick.getRawAxis(XboxButtonMap.LEFT_Y_AXIS));
        // System.out.println("LX " +
        // mJoystick.getRawAxis(XboxButtonMap.LEFT_X_AXIS));
        // System.out.println("RY " +
        // mJoystick.getRawAxis(XboxButtonMap.RIGHT_Y_AXIS));
        // System.out.println("RX " +
        // mJoystick.getRawAxis(XboxButtonMap.RIGHT_X_AXIS));
        // System.out.println("LT " +
        // mJoystick.getRawAxis(XboxButtonMap.LEFT_TRIGGER));
        // System.out.println("RT " +
        // mJoystick.getRawAxis(XboxButtonMap.RIGHT_TRIGGER));
        //
        // System.out.println();
    }

    @Override
    public void updateSmartDashboard()
    {
        SmartDashboard.putBoolean(SmartDashBoardNames.sOPERATOR_JOYSTICK_IS_CLIMBING, mClimb);
        SmartDashboard.putBoolean(SmartDashBoardNames.sOPERATOR_JOYSTICK_IS_CATCHING, mCatch);
        SmartDashboard.putBoolean(SmartDashBoardNames.sOPERATOR_JOYSTICK_GREEN_LIGHT_BTN, mGreenRelayOn);
        SmartDashboard.putBoolean(SmartDashBoardNames.sOPERATOR_JOYSTICK_BLUE_LIGHT_BTN, mBlueRelayOn);
    }

    @Override
    public void updateLog()
    {
        mLogger.updateLogger(mClimb);
        mLogger.updateLogger(mCatch);
        mLogger.updateLogger(mGearBossPos.toString());
    }

    @Override
    public boolean greenLightOn()
    {
        return mGreenRelayOn;
    }

    @Override
    public boolean blueLightOn()
    {
        return mBlueRelayOn;
    }

    @Override
    public GearBossPositions moveGearBossToPosition()
    {
        return mGearBossPos;
    }

    @Override
    public boolean isCatchRope()
    {

        return mCatch;
    }

    @Override
    public boolean isClimb()
    {

        return mClimb;
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
    public boolean driveToPeg()
    {
        return mDriveToPeg;
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

    @Override
    public boolean DriveSmoothlyToPosition()
    {
        return mDriveSmoothlyToPosition;
    }

    @Override
    public boolean isPooperOpen()
    {
        return mSphincterIsOpen;
    }
}
