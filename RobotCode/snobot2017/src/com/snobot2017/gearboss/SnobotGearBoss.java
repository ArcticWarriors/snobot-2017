package com.snobot2017.gearboss;

import edu.wpi.first.wpilibj.Solenoid;

public class SnobotGearBoss implements IGearBoss
{
    private Solenoid mGearSolenoid;

    public SnobotGearBoss(Solenoid aGearSolenoid)
    {
        mGearSolenoid = aGearSolenoid;
    }
    @Override
    public void init()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void update()
    {
        // TODO Auto-generated method stub

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
