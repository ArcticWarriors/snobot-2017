package com.snobot2017.vision;

import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

import com.snobot.lib.adb.AdbBridge;
import com.snobot.lib.external_connection.RobotConnectionServer;

public class VisionAdbServer extends RobotConnectionServer
{
    private static final String sRESTART_APP_COMMAND = 
            "shell am force-stop snobit.com.cameratester \\; " + 
            "am start snobit.com.cameratester/snobit.com.cameratester.SelfieCameraActivity";

    private static final Path sADB_PATH = Paths.get("C:/Users/PJ/AppData/Local/Android/sdk/platform-tools/adb.exe");

    public VisionAdbServer(int aAppBindPort, int aMjpegBindPort)
    {
        super(aAppBindPort);

        AdbBridge adb = new AdbBridge(sRESTART_APP_COMMAND, sADB_PATH.toString());
        adb.start();
        adb.reversePortForward(aAppBindPort, aAppBindPort);
        adb.portForward(aMjpegBindPort, aMjpegBindPort);
        adb.reversePortForward(aMjpegBindPort, aMjpegBindPort);
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

        // sLOGGER.log(logLevel, message);
    }

    public double getTimestamp()
    {
        return System.currentTimeMillis();
    }

}
