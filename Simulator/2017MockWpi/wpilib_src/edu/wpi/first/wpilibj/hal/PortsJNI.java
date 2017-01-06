/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2016-2017. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.hal;

public class PortsJNI extends JNIWrapper
{

    public static int getNumAccumulators()
    {
        return 0;
    }

    public static int getNumAnalogTriggers()
    {
        return 0;
    }

    public static int getNumAnalogInputs()
    {
        return 8;
    }

    public static int getNumAnalogOutputs()
    {
        return 2;
    }

    public static int getNumCounters()
    {
        return 0;
    }

    public static int getNumDigitalHeaders()
    {
        return 0;
    }

    public static int getNumPWMHeaders()
    {
        return 0;
    }

    public static int getNumDigitalChannels()
    {
        return 26;
    }

    public static int getNumPWMChannels()
    {
        return 20;
    }

    public static int getNumDigitalPWMOutputs()
    {
        return 0;
    }

    public static int getNumEncoders()
    {
        return 0;
    }

    public static int getNumInterrupts()
    {
        return 0;
    }

    public static int getNumRelayChannels()
    {
        return 0;
    }

    public static int getNumRelayHeaders()
    {
        return 0;
    }

    public static int getNumPCMModules()
    {
        return 0;
    }

    public static int getNumSolenoidChannels()
    {
        return 0;
    }

    public static int getNumPDPModules()
    {
        return 23;
    }

    public static int getNumPDPChannels()
    {
        return 16;
    }
}
