package com.snobot.xlib;

public class ToggleButton
{
    private boolean mSwitchState;
    private boolean mLastSwitchState;

    public ToggleButton(int aButton)
    {
        mSwitchState = false;
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

}
