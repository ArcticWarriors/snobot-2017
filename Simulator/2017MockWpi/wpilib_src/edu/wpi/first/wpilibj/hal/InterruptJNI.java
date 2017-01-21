/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2016-2017. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.hal;

public class InterruptJNI extends JNIWrapper
{
    public static final int HalInvalidHandle = 0;

    public interface InterruptJNIHandlerFunction
    {
        void apply(int interruptAssertedMask, Object param);
    }

    public static int initializeInterrupts(boolean watcher)
    {
        return 0;
    }

    public static void cleanInterrupts(int interruptHandle)
    {

    }

    public static int waitForInterrupt(int interruptHandle, double timeout, boolean ignorePrevious)
    {
        return 0;
    }

    public static void enableInterrupts(int interruptHandle)
    {

    }

    public static void disableInterrupts(int interruptHandle)
    {

    }

    public static double readInterruptRisingTimestamp(int interruptHandle)
    {
        return 0;
    }

    public static double readInterruptFallingTimestamp(int interruptHandle)
    {
        return 0;
    }

    public static void requestInterrupts(int interruptHandle, int digitalSourceHandle, int analogTriggerType)
    {

    }

    public static void attachInterruptHandler(int interruptHandle, InterruptJNIHandlerFunction handler, Object param)
    {

    }

    public static void setInterruptUpSourceEdge(int interruptHandle, boolean risingEdge, boolean fallingEdge)
    {

    }
}
