package com.snobot2017.autonomous;

import com.snobot.lib.InDeadbandHelper;
import com.snobot2017.drivetrain.IDriveTrain;
import com.snobot2017.positioner.IPositioner;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Turns N degrees
 * 
 * @author jbnol
 *
 */
public class TurnWithDegrees extends Command
{
    private double mSpeed;
    private double mTurnAngle;
    private IDriveTrain mDriveTrain;
    private IPositioner mPositioner;
    private double mTurnMeasure;
    private boolean mDirection;
    private boolean mFinished;

    /**
     * Constructor
     * 
     * @param aSpeed
     *            The speed to drive
     * @param aTurnAngle
     * @param aDriveTrain
     * @param aPositioner
     */
    public TurnWithDegrees(double aSpeed, double aTurnAngle, IDriveTrain aDriveTrain, IPositioner aPositioner)
    {
        mTurnAngle = aTurnAngle;
        mPositioner = aPositioner;
        mDriveTrain = aDriveTrain;
        mSpeed = aSpeed;
        mFinished = false;
    }

    @Override
    protected void execute()
    {
        mTurnMeasure = (mTurnAngle - mPositioner.getOrientationDegrees()) % 360;
        if ((mTurnMeasure) < 0)
        {
            mTurnMeasure = mTurnMeasure + 360;
        }
        if (mTurnMeasure <= 180)
        {
            mDriveTrain.setLeftRightSpeed(-mSpeed, -mSpeed);
        }
        else
        {
            mDriveTrain.setLeftRightSpeed(mSpeed, mSpeed);
        }

        InDeadbandHelper mInDeadbandHelper = new InDeadbandHelper(10);

        if (mInDeadbandHelper.isFinished(Math.abs(mTurnAngle - mPositioner.getOrientationDegrees()) < 5))
        {
            mDriveTrain.setLeftRightSpeed(0, 0);
            mFinished = true;
        }
    }

    @Override
    protected boolean isFinished()
    {
        return mFinished;
    }
}