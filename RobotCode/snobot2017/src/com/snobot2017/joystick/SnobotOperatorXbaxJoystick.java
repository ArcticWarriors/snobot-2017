package com.snobot2017.joystick;

import com.snobot.lib.Logger;
import com.snobot.lib.ui.XboxButtonMap;
import com.snobot2017.SmartDashBoardNames;

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
    private Logger mLogger;

    // Gear Boss
    private GearBossPositions mGearBossPos;

    // Ready for take off
    private double mLiftOffSpeed;

    public SnobotOperatorXbaxJoystick(Joystick aJoystick, Logger aLogger)
    {
        mJoystick = aJoystick;
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
        if (mJoystick.getRawButton(XboxButtonMap.A_BUTTON))
        {
            mGearBossPos = GearBossPositions.UP;
        }
        else if (mJoystick.getRawButton(XboxButtonMap.B_BUTTON))
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
        //mLogger.updateLogger(mGearBossPos);
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

}
