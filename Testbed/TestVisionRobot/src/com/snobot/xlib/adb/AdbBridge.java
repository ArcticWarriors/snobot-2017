package com.snobot.xlib.adb;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdbBridge
{
    private Logger sLOGGER = Logger.getLogger("AdbBridge");
    public final static Path DEFAULT_LOCATION = Paths.get("C:/Users/PJ/AppData/Local/Android/sdk/platform-tools/adb.exe");

    protected Path mAdbLocation;
    protected String mRestartAppCommand;

    public AdbBridge(String aRestartAppCommand)
    {
        mRestartAppCommand = aRestartAppCommand;

        Path adb_location;
        String env_val = System.getenv("FRC_ADB_LOCATION");
        if (env_val == null || "".equals(env_val))
        {
            adb_location = DEFAULT_LOCATION;
        }
        else
        {
            adb_location = Paths.get(env_val);
        }
        mAdbLocation = adb_location;
    }

    public AdbBridge(Path location)
    {
        mAdbLocation = location;
    }

    private boolean runCommand(String args)
    {
        Runtime r = Runtime.getRuntime();
        String cmd = mAdbLocation.toString() + " " + args;
        sLOGGER.log(Level.FINE, "ADB Command: " + cmd);

        try
        {
            Process p = r.exec(cmd);
            p.waitFor();
        }
        catch (IOException e)
        {
            sLOGGER.log(Level.WARNING, "Could not run command: " + cmd, e);
            return false;
        }
        catch (InterruptedException e)
        {
            sLOGGER.log(Level.WARNING, "Could not run command: " + cmd, e);
            return false;
        }
        return true;
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
        runCommand(mRestartAppCommand);
     }

}
