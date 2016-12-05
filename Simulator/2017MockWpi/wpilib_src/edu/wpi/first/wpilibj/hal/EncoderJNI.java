/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2016. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.hal;

import java.nio.IntBuffer;

public class EncoderJNI extends JNIWrapper
{

    public static int initializeEncoder(int digitalSourceHandleA, int analogTriggerTypeA, 
                                        int digitalSourceHandleB, int analogTriggerTypeB,
                                        boolean reverseDirection, int encodingType)
    {
        return 0;
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
        return 0;
    }

    public static int getEncodingScaleFactor(int encoderHandle)
    {
        return 0;
    }

    public static void resetEncoder(int encoderHandle)
    {

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
        return 0;
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
}
