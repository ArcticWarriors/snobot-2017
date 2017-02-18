package com.snobot2017.joystick;

import com.snobot.lib.Logger;
import com.snobot.lib.ui.LatchedButton;
import com.snobot.lib.ui.XboxButtonMap;
import com.snobot2017.SmartDashBoardNames;
import com.snobot2017.autonomous.AutonomousFactory;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SnobotDriveXbaxJoystick implements IDriverJoystick
{
    private Joystick mJoystick;
    private double mLeftSpeed;
    private double mRightSpeed;
    private Logger mLogger;
    private LatchedButton mDPadUp;
    private LatchedButton mDPadDown;
    private AutonomousFactory mAutonFactory;

    public SnobotDriveXbaxJoystick(Joystick aJoystick, Logger aLogger, AutonomousFactory aAutonFactory)
    {

        mJoystick = aJoystick;
        mLogger = aLogger;
        mAutonFactory = aAutonFactory;
        mDPadUp = new LatchedButton();
        mDPadDown = new LatchedButton();
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
        mLeftSpeed = -mJoystick.getRawAxis(XboxButtonMap.LEFT_Y_AXIS);
        mRightSpeed = -mJoystick.getRawAxis(XboxButtonMap.RIGHT_Y_AXIS);
        
        if(mDPadUp.update(mJoystick.getPOV(0) == 0))
        {
            mAutonFactory.incrementAutonMode(1);
        }
        else if(mDPadDown.update(mJoystick.getPOV(180) == 180))
        {
            mAutonFactory.incrementAutonMode(-1);
        }
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
        SmartDashboard.putNumber(SmartDashBoardNames.sAUTON_NUM, mAutonFactory.autonMode());
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
