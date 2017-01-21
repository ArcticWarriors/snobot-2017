package com.snobot.simulator.module_wrapper;

import com.snobot.simulator.motor_sim.IMotorSimulator;

import edu.wpi.first.wpilibj.hal.HAL;

public class SpeedControllerWrapper extends ASensorWrapper
{

    private final double mWaitTime;
    private IMotorSimulator mMotorSimulator;

    public SpeedControllerWrapper(int index)
    {
        this(index, HAL.getCycleTime());
    }

    public SpeedControllerWrapper(int index, double aWaitTime)
    {
        super("Speed Controller " + index);

        mWaitTime = aWaitTime;
        mMotorSimulator = new IMotorSimulator.NullMotorSimulator();
    }

    public void setMotorSimulator(IMotorSimulator aSimulator)
    {
        mMotorSimulator = aSimulator;
    }

    public double get()
    {
        return mMotorSimulator.getVoltagePercentage();
    }

    public void set(double speed)
    {
        mMotorSimulator.setVoltagePercentage(speed);
        mMotorSimulator.update(mWaitTime);
    }

    public double getPosition()
    {
        return mMotorSimulator.getPosition();
    }

    public double getVelocity()
    {
        return mMotorSimulator.getVelocity();
    }

    public void reset()
    {
        mMotorSimulator.reset();
    }

    public void reset(double aPosition, double aVelocity, double aCurrent)
    {
        mMotorSimulator.reset(aPosition, aVelocity, aCurrent);
    }
}
