/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2016. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.hal;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.DigitalSourceWrapper;

@SuppressWarnings("AbbreviationAsWordInName")
public class DIOJNI extends JNIWrapper
{

    public static int initializeDIOPort(int halPortHandle, boolean input)
    {
        DigitalSourceWrapper wrapper = new DigitalSourceWrapper(halPortHandle);
        SensorActuatorRegistry.get().register(wrapper, halPortHandle);

        return halPortHandle;
    }

    public static boolean checkDIOChannel(int channel)
    {
        return !SensorActuatorRegistry.get().getDigitalSources().containsKey(channel);
    }

    public static void freeDIOPort(int dioPortHandle)
    {

    }

    public static void setDIO(int dioPortHandle, short value)
    {
        getWrapperFromBuffer(dioPortHandle).set(value == 1);
    }

    public static boolean getDIO(int dioPortHandle)
    {
        return getWrapperFromBuffer(dioPortHandle).get();
    }

    public static boolean getDIODirection(int dioPortHandle)
    {
        return false;
    }

    public static void pulse(int dioPortHandle, double pulseLength)
    {

    }

    public static boolean isPulsing(int dioPortHandle)
    {
        return false;
    }

    public static boolean isAnyPulsing()
    {
        return false;
    }

    public static short getLoopTiming()
    {
        return 0;
    }

    public static int allocateDigitalPWM()
    {
        return 0;
    }

    public static void freeDigitalPWM(int pwmGenerator)
    {

    }

    public static void setDigitalPWMRate(double rate)
    {

    }

    public static void setDigitalPWMDutyCycle(int pwmGenerator, double dutyCycle)
    {

    }

    public static void setDigitalPWMOutputChannel(int pwmGenerator, int channel)
    {

    }

    /////////////////////////////////////
    // Our stuff
    /////////////////////////////////////
    private static DigitalSourceWrapper getWrapperFromBuffer(long digital_port_pointer)
    {
        int port = (int) digital_port_pointer;
        return SensorActuatorRegistry.get().getDigitalSources().get(port);
    }
}
