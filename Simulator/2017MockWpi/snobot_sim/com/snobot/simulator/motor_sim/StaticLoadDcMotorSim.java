package com.snobot.simulator.motor_sim;

public class StaticLoadDcMotorSim implements IMotorSimulator
{
    protected DcMotorModel mMotorModel;
    protected double mLoad;

    protected double mVoltagePercentage;


    public StaticLoadDcMotorSim(DcMotorModel aModel, double aLoad)
    {
        mMotorModel = aModel;
        mLoad = aLoad;
    }

    @Override
    public void update(double cycleTime)
    {
        mMotorModel.step(mVoltagePercentage * 12, mLoad, 0, cycleTime);
    }

    @Override
    public void setVoltagePercentage(double speed)
    {
        mVoltagePercentage = speed;
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
        mMotorModel.reset(0, 0, 0);
    }

    public double getCurrent()
    {
        return mMotorModel.getCurrent();
    }

}
