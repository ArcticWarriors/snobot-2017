/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2016-2017. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.hal;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.AnalogWrapper;

public class AnalogGyroJNI extends JNIWrapper
{
    public static int initializeAnalogGyro(int halAnalogInputHandle)
    {
        return halAnalogInputHandle;
    }

    public static void setupAnalogGyro(int handle)
    {

    }

    public static void freeAnalogGyro(int handle)
    {

    }

    public static void setAnalogGyroParameters(int handle, double voltsPerDegreePerSecond, double offset, int center)
    {

    }

    public static void setAnalogGyroVoltsPerDegreePerSecond(int handle, double voltsPerDegreePerSecond)
    {

    }

    public static void resetAnalogGyro(int handle)
    {

    }

    public static void calibrateAnalogGyro(int handle)
    {

    }

    public static void setAnalogGyroDeadband(int handle, double volts)
    {

    }

    public static double getAnalogGyroAngle(int handle)
    {
        AnalogWrapper wrapper = getWrapperFromBuffer(handle);
        return wrapper.getAccumulator();
    }

    public static double getAnalogGyroRate(int handle)
    {
        return 0;
    }

    public static double getAnalogGyroOffset(int handle)
    {
        return 0;
    }

    public static int getAnalogGyroCenter(int handle)
    {
        return 0;
    }

    // ////////////////////////////////////////////////////
    // Our stuff
    // ////////////////////////////////////////////////////
    private static AnalogWrapper getWrapperFromBuffer(long buffer)
    {
        int port = (int) buffer;
        return SensorActuatorRegistry.get().getAnalog().get(port);
    }
}
