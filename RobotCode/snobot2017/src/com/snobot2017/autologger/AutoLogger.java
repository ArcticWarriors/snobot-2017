package com.snobot2017.autologger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.snobot.lib.logging.ALogger;
import com.snobot.lib.modules.ILoggableModule;
import com.snobot.lib.modules.IUpdateableModule;
import com.snobot2017.drivetrain.IDriveTrain;
import com.snobot2017.joystick.IAutoLoggerJoystick;

/**
 * Class for logger
 * 
 * @author Andrew Johnson
 *
 */

public class AutoLogger extends ALogger implements IUpdateableModule, ILoggableModule
{
    // Current Date and Time
    private String mLogDate;

    // File Writer
    private FileWriter mLogWriter;

    // A count that increases every teleop cycle
    private boolean mIsRecording;
    private DateFormat mDateFormat;
    private IDriveTrain mDriveTrain;

    // File Path set by preferences
    private String mLogFilePath;
    
    private IAutoLoggerJoystick mJoystick;

    // Constructor
    public AutoLogger(int aLogConfigCount, String aLogPath, IAutoLoggerJoystick aDriverJoystick, IDriveTrain aDriveTrain)
    {
        mLogFilePath = aLogPath;
        mJoystick = aDriverJoystick;
        mDriveTrain = aDriveTrain;
        mDateFormat = new SimpleDateFormat("yyyyMMdd_hhmmssSSS");
        mLogDate = mDateFormat.format(new Date());
        mIsRecording = false;
    }

    /**
     * Initializes member-variables and opens file-stream
     * 
     * @throws IOException
     */
    public void initializeLogHeaders()
    {
    }
    
    private void startLog()
    {
        System.out.println("---------------------------------------");
        System.out.println("Staring auto logger");
        System.out.println("---------------------------------------");

        try
        {
            File dir = new File(mLogFilePath);
            if (!dir.exists())
            {
                System.err.println("ERROR CREATING AUTO LOGGER: Path to '" + mLogFilePath + "' does not exist.  Bailing");
                return;
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
     * Lets robot check for when to log
     */
    public boolean logNow()
    {
        return true;
    }

    @Override
    public void update()
    {
        if (mJoystick.isRecording())
        {
            if (mIsRecording)
            {
                System.out.println("---------------------------------------");
                System.out.println("Ending auto logger");
                System.out.println("---------------------------------------");
                mIsRecording = false;
                this.endLogger();
            }
            else
            {
                mIsRecording = true;
                this.startLog();
                this.addHeader("LeftMotorSpeed");
                this.addHeader("RightMotorSpeed");
                this.endHeader();
            }
        }
        if (mIsRecording)
        {
            startRow();
            updateLogger(mDriveTrain.getLeftMotorSpeed());
            updateLogger(mDriveTrain.getRightMotorSpeed());
            endLogger();
        }

    }

    @Override
    public void updateLog()
    {

    }
}
