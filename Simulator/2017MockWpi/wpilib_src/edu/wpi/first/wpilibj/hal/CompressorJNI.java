/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2016. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.hal;

public class CompressorJNI extends JNIWrapper
{
    public static int initializeCompressor(byte module)
    {
        return 0;
    }

    public static boolean checkCompressorModule(byte module)
    {
        return false;
    }

    public static boolean getCompressor(int compressorHandle)
    {
        return false;
    }

    public static void setCompressorClosedLoopControl(int compressorHandle, boolean value)
    {

    }

    public static boolean getCompressorClosedLoopControl(int compressorHandle)
    {
        return false;
    }

    public static boolean getCompressorPressureSwitch(int compressorHandle)
    {
        return false;
    }

    public static double getCompressorCurrent(int compressorHandle)
    {
        return 0;
    }

    public static boolean getCompressorCurrentTooHighFault(int compressorHandle)
    {
        return false;
    }

    public static boolean getCompressorCurrentTooHighStickyFault(int compressorHandle)
    {
        return false;
    }

    public static boolean getCompressorShortedStickyFault(int compressorHandle)
    {
        return false;
    }

    public static boolean getCompressorShortedFault(int compressorHandle)
    {
        return false;
    }

    public static boolean getCompressorNotConnectedStickyFault(int compressorHandle)
    {
        return false;
    }

    public static boolean getCompressorNotConnectedFault(int compressorHandle)
    {
        return false;
    }

    public static void clearAllPCMStickyFaults(byte compressorModule)
    {

    }
}
