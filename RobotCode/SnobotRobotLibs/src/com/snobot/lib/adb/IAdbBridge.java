package com.snobot.lib.adb;

/**
 * Interface for the ADB bridge. Allows us to switch to using the native
 * application for windows, and using Spectrum's RIODroid for the robot
 * 
 * @author PJ
 *
 */
public interface IAdbBridge
{

    /**
     * Starts the ADB server
     */
    void start();

    /**
     * Stops the ADB server
     */
    void stop();

    /**
     * Restarts the ADB. Calls stop() then start()
     */
    void restartAdb();

    /**
     * Restarts the app you are connecting to
     */
    void restartApp();

    /**
     * Forwards a port on the phone to a port on the robot
     * 
     * @param aLocalPort
     *            The local port to forward data to
     * @param aRemotePort
     *            The port the robot is sending data to
     */
    void portForward(int aLocalPort, int aRemotePort);

    /**
     * 
     * Forwards a port on the phone to a port on the robot
     * 
     * @param aLocalPort
     *            The local port to forward data to
     * @param aRemotePort
     *            The port the robot is sending data to
     */
    void reversePortForward(int aLocalPort, int aRemotePort);

}