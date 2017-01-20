package com.snobot2017.joystick;

import com.snobot.lib.ui.XboxButtonMap;

import edu.wpi.first.wpilibj.Joystick;

public class SnobotDriveXbaxJoystick implements IDriverJoystick
{
    private Joystick mJoystick;
    private double mLeftSpeed;
    private double mRightSpeed;


public SnobotDriveXbaxJoystick (Joystick aJoystick)
    {
    
        mJoystick = aJoystick;
    
    }

public void init()
    {
    
    }

public void update()
    {
    
        mLeftSpeed= mJoystick.getRawAxis(XboxButtonMap.LEFT_Y_AXIS);
        mRightSpeed= mJoystick.getRawAxis(XboxButtonMap.RIGHT_Y_AXIS);
        
    }

public void control()
    {
    
    }
    
public void rereadPreferences()
    {
    
    }

public void updateSmartDashboard()
    {
    
    }

public void updatelog()
    {
    
    }

public void stop()
    {
    
    }

public double getLeftSpeed()
    {
    
    return mLeftSpeed;
    
    }

public double getRightSpeed()
    {
    
        return mRightSpeed;  
    
    }

public double getArcadeTurn()
    {
    
        return 0;
    
    }

public double getArcadePower()
    {
    
        return 0;
    
    }

public boolean isArcadeMode()
    {
    
        return false;
    
    }

@Override
public void updateLog()
{
    // TODO Auto-generated method stub
    
}

}
