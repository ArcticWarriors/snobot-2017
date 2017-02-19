package com.snobot.lib.modules;

public interface IControllableModule
{

    /**
     * Setting sensor and device states.
     */
    void control();

    /**
     * Stops all sensors and motors
     */
    void stop();
}
