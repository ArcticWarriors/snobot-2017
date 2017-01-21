package com.snobot2017.climbing;

import com.snobot.lib.Logger;
import com.snobot2017.SmartDashBoardNames;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climbing implements IClimbing
{

    private SpeedController mClimbingMotor;
    private Logger mLogger;
  
    public Climbing(SpeedController aClimbingMotor, Logger aLogger)
    {
        mClimbingMotor = aClimbingMotor;
        mLogger = aLogger;
        SmartDashboard.putNumber(SmartDashBoardNames.sROBOT_CATCHING_ROPE, 1);
        SmartDashboard.putNumber(SmartDashBoardNames.sROBOT_CLIMBING_ROPE, 1);
    }
    @Override
    public void init()
    {
        mLogger.addHeader("RotationSpeed");
    }

    @Override
    public void update()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void control()
    {
        controlRotation();
    }

    private void controlRotation()
    {
        
    }
    
    @Override
    public void rereadPreferences()
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateSmartDashboard()
    {

    }

    @Override
    public void updateLog()
    {
        mLogger.updateLogger(0);
    }

    @Override
    public void stop()
    {
       mClimbingMotor.set(0); 
      mLogger.updateLogger(0);  
    }

    @Override
    public void catchRope()
    {
      
       double mNumber = SmartDashboard.getNumber(SmartDashBoardNames.sROBOT_CATCHING_ROPE);
       mClimbingMotor.set(mNumber);
       mLogger.updateLogger(mNumber);
    }

    @Override
    public void climbRope()
    {
        double mNumber = SmartDashboard.getNumber(SmartDashBoardNames.sROBOT_CLIMBING_ROPE);
        mClimbingMotor.set(mNumber);
        mLogger.updateLogger(mNumber);
    }

}
