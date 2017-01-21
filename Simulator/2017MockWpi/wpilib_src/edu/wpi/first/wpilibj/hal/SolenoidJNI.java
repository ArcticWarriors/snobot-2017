/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2016-2017. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.hal;

import java.util.Map.Entry;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.SolenoidWrapper;

public class SolenoidJNI extends JNIWrapper
{
    public static int initializeSolenoidPort(int halPortHandle)
    {
        SolenoidWrapper wrapper = new SolenoidWrapper(halPortHandle);
        SensorActuatorRegistry.get().register(wrapper, halPortHandle);

        return halPortHandle;
    }

    public static boolean checkSolenoidModule(int module)
    {
        return true;
    }

    public static boolean checkSolenoidChannel(int channel)
    {
        return true;
    }

    public static void freeSolenoidPort(int portHandle)
    {

    }

    public static void setSolenoid(int portHandle, boolean on)
    {
        getWrapperFromBuffer(portHandle).set(on);
    }

    public static boolean getSolenoid(int portHandle)
    {
        return getWrapperFromBuffer(portHandle).get();
    }

    public static byte getAllSolenoids(byte module)
    {
        byte output = 0;

        for (Entry<Integer, SolenoidWrapper> pair : SensorActuatorRegistry.get().getSolenoids().entrySet())
        {
            if (pair.getValue().get())
            {
                output |= 1 << pair.getKey();
            }
            // output |= pair.
        }

        return output;
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

    // ******************************************
    // Our additions
    // ******************************************
    private static SolenoidWrapper getWrapperFromBuffer(long digital_port_pointer)
    {
        int port = (int) digital_port_pointer;
        return SensorActuatorRegistry.get().getSolenoids().get(port);
    }
}
