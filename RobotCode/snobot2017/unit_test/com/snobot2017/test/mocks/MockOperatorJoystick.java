package com.snobot2017.test.mocks;

import com.snobot2017.joystick.IOperatorJoystick;

public class MockOperatorJoystick implements IOperatorJoystick
{

    @Override
    public void update()
    {

    }

    @Override
    public void initializeLogHeaders()
    {

    }

    @Override
    public void updateLog()
    {

    }

    @Override
    public void updateSmartDashboard()
    {

    }

    @Override
    public boolean isCatchRope()
    {
        return false;
    }

    @Override
    public boolean isClimb()
    {
        return false;
    }

    @Override
    public boolean greenLightOn()
    {
        return false;
    }

    @Override
    public boolean blueLightOn()
    {
        return false;
    }

    @Override
    public GearBossPositions moveGearBossToPosition()
    {
        return null;
    }

    @Override
    public void setShouldRumble(boolean aRumble)
    {

    }

}
