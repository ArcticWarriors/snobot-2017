/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2016. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.hal;

public class RelayJNI extends DIOJNI
{
    public static int initializeRelayPort(int halPortHandle, boolean forward)
    {
        return 0;
    }

    public static void freeRelayPort(int relayPortHandle)
    {

    }

    public static boolean checkRelayChannel(int channel)
    {
        return false;
    }

    public static void setRelay(int relayPortHandle, boolean on)
    {

    }

    public static boolean getRelay(int relayPortHandle)
    {
        return false;
    }
}
