package com.snobot2017.autonomous;

import com.snobot2017.drivetrain.IDriveTrain;

import edu.wpi.first.wpilibj.command.TimedCommand;

public class StupidTurn extends TimedCommand
{
    private double mSpeed;
    private boolean mDirection;
    private IDriveTrain mDriveTrain;
    public StupidTurn(double aSpeed,IDriveTrain aDriveTrain, double aTimeout, boolean aDirection)
    {
        super(aTimeout);
        mSpeed=aSpeed;
        mDriveTrain=aDriveTrain;
        mDirection=aDirection;
        Turn();
    }
   private void Turn()
   {
       if(mDirection==true)
       {
           mDriveTrain.setLeftRightSpeed(mSpeed, -mSpeed);
       }
       else
       {
           mDriveTrain.setLeftRightSpeed(-mSpeed, mSpeed);
       }
   }
    @Override
protected boolean isFinished()
{
    // TODO Auto-generated method stub
    return false;
}

}