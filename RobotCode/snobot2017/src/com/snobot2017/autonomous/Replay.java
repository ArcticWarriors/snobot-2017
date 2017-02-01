package com.snobot2017.autonomous;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.snobot2017.Properties2017;
import com.snobot2017.drivetrain.IDriveTrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 * reads a log file and makes the motor replay
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
     * @param aDriveTrain
     * @throws IOException
     */
    public Replay(IDriveTrain aDriveTrain) throws IOException
    {
        mBufferedReader = new BufferedReader(new FileReader(new File(mFilePath)));
        mFilePath = Properties2017.sAUTO_LOG_RUN_PATH.getValue();
        mDriveTrain = aDriveTrain;
    }

    @Override
    public void execute()
    {
        try
        {
            this.setMotors();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * reads log file and sets motors in same timeframe
     * @throws IOException
     */
    private void setMotors() throws IOException
    {
        // TODO sync file with time
        mBufferedReader.readLine();
        String line;
        while ((line = mBufferedReader.readLine()) != null)
        {
            System.out.println("\n" + line);

            String[] Split = line.split(mDelim);
            Double[] nums = new Double[Split.length - 1];

            for (int x = 1; x < Split.length; x++)
            {
                nums[x - 1] = Double.parseDouble(Split[x]);
            }

            double mLeftSpeed = (nums[0] + nums[1]) / 2;
            double mRightSpeed = (nums[2] + nums[3]) / 2;
            mDriveTrain.setLeftRightSpeed(mLeftSpeed, mRightSpeed);
        }
        mFinished = true;
    }

    @Override
    protected boolean isFinished()
    {
        return mFinished;
    }
}
