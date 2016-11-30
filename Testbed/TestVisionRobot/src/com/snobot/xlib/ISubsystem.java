package com.snobot.xlib;

/**
 * Main interface for the Robot subsystems.
 * 
 * @author Ayush/Ammar
 *
 */
public interface ISubsystem
{

    /**
     * Perform initialization.
     */
    void init();

    /**
     * Gathering and storing current sensor information. Ex. Motor Speed.
     */
    void update();

    /**
     * Setting sensor and device states.
     */
    void control();

    /**
     * Rereads and applies current preferences.
     */
    void rereadPreferences();

    /**
     * Updates information that is sent to SmartDashboard Takes Enum argument
     */
    void updateSmartDashboard();

    /**
     * Updates the logger.
     */
    void updateLog();

    /**
     * Stops all sensors and motors
     */
    void stop();
}
