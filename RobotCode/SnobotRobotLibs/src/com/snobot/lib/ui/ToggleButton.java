package com.snobot.lib.ui;

public class ToggleButton
{
    private boolean mSwitchState;
    private boolean mLastSwitchState;

    public ToggleButton()
    {
        this(false);
    }

    public ToggleButton(boolean aDefaultState)
    {
        mSwitchState = aDefaultState;
    }

    public boolean update(boolean aCurrentState)
    {
        if (aCurrentState && !mLastSwitchState)
        {
            mSwitchState = !mSwitchState;
        }

        mLastSwitchState = aCurrentState;
        return mSwitchState;

    }

    public boolean getState()
    {
        return mSwitchState;
    }

    public void setState(boolean aState)
    {
        mSwitchState = aState;
        mLastSwitchState = false;
    }

}
