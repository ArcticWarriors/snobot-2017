/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2016. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.hal;

import java.nio.ByteBuffer;

@SuppressWarnings("AbbreviationAsWordInName")
public class I2CJNI extends JNIWrapper
{
    public static void i2CInitialize(byte port)
    {

    }

    public static int i2CTransaction(byte port, byte address, ByteBuffer dataToSend, byte sendSize, ByteBuffer dataReceived, byte receiveSize)
    {
        return 0;
    }

    public static int i2CWrite(byte port, byte address, ByteBuffer dataToSend, byte sendSize)
    {
        return 0;
    }

    public static int i2CRead(byte port, byte address, ByteBuffer dataRecieved, byte receiveSize)
    {
        return 0;
    }

    public static void i2CClose(byte port)
    {

    }
}
