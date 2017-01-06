/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2016-2017. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.hal;

@SuppressWarnings("AbbreviationAsWordInName")
public class HALUtil extends JNIWrapper
{
    public static final int NULL_PARAMETER = -1005;
    public static final int SAMPLE_RATE_TOO_HIGH = 1001;
    public static final int VOLTAGE_OUT_OF_RANGE = 1002;
    public static final int LOOP_TIMING_ERROR = 1004;
    public static final int INCOMPATIBLE_STATE = 1015;
    public static final int ANALOG_TRIGGER_PULSE_OUTPUT_ERROR = -1011;
    public static final int NO_AVAILABLE_RESOURCES = -104;
    public static final int PARAMETER_OUT_OF_RANGE = -1028;

    public static short getFPGAVersion()
    {
        return 0;
    }

    public static int getFPGARevision()
    {
        return 0;
    }

    public static long getFPGATime()
    {
        return (long) (HAL.getMatchTime() * 1e6);
    }

    public static int getHALRuntimeType()
    {
        return 0;
    }

    public static boolean getFPGAButton()
    {
        return false;
    }

    public static String getHALErrorMessage(int code)
    {
        return "";
    }

    public static int getHALErrno()
    {
        return 0;
    }

    public static String getHALstrerror(int errno)
    {
        return "";
    }

    public static String getHALstrerror()
    {
        return getHALstrerror(getHALErrno());
    }
}
