package com.snobot2017.joystick;

import com.snobot.lib.logging.ILogger;
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
public class SnobotOperatorXbaxJoystick implements IOperatorJoystick
{
    private Joystick mJoystick;
    private boolean mClimb;
    private boolean mCatch;
    private ILogger mLogger;

    // Gear Boss
    private GearBossPositions mGearBossPos;

    // Sphincter
    private boolean mSphincterIsOpen;

    // Lights
    private ToggleButton mToggleGreenLight;
    private ToggleButton mToggleBlueLight;
    private boolean mGreenRelayOn = true;
    private boolean mBlueRelayOn = true;

    public SnobotOperatorXbaxJoystick(Joystick aJoystick, ILogger aLogger)
    {
        mJoystick = aJoystick;

        mToggleGreenLight = new ToggleButton(true);
        mToggleBlueLight = new ToggleButton(true);
        mLogger = aLogger;
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
        }
        else if (mJoystick.getRawButton(XboxButtonMap.A_BUTTON))
        {
            mGearBossPos = GearBossPositions.DOWN;
        }
        else
        {
            mGearBossPos = GearBossPositions.NONE;
        }

        // Climb
        mClimb = mJoystick.getRawButton(XboxButtonMap.RB_BUTTON);
        mCatch = mJoystick.getRawButton(XboxButtonMap.LB_BUTTON);

        // Sphincter
        mSphincterIsOpen = mJoystick.getRawButton(XboxButtonMap.Y_BUTTON);

        // Light
        mGreenRelayOn = mToggleGreenLight.update(mJoystick.getRawButton(XboxButtonMap.START_BUTTON));
        mBlueRelayOn = mToggleBlueLight.update(mJoystick.getRawButton(XboxButtonMap.BACK_BUTTON));
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
    public boolean isPooperOpen()
    {
        return mSphincterIsOpen;
    }

    @Override
    public void setShouldRumble(boolean aRumble)
    {
//    	System.out.println("RUmbling " + aRumble);
        if(aRumble)
        {
            mJoystick.setRumble(RumbleType.kLeftRumble, 1);
            mJoystick.setRumble(RumbleType.kRightRumble, 1);
        }
        else
        {
            mJoystick.setRumble(RumbleType.kLeftRumble, 0);
            mJoystick.setRumble(RumbleType.kRightRumble, 0);
        }
    }
}
