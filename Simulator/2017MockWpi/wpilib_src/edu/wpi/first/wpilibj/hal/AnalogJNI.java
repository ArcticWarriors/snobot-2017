/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2016-2017. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.hal;

import java.nio.IntBuffer;
import java.nio.LongBuffer;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.AnalogWrapper;

public class AnalogJNI extends JNIWrapper
{
    /**
     * <i>native declaration :
     * AthenaJava\target\native\include\HAL\Analog.h:58</i><br>
     * enum values
     */
    public interface AnalogTriggerType
    {
        /**
         * <i>native declaration :
         * AthenaJava\target\native\include\HAL\Analog.h:54</i>
         */
        int kInWindow = 0;
        /**
         * <i>native declaration :
         * AthenaJava\target\native\include\HAL\Analog.h:55</i>
         */
        int kState = 1;
        /**
         * <i>native declaration :
         * AthenaJava\target\native\include\HAL\Analog.h:56</i>
         */
        int kRisingPulse = 2;
        /**
         * <i>native declaration :
         * AthenaJava\target\native\include\HAL\Analog.h:57</i>
         */
        int kFallingPulse = 3;
    }

    public static int initializeAnalogInputPort(int halPortHandle)
    {
        AnalogWrapper wrapper = new AnalogWrapper(halPortHandle);
        SensorActuatorRegistry.get().register(wrapper, halPortHandle);

        return halPortHandle;
    }

    public static void freeAnalogInputPort(int portHandle)
    {

    }

    public static int initializeAnalogOutputPort(int halPortHandle)
    {
        AnalogWrapper wrapper = new AnalogWrapper(halPortHandle);
        SensorActuatorRegistry.get().register(wrapper, halPortHandle);

        return halPortHandle;
    }

    public static void freeAnalogOutputPort(int portHandle)
    {

    }

    public static boolean checkAnalogModule(byte module)
    {
        return false;
    }

    public static boolean checkAnalogInputChannel(int channel)
    {
        return !SensorActuatorRegistry.get().getAnalog().containsKey(channel);
    }

    public static boolean checkAnalogOutputChannel(int channel)
    {
        return false;
    }

    public static void setAnalogOutput(int portHandle, double voltage)
    {

    }

    public static double getAnalogOutput(int portHandle)
    {
        return 0;
    }

    public static void setAnalogSampleRate(double samplesPerSecond)
    {
        sAnalogSampleRate = samplesPerSecond;
    }

    public static double getAnalogSampleRate()
    {
        return sAnalogSampleRate;
    }

    public static void setAnalogAverageBits(int analogPortHandle, int bits)
    {

    }

    public static int getAnalogAverageBits(int analogPortHandle)
    {
        return 1;
    }

    public static void setAnalogOversampleBits(int analogPortHandle, int bits)
    {

    }

    public static int getAnalogOversampleBits(int analogPortHandle)
    {
        return 0;
    }

    public static short getAnalogValue(int analogPortHandle)
    {
        return 0;
    }

    public static int getAnalogAverageValue(int analogPortHandle)
    {
        return 0;
    }

    public static int getAnalogVoltsToValue(int analogPortHandle, double voltage)
    {
        return 0;
    }

    public static double getAnalogVoltage(int analogPortHandle)
    {
        return getWrapperFromBuffer(analogPortHandle).getVoltage();
    }

    public static double getAnalogAverageVoltage(int analogPortHandle)
    {
        return 0;
    }

    public static int getAnalogLSBWeight(int analogPortHandle)
    {
        return 256;
    }

    public static int getAnalogOffset(int analogPortHandle)
    {
        return 0;
    }

    public static boolean isAccumulatorChannel(int analogPortHandle)
    {
        return false;
    }

    public static void initAccumulator(int analogPortHandle)
    {

    }

    public static void resetAccumulator(int analogPortHandle)
    {
        getWrapperFromBuffer(analogPortHandle).setAccumulator(0);
    }

    public static void setAccumulatorCenter(int analogPortHandle, int center)
    {

    }

    public static void setAccumulatorDeadband(int analogPortHandle, int deadband)
    {

    }

    public static long getAccumulatorValue(int analogPortHandle)
    {
        return 0;
    }

    public static int getAccumulatorCount(int analogPortHandle)
    {
        return 0;
    }

    public static void getAccumulatorOutput(int analogPortHandle, LongBuffer value, LongBuffer count)
    {
        double accum_value = getWrapperFromBuffer(analogPortHandle).getAccumulator();
        accum_value *= 1000000000;
        accum_value *= .007; // Volts per degree second
        accum_value *= 100;

        value.put((long) accum_value);
        count.put(1);
    }

    public static int initializeAnalogTrigger(int analogInputHandle, IntBuffer index)
    {
        return analogInputHandle;
    }

    public static void cleanAnalogTrigger(int analogTriggerHandle)
    {

    }

    public static void setAnalogTriggerLimitsRaw(int analogTriggerHandle, int lower, int upper)
    {

    }

    public static void setAnalogTriggerLimitsVoltage(int analogTriggerHandle, double lower, double upper)
    {

    }

    public static void setAnalogTriggerAveraged(int analogTriggerHandle, boolean useAveragedValue)
    {

    }

    public static void setAnalogTriggerFiltered(int analogTriggerHandle, boolean useFilteredValue)
    {

    }

    public static boolean getAnalogTriggerInWindow(int analogTriggerHandle)
    {
        return false;
    }

    public static boolean getAnalogTriggerTriggerState(int analogTriggerHandle)
    {
        return false;
    }

    public static boolean getAnalogTriggerOutput(int analogTriggerHandle, int type)
    {
        return false;
    }

    //////////////////////////////////////////////////////
    // Our stuff
    //////////////////////////////////////////////////////
    private static AnalogWrapper getWrapperFromBuffer(long buffer)
    {
        int port = (int) buffer;
        return SensorActuatorRegistry.get().getAnalog().get(port);
    }

    private static double sAnalogSampleRate;
}
