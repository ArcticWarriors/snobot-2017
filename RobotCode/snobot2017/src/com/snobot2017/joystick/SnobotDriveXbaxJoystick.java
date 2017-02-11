package com.snobot2017.joystick;

import com.snobot.lib.Logger;
import com.snobot.lib.ui.XbaxButtonMap;
import com.snobot2017.SmartDashBoardNames;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SnobotDriveXbaxJoystick implements IDriverJoystick
{
    private Joystick mJoystick;
    private double mLeftSpeed;
    private double mRightSpeed;
    private Logger mLogger;

    public SnobotDriveXbaxJoystick(Joystick aJoystick, Logger aLogger)
    {

        mJoystick = aJoystick;
        mLogger = aLogger;
    }

    @Override
    public void init()
    {
        mLogger.addHeader("XbaxLeftJoystickSpeed");
        mLogger.addHeader("XbaxRightJoystickSpeed");
    }

    @Override
    public void update()
    {
        mLeftSpeed = -mJoystick.getRawAxis(XbaxButtonMap.LEFT_Y_AXIS);
        mRightSpeed = -mJoystick.getRawAxis(XbaxButtonMap.RIGHT_Y_AXIS);
    }

    @Override
    public void control()
    {
        // Nothing
    }

    @Override
    public void rereadPreferences()
    {
        // Nothing
    }

    @Override
    public void updateSmartDashboard()
    {
        SmartDashboard.putNumber(SmartDashBoardNames.sLEFT_XBAX_JOYSTICK_SPEED, mLeftSpeed);
        SmartDashboard.putNumber(SmartDashBoardNames.sRIGHT_XBAX_JOYSTICK_SPEED, mRightSpeed);
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
        // Nothing
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
