package snobot.com.visionapp;

import android.util.Log;

import java.nio.ByteBuffer;

import snobot.com.visionapp.utils.RobotConnection;

/**
 * Created by PJ on 11/24/2016.
 */

public class VisionRobotConnection extends RobotConnection {

    private static final String sTAG = "RobotConnection";

    private static final String sHEARTBEAT_MESSAGE = "heartbeat";
    private static final String sTAKE_PICTURE_MESSAGE = "takepicture";

    private final CameraActivity mCameraActivity;


    public VisionRobotConnection(CameraActivity aCameraActivity) {
        super();
        mCameraActivity = aCameraActivity;
    }

    public VisionRobotConnection(CameraActivity aCameraActivity, String host, int port) {
        super(host, port);
        mCameraActivity = aCameraActivity;
    }

    public void handleMessage(String message) {
        if(sHEARTBEAT_MESSAGE.equals(message))
        {
            mLastHeartbeatReceiveTime = System.currentTimeMillis();
        }
        else if(sTAKE_PICTURE_MESSAGE.equals(message))
        {
            mCameraActivity.takePicture();
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

    public void sendPictureTakenMessage()
    {
        String message = "pictureTaken\n";
        send(ByteBuffer.wrap(message.getBytes()));
    }
}
