package com.snobot2017.gearboss;

import com.snobot.lib.Logger;
import com.snobot2017.SmartDashBoardNames;
import com.snobot2017.joystick.IOperatorJoystick;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Controls the gear boss
 * @author Owner/Nora
 *
 */
public class SnobotGearBoss implements IGearBoss
{
    private DoubleSolenoid mGearSolenoid;
    private IOperatorJoystick mOperatorJoystick;
    private Logger mLogger;
    private boolean mSolenoidInOrOut;

    public SnobotGearBoss(DoubleSolenoid aGearSolenoid, IOperatorJoystick aOperatorJoystick, Logger aLogger)
    {
        mGearSolenoid = aGearSolenoid;
        mOperatorJoystick = aOperatorJoystick;
        mLogger = aLogger;
        
    }

    @Override
    public void init()
    {
        mLogger.addHeader("SolenoidPosition");
    }

    @Override
    public void update()
    {
        mSolenoidInOrOut = mGearSolenoid.get() == Value.kForward;
    }

    @Override
    public void control()
    {
        switch (mOperatorJoystick.moveGearBossToPosition())
        {
        case UP:
        {
            moveGearHigh();
            break;
        }
        case DOWN:
        {
            moveGearLow();
            break;
        }
        case NONE:
        {
            break;
        }
        }
    }

    @Override
    public void rereadPreferences()
    {
        // Nothing
    }

    @Override
    public void updateSmartDashboard()
    {
        SmartDashboard.putBoolean(SmartDashBoardNames.sGEAR_BOSS_SOLENOID, mSolenoidInOrOut);
    }

    @Override
    public void updateLog()
    {
        mLogger.updateLogger(mSolenoidInOrOut);
    }

    @Override
    public void stop()
    {
        mGearSolenoid.set(Value.kForward);
    }

    @Override
    public void moveGearHigh()
    {
        mGearSolenoid.set(Value.kReverse);
    }

    @Override
    public void moveGearLow()
    {
        mGearSolenoid.set(Value.kForward);
    }

    @Override
    public boolean getGearHeight()
    {
        return mGearSolenoid.get() == Value.kForward;
    }


}
