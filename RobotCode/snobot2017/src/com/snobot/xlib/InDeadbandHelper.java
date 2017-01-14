package com.snobot.xlib;

public class InDeadbandHelper
{
    private int mLoopsRequired;
    private int mLoopsSatisfied;

    public InDeadbandHelper(int aLoopsRequired)
    {
        mLoopsRequired = aLoopsRequired;
    }

    public boolean isFinished(boolean aInRange)
    {
        if (aInRange)
        {
            mLoopsSatisfied++;
        }
        else
        {
            mLoopsSatisfied = 0;
        }

        return mLoopsSatisfied >= mLoopsRequired;
    }

    public int inDeadband()
    {
        return mLoopsSatisfied;
    }
}
