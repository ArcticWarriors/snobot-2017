package com.snobot2017.climbing;

import com.snobot.lib.logging.ILogger;
import com.snobot2017.Properties2017;
import com.snobot2017.SmartDashBoardNames;
import com.snobot2017.joystick.IOperatorJoystick;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Controls the climber
 *
 * @author Jeff
 *
 */
public class Climbing implements IClimbing
{

    private SpeedController mClimbingMotor;
    private IOperatorJoystick mJoystick;
    private ILogger mLogger;
    private double mMotorSpeed;

    /**
     * This is the constructor
     * 
     * @param aClimbingMotor
     * @param aLogger
     * @param aJoystick
     */
    public Climbing(SpeedController aClimbingMotor, ILogger aLogger, IOperatorJoystick aJoystick)
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
        mMotorSpeed = mClimbingMotor.get();
    }

    @Override
    public void control()
    {
        controlRotation();
    }

    /**
     * This passes the button press for catching and climbing modes.
     */
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
        // Nothing
    }

    @Override
    public void updateSmartDashboard()
    {
        SmartDashboard.putNumber(SmartDashBoardNames.sROBOT_ROPE_MOTOR_SPEED, mMotorSpeed);
    }

    @Override
    public void updateLog()
    {
        mLogger.updateLogger(mMotorSpeed);
    }

    @Override
    public void stop()
    {
        mClimbingMotor.set(0);
    }

    @Override
    public void catchRope()
    {
        double speed = Properties2017.sROBOT_CATCHING_ROPE_SPEED.getValue();
        mClimbingMotor.set(speed);
        mLogger.updateLogger(speed);
    }

    @Override
    public void climbRope()
    {
        double speed = Properties2017.sROBOT_CLIMBING_ROPE_SPEED.getValue();
        mClimbingMotor.set(speed);
        mLogger.updateLogger(speed);
    }

}