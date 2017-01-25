package com.snobot.vision_app.app2017;

import android.util.Log;

import java.nio.ByteBuffer;

import com.snobot.vision_app.utils.RobotConnection;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.core.Mat;

/**
 * Created by PJ on 11/24/2016.
 */

public class VisionRobotConnection extends RobotConnection {

    private static final String sTAG = "RobotConnection";

    private static final String sHEARTBEAT_MESSAGE = "heartbeat";
    private static final String sUSE_FRONT_CAMERA = "usefrontcamera";
    private static final String sUSE_BACK_CAMERA = "usebackcamera";

    private final IVisionActivity mCameraActivity;

    public interface IVisionActivity
    {
        void useCamera(int aCameraId);
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
        Log.i(sTAG, message);
        if(sHEARTBEAT_MESSAGE.equals(message))
        {
            mLastHeartbeatReceiveTime = System.currentTimeMillis();
        }
        else if(sUSE_FRONT_CAMERA.equals(message))
        {
            mCameraActivity.useCamera(CameraBridgeViewBase.CAMERA_ID_FRONT);
        }
        else if(sUSE_BACK_CAMERA.equals(message))
        {
            mCameraActivity.useCamera(CameraBridgeViewBase.CAMERA_ID_BACK);
        }
        else
        {
            Log.e(sTAG, "Parsing unknown messages: " + message);
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
    protected void sendHeartbeatMessage() {
        String message  = sHEARTBEAT_MESSAGE + "\n";
        send(ByteBuffer.wrap(message.getBytes()));
    }
}
