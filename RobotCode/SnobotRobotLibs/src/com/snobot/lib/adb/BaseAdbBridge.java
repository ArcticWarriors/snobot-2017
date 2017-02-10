package com.snobot.lib.adb;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseAdbBridge implements IAdbBridge
{
    protected static final Logger sLOGGER = Logger.getLogger("AdbBridge");

    protected final String mAppPackage;
    protected final String mAppActivity;

    public BaseAdbBridge(String aAppPackage, String aAppMainActivity)
    {
        mAppPackage = aAppPackage;
        mAppActivity = aAppMainActivity;
    }



    @Override
    public void restartAdb()
    {
        sLOGGER.log(Level.INFO, "Restarting ADB");
        stop();
        start();
    }

    @Override
    public void restartApp()
    {
        sLOGGER.log(Level.INFO, "Restarting App");

        runCommand("shell am force-stop " + mAppPackage);
        runCommand("shell am start -n \"" + mAppPackage + "/" + mAppActivity);
    }

    @Override
    public void portForward(int local_port, int remote_port)
    {
        runCommand("forward tcp:" + local_port + " tcp:" + remote_port);
    }

    @Override
    public void reversePortForward(int remote_port, int local_port)
    {
        runCommand("reverse tcp:" + remote_port + " tcp:" + local_port);
    }

    /**
     * Runs a command through the ADB
     * 
     * @param aCommand
     *            The command to run. Does not include 'adb'
     * @return If the command was succesful
     */
    protected abstract boolean runCommand(String aCommand);

}