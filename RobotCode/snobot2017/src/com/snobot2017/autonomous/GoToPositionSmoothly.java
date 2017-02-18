package com.snobot2017.autonomous;

import com.snobot2017.SnobotActor.ISnobotActor;

import edu.wpi.first.wpilibj.command.Command;

public class GoToPositionSmoothly extends Command
{
    private boolean mFinished;
    private ISnobotActor mSnobotActor;

    public GoToPositionSmoothly(double aX, double aY, double aSpeed, ISnobotActor aSnobotActor)
    {
        mSnobotActor = aSnobotActor;
        mFinished = false;

        mSnobotActor.setDriveSmoothlyToPositionGoal(aX, aY, aSpeed);
    }

    @Override
    protected void execute()
    {
        mFinished = mSnobotActor.executeControlMode();
    }

    @Override
    protected boolean isFinished()
    {
        return mFinished;
    }

}

