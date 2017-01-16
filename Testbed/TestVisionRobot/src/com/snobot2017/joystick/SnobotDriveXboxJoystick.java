package com.snobot2017.joystick;

import com.snobot.lib.ui.LatchedButton;
import com.snobot.lib.ui.XboxButtonMap;

import edu.wpi.first.wpilibj.Joystick;

/**
 * 
 * @author jbnol_000
 *
 *         Xbax Joystick class
 */
public class SnobotDriveXboxJoystick implements IDriverJoystick
{
    private Joystick mJoystick;
    private double mLeftSpeed;
    private double mRightSpeed;
    private boolean mTakePicture;
    private LatchedButton mTakePictureLatcher;

    /**
     * 
     * @param aJoystick
     *            The Joystick
     */
    public SnobotDriveXboxJoystick(Joystick aJoystick)
    {
        mJoystick = aJoystick;
        mTakePictureLatcher = new LatchedButton();
    }

    @Override
    public void init()
    {

    }

    @Override
    public void update()
    {
        mLeftSpeed = mJoystick.getRawAxis(XboxButtonMap.LEFT_Y_AXIS);
        mRightSpeed = mJoystick.getRawAxis(XboxButtonMap.RIGHT_Y_AXIS);

        mTakePicture = mTakePictureLatcher.update(mJoystick.getRawButton(4));
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

    }

    @Override
    public void updateLog()
    {

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

    @Override
    public boolean isTakePicture()
    {
        return mTakePicture;
    }

}
