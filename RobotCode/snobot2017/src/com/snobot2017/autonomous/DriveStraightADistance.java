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
public DriveStraightADistance(double aDistance,double aSpeed,IDriveTrain aDriveTrain, IPositioner aPositioner)
{
    mDistance=aDistance;
    mDriveTrain=aDriveTrain;
    mPositioner=aPositioner;
    mSpeed=aSpeed;
}
private void Drive()
{
    mDriveTrain.setLeftRightSpeed(mSpeed, mSpeed);
    if(mPositioner.getTotalDistance()== mDistance)
    {
        mDriveTrain.setLeftRightSpeed(0, 0);
    }
        
}
@Override
protected boolean isFinished()
{
    // TODO Auto-generated method stub
    return false;
}
}

