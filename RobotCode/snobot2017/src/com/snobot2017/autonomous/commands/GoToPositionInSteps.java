package com.snobot2017.autonomous.commands;

import com.snobot2017.SnobotActor.ISnobotActor;

import edu.wpi.first.wpilibj.command.Command;

public class GoToPositionInSteps extends Command
{
    private boolean mFinished;
    private ISnobotActor mSnobotActor;

    double mX;
    double mY;
    double mSpeed;

    public GoToPositionInSteps(double aX, double aY, double aSpeed, ISnobotActor aSnobotActor)
    {
        mSnobotActor = aSnobotActor;
        mFinished = false;

        mX = aX;
        mY = aX;
        mSpeed = aSpeed;
    }

    @Override
    protected void initialize()
    {
        super.initialize();

        mSnobotActor.setGoToPositionInStepsGoal(mX, mY, mSpeed);
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
