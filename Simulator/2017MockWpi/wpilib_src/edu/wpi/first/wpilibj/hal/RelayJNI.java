/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2016-2017. All Rights Reserved.                        */
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

        return forward ? halPortHandle : halPortHandle + 1;
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
        boolean forward = relayPortHandle % 2 == 0;
        
        RelayWrapper wrapper = null;
        if(forward)
        {
            wrapper = getWrapperFromBuffer(relayPortHandle);
            wrapper.setRelayForwards(on);
        }
        else
        {
            wrapper = getWrapperFromBuffer(relayPortHandle - 1);
            wrapper.setRelayReverse(on);
        }
            

//        RelayWrapper wrapper = getWrapperFromBuffer(relayPortHandle);
//        if (wrapper == null)
//        {
//            wrapper = getWrapperFromBuffer(relayPortHandle + 1);
//            forward = false;
//        }
//
//        if (forward)
//        {
//            wrapper.setRelayForwards(on);
//        }
//        else
//        {
        // // wrapper.setRelayReverse(on);
        // }
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

    public static int getPort(byte channel)
    {
        return channel * 2;
    }
}
