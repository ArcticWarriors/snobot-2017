package com.snobot2017.vision;

import java.nio.ByteBuffer;
import java.util.logging.Level;

import com.snobot.lib.adb.AdbBridge;
import com.snobot.lib.external_connection.RobotConnectionServer;
import com.snobot2017.Properties2017;

public class VisionAdbServer extends RobotConnectionServer
{
    private static final String sAPP_PACKAGE = "snobot.com.visionapp";
    private static final String sAPP_MAIN_ACTIVITY = "CameraActivity";

    private static final double sTIMEOUT_PERIOD = 1.1; // Based on how often the App sends the heartbeat

    private AdbBridge mAdb;

    public VisionAdbServer(int aAppBindPort, int aAppMjpegBindPort, int aAppForwardedMjpegBindPort)
    {
        super(aAppBindPort, sTIMEOUT_PERIOD);

        mAdb = new AdbBridge(Properties2017.sADB_LOCATION.getValue(), sAPP_PACKAGE, sAPP_MAIN_ACTIVITY);
        mAdb.start();
        mAdb.reversePortForward(aAppBindPort, aAppBindPort);
        mAdb.portForward(aAppForwardedMjpegBindPort, aAppMjpegBindPort);
    }

    @Override
    public void handleMessage(String message, double timestamp)
    {
        Level logLevel = Level.INFO;
        if ("heartbeat".equals(message))
        {
            String outMessage = "heartbeat";
            ByteBuffer buffer = ByteBuffer.wrap(outMessage.getBytes());
            send(buffer);

            logLevel = Level.FINE;
        }
        else if ("pictureTaken".equals(message))
        {
            logLevel = Level.INFO;
        }
        else
        {
            System.err.println("Unknown message " + message);
            logLevel = Level.SEVERE;
        }

        sLOGGER.log(logLevel, message);
    }

    @Override
    public double getTimestamp()
    {
        return System.currentTimeMillis() * 1e-3;
    }

    @Override
    public void onConnected()
    {
        sLOGGER.info("App connected");
    }

    @Override
    public void onDisconnected()
    {
        sLOGGER.warning("App disconnected");
    }

    public void restartApp()
    {
        mAdb.restartApp();
    }

}
