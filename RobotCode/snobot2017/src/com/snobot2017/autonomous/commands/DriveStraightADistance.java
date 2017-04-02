package com.snobot2017.autonomous.commands;

import com.snobot2017.SnobotActor.ISnobotActor;

import edu.wpi.first.wpilibj.command.Command;

public class DriveStraightADistance extends Command
{
    private boolean mFinished;
    private ISnobotActor mSnobotActor;

    private double mDistance;
    private double mSpeed;

    public DriveStraightADistance(double aDistance, double aSpeed, ISnobotActor aSnobotActor)
    {
        mSnobotActor = aSnobotActor;
        mFinished = false;

        mDistance = aDistance;
        mSpeed = aSpeed;
    }

    @Override
    protected void initialize()
    {
        super.initialize();

        mSnobotActor.setDistanceGoal(mDistance, mSpeed);
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
