package com.snobot.vision_app.app2017;

import android.util.Log;

import java.nio.ByteBuffer;
import java.util.List;

import com.snobot.vision_app.app2017.messages.HeartbeatMessage;
import com.snobot.vision_app.app2017.messages.TargetUpdateMessage;
import com.snobot.vision_app.utils.RobotConnection;

import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.Mat;

/**
 * Created by PJ on 11/24/2016.
 */

public class VisionRobotConnection extends RobotConnection {

    private static final String sTAG = "RobotConnection";

    // Incoming Messages
    private static final String sHEARTBEAT_MESSAGE_TYPE = "heartbeat";
    private static final String sITERATE_SHOWN_IMAGE_MESSAGE_TYPE = "iterate_display_image";
    private static final String sCAMERA_DETECTION_MESSAGE_TYPE = "camera_direction";

    private final IVisionActivity mCameraActivity;

    public interface IVisionActivity
    {
        void useCamera(int aCameraId);

        void iterateDisplayType();
    }


    public VisionRobotConnection(IVisionActivity aCameraActivity) {
        super();
        mCameraActivity = aCameraActivity;
    }

    public VisionRobotConnection(IVisionActivity aCameraActivity, String host, int port) {
        super(host, port);
        mCameraActivity = aCameraActivity;
    }

    @Override
    public void handleMessage(String message) {

        try
        {
            JSONObject jsonObject = new JSONObject(message);
            String type = (String) jsonObject.get("type");

            if(sHEARTBEAT_MESSAGE_TYPE.equals(type))
            {
                mLastHeartbeatReceiveTime = System.currentTimeMillis();
            }
            else if(sCAMERA_DETECTION_MESSAGE_TYPE.equals(type))
            {
                Log.i(sTAG, message);
                String direction = (String) jsonObject.get("direction");
                if("Front".equals(direction))
                {
                    mCameraActivity.useCamera(CameraBridgeViewBase.CAMERA_ID_FRONT);
                }
                else
                {
                    mCameraActivity.useCamera(CameraBridgeViewBase.CAMERA_ID_BACK);
                }
            }
            else if (sITERATE_SHOWN_IMAGE_MESSAGE_TYPE.equals(type))
            {
                Log.i(sTAG, message);
                mCameraActivity.iterateDisplayType();
            }
            else
            {
                Log.e(sTAG, "Parsing unknown messages: " + message);
            }
        }
        catch(Exception e)
        {
            Log.e(sTAG, "Couldn't parse message" + message, e);
        }
    }


    @Override
    protected void onRobotConnected() {
        Log.i(sTAG, "Connected");
    }

    @Override
    protected void onRobotDisconnected() {
        Log.i(sTAG, "Disconnected");
    }

    @Override
    public void sendHeartbeatMessage()
    {
        try
        {
            send(new HeartbeatMessage().getJson());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    protected void send(JSONObject aJson)
    {
        String message = aJson.toString() + "\n";
        send(ByteBuffer.wrap(message.getBytes()));
    }

    public void sendVisionUpdate(List<TargetUpdateMessage.TargetInfo> aTargets, double aLatencySec)
    {
        try
        {
            send(new TargetUpdateMessage(aTargets, aLatencySec).getJson());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
