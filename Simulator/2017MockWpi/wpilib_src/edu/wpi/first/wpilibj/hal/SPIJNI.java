/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2016-2017. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.hal;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.AnalogSpiWrapper;
import com.snobot.simulator.module_wrapper.AnalogWrapper;

@SuppressWarnings("AbbreviationAsWordInName")
public class SPIJNI extends JNIWrapper
{
    public static void spiInitialize(byte port)
    {
        int conv_port = port + 100;
        SensorActuatorRegistry.get().register(new AnalogSpiWrapper(conv_port), conv_port);
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
        int numToPut = 0x5200;
        numToPut = numToPut << 5;
        numToPut |= 0xe0000000;

        dataReceived.putInt(numToPut);
        dataReceived.position(0);
        return 0xe;
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
        AnalogWrapper wrapper = getWrapper(port);
        wrapper.setAccumulator(0);
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
        AnalogWrapper wrapper = getWrapper(port);

        double accum = wrapper.getAccumulator();

        accum = accum / 0.0125;
        accum = accum / 0.001;

        return (long) accum;
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

    //////////////////////////////////////////////////
    // Our stuff
    //////////////////////////////////////////////////
    private static AnalogWrapper getWrapper(byte port)
    {
        int conv_port = port + 100;
        AnalogWrapper wrapper = SensorActuatorRegistry.get().getAnalog().get(conv_port);

        return wrapper;
    }
}
