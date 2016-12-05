/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2016. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.hal;

import java.nio.ByteBuffer;

/**
 * JNI Wrapper for HAL<br>
 * .
 */
@SuppressWarnings(
{ "AbbreviationAsWordInName", "MethodName" })
public class HAL extends JNIWrapper
{
    public static void waitForDSData()
    {

    }

    public static int initialize(int mode)
    {
        return 0;
    }

    public static void observeUserProgramStarting()
    {

    }

    public static void observeUserProgramDisabled()
    {

    }

    public static void observeUserProgramAutonomous()
    {

    }

    public static void observeUserProgramTeleop()
    {

    }

    public static void observeUserProgramTest()
    {

    }

    public static void report(int resource, int instanceNumber)
    {
        report(resource, instanceNumber, 0, "");
    }

    public static void report(int resource, int instanceNumber, int context)
    {
        report(resource, instanceNumber, context, "");
    }

    /**
     * Report the usage of a resource of interest. <br>
     *
     * <p>
     * Original signature:
     * <code>uint32_t report(tResourceType, uint8_t, uint8_t, const
     * char*)</code>
     *
     * @param resource
     *            one of the values in the tResourceType above (max value 51).
     *            <br>
     * @param instanceNumber
     *            an index that identifies the resource instance. <br>
     * @param context
     *            an optional additional context number for some cases (such as
     *            module number). Set to 0 to omit. <br>
     * @param feature
     *            a string to be included describing features in use on a
     *            specific resource. Setting the same resource more than once
     *            allows you to change the feature string.
     */
    public static int report(int resource, int instanceNumber, int context, String feature)
    {
        return 0;
    }

    private static native int nativeGetControlWord();

    @SuppressWarnings("JavadocMethod")
    public static void getControlWord(ControlWord controlWord)
    {
        int word = nativeGetControlWord();
        controlWord.update((word & 1) != 0, ((word >> 1) & 1) != 0, ((word >> 2) & 1) != 0, ((word >> 3) & 1) != 0, ((word >> 4) & 1) != 0,
                ((word >> 5) & 1) != 0);
    }

    private static native int nativeGetAllianceStation();

    @SuppressWarnings("JavadocMethod")
    public static AllianceStationID getAllianceStation()
    {
        switch (nativeGetAllianceStation())
        {
        case 0:
            return AllianceStationID.Red1;
        case 1:
            return AllianceStationID.Red2;
        case 2:
            return AllianceStationID.Red3;
        case 3:
            return AllianceStationID.Blue1;
        case 4:
            return AllianceStationID.Blue2;
        case 5:
            return AllianceStationID.Blue3;
        default:
            return null;
        }
    }

    public static int kMaxJoystickAxes = 12;
    public static int kMaxJoystickPOVs = 12;

    public static short getJoystickAxes(byte joystickNum, float[] axesArray)
    {
        return 0;
    }

    public static short getJoystickPOVs(byte joystickNum, short[] povsArray)
    {
        return 0;
    }

    public static int getJoystickButtons(byte joystickNum, ByteBuffer count)
    {
        return 0;
    }

    public static int setJoystickOutputs(byte joystickNum, int outputs, short leftRumble, short rightRumble)
    {
        return 0;
    }

    public static int getJoystickIsXbox(byte joystickNum)
    {
        return 0;
    }

    public static int getJoystickType(byte joystickNum)
    {
        return 0;
    }

    public static String getJoystickName(byte joystickNum)
    {
        return "Joystick " + joystickNum;
    }

    public static int getJoystickAxisType(byte joystickNum, byte axis)
    {
        return 0;
    }

    public static double getMatchTime()
    {
        return 0;
    }

    public static boolean getSystemActive()
    {
        return false;
    }

    public static boolean getBrownedOut()
    {
        return false;
    }

    public static int setErrorData(String error)
    {
        return 0;
    }

    public static int sendError(boolean isError, int errorCode, boolean isLVCode, String details, String location, String callStack, boolean printMsg)
    {
        return 0;
    }
}
