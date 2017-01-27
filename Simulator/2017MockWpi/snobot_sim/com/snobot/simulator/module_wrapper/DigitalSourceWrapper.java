package com.snobot.simulator.module_wrapper;

public class DigitalSourceWrapper extends ASensorWrapper
{

    private boolean mState;
    private boolean mIsEncoder;

    public DigitalSourceWrapper(int index)
    {
        super("Digital Source" + index);
        mState = false;
    }

    public boolean get()
    {
        return mState;
    }

    public void set(boolean aState)
    {
        mState = aState;
    }

    public boolean isEncoder()
    {
        return mIsEncoder;
    }

    public void setIsEncoder(boolean aIsEncoder)
    {
        this.mIsEncoder = aIsEncoder;
    }

}
