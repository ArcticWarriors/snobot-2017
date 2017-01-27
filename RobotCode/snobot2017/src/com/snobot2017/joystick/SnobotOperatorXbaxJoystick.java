package com.snobot2017.joystick;

import com.snobot.lib.ui.LatchedButton;
import com.snobot.lib.ui.XboxButtonMap;

import edu.wpi.first.wpilibj.Joystick;

/**
 * The operator joystick class
 * 
 * @author ayush
 *
 */
public class SnobotOperatorXbaxJoystick implements IOperatorJoystick
{
    private Joystick mJoystick;
    private boolean mClimb;
    private boolean mCatch;
    // Gear Boss
    private GearBossPositions mGearBossPos;

    // Ready for take off
    private double mLiftOffSpeed;

    private LatchedButton mSwitchAppViewLatcher;
    private boolean mSwitchAppView;

    public SnobotOperatorXbaxJoystick(Joystick aJoystick)
    {
        mJoystick = aJoystick;

        mSwitchAppViewLatcher = new LatchedButton();
    }

    @Override
    public void init()
    {

    }

    @Override
    public void update()
    {
        // Gear Boss
        if (mJoystick.getPOV() == (XboxButtonMap.D_PAD_UP))
        {
            mGearBossPos = GearBossPositions.UP;
        }
        else if (mJoystick.getPOV() == (XboxButtonMap.D_PAD_DOWN))
        {
            mGearBossPos = GearBossPositions.DOWN;
        }
        else
        {
            mGearBossPos = GearBossPositions.NONE;
        }

        // Ready for take off
        mLiftOffSpeed = mJoystick.getRawAxis(XboxButtonMap.RIGHT_Y_AXIS);

        // Climb
        mClimb = mJoystick.getRawButton(XboxButtonMap.RB_BUTTON);
        mCatch = mJoystick.getRawButton(XboxButtonMap.LB_BUTTON);
        
        //App
        mSwitchAppView = mSwitchAppViewLatcher.update(mJoystick.getRawButton(XboxButtonMap.A_BUTTON));
    }

    @Override
    public void control()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void rereadPreferences()
    {

    }

    @Override
    public void updateSmartDashboard()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateLog()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void stop()
    {
        // TODO Auto-generated method stub

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

}
