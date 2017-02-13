package com.snobot2017.autologger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.snobot.lib.ISubsystem;
import com.snobot.lib.ui.LatchedButton;
import com.snobot.lib.ui.ToggleButton;
import com.snobot.lib.ui.XboxButtonMap;
import com.snobot2017.drivetrain.IDriveTrain;
import com.snobot2017.joystick.IDriverJoystick;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;

/**
 * Class for logger
 * 
 * @author Andrew Johnson
 *
 */

public class AutoLogger implements ISubsystem
{
    // Current Date and Time
    private String mLogDate;

    // File Writer
    private FileWriter mLogWriter;

    // A count that increases every teleop cycle
    private int mCurrentLogCount;
    private boolean mLogNow = false;
    private DateFormat mDateFormat;
    private IDriveTrain mDriveTrain;
    
    private LatchedButton mDriverYButton;

    // A count that is used to indicate when to log (set by preferences)
    private int mConfigLogCount;

    // File Path set by preferences
    private String mLogFilePath;
    
    private Joystick mDriverJoystick;

    // Constructor
    public AutoLogger(int aLogConfigCount, String aLogPath, Joystick aDriverJoystick, IDriveTrain aDriveTrain)
    {
        mConfigLogCount = aLogConfigCount;
        mLogFilePath = aLogPath;
        mDriverJoystick = aDriverJoystick;
        mDriveTrain = aDriveTrain;
        mDriverYButton = new LatchedButton();
        mDateFormat = new SimpleDateFormat("yyyyMMdd_hhmmssSSS");
        mLogDate = mDateFormat.format(new Date());
    }

    /**
     * Initializes member-variables and opens file-stream
     * 
     * @throws IOException
     */
    public void init()
    {
    }
    
    private void startLog()
    {
        mCurrentLogCount = 0;

        try
        {
            File dir = new File(mLogFilePath);
            if (!dir.exists())
            {
                dir.mkdirs();
            }
            mLogWriter = new FileWriter(mLogFilePath + "RobotLog_" + mLogDate + "_log.csv");
            System.out.println(mLogFilePath + "RobotLog_" + mLogDate + "_log.csv");
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
    public void startLogEntry(String aLogDate)
    {
        try
        {
            if (mLogWriter != null)
            {
                mLogWriter.write(aLogDate);
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

    /**
     * flushes the file
     */
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

	@Override
	public void update() 
	{
		if(mDriverYButton.update(mDriverJoystick.getRawButton(XboxButtonMap.Y_BUTTON)))
		{
			if(mLogNow)
			{
				mLogNow = false;
				this.endLogger();
			}
			else
			{
			mLogNow = true;
	        this.startLog();
	        this.addHeader("LeftMotorSpeed");
	        this.addHeader("RightMotorSpeed");
			this.endHeader();
			}
		}
		if(mLogNow)
		{
			String logDate = mDateFormat.format(new Date());
            this.startLogEntry(logDate);
            updateLogger(mDriveTrain.getLeftMotorSpeed());
            updateLogger(mDriveTrain.getRightMotorSpeed());
            this.endLogger();
		}
		
	}

	@Override
	public void control() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rereadPreferences() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSmartDashboard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateLog() {
		// TODO Auto-generated method stub
		
	}
}
