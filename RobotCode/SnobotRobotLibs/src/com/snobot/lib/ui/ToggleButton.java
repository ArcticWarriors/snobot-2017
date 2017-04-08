package com.snobot.lib.ui;

/**
 * A toggle button requires one click to go HIGH, and then will remain HIGH
 * until it is selected again
 * 
 * @author PJ
 *
 */
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
