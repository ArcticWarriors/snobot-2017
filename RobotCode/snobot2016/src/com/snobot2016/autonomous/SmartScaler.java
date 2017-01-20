package com.snobot2016.autonomous;

import com.snobot2016.scaling.IScaling;
import com.snobot2016.scaling.IScaling.ScaleAngles;

import edu.wpi.first.wpilibj.command.Command;

public class SmartScaler extends Command
{
    private IScaling mScaling;
    private ScaleAngles mGoalAngle;
    private boolean mFinished;

    public SmartScaler(IScaling aScaling, ScaleAngles aGoalAngle)
    {
        mScaling = aScaling;
        mGoalAngle = aGoalAngle;

    }

    @Override
    protected void initialize()
    {

    }

    @Override
    protected void execute()
    {
        if (mScaling.goToPosition(mGoalAngle))
        {
            mFinished = true;
        }

    }

    @Override
    protected boolean isFinished()
    {
        return mFinished;
    }

    @Override
    protected void end()
    {

    }

    @Override
    protected void interrupted()
    {

    }

}
