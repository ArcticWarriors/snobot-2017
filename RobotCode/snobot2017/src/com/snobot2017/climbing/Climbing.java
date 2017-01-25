package com.snobot2017.climbing;

import com.snobot.lib.Logger;
import com.snobot2017.SmartDashBoardNames;
import com.snobot2017.joystick.IOperatorJoystick;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climbing implements IClimbing
{

    private SpeedController mClimbingMotor;
    private IOperatorJoystick mJoystick;
    private Logger mLogger;

    public Climbing(SpeedController aClimbingMotor, Logger aLogger, IOperatorJoystick aJoystick)
    {
        mClimbingMotor = aClimbingMotor;
        mLogger = aLogger;
        mJoystick = aJoystick;
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
        if (mJoystick.isCatchRope())
        {
            catchRope();
        }
        else if (mJoystick.isClimb())
        {
            climbRope();
        }
        else
        {
            stop();
        }
    }

    @Override
    public void rereadPreferences()
    {

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
        double speed = SmartDashboard.getNumber(SmartDashBoardNames.sROBOT_CATCHING_ROPE_SPEED, 0.5);
        mClimbingMotor.set(speed);
        mLogger.updateLogger(speed);
    }

    @Override
    public void climbRope()
    {
        double speed = SmartDashboard.getNumber(SmartDashBoardNames.sROBOT_CLIMBING_ROPE_SPEED, 1.0);
        mClimbingMotor.set(speed);
        mLogger.updateLogger(speed);
    }

}
