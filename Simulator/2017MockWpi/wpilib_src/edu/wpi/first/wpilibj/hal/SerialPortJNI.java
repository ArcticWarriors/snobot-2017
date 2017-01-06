/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2016-2017. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.hal;

import java.nio.ByteBuffer;

public class SerialPortJNI extends JNIWrapper
{
    public static void serialInitializePort(byte port)
    {

    }

    public static void serialSetBaudRate(byte port, int baud)
    {

    }

    public static void serialSetDataBits(byte port, byte bits)
    {

    }

    public static void serialSetParity(byte port, byte parity)
    {

    }

    public static void serialSetStopBits(byte port, byte stopBits)
    {

    }

    public static void serialSetWriteMode(byte port, byte mode)
    {

    }

    public static void serialSetFlowControl(byte port, byte flow)
    {

    }

    public static void serialSetTimeout(byte port, double timeout)
    {

    }

    public static void serialEnableTermination(byte port, char terminator)
    {

    }

    public static void serialDisableTermination(byte port)
    {

    }

    public static void serialSetReadBufferSize(byte port, int size)
    {

    }

    public static void serialSetWriteBufferSize(byte port, int size)
    {

    }

    public static int serialGetBytesRecieved(byte port)
    {
        return 0;
    }

    public static int serialRead(byte port, ByteBuffer buffer, int count)
    {
        return 0;
    }

    public static int serialWrite(byte port, ByteBuffer buffer, int count)
    {
        return 0;
    }

    public static void serialFlush(byte port)
    {

    }

    public static void serialClear(byte port)
    {

    }

    public static void serialClose(byte port)
    {

    }
}
