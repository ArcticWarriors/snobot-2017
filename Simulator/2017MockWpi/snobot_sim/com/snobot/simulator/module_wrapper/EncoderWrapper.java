package com.snobot.simulator.module_wrapper;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

public class EncoderWrapper extends ASensorWrapper
{
    private SpeedControllerWrapper mMotorWrapper;
    private double mEncodingFactor;
    private double mDistancePerTick;

    public EncoderWrapper(int aIndexA, int aIndexB)
    {
        super("Encoder (" + aIndexA + ", " + aIndexB + ")");

        setEncodingFactor(CounterBase.EncodingType.k4X);
        mDistancePerTick = 1;
    }

    private void setEncodingFactor(EncodingType aFactor)
    {
        switch (aFactor)
        {
        case k1X:
            mEncodingFactor = 1.0;
            break;
        case k2X:
            mEncodingFactor = 0.5;
            break;
        case k4X:
            mEncodingFactor = 0.25;
            break;
        default:
            // This is never reached, EncodingType enum limits values
            mEncodingFactor = 0.0;
            break;
        }
    }

    public void reset()
    {
        if (mMotorWrapper != null)
        {
            mMotorWrapper.resetDistance();
        }
    }

    public int getRaw()
    {
        return (int) (getDistance() / (mEncodingFactor * mDistancePerTick));
    }

    public double getDistance()
    {
        if (mMotorWrapper == null)
        {
            return 0;
        }
        else
        {
            return mMotorWrapper.getPosition();
        }
    }

    public boolean isHookedUp()
    {
        return mMotorWrapper != null;
    }

    public void setSpeedController(SpeedControllerWrapper aMotorWrapper)
    {
        mMotorWrapper = aMotorWrapper;
    }

    public void setDistancePerTick(double aDistancePerTick)
    {
        mDistancePerTick = aDistancePerTick;
    }
}