/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2016-2017. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.hal;

public class DigitalGlitchFilterJNI extends JNIWrapper
{
    public static void setFilterSelect(int digitalPortHandle, int filterIndex)
    {

    }

    public static int getFilterSelect(int digitalPortHandle)
    {
        return 0;
    }

    public static void setFilterPeriod(int filterIndex, int fpgaCycles)
    {

    }

    public static int getFilterPeriod(int filterIndex)
    {
        return 0;
    }
}
