package com.snobot2016.light;

/**
 * Author Jeffrey/ Michael
 * interface for light class
 */

import com.snobot.xlib.ISubsystem;

public interface ILight extends ISubsystem
{
    /**
     * Tells you if the LIght is on
     * @return true if light is on
     */
    boolean isLightOn();
}
