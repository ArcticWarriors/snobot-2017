package com.snobot2017.vision;

import com.snobot2017.joystick.IOperatorJoystick;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.Relay.Value;

public class LightManager
{
    private Relay mGreenRelay;
    private Relay mBlueRelay;
    private IOperatorJoystick mJoystick;
    public LightManager(IOperatorJoystick aJoystick, Relay aRelay, Relay anotherRelay)
    {
        mGreenRelay = aRelay;
        mBlueRelay = anotherRelay;
        
        mJoystick = aJoystick;
    }
    
    public void update()
    {
        if(mJoystick.greenLightOn())
        {
            mGreenRelay.set(Value.kOn);
        }
        else
        {
            mGreenRelay.set(Value.kOff);
        }
        
        if(mJoystick.blueLightOn())
        {
            mBlueRelay.set(Value.kOn);
        }
        else
        {
            mBlueRelay.set(Value.kOff);
        }
    }
    
    
}
