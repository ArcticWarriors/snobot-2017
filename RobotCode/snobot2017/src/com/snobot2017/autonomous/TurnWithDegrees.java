package com.snobot2017.autonomous;

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
     * @param aSpeed The speed to drive
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

    private void Turn()
    {
        mTurnMeasure = (mTurnAngle - mPositioner.getOrientationDegrees()) % 360;
        if ((mTurnMeasure) < 0)
        {
            mTurnMeasure = mTurnMeasure + 360;
        }
        if (mTurnMeasure <= 180)
        {
            mDirection = false;
        }
        else
        {
            mDirection = true;
        }
        
        // TODO  andrew - remove unnecessary direction and add bufferonis
        
        if (mDirection == true)
        {
            mDriveTrain.setLeftRightSpeed(mSpeed, mSpeed);
            if (mTurnAngle >= mPositioner.getOrientationDegrees())
                
            {
                mDriveTrain.setLeftRightSpeed(0, 0);
                mFinished = true;
            }
        }
        else
        {
            mDriveTrain.setLeftRightSpeed(-mSpeed, -mSpeed);
            if (mTurnAngle <= mPositioner.getOrientationDegrees())
            {
                mDriveTrain.setLeftRightSpeed(0, 0);
                mFinished = true;
            }
        }
        
        //System.out.println("TurnWithDegrees " + mTurnAngle + " " + mPositioner.getOrientationDegrees() + " " + mTurnMeasure);

    }

    @Override
    protected void execute()
    {
        super.execute();
        Turn();
    }

    @Override
    protected boolean isFinished()
    {
        return mFinished;
        
    }
}