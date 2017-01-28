package com.snobot2017.autonomous;

import com.snobot2017.drivetrain.IDriveTrain;
import com.snobot2017.positioner.IPositioner;

import edu.wpi.first.wpilibj.command.Command;

public class DriveStraightADistance extends Command
{
    private double mDistance;
    private IDriveTrain mDriveTrain;
    private IPositioner mPositioner;
    private double mSpeed;
    private double mStartDistance;
    private boolean mFinished;

    public DriveStraightADistance(double aDistance, double aSpeed, IDriveTrain aDriveTrain, IPositioner aPositioner)
    {
        mDistance = aDistance;
        mDriveTrain = aDriveTrain;
        mPositioner = aPositioner;
        mSpeed = aSpeed;
        mFinished = false;
    }

    @Override
    protected void initialize()
    {
        // TODO Auto-generated method stub
        super.initialize();
        mStartDistance = mPositioner.getTotalDistance();
    }

    private void Drive()
    {
        mFinished = (mPositioner.getTotalDistance() - mStartDistance) >= mDistance;
        System.out.println("Drive " + mFinished + " " + mPositioner.getTotalDistance() + " " + mStartDistance + " " + mDistance);

        if (mFinished == true)
        {
            mDriveTrain.stop();
        }
        else
        {
            mDriveTrain.setLeftRightSpeed(mSpeed, mSpeed);
        }
    }

    @Override
    protected void execute()
    {
        super.execute();
        Drive();

    }

    @Override
    protected boolean isFinished()
    {
        return mFinished;
    }
}
