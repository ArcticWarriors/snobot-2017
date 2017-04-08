package com.snobot.lib.ui;

/**
 * A latched button will return TRUE once when it is pressed and held. Useful
 * for things like incrementing a value, when you want to garuntee a "single
 * press" that lasts many update loops only counts as one update
 * 
 * @author PJ
 *
 */
public class LatchedButton
{
    private boolean mLastState;
    private boolean mLastOutput;

    public LatchedButton()
    {
        mLastState = false;
    }

    public boolean update(boolean aCurrentState)
    {
        boolean output = mLastOutput = false;
        if (!mLastState && aCurrentState)
        {
            output = mLastOutput = true;
        }

        mLastState = aCurrentState;

        return output;

    }

    public boolean getState()
    {
        return mLastOutput;
    }

}
