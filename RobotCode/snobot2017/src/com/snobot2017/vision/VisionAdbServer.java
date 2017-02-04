package com.snobot2017.vision;

import java.nio.ByteBuffer;
import java.util.logging.Level;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.spectrum3847.RIOdroid.RIOadb;
import org.spectrum3847.RIOdroid.RIOdroid;

import com.snobot.lib.external_connection.RobotConnectionServer;
import com.snobot2017.vision.messages.HeartbeatMessage;
import com.snobot2017.vision.messages.IterateDisplayImageMessage;
import com.snobot2017.vision.messages.SetCameraDirectionMessage;
import com.snobot2017.vision.messages.TargetUpdateMessage;

public class VisionAdbServer extends RobotConnectionServer
{
    private static final String sAPP_PACKAGE = "snobot.com.visionapp";
    private static final String sAPP_MAIN_ACTIVITY = "com.snobot.vision_app.app2017.SnobotVisionGLActivity";
//    private static final String sAPP_PACKAGE = "snobot.com.visionapp";
//    private static final String sAPP_MAIN_ACTIVITY = "com.snobot.vision_app.app2017.SnobotVisionStandardActivity";

    private static final double sTIMEOUT_PERIOD = 1.1; // Based on how often the App sends the heartbeat

    private static final String sHEARTBEAT_TYPE = "heartbeat";
    private static final String sTARGET_UPDATE_MESSAGE = "target_update";

    public enum CameraFacingDirection
    {
        Front, Rear
    }

    // private AdbBridge mAdb;
    private TargetUpdateMessage mLatestTargetUpdate;

    public VisionAdbServer(int aAppBindPort, int aAppMjpegBindPort, int aAppForwardedMjpegBindPort)
    {
        super(aAppBindPort, sTIMEOUT_PERIOD);

        try
        {
            RIOadb.init();
        }
        catch (Exception e)
        {
            System.out.println("AHHHH " + e.getMessage());
            e.printStackTrace();
        }

        try
        {
            executeCommand("adb reverse tcp:" + aAppBindPort + " tcp:" + aAppBindPort);
            // RIOdroid.executeCommand("adb forward tcp:" +
            // aAppForwardedMjpegBindPort + " tcp:" + aAppMjpegBindPort);
            executeCommand("adb reverse tcp:" + aAppMjpegBindPort + " tcp:" + aAppForwardedMjpegBindPort);
        }
        catch (Exception e)
        {
            System.out.println("AHHHH " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void executeCommand(String aCommand)
    {
        System.out.println(aCommand);
        RIOdroid.executeCommand(aCommand);
    }

    @Override
    public void handleMessage(String aMessage, double aTimestamp)
    {
        Level logLevel = Level.FINE;

        try
        {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(aMessage);

            String type = (String) jsonObject.get("type");

            if (sHEARTBEAT_TYPE.equals(type))
            {
                send(new HeartbeatMessage().getJson());
            }
            else if (sTARGET_UPDATE_MESSAGE.equals(type))
            {
                mLatestTargetUpdate = new TargetUpdateMessage(jsonObject);
            }
            else
            {
                System.err.println("Unknown message " + aMessage);
                logLevel = Level.SEVERE;
            }
        }
        catch (Exception e)
        {
            sLOGGER.severe("Error parsing message, incoming='" + aMessage + "' - " + e);
        }

        sLOGGER.log(logLevel, aMessage);
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
        // mAdb.restartApp();
    }

    public void restartAdb()
    {
        // mAdb.restartAdb();
    }

    protected void send(JSONObject aObject)
    {
        String message = aObject.toJSONString() + "\n";
        send(ByteBuffer.wrap(message.getBytes()));
    }

    public void setCameraDirection(CameraFacingDirection aDirection)
    {
        send(new SetCameraDirectionMessage(aDirection).getJson());
    }

    public void iterateShownImage()
    {
        send(new IterateDisplayImageMessage().getJson());
    }

    public TargetUpdateMessage getLatestTargetUpdate()
    {
        return mLatestTargetUpdate;
    }

}
