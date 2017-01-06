/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2016-2017. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.hal;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.SpeedControllerWrapper;

import edu.wpi.first.wpilibj.PWMConfigDataResult;

@SuppressWarnings("AbbreviationAsWordInName")
public class PWMJNI extends DIOJNI
{

    public static int initializePWMPort(int halPortHandle)
    {
        SpeedControllerWrapper wrapper = new SpeedControllerWrapper((int) halPortHandle);
        SensorActuatorRegistry.get().register(wrapper, (int) halPortHandle, false);

        return halPortHandle;
    }

    public static boolean checkPWMChannel(int channel)
    {
        return !SensorActuatorRegistry.get().getSpeedControllers().containsKey(channel);
    }

    public static void freePWMPort(int pwmPortHandle)
    {

    }

    public static void setPWMConfigRaw(int pwmPortHandle, int maxPwm, int deadbandMaxPwm, int centerPwm, int deadbandMinPwm, int minPwm)
    {

    }

    public static void setPWMConfig(int pwmPortHandle, double maxPwm, double deadbandMaxPwm, double centerPwm, double deadbandMinPwm, double minPwm)
    {

    }

    public static PWMConfigDataResult getPWMConfigRaw(int pwmPortHandle)
    {
        return null;
    }

    public static void setPWMEliminateDeadband(int pwmPortHandle, boolean eliminateDeadband)
    {

    }

    public static boolean getPWMEliminateDeadband(int pwmPortHandle)
    {
        return false;
    }

    public static void setPWMRaw(int pwmPortHandle, short value)
    {

    }

    public static void setPWMSpeed(int pwmPortHandle, double speed)
    {
        getWrapperFromBuffer((int) pwmPortHandle).set(speed);
    }

    public static void setPWMPosition(int pwmPortHandle, double position)
    {

    }

    public static short getPWMRaw(int pwmPortHandle)
    {
        return 0;
    }

    public static double getPWMSpeed(int pwmPortHandle)
    {
        return getWrapperFromBuffer((int) pwmPortHandle).get();
    }

    public static double getPWMPosition(int pwmPortHandle)
    {
        return 0;
    }

    public static void setPWMDisabled(int pwmPortHandle)
    {

    }

    public static void latchPWMZero(int pwmPortHandle)
    {

    }

    public static void setPWMPeriodScale(int pwmPortHandle, int squelchMask)
    {

    }

    // *************************************************
    // Our custom functions
    // *************************************************
    private static SpeedControllerWrapper getWrapperFromBuffer(int digital_port_pointer)
    {
        return SensorActuatorRegistry.get().getSpeedControllers().get(digital_port_pointer);
    }
}
