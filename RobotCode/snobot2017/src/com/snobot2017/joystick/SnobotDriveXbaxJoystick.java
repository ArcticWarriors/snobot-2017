package com.snobot2017.joystick;

import com.snobot.lib.ui.XboxButtonMap;

import edu.wpi.first.wpilibj.Joystick;

public class SnobotDriveXbaxJoystick implements IDriverJoystick
{
    private Joystick mJoystick;
    private double mLeftSpeed;
    private double mRightSpeed;

    public SnobotDriveXbaxJoystick(Joystick aJoystick)
    {

        mJoystick = aJoystick;

    }

    @Override
    public void init()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void update()
    {
        mLeftSpeed = mJoystick.getRawAxis(XboxButtonMap.LEFT_Y_AXIS);
        mRightSpeed = mJoystick.getRawAxis(XboxButtonMap.RIGHT_Y_AXIS);
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
    public double getRightSpeed()
    {

        return mRightSpeed;
    }

    @Override
    public double getLeftSpeed()
    {

        return mLeftSpeed;
    }

    @Override
    public double getArcadePower()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double getArcadeTurn()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isArcadeMode()
    {
        // TODO Auto-generated method stub
        return false;
    }

}
