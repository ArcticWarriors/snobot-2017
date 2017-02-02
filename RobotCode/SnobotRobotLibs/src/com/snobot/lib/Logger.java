package com.snobot.lib;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class for logger
 * 
 * @author Calvin Do
 *
 */

public class Logger
{
    // File Writer
    private FileWriter mLogWriter;

    // A count that increases every teleop cycle
    private int mCurrentLogCount;

    // A count that is used to indicate when to log (set by preferences)
    private int mConfigLogCount;

    // File Path set by preferences
    private String mLogFilePath;
    private SimpleDateFormat mLogDateFormat;

    private boolean mRunning;

    public Logger()
    {
        mRunning = false;
    }

    public void startLogging(SimpleDateFormat aLogFormat, int aLogConfigCount, String aLogPath)
    {
        mRunning = false;
        mLogDateFormat = aLogFormat;
        mConfigLogCount = aLogConfigCount;
        mLogFilePath = aLogPath;
    }

    /**
     * Initializes member-variables and opens file-stream
     * 
     * @throws IOException
     */
    public void init()
    {
        if (mRunning)
        {
            mCurrentLogCount = 0;

            try
            {
                File dir = new File(mLogFilePath);
                if (!dir.exists())
                {
                    dir.mkdirs();
                }

                String timeString = mLogDateFormat.format(new Date());
                mLogWriter = new FileWriter(mLogFilePath + "RobotLog_" + timeString + "_log.csv");

                mLogWriter.write("Date and Time");

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
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
    public void startRow()
    {

        try
        {
            if (mLogWriter != null)
            {
                String timeString = mLogDateFormat.format(new Date());
                mLogWriter.write(timeString);
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
