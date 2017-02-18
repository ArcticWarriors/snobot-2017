/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2016-2017. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.hal;

@SuppressWarnings("AbbreviationAsWordInName")
public class PDPJNI extends JNIWrapper
{
    public static void initializePDP(int module)
    {

    }

    public static boolean checkPDPModule(int module)
    {
        return true;
    }

    public static boolean checkPDPChannel(int channel)
    {
        return channel >= 0 && channel <= 16;
    }

    public static double getPDPTemperature(int module)
    {
        return 0;
    }

    public static double getPDPVoltage(int module)
    {
        return 0;
    }

    public static double getPDPChannelCurrent(byte channel, int module)
    {
        return 0;
    }

    public static double getPDPTotalCurrent(int module)
    {
        return 0;
    }

    public static double getPDPTotalPower(int module)
    {
        return 0;
    }

    public static double getPDPTotalEnergy(int module)
    {
        return 0;
    }

    public static void resetPDPTotalEnergy(int module)
    {

    }

    public static void clearPDPStickyFaults(int module)
    {

    }
}
