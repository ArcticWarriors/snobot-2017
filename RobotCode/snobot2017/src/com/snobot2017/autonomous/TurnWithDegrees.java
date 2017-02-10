package com.snobot2017.autonomous;

import com.snobot.lib.InDeadbandHelper;
import com.snobot2017.SnobotActor.ISnobotActor;
import com.snobot2017.SnobotActor.SnobotActor;
import com.snobot2017.drivetrain.IDriveTrain;
import com.snobot2017.positioner.IPositioner;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Turns N degrees
 * 
 * @author Jeff
 *
 */
public class TurnWithDegrees extends Command
{
    private SnobotActor mSnobotActor;
    private double mSpeed;
    private double mTurnAngle;
    private IDriveTrain mDriveTrain;
    private IPositioner mPositioner;
    private double mTurnMeasure;
    private InDeadbandHelper mInDeadbandHelper;
    private boolean mFinished;

    /**
     * Constructor 
     * 
     * @param aSpeed The speed to drive
     * @param aTurnAngle
     * @param aDriveTrain
     * @param aPositioner
     */
    public TurnWithDegrees(double aSpeed, double aTurnAngle, ISnobotActor aSnobotActor)
    {
        mTurnAngle = aTurnAngle;
        mSpeed = aSpeed;
        mFinished = false;
    }

    @Override
    protected void execute()
    {
        mFinished = mSnobotActor.turnToAngle(mTurnAngle, mSpeed);
        mTurnMeasure = (mTurnAngle - mPositioner.getOrientationDegrees()) % 360;
        if ((mTurnMeasure) < 0)
        {
            mTurnMeasure = mTurnMeasure + 360;
        }
        if (mTurnMeasure <= 180)
        {
            mDriveTrain.setLeftRightSpeed(mSpeed, -mSpeed);
        }
        else
        {
            mDriveTrain.setLeftRightSpeed(-mSpeed, mSpeed);
        }

        if (mInDeadbandHelper.isFinished(Math.abs(mTurnAngle - mPositioner.getOrientationDegrees()) < 5))
        {
            mDriveTrain.setLeftRightSpeed(0, 0);
            mFinished = true;
        }
        else
        {
            mFinished = false;
        }
        
        //System.out.println("TurnWithDegrees " + mTurnAngle + " " + mPositioner.getOrientationDegrees() + " " + mTurnMeasure);
    }

    @Override
    protected boolean isFinished()
    {
        return mFinished;
        
    }
}