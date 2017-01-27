/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2016-2017. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.hal;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.SensorActuatorRegistry.EncoderPair;
import com.snobot.simulator.module_wrapper.EncoderWrapper;

public class EncoderJNI extends JNIWrapper
{

    public static int initializeEncoder(int digitalSourceHandleA, int analogTriggerTypeA, int digitalSourceHandleB, int analogTriggerTypeB,
            boolean reverseDirection, int encodingType)
    {

        int output = digitalSourceHandleA << 16;
        output |= digitalSourceHandleB;

        EncoderWrapper wrapper = new EncoderWrapper(digitalSourceHandleA, digitalSourceHandleB);
        EncoderPair ports = new EncoderPair(digitalSourceHandleA, digitalSourceHandleB);
        SensorActuatorRegistry.get().register(wrapper, ports);

        SensorActuatorRegistry.get().getDigitalSources().get(digitalSourceHandleA).setIsEncoder(true);
        SensorActuatorRegistry.get().getDigitalSources().get(digitalSourceHandleB).setIsEncoder(true);

        return output;
    }

    public static void freeEncoder(int encoderHandle)
    {

    }

    public static int getEncoder(int encoderHandle)
    {
        return 0;
    }

    public static int getEncoderRaw(int encoderHandle)
    {
        return getWrapperFromBuffer(encoderHandle).getRaw();
    }

    public static int getEncodingScaleFactor(int encoderHandle)
    {
        return 0;
    }

    public static void resetEncoder(int encoderHandle)
    {
        getWrapperFromBuffer(encoderHandle).reset();
    }

    public static double getEncoderPeriod(int encoderHandle)
    {
        return 0;
    }

    public static void setEncoderMaxPeriod(int encoderHandle, double maxPeriod)
    {

    }

    public static boolean getEncoderStopped(int encoderHandle)
    {
        return false;
    }

    public static boolean getEncoderDirection(int encoderHandle)
    {
        return false;
    }

    public static double getEncoderDistance(int encoderHandle)
    {
        return getWrapperFromBuffer(encoderHandle).getDistance();
    }

    public static double getEncoderRate(int encoderHandle)
    {
        return 0;
    }

    public static void setEncoderMinRate(int encoderHandle, double minRate)
    {

    }

    public static void setEncoderDistancePerPulse(int encoderHandle, double distancePerPulse)
    {

    }

    public static void setEncoderReverseDirection(int encoderHandle, boolean reverseDirection)
    {

    }

    public static void setEncoderSamplesToAverage(int encoderHandle, int samplesToAverage)
    {

    }

    public static int getEncoderSamplesToAverage(int encoderHandle)
    {
        return 0;
    }

    public static void setEncoderIndexSource(int encoderHandle, int digitalSourceHandle, int analogTriggerType, int indexingType)
    {

    }

    @SuppressWarnings("AbbreviationAsWordInName")
    public static int getEncoderFPGAIndex(int encoderHandle)
    {
        return 0;
    }

    public static int getEncoderEncodingScale(int encoderHandle)
    {
        return 0;
    }

    public static double getEncoderDecodingScaleFactor(int encoderHandle)
    {
        return 0;
    }

    public static double getEncoderDistancePerPulse(int encoderHandle)
    {
        return 0;
    }

    public static int getEncoderEncodingType(int encoderHandle)
    {
        return 0;
    }

    // *************************************************
    // Our custom functions
    // *************************************************
    private static EncoderWrapper getWrapperFromBuffer(long digital_port_pointer)
    {
        int portA = (int) (digital_port_pointer >> 16);
        int portB = (int) (digital_port_pointer & 0xFFFF);

        EncoderWrapper wrapper = SensorActuatorRegistry.get().getEncoder(portA, portB);

        return wrapper;
    }

    public static double __getDistance(long aPort)
    {
        return getWrapperFromBuffer(aPort).getDistance();
    }
}
