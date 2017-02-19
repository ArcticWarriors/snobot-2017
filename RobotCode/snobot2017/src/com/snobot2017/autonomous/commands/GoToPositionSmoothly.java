package com.snobot2017.autonomous.commands;

import com.snobot2017.SnobotActor.ISnobotActor;

import edu.wpi.first.wpilibj.command.Command;

public class GoToPositionSmoothly extends Command
{
    private boolean mFinished;
    private ISnobotActor mSnobotActor;

    public GoToPositionSmoothly(double aX, double aY, ISnobotActor aSnobotActor)
    {
        mSnobotActor = aSnobotActor;
        mFinished = false;

        mSnobotActor.setDriveSmoothlyToPositionGoal(aX, aY);
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

