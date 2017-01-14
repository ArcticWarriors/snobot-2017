package com.snobot.xlib;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.snobot2016.Properties2016;

/**
 * Class for logger
 * 
 * @author Calvin Do
 *
 */

public class Logger
{
    // Current Date and Time
    private String mLogDate;

    // File Writer
    private FileWriter mLogWriter;

    // A count that increases every teleop cycle
    private int mCurrentLogCount;

    // A count that is used to indicate when to log (set by preferences)
    private int mConfigLogCount;

    // File Path set by preferences
    private String mLogFilePath;

    public Logger(String aLogDate)
    {
        mLogDate = aLogDate;

    }

    /**
     * Initializes member-variables and opens file-stream
     * 
     * @throws IOException
     */
    public void init()
    {

        mConfigLogCount = Properties2016.sLOG_COUNT.getValue();
        mLogFilePath = Properties2016.sLOG_FILE_PATH.getValue();
        mCurrentLogCount = 0;

        try
        {
            File dir = new File(mLogFilePath);
            if (!dir.exists())
            {
                dir.mkdirs();
            }
            mLogWriter = new FileWriter(mLogFilePath + "RobotLog_" + mLogDate + "_log.csv");

            mLogWriter.write("Date and Time");

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Adds a new header to represent logged data
     * 
     * @param aHeader
     */
    public void addHeader(String aHeader)
    {

        try
        {
            if (mLogWriter != null)
            {
                mLogWriter.write("," + aHeader);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            this.stop();
            mLogWriter = null;
        }
    }

    /**
     * Stops accepting new headers
     */
    public void endHeader()
    {
        try
        {
            if (mLogWriter != null)
            {
                mLogWriter.write("\n");
                mLogWriter.flush();
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
            this.stop();
            mLogWriter = null;
        }
    }

    /**
     * Begins accepting new log entries
     */
    public void startLogEntry(String mLogDate)
    {

        try
        {
            if (mLogWriter != null)
            {
                mLogWriter.write(mLogDate);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            this.stop();
            mLogWriter = null;
        }

    }

    /**
     * Updates log information
     * 
     * @param aEntry
     */
    public void updateLogger(String aEntry)
    {
        try
        {
            if (mLogWriter != null)
            {
                mLogWriter.write("," + aEntry);
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
            this.stop();
            mLogWriter = null;
        }
    }

    /**
     * Updates log information
     * 
     * @param aEntry
     */
    public void updateLogger(int aEntry)
    {
        updateLogger("" + aEntry);
    }

    /**
     * Updates log information
     * 
     * @param aEntry
     */
    public void updateLogger(double aEntry)
    {
        updateLogger("" + aEntry);
    }

    /**
     * Updates log information
     * 
     * @param aEntry
     */
    public void updateLogger(boolean aEntry)
    {
        // Convert boolean to a number, then log
        updateLogger(aEntry ? 1 : 0);

    }

    /**
     * Stops accepting log entries
     */
    public void endLogger()
    {
        try
        {
            if (mLogWriter != null)
            {
                mLogWriter.write("\n");
                mLogWriter.flush();
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
            this.stop();
            mLogWriter = null;
        }
    }

    /**
     * Closes file-stream
     */
    public void stop()
    {
        try
        {
            if (mLogWriter != null)
            {
                mLogWriter.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            this.stop();
            mLogWriter = null;
        }
    }

    /**
     * Lets robot check for when to log
     */
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

    public void flush()
    {
        try
        {
            if (mLogWriter != null)
            {
                mLogWriter.flush();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            this.stop();
            mLogWriter = null;
        }
    }
}
