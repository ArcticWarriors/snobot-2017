package com.snobot.simulator.motor_sim;

public class StaticLoadDcMotorSim extends BaseDcMotorSimulator
{
    protected double mLoad;

    public StaticLoadDcMotorSim(DcMotorModel aModel, double aLoad)
    {
        super(aModel);

        mLoad = aLoad;
    }

    @Override
    public void update(double cycleTime)
    {
        mMotorModel.step(mVoltagePercentage * 12, mLoad, 0, cycleTime);
    }

}
