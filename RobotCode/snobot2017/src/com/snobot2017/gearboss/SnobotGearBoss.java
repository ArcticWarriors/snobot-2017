package com.snobot2017.gearboss;

import com.snobot2017.joystick.IOperatorJoystick;

import edu.wpi.first.wpilibj.Solenoid;

public class SnobotGearBoss implements IGearBoss
{
    private Solenoid mGearSolenoid;
    private IOperatorJoystick mOperatorJoystick;

    public SnobotGearBoss(Solenoid aGearSolenoid, IOperatorJoystick aOperatorJoystick)
    {
        mGearSolenoid = aGearSolenoid;
        mOperatorJoystick = aOperatorJoystick;
    }

    @Override
    public void init()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void update()
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
            // Do nothing.
            break;
        }
        }
    }

    @Override
    public void control()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void rereadPreferences()
    {
        // TODO Auto-generated method stub

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
    public void moveGearHigh()
    {
        mGearSolenoid.set(true);
    }

    @Override
    public void moveGearLow()
    {
        mGearSolenoid.set(false);
    }

    @Override
    public boolean getGearHeight()
    {
        return mGearSolenoid.get();
    }

}
