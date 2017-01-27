package com.snobot.simulator.module_wrapper;

import com.snobot.simulator.SensorActuatorRegistry;

public class SolenoidWrapper extends ASensorWrapper
{
    private final CompressorWrapper mCompressor;
    private boolean mState;
    private double mOnTransitionPressureDrop;
    private double mOffTransitionPressureDrop;
    private boolean mIsReal;

    public SolenoidWrapper(int aIndex)
    {
        super("Solenoid " + aIndex);
        mCompressor = SensorActuatorRegistry.get().getCompressor();
        mState = false;
        mOnTransitionPressureDrop = 10;
        mOffTransitionPressureDrop = 10;
        mIsReal = false;
    }

    public boolean get()
    {
        return mState;
    }

    public void set(boolean aState)
    {
        if (aState != mState)
        {
            if (aState)
            {
                mCompressor.solenoidFired(mOnTransitionPressureDrop);
            }
            else
            {
                mCompressor.solenoidFired(mOffTransitionPressureDrop);
            }
        }

        mState = aState;
    }

    public void setTransitionPressure(double aPressure)
    {
        mOnTransitionPressureDrop = mOffTransitionPressureDrop = aPressure;
    }

    public void setIsReal(boolean aIsReal)
    {
        mIsReal = aIsReal;
    }

    public boolean isReal()
    {
        return mIsReal;
    }
}
