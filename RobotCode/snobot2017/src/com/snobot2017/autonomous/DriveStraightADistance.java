package com.snobot2017.autonomous;

import com.snobot2017.SnobotActor.ISnobotActor;

import edu.wpi.first.wpilibj.command.Command;

public class DriveStraightADistance extends Command
{
    private boolean mFinished;
    private ISnobotActor mSnobotActor;

    public DriveStraightADistance(double aDistance, double aSpeed, ISnobotActor aSnobotActor)
    {
        mSnobotActor = aSnobotActor;
        mFinished = false;

        mSnobotActor.setDistanceGoal(aDistance, aSpeed);
    }

    @Override
    protected void initialize()
    {
        super.initialize();
    }

    @Override
    protected void execute()
    {
        super.execute();
        mFinished = mSnobotActor.executeControlMode();
    }

    @Override
    protected boolean isFinished()
    {
        return mFinished;
    }
}
