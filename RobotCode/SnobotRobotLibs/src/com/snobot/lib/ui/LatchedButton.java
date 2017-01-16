package com.snobot.lib.ui;

public class LatchedButton
{
    private boolean mLastState;

    public LatchedButton()
    {
        mLastState = false;
    }

    public boolean update(boolean aCurrentState)
    {
        boolean output = false;
        if (!mLastState && aCurrentState)
        {
            output = true;
        }

        mLastState = aCurrentState;

        return output;

    }

}
