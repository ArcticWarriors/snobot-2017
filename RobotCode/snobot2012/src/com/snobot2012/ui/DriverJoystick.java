package com.snobot2012.ui;

import com.snobot.lib.ui.XboxButtonMap;

import edu.wpi.first.wpilibj.Joystick;

public class DriverJoystick
{
    private Joystick mJoystick;

    public DriverJoystick(Joystick aJoystick)
    {
        mJoystick = aJoystick;
    }

    public double getLeftThrottle()
    {
        return -mJoystick.getRawAxis(XboxButtonMap.LEFT_Y_AXIS);
    }

    public double getRightThrottle()
    {
        return -mJoystick.getRawAxis(XboxButtonMap.RIGHT_Y_AXIS);
    }
}
