/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2016. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.hal;

public class SolenoidJNI extends JNIWrapper
{
    public static int initializeSolenoidPort(int halPortHandle)
    {
        return 0;
    }

    public static boolean checkSolenoidModule(int module)
    {
        return false;
    }

    public static boolean checkSolenoidChannel(int channel)
    {
        return false;
    }

    public static void freeSolenoidPort(int portHandle)
    {

    }

    public static void setSolenoid(int portHandle, boolean on)
    {

    }

    public static boolean getSolenoid(int portHandle)
    {
        return false;
    }

    public static byte getAllSolenoids(byte module)
    {
        return 0;
    }

    public static int getPCMSolenoidBlackList(byte module)
    {
        return 0;
    }

    public static boolean getPCMSolenoidVoltageStickyFault(byte module)
    {
        return false;
    }

    public static boolean getPCMSolenoidVoltageFault(byte module)
    {
        return false;
    }

    public static void clearAllPCMStickyFaults(byte module)
    {

    }
}
