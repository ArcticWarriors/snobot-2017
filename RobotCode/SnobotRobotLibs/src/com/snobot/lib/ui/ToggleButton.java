package com.snobot.lib.ui;

public class ToggleButton
{
    private boolean mSwitchState;
    private boolean mLastSwitchState;

    public ToggleButton()
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
