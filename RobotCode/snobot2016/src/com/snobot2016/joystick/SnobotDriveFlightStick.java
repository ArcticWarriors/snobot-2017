package com.snobot2016.joystick;

import edu.wpi.first.wpilibj.Joystick;

/**
 * 
 * @author jbnol_000
 * 
 * Special class for the Flightsticks driver joysticks
 *
 */
public class SnobotDriveFlightStick implements IDriverJoystick
{

    private Joystick mLeft;
    private Joystick mRight;
    private double mRightSpeed;
    private double mLeftSpeed;
    
    /**
     * 
     * @param aLeft
     *             Left FlightStick
     * @param aRight
     *              Right FlightStick
     *              
     */

    public SnobotDriveFlightStick(Joystick aLeft, Joystick aRight)
    {
        mLeft = aLeft;
        mRight = aRight;
    }

    public void init()
    {

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

}
