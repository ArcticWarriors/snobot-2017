package com.snobot2017.vision;

import java.nio.ByteBuffer;
import java.util.logging.Level;

import com.snobot.xlib.adb.AdbBridge;
import com.snobot.xlib.external_connection.RobotConnectionServer;
import com.snobot2017.Properties2017;

public class VisionAdbServer extends RobotConnectionServer
{
    private static final String sRESTART_APP_COMMAND = 
            "shell am force-stop snobit.com.cameratester \\; " + 
            "am start snobit.com.cameratester/snobit.com.cameratester.SelfieCameraActivity";

    public VisionAdbServer(int bindPort)
    {
        super(bindPort);

        AdbBridge adb = new AdbBridge(sRESTART_APP_COMMAND, Properties2017.sADB_LOCATION.getValue());
        adb.start();
        adb.reversePortForward(bindPort, bindPort);
        adb.portForward(1180, 5800);
    }

    @Override
    public void handleMessage(String message, double timestamp)
    {
        Level logLevel = Level.INFO;
        if ("heartbeat".equals(message))
        {
            String outMessage = "heartbeat\n";
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
        return System.currentTimeMillis();
    }

    public void takePicture()
    {
        ByteBuffer message = ByteBuffer.wrap("takepicture\n".getBytes());
        send(message);
    }

}
