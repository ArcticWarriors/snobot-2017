package com.snobot.simulator.motor_sim;

public class StaticLoadDcMotorSim implements IMotorSimulator
{
    protected DcMotorModel mMotorModel;
    protected double mLoad;

    protected double mAppliedVoltage;


    public StaticLoadDcMotorSim(DcMotorModel aModel, double aLoad)
    {
        mMotorModel = aModel;
        mLoad = aLoad;
    }

    @Override
    public void update(double cycleTime)
    {
        mMotorModel.step(mAppliedVoltage, mLoad, 0, cycleTime);
    }

    @Override
    public void setAppliedVoltage(double speed)
    {
        mAppliedVoltage = speed;
    }

    @Override
    public double getAppliedVoltage()
    {
        return mAppliedVoltage;
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
