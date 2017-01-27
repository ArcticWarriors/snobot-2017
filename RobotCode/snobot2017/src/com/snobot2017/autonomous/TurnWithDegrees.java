package com.snobot2017.autonomous;

import com.snobot2017.drivetrain.IDriveTrain;
import com.snobot2017.positioner.IPositioner;

import edu.wpi.first.wpilibj.command.Command;

public class TurnWithDegrees extends Command
{
private double mSpeed;
private double mTurnAngle;
private IDriveTrain mDriveTrain;
private IPositioner mPositioner;
private double mTurnMeasure;
private boolean mDirection;
public TurnWithDegrees(double aSpeed, double aTurnAngle, IDriveTrain aDriveTrain, IPositioner aPositioner)
{
    mTurnAngle=aTurnAngle;
    mPositioner=aPositioner;
    mDriveTrain=aDriveTrain;
    mSpeed=aSpeed;
}
private void Turn()
{
    mTurnMeasure =(mTurnAngle-mPositioner.getOrientationDegrees()) % 360;
    if((mTurnMeasure) < 0 )
    {
      mTurnMeasure=mTurnMeasure+360;
    }
    if(mTurnMeasure<=180)
    {
        mDirection=false;
    }
    else
    {
        mDirection=true;
    }
    if (mDirection==true)
    {
        mDriveTrain.setLeftRightSpeed(mSpeed,-mSpeed);
        if (mTurnAngle== mPositioner.getOrientationDegrees())
        {
            mDriveTrain.setLeftRightSpeed(0, 0);
        }
    }
    else
    {
        mDriveTrain.setLeftRightSpeed(-mSpeed, mSpeed);
    if (mTurnAngle== mPositioner.getOrientationDegrees())
    {
        mDriveTrain.setLeftRightSpeed(0, 0);
    }
    }
}
@Override
protected boolean isFinished()
{
    // TODO Auto-generated method stub
    return false;
}
}