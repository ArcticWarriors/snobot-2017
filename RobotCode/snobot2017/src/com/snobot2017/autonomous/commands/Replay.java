package com.snobot2017.autonomous.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.snobot2017.drivetrain.IDriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 * reads a log file and makes the motor replay
 * 
 * @author Andrew
 *
 */
public class Replay extends Command
{
    private BufferedReader mBufferedReader;
    private String mFilePath;
    private String mDelim = ",";
    private IDriveTrain mDriveTrain;
    private boolean mFinished = false;

    /**
     * Constructor
     * 
     * @param aDriveTrain
     * @throws IOException
     */
    public Replay(IDriveTrain aDriveTrain, String aFilePath) throws IOException
    {
        mFilePath = aFilePath;
        mBufferedReader = new BufferedReader(new FileReader(new File(mFilePath)));
        mDriveTrain = aDriveTrain;
    }

    @Override
    public void initialize()
    {
        try
        {
            mBufferedReader.readLine();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * reads log file and sets motors in same timeframe
     * 
     * @throws IOException
     */
    @Override
    public void execute()
    {
        try
        {
            String line = mBufferedReader.readLine();
            if (line != null)
            {
                System.out.println("\n" + line);

                String[] Split = line.split(mDelim);
                Double[] nums = new Double[Split.length - 1];

                for (int x = 1; x < Split.length; x++)
                {
                    nums[x - 1] = Double.parseDouble(Split[x]);
                }

                if (nums.length ==  2)
                {
                    double mLeftSpeed = (nums[0]);
                    double mRightSpeed = (nums[1]);
                    mDriveTrain.setLeftRightSpeed(mLeftSpeed, mRightSpeed);
                }
            }
            else
            {
                mFinished = true;
                mDriveTrain.setLeftRightSpeed(0, 0);
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * reads log file and sets motors in same timeframe
     * 
     * @throws IOException
     */

    @Override
    protected boolean isFinished()
    {
        return mFinished;
    }
}
