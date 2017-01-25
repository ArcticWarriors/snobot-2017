package com.snobot.lib.adb;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdbBridge
{
    private static final Logger sLOGGER = Logger.getLogger("AdbBridge");

    protected final Path mAdbLocation;
    protected final boolean mValidAdb;

    protected final String mAppPackage;
    protected final String mAppActivity;

    public AdbBridge(String aAdbLocation, String aAppPackage, String aAppMainActivity)
    {
        mAdbLocation = Paths.get(aAdbLocation);
        mValidAdb = Files.exists(mAdbLocation);

        mAppPackage = aAppPackage;
        mAppActivity = aAppMainActivity;

        if (!mValidAdb)
        {
            sLOGGER.severe("ADB could not be found at '" + aAdbLocation + "'");
        }
    }

    private boolean runCommand(String args)
    {
        if (!mValidAdb)
        {
            sLOGGER.log(Level.SEVERE, "ADB Location is not valid, cannot run commands!");
            return false;
        }

        Runtime r = Runtime.getRuntime();
        String cmd = mAdbLocation.toString() + " " + args;
        sLOGGER.log(Level.FINE, "ADB Command: " + cmd);

        boolean success = false;

        try
        {
            System.out.println(cmd);
            Process p = r.exec(cmd);
            success = p.waitFor(10, TimeUnit.SECONDS);
        }
        catch (IOException e)
        {
            sLOGGER.log(Level.WARNING, "Could not run command: " + cmd, e);
        }
        catch (InterruptedException e)
        {
            sLOGGER.log(Level.WARNING, "Could not run command: " + cmd, e);
        }

        return success;
    }

    public void start()
    {
        sLOGGER.log(Level.INFO, "Starting ADB");
        runCommand("start-server");
    }

    public void stop()
    {
        sLOGGER.log(Level.INFO, "Stoping ADB");
        runCommand("kill-server");
    }

    public void restartAdb()
    {
        sLOGGER.log(Level.INFO, "Restarting ADB");
        stop();
        start();
    }

    public void portForward(int local_port, int remote_port)
    {
        runCommand("forward tcp:" + local_port + " tcp:" + remote_port);
    }

    public void reversePortForward(int remote_port, int local_port)
    {
        runCommand("reverse tcp:" + remote_port + " tcp:" + local_port);
    }

    public void restartApp()
    {
        sLOGGER.log(Level.INFO, "Restarting App");

        runCommand("shell am force-stop " + mAppPackage);
        runCommand("shell am start -n \"" + mAppPackage + "/" + mAppActivity);
    }

}
