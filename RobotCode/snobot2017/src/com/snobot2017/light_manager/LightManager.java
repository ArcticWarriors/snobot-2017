package com.snobot2017.light_manager;

import com.snobot.lib.modules.IUpdateableModule;
import com.snobot2017.Properties2017;
import com.snobot2017.SnobotActor.ISnobotActor;
import com.snobot2017.joystick.IOperatorJoystick;
import com.snobot2017.vision.VisionManager;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;

public class LightManager implements IUpdateableModule
{
    private static final Value sLIGHT_ON_VALUE = Value.kForward;
    private static final Value sLIGHT_OFF_VALUE = Value.kOff;

    private Relay mGreenRelay;
    private Relay mBlueRelay;
    private IOperatorJoystick mOperatorJoystick;
    private ISnobotActor mSnobotActor;
    private VisionManager mVisionManager;
    private int mFlashCounter;

    public LightManager(IOperatorJoystick aOperatorJoystick, ISnobotActor aSnobotActor, VisionManager aVisionManager, Relay aRelay, Relay anotherRelay)
    {
        mGreenRelay = aRelay;
        mBlueRelay = anotherRelay;
        
        mOperatorJoystick = aOperatorJoystick;
        mSnobotActor = aSnobotActor;
        mVisionManager = aVisionManager;
        mFlashCounter = 0;
    }
    
    @Override
    public void update()
    {
        if(mOperatorJoystick.greenLightOn())
        {
            mGreenRelay.set(sLIGHT_ON_VALUE);
        }
        else
        {
            mGreenRelay.set(sLIGHT_OFF_VALUE);
        }
        
        if(!mSnobotActor.isInAction())
        {
            if(mVisionManager.seesTarget())
            {
                mBlueRelay.set(sLIGHT_ON_VALUE);
            }
            else
            {
                mBlueRelay.set(sLIGHT_OFF_VALUE);
            }
//            if(mOperatorJoystick.blueLightOn())
//            {
//                mBlueRelay.set(sLIGHT_ON_VALUE);
//            }
//            else
//            {
//                mBlueRelay.set(sLIGHT_OFF_VALUE);
//            }
        }
        else
        {
            int loopsToFlash = Properties2017.sFLASH_LIGHT_LOOPS.getValue();
            if (mFlashCounter > loopsToFlash && mBlueRelay.get() == sLIGHT_OFF_VALUE)
            {
                mBlueRelay.set(sLIGHT_ON_VALUE);
                mFlashCounter = 0;
            }
            else if (mFlashCounter > loopsToFlash && mBlueRelay.get() == sLIGHT_ON_VALUE)
            {
                mBlueRelay.set(sLIGHT_OFF_VALUE);
                mFlashCounter = 0;
            }
            else
            {
                mFlashCounter++;
            }
        }
    }
    
    
}
