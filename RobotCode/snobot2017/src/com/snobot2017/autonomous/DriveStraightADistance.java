package com.snobot2017.autonomous;

import com.snobot2017.SnobotActor.ISnobotActor;

import edu.wpi.first.wpilibj.command.Command;

public class DriveStraightADistance extends Command
{
    private double mDistance;
    private double mSpeed;
    private double mStartDistance;
    private boolean mFinished;
    private ISnobotActor mSnobotActor;

    public DriveStraightADistance(double aDistance, double aSpeed, ISnobotActor aSnobotActor)
    {
        mSnobotActor = aSnobotActor;
        mDistance = aDistance;
        mSpeed = aSpeed;
        mFinished = false;
        mSnobotActor.setGoal(mDistance, aSpeed);
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
        mFinished = mSnobotActor.driveDistance();
    }

    @Override
    protected boolean isFinished()
    {
        return mFinished;
    }
}
