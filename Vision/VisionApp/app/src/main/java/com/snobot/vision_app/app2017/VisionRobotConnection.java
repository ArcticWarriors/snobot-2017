package com.snobot.vision_app.app2017;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.nio.ByteBuffer;
import java.util.List;

import com.snobot.vision_app.app2017.broadcastReceivers.RobotConnectionStatusBroadcastReceiver;
import com.snobot.vision_app.app2017.messages.in.CameraDirectionMessage;
import com.snobot.vision_app.app2017.messages.in.SetRecordingMessage;
import com.snobot.vision_app.app2017.messages.out.HeartbeatMessage;
import com.snobot.vision_app.app2017.messages.out.TargetUpdateMessage;
import com.snobot.vision_app.utils.RobotConnection;

import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.android.CameraBridgeViewBase;

/**
 * Created by PJ on 11/24/2016.
 */

public class VisionRobotConnection extends RobotConnection {

    private static final String sTAG = "RobotConnection";

    // Incoming Messages
    private static final String sHEARTBEAT_MESSAGE_TYPE = "heartbeat";
    private static final String sITERATE_SHOWN_IMAGE_MESSAGE_TYPE = "iterate_display_image";
    private static final String sCAMERA_DIRECTION_MESSAGE_TYPE = "camera_direction";
    private static final String sRECORD_IMAGES_MESSAGE_TYPE = "record_images";

    private final IVisionActivity mCameraActivity;

    public interface IVisionActivity
    {
        void useCamera(int aCameraId);

        void iterateDisplayType();

        void setRecording(boolean aRecord);
    }


    public VisionRobotConnection(IVisionActivity aCameraActivity) {
        super();
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
            else if(sCAMERA_DIRECTION_MESSAGE_TYPE.equals(type))
            {
                Log.i(sTAG, message);
                CameraDirectionMessage dirMessage = new CameraDirectionMessage(jsonObject);
                mCameraActivity.useCamera(dirMessage.getCameraDirection());
            }
            else if (sRECORD_IMAGES_MESSAGE_TYPE.equals(type))
            {
                Log.i(sTAG, message);
                SetRecordingMessage recordingMessage = new SetRecordingMessage(jsonObject);
                mCameraActivity.setRecording(recordingMessage.shouldRecord());
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
        Intent i = new Intent(RobotConnectionStatusBroadcastReceiver.ACTION_ROBOT_CONNECTED);
        ((Context) mCameraActivity).sendBroadcast(i);
    }

    @Override
    protected void onRobotDisconnected() {
        Intent i = new Intent(RobotConnectionStatusBroadcastReceiver.ACTION_ROBOT_DISCONNECTED);
        ((Context) mCameraActivity).sendBroadcast(i);
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
            JSONObject message = new TargetUpdateMessage(aTargets, aLatencySec).getJson();
//            Log.i(sTAG, message.toString());
            send(message);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
