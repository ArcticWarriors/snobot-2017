/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2016-2017. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.hal;

import java.nio.IntBuffer;

public class CounterJNI extends JNIWrapper
{
    public static int initializeCounter(int mode, IntBuffer index)
    {
        return 0;
    }

    public static void freeCounter(int counterHandle)
    {

    }

    public static void setCounterAverageSize(int counterHandle, int size)
    {

    }

    public static void setCounterUpSource(int counterHandle, int digitalSourceHandle, int analogTriggerType)
    {

    }

    public static void setCounterUpSourceEdge(int counterHandle, boolean risingEdge, boolean fallingEdge)
    {

    }

    public static void clearCounterUpSource(int counterHandle)
    {

    }

    public static void setCounterDownSource(int counterHandle, int digitalSourceHandle, int analogTriggerType)
    {

    }

    public static void setCounterDownSourceEdge(int counterHandle, boolean risingEdge, boolean fallingEdge)
    {

    }

    public static void clearCounterDownSource(int counterHandle)
    {

    }

    public static void setCounterUpDownMode(int counterHandle)
    {

    }

    public static void setCounterExternalDirectionMode(int counterHandle)
    {

    }

    public static void setCounterSemiPeriodMode(int counterHandle, boolean highSemiPeriod)
    {

    }

    public static void setCounterPulseLengthMode(int counterHandle, double threshold)
    {

    }

    public static int getCounterSamplesToAverage(int counterHandle)
    {
        return 0;
    }

    public static void setCounterSamplesToAverage(int counterHandle, int samplesToAverage)
    {

    }

    public static void resetCounter(int counterHandle)
    {

    }

    public static int getCounter(int counterHandle)
    {
        return 0;
    }

    public static double getCounterPeriod(int counterHandle)
    {
        return 0;
    }

    public static void setCounterMaxPeriod(int counterHandle, double maxPeriod)
    {

    }

    public static void setCounterUpdateWhenEmpty(int counterHandle, boolean enabled)
    {

    }

    public static boolean getCounterStopped(int counterHandle)
    {
        return false;
    }

    public static boolean getCounterDirection(int counterHandle)
    {
        return false;
    }

    public static void setCounterReverseDirection(int counterHandle, boolean reverseDirection)
    {

    }
}
