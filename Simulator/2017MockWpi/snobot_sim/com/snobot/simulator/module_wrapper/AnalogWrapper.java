package com.snobot.simulator.module_wrapper;

public class AnalogWrapper extends ASensorWrapper
{

    protected double mVoltage;
    protected double mAccumulator;

    public AnalogWrapper(int aIndex)
    {
        super("Analog " + aIndex);
    }

    public void setVoltage(double aVoltage)
    {
        mVoltage = aVoltage;
    }

    public double getVoltage()
    {
        return mVoltage;
    }

    public void setAccumulator(double aValue)
    {
        mAccumulator = aValue;
    }

    public double getAccumulator()
    {
        return mAccumulator;
    }
}
