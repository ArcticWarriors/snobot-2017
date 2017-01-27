package com.snobot2012.ui;


import com.snobot.lib.ui.XboxButtonMap;

import edu.wpi.first.wpilibj.Joystick;

public class OperatorJoystick
{
    private Joystick mJoystick;

    public OperatorJoystick(Joystick aJoystick)
    {
        mJoystick = aJoystick;
    }

    public double useIntakeLower()
    {
        return mJoystick.getRawAxis(XboxButtonMap.LEFT_Y_AXIS);
    }

    public double useIntakeUpper()
    {
        double intakeMotorUpperSpeed = 0;
        if (mJoystick.getRawButton(XboxButtonMap.Y_BUTTON))
        {
            intakeMotorUpperSpeed = 1;
        }
        else if (mJoystick.getRawButton(XboxButtonMap.A_BUTTON))
        {
            intakeMotorUpperSpeed = -1;
        }
        else
        {
            intakeMotorUpperSpeed = 0;
        }

        return intakeMotorUpperSpeed;
    }


    public boolean incrementShooterSpeed()
    {
        return mJoystick.getRawButton(XboxButtonMap.B_BUTTON);
    }

    public boolean decrementShooterSpeed()
    {
        return mJoystick.getRawButton(XboxButtonMap.X_BUTTON);
    }

    public boolean shootButton()
    {
        return mJoystick.getRawButton(XboxButtonMap.RB_BUTTON);
    }

}
