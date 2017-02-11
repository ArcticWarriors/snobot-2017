package com.snobot2017.joystick;

import com.snobot.lib.Logger;
import com.snobot.lib.ui.LatchedButton;
import com.snobot.lib.ui.XbaxButtonMap;
import com.snobot2017.SmartDashBoardNames;

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
    private Logger mLogger;

    // Gear Boss
    private GearBossPositions mGearBossPos;

    // Ready for take off
    private double mLiftOffSpeed;
    
    //App stuff
    private LatchedButton mSwitchAppViewLatcher;
    private LatchedButton mSwitchToFrontCameraLatcher;
    private LatchedButton mSwitchToRearCameraLatcher;
    private LatchedButton mRestartAppLatcher;
    private boolean mSwitchAppView;
    private boolean mSwitchToFrontCamera;
    private boolean mSwitchToRearCamera;
    private boolean mRestartApp;

    public SnobotOperatorXbaxJoystick(Joystick aJoystick, Logger aLogger)
    {
        mJoystick = aJoystick;

        mSwitchAppViewLatcher = new LatchedButton();
        mSwitchToFrontCameraLatcher = new LatchedButton();
        mSwitchToRearCameraLatcher = new LatchedButton();
        mRestartAppLatcher = new LatchedButton();
        mLogger = aLogger;
    }

    @Override
    public void init()
    {
        mLogger.addHeader("LiftOffSpeed");
        mLogger.addHeader("Climb");
        mLogger.addHeader("Catch");
        mLogger.addHeader("GearBossPosition");
    }

    @Override
    public void update()
    {
        // Gear Boss
        if (mJoystick.getRawButton(XbaxButtonMap.A_BUTTON))
        {
            mGearBossPos = GearBossPositions.UP;
        }
        else if (mJoystick.getRawButton(XbaxButtonMap.B_BUTTON))
        {
            mGearBossPos = GearBossPositions.DOWN;
        }
        else
        {
            mGearBossPos = GearBossPositions.NONE;
        }

        // Ready for take off
        mLiftOffSpeed = mJoystick.getRawAxis(XbaxButtonMap.RIGHT_Y_AXIS);

        // Climb
        mClimb = mJoystick.getRawButton(XbaxButtonMap.RB_BUTTON);
        mCatch = mJoystick.getRawButton(XbaxButtonMap.LB_BUTTON);
        
        //App
        mSwitchAppView = mSwitchAppViewLatcher.update(mJoystick.getRawButton(XbaxButtonMap.A_BUTTON));
        mSwitchToFrontCamera = mSwitchToFrontCameraLatcher.update(mJoystick.getRawButton(XbaxButtonMap.X_BUTTON));
        mSwitchToRearCamera = mSwitchToRearCameraLatcher.update(mJoystick.getRawButton(XbaxButtonMap.Y_BUTTON));
        mRestartApp = mRestartAppLatcher.update(mJoystick.getRawButton(XbaxButtonMap.B_BUTTON));
    }
    
    @Override
    public void control()
    {
       // Nothing
    }

    @Override
    public void rereadPreferences()
    {
        // Nothing
    }

    @Override
    public void updateSmartDashboard()
    {
        SmartDashboard.putBoolean(SmartDashBoardNames.sCLIMBING_OPERATOR_JOYSTICK_SPEED, mClimb);
        SmartDashboard.putBoolean(SmartDashBoardNames.sCATCHING_OPERATOR_JOYSTICK_SPEED, mCatch);
        SmartDashboard.putNumber(SmartDashBoardNames.sWE_HAVE_LIFT_OFF, mLiftOffSpeed);
     }

    @Override
    public void updateLog()
    {
        mLogger.updateLogger(mLiftOffSpeed);
        mLogger.updateLogger(mClimb);
        mLogger.updateLogger(mCatch);
        mLogger.updateLogger(mGearBossPos.toString());
    }

    @Override
    public void stop()
    {
        // Nothing
    }

    @Override
    public double getTakeOffSpeed()
    {
        return mLiftOffSpeed;
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

}
