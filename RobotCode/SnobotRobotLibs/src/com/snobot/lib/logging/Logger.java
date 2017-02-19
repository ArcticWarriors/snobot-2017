package com.snobot.lib.logging;

/**
 * Class for logger
 * 
 * @author Calvin Do
 *
 */

public class Logger extends ALogger implements ILogger
{
    // A count that increases every teleop cycle
    int mCurrentLogCount;

    // A count that is used to indicate when to log (set by preferences)
    int mConfigLogCount;

    public Logger()
    {

    }

    public boolean logNow()
    {
        if (mCurrentLogCount < mConfigLogCount)
        {
            mCurrentLogCount++;
            return false;
        }
        else
        {
            mCurrentLogCount = 0;
            return true;
        }
    }
}
