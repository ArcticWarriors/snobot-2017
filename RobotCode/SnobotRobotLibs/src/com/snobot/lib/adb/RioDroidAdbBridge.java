package com.snobot.lib.adb;

import java.util.logging.Level;

import org.spectrum3847.RIOdroid.RIOdroid;

public class RioDroidAdbBridge extends BaseAdbBridge
{
    public RioDroidAdbBridge(String aAppPackage, String aAppMainActivity)
    {
        super(aAppPackage, aAppMainActivity);
    }

    @Override
    protected boolean runCommand(String aCommand)
    {
        boolean success = false;

        try
        {
            RIOdroid.executeCommand(aCommand);
            success = true;
        }
        catch (Exception e)
        {
            sLOGGER.log(Level.WARNING, "Could not run command: " + aCommand, e);
        }

        return success;
    }

    @Override
    public void start()
    {
        sLOGGER.log(Level.INFO, "Starting ADB");
        RIOdroid.initUSB();
    }

    @Override
    public void stop()
    {
        sLOGGER.log(Level.INFO, "Stoping ADB");
    }

}
