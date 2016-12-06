package com.snobot.simulator.module_wrapper;

import edu.wpi.first.wpilibj.hal.HAL;

public class SpeedControllerWrapper extends ASensorWrapper
{

    private double mVoltagePercent;
    private double mMaxSpeed;
    private double mVelocity;
    private double mPosition;

    public SpeedControllerWrapper(int index)
    {
        super("Speed Controller " + index);
        mVoltagePercent = 0;
        mMaxSpeed = 1;
    }

    public void setMotorParameters(double aMaxSpeed)
    {
        mMaxSpeed = aMaxSpeed;
    }

    public double get()
    {
        return mVoltagePercent;
    }

    public void set(double speed)
    {
        mVoltagePercent = speed;
        mVelocity = mMaxSpeed * mVoltagePercent;
        mPosition += mVelocity * HAL.getCycleTime();
    }

    public double getPosition()
    {
        return mPosition;
    }

    public void resetDistance()
    {
        mPosition = 0;
    }

    public void setPosition(double startPosition)
    {
        mPosition = startPosition;
    }
}
