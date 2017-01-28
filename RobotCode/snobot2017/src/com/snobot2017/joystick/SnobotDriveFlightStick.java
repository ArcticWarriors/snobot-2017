package com.snobot2017.joystick;

import com.snobot.lib.Logger;
import com.snobot2017.SmartDashBoardNames;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SnobotDriveFlightStick implements IDriverJoystick
{

    private Joystick mLeft;
    private Joystick mRight;
    private double mRightSpeed;
    private double mLeftSpeed;
    private Logger mLogger;

    public SnobotDriveFlightStick(Joystick aLeft, Joystick aRight, Logger aLogger)
    {
        mLeft = aLeft;
        mRight = aRight;
        mLogger = aLogger;
    }

    @Override
    public void init()
    {
        mLogger.addHeader("RightDriveJoystickSpeed");
        mLogger.addHeader("LeftDriveJoystickSpeed");
    }

    @Override
    public void update()
    {
        mLeftSpeed = mLeft.getY();
        mRightSpeed = mRight.getY();
    }

    @Override
    public void control()
    {
       
    }

    @Override
    public void rereadPreferences()
    {
        
    }

    @Override
    public void updateSmartDashboard()
    {
        SmartDashboard.putNumber(SmartDashBoardNames.sLEFT_JOYSTICK_SPEED, mLeftSpeed);
        SmartDashboard.putNumber(SmartDashBoardNames.sRIGHT_JOYSTICK_SPEED, mRightSpeed);

    }

    @Override
    public void updateLog()
    {
        mLogger.updateLogger(mLeftSpeed);
        mLogger.updateLogger(mRightSpeed);
    }

    @Override
    public void stop()
    {

    }

    @Override
    public double getRightSpeed()
    {
        return mRightSpeed;
    }

    @Override
    public double getLeftSpeed()
    {
        return mLeftSpeed;
    }

    @Override
    public double getArcadePower()
    {
        return 0;
    }

    @Override
    public double getArcadeTurn()
    {
        return 0;
    }

    @Override
    public boolean isArcadeMode()
    {
        return false;
    }

}
