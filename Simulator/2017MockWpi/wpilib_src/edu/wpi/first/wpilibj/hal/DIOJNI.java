/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2016. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.hal;

@SuppressWarnings("AbbreviationAsWordInName")
public class DIOJNI extends JNIWrapper
{
    public static int initializeDIOPort(int halPortHandle, boolean input)
    {
        return 0;
    }

    public static boolean checkDIOChannel(int channel)
    {
        return false;
    }

    public static void freeDIOPort(int dioPortHandle)
    {

    }

    public static void setDIO(int dioPortHandle, short value)
    {

    }

    public static boolean getDIO(int dioPortHandle)
    {
        return false;
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
}
