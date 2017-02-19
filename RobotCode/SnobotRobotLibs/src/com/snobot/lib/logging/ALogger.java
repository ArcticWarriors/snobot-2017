package com.snobot.lib.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class ALogger implements ILogger
{

    private FileWriter mLogWriter;
    private String mLogFilePath;
    private SimpleDateFormat mLogDateFormat;
    protected boolean mRunning;

    public ALogger()
    {
        mRunning = false;
    }

    @Override
    public void startLogging(SimpleDateFormat aLogFormat, String aLogPath)
    {
        mRunning = true;
        mLogDateFormat = aLogFormat;
        mLogFilePath = aLogPath;
    }

    public void initializeLogger()
    {
        if (mRunning)
        {
            try
            {
                File dir = new File(mLogFilePath);
                if (!dir.exists())
                {
                    System.err.println("ERROR CREATING LOGGER: Path to '" + mLogFilePath + "' does not exist.  Bailing");
                    mRunning = false;
                    return;
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

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
    public void updateLogger(int aEntry)
    {
        updateLogger("" + aEntry);
    }

    @Override
    public void updateLogger(double aEntry)
    {
        updateLogger("" + aEntry);
    }

    @Override
    public void updateLogger(boolean aEntry)
    {
        // Convert boolean to a number, then log
        updateLogger(aEntry ? 1 : 0);
    
    }

    @Override
    public void endRow()
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

    @Override
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