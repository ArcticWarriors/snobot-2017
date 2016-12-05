/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2016. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.hal;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.RelayWrapper;

public class RelayJNI extends DIOJNI
{
    public static int initializeRelayPort(int halPortHandle, boolean forward)
    {
        RelayWrapper wrapper = new RelayWrapper(halPortHandle);
        SensorActuatorRegistry.get().register(wrapper, halPortHandle);

        return halPortHandle;
    }

    public static void freeRelayPort(int relayPortHandle)
    {

    }

    public static boolean checkRelayChannel(int channel)
    {
        return true;
    }

    public static void setRelay(int relayPortHandle, boolean on)
    {
        getWrapperFromBuffer(relayPortHandle).setRelayForwards(on);
    }

    public static boolean getRelay(int relayPortHandle)
    {
        return getWrapperFromBuffer(relayPortHandle).getRelayForwards();
    }

    //////////////////////////////////
    //
    //////////////////////////////////
    private static RelayWrapper getWrapperFromBuffer(long digital_port_pointer)
    {
        int port = (int) digital_port_pointer;

        return SensorActuatorRegistry.get().getRelays().get(port);
    }
}
