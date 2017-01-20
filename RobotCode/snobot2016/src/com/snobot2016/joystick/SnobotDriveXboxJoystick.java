package com.snobot2016.joystick;

import com.snobot.xlib.XboxButtonMap;

import edu.wpi.first.wpilibj.Joystick;

/**
 * 
 * @author jbnol_000
 *
 * Xbax Joystick class
 */
public class SnobotDriveXboxJoystick implements IDriverJoystick
{
    private Joystick mJoystick;
    private double mLeftSpeed;
    private double mRightSpeed;

    /**
     * 
     * @param aJoystick
     *                  The Joystick
     */
    public SnobotDriveXboxJoystick(Joystick aJoystick)
    {
        mJoystick = aJoystick;
    }

    @Override
    public void init()
    {

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

    }

    @Override
    public void rereadPreferences()
    {

    }

    @Override
    public void updateSmartDashboard()
    {

    }

    @Override
    public void updateLog()
    {

    }

    @Override
    public void stop()
    {

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
        return 0;
    }

    @Override
    public double getArcadeTurn()
    {
        return 0;
    }

    @Override
    public boolean isArcadeMode()
    {
        return false;
    }

}
