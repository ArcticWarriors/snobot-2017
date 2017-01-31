package com.snobot2017.autonomous;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.snobot2017.Properties2017;
import com.snobot2017.drivetrain.IDriveTrain;

import edu.wpi.first.wpilibj.command.Command;

public class AutonCopy extends Command
{
    BufferedReader br;
    private String mFilePath;
    private String delim = ",";
    private IDriveTrain mDriveTrain;
    private boolean mFinished = false;

    public AutonCopy(IDriveTrain aDriveTrain) throws IOException
    {
        br = new BufferedReader(new FileReader(new File(mFilePath)));
        mFilePath = Properties2017.sAUTO_LOG_RUN_PATH.getValue();
        mDriveTrain = aDriveTrain;
    }

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

    public void setMotors() throws IOException
    {

        br.readLine();
        String line;
        while ((line = br.readLine()) != null)
        {
            System.out.println("\n" + line);

            String[] Split = line.split(delim);
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