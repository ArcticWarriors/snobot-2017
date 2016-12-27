package com.snobot.simulator.motor_sim;

public abstract class BaseDcMotorSimulator implements IMotorSimulator
{

    protected final DcMotorModel mMotorModel;
    protected double mVoltagePercentage;

    public BaseDcMotorSimulator(DcMotorModel aMotorModel)
    {
        mMotorModel = aMotorModel;
    }

    @Override
    public void setVoltagePercentage(double aSpeed)
    {
        mVoltagePercentage = aSpeed;
    }

    @Override
    public double getVoltagePercentage()
    {
        return mVoltagePercentage;
    }

    @Override
    public double getVelocity()
    {
        return mMotorModel.getVelocity();
    }

    @Override
    public double getPosition()
    {
        return mMotorModel.getPosition();
    }

    public double getAcceleration()
    {
        return mMotorModel.getAcceleration();
    }

    @Override
    public void reset()
    {
        reset(0, 0, 0);
    }

    @Override
    public void reset(double aPosition, double aVelocity, double aCurrent)
    {
        mMotorModel.reset(0, 0, 0);
    }

    public double getCurrent()
    {
        return mMotorModel.getCurrent();
    }

}