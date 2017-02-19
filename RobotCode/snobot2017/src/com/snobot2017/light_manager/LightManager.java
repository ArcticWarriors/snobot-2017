package com.snobot2017.light_manager;

import com.snobot2017.SnobotActor.ISnobotActor;
import com.snobot2017.joystick.IOperatorJoystick;
import com.snobot2017.joystick.IVisionJoystick;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Direction;
import edu.wpi.first.wpilibj.Relay.Value;

public class LightManager
{
    private Relay mGreenRelay;
    private Relay mBlueRelay;
    private IOperatorJoystick mOperatorJoystick;
    private ISnobotActor mSnobotActor;
    private int mCounter;
    public LightManager(IOperatorJoystick aOperatorJoystick, ISnobotActor aSnobotActor, Relay aRelay, Relay anotherRelay)
    {
        mGreenRelay = aRelay;
        mBlueRelay = anotherRelay;
        
        mOperatorJoystick = aOperatorJoystick;
        mSnobotActor = aSnobotActor;
        mCounter = 0;
    }
    
    public void update()
    {
        if(mOperatorJoystick.greenLightOn())
        {
            mGreenRelay.set(Value.kForward);
        }
        else
        {
            mGreenRelay.set(Value.kOff);
        }
        
        if(!mSnobotActor.isInAction())
        {
            if(mOperatorJoystick.blueLightOn())
            {
                mBlueRelay.set(Value.kForward);
            }
            else
            {
                mBlueRelay.set(Value.kOff);
            }
        }
        else
        {
            if(mCounter > 2 && mBlueRelay.get() == Value.kOff)
            {
                mBlueRelay.set(Value.kForward);
                mCounter = 0;
            }
            else if(mCounter > 2 && mBlueRelay.get() == Value.kForward)
            {
                mBlueRelay.set(Value.kOff);
                mCounter = 0;
            }
            else
            {
                mCounter++;
            }
        }
    }
    
    
}
