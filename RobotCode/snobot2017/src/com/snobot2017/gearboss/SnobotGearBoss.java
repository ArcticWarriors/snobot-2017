package com.snobot2017.gearboss;

import com.snobot.lib.logging.ILogger;
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
    private static final Value sGEAR_DOWN_VALUE = Value.kForward;
    private static final Value sGEAR_UP_VALUE = Value.kReverse;

    private DoubleSolenoid mGearSolenoid;
    private IOperatorJoystick mOperatorJoystick;
    private ILogger mLogger;
    private boolean mGearIsUp;

    public SnobotGearBoss(DoubleSolenoid aGearSolenoid, IOperatorJoystick aOperatorJoystick, ILogger aLogger)
    {
        mGearSolenoid = aGearSolenoid;
        mOperatorJoystick = aOperatorJoystick;
        mLogger = aLogger;

        moveGearHigh();
    }

    @Override
    public void init()
    {
        mLogger.addHeader("SolenoidPosition");
    }

    @Override
    public void update()
    {
        mGearIsUp = mGearSolenoid.get() == sGEAR_UP_VALUE;
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
        SmartDashboard.putBoolean(SmartDashBoardNames.sGEAR_BOSS_SOLENOID, mGearIsUp);
    }

    @Override
    public void updateLog()
    {
        mLogger.updateLogger(mGearIsUp);
    }

    @Override
    public void stop()
    {
        moveGearHigh();
    }

    @Override
    public void moveGearHigh()
    {
        mGearSolenoid.set(sGEAR_UP_VALUE);
    }

    @Override
    public void moveGearLow()
    {
        mGearSolenoid.set(sGEAR_DOWN_VALUE);
    }

    @Override
    public boolean isGearUp()
    {
        return mGearIsUp;
    }


}
