package com.snobot2017.autonomous.commands;

import com.snobot2017.SnobotActor.ISnobotActor;

import edu.wpi.first.wpilibj.command.Command;

public class GoToPositionInSteps extends Command
{
    private boolean mFinished;
    private ISnobotActor mSnobotActor;

    public GoToPositionInSteps(double aX, double aY, double aSpeed, ISnobotActor aSnobotActor)
    {
        mSnobotActor = aSnobotActor;
        mFinished = false;

        mSnobotActor.setGoToPositionInStepsGoal(aX, aY, aSpeed);
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
