/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2016. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.hal;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;

@SuppressWarnings("AbbreviationAsWordInName")
public class SPIJNI extends JNIWrapper
{
    public static void spiInitialize(byte port)
    {

    }

    public static int spiTransaction(byte port, ByteBuffer dataToSend, ByteBuffer dataReceived, byte size)
    {
        return 0;
    }

    public static int spiWrite(byte port, ByteBuffer dataToSend, byte sendSize)
    {
        return 0;
    }

    public static int spiRead(byte port, ByteBuffer dataReceived, byte size)
    {
        return 0;
    }

    public static void spiClose(byte port)
    {

    }

    public static void spiSetSpeed(byte port, int speed)
    {

    }

    public static void spiSetOpts(byte port, int msbFirst, int sampleOnTrailing, int clkIdleHigh)
    {

    }

    public static void spiSetChipSelectActiveHigh(byte port)
    {

    }

    public static void spiSetChipSelectActiveLow(byte port)
    {

    }

    public static void spiInitAccumulator(byte port, int period, int cmd, byte xferSize, int validMask, int validValue, byte dataShift, byte dataSize,
            boolean isSigned, boolean bigEndian)
    {

    }

    public static void spiFreeAccumulator(byte port)
    {

    }

    public static void spiResetAccumulator(byte port)
    {

    }

    public static void spiSetAccumulatorCenter(byte port, int center)
    {

    }

    public static void spiSetAccumulatorDeadband(byte port, int deadband)
    {

    }

    public static int spiGetAccumulatorLastValue(byte port)
    {
        return 0;
    }

    public static long spiGetAccumulatorValue(byte port)
    {
        return 0;
    }

    public static int spiGetAccumulatorCount(byte port)
    {
        return 0;
    }

    public static double spiGetAccumulatorAverage(byte port)
    {
        return 0;
    }

    public static void spiGetAccumulatorOutput(byte port, LongBuffer value, LongBuffer count)
    {

    }
}
