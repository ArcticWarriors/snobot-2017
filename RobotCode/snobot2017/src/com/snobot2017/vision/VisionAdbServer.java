package com.snobot2017.vision;

import java.nio.ByteBuffer;
import java.util.logging.Level;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.snobot.lib.adb.IAdbBridge;
import com.snobot.lib.adb.NativeAdbBridge;
import com.snobot.lib.adb.RioDroidAdbBridge;
import com.snobot.lib.external_connection.RobotConnectionServer;
import com.snobot2017.PortMappings2017;
import com.snobot2017.Properties2017;
import com.snobot2017.vision.messages.HeartbeatMessage;
import com.snobot2017.vision.messages.IterateDisplayImageMessage;
import com.snobot2017.vision.messages.RecordingMessage;
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

    private IAdbBridge mAdb;
    private TargetUpdateMessage mLatestTargetUpdate;
    private boolean mFreshImage;

    public VisionAdbServer(int aAppBindPort, int aAppMjpegBindPort, int aAppForwardedMjpegBindPort)
    {
        super(aAppBindPort, sTIMEOUT_PERIOD);

        // TODO update
        if (true)
        {
            mAdb = new NativeAdbBridge(Properties2017.sADB_LOCATION.getValue(), sAPP_PACKAGE, sAPP_MAIN_ACTIVITY, Properties2017.sKILL_OLD_ADBS);
        }
        else
        {
            mAdb = new RioDroidAdbBridge(sAPP_PACKAGE, sAPP_MAIN_ACTIVITY);
        }

        mAdb.reversePortForward(aAppBindPort, aAppBindPort);
        mAdb.portForward(aAppMjpegBindPort, aAppForwardedMjpegBindPort);

        mFreshImage = false;
    }

    @Override
    public void handleMessage(String aMessage, double aTimestamp)
    {
        Level logLevel = Level.FINER;

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
                logLevel = Level.FINE;
                mLatestTargetUpdate = new TargetUpdateMessage(jsonObject, getTimestamp());
                mFreshImage = true;
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
//        return HAL.getMatchTime();
//    	return Hal.get
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
        mAdb.reversePortForward(PortMappings2017.sADB_BIND_PORT, PortMappings2017.sADB_BIND_PORT);
        mAdb.portForward(PortMappings2017.sAPP_MJPEG_PORT, PortMappings2017.sAPP_MJPEG_PORT);
    }

    public void restartAdb()
    {
        mAdb.restartAdb();
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

    public void sendStartRecordingMessage(String aName)
    {
        send(new RecordingMessage(true, aName).getJson());
    }

    public void sendStopRecordingMessage()
    {
        send(new RecordingMessage(false, "").getJson());
    }

    public void iterateShownImage()
    {
        send(new IterateDisplayImageMessage().getJson());
    }

    public TargetUpdateMessage getLatestTargetUpdate()
    {
        return mLatestTargetUpdate;
    }

    public boolean hasFreshImage()
    {
        boolean output = mFreshImage;
        mFreshImage = false;
        return output;
    }

}
