package com.snobot.simulator.motor_sim;

public class SimpleMotorSimulator implements IMotorSimulator
{

    private double mMaxSpeed;

    private double mVoltagePercent;
    private double mVelocity;
    private double mPosition;

    public SimpleMotorSimulator(double aMaxSpeed)
    {
        mMaxSpeed = aMaxSpeed;
    }

    @Override
    public void setVoltagePercentage(double speed)
    {
        mVoltagePercent = speed;
    }

    @Override
    public double getVoltagePercentage()
    {
        return mVoltagePercent;
    }

    @Override
    public double getVelocity()
    {
        return mVelocity;
    }

    @Override
    public double getPosition()
    {
        return mPosition;
    }

    @Override
    public void reset()
    {
        mPosition = mVelocity = 0;
    }

    @Override
    public void update(double aUpdateTime)
    {
        mVelocity = mMaxSpeed * mVoltagePercent;
        mPosition += mVelocity * aUpdateTime;
    }
}
