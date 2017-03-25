package com.snobot.lib.adb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class NativeAdbBridge extends BaseAdbBridge
{
    protected final Path mAdbLocation;
    protected final boolean mValidAdb;

    public NativeAdbBridge(String aAdbLocation, String aAppPackage, String aAppMainActivity, boolean aKillOldAdbs)
    {
        super(aAppPackage, aAppMainActivity);

        mAdbLocation = Paths.get(aAdbLocation);
        mValidAdb = Files.exists(mAdbLocation);
        
        if (aKillOldAdbs)
        {
            killOldAdbs();
        }

        if (!mValidAdb)
        {
            sLOGGER.severe("ADB could not be found at '" + aAdbLocation + "'");
        }
    }

    private void killOldAdbs()
    {
        if (System.getProperty("os.name").startsWith("Windows"))
        {
            try
            {
                Process p = Runtime.getRuntime().exec("tasklist");
                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null)
                {

                    if (line.contains("adb"))
                    {
                        sLOGGER.warning("Found running ADB, killing it");
                        Runtime.getRuntime().exec("taskkill /F /IM adb.exe");
                        // killProcess.wait(1000);
                    }
                }
                sLOGGER.info("Killed old ADB's");
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void start()
    {
        sLOGGER.log(Level.INFO, "Starting ADB");
        runCommand("start-server");
    }

    @Override
    public void stop()
    {
        sLOGGER.log(Level.INFO, "Stoping ADB");
        runCommand("kill-server");
    }

    @Override
    protected boolean runCommand(String args)
    {
        if (!mValidAdb)
        {
            sLOGGER.log(Level.SEVERE, "ADB Location is not valid, cannot run commands!");
            return false;
        }

        Runtime r = Runtime.getRuntime();
        String cmd = mAdbLocation.toString() + " " + args;

        boolean success = false;

        try
        {
            sLOGGER.log(Level.INFO, "Running ADB Command: " + cmd);

            Process p = r.exec(cmd);
            success = p.waitFor(10, TimeUnit.SECONDS);
        }
        catch (IOException | InterruptedException e)
        {
            sLOGGER.log(Level.WARNING, "Could not run command: " + cmd, e);
        }

        return success;
    }

}
