package com.snobot.vision_app.app2017.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class RobotConnectionStatusBroadcastReceiver extends BroadcastReceiver {
    public static final String ACTION_ROBOT_CONNECTED = "action_robot_connected";
    public static final String ACTION_ROBOT_DISCONNECTED = "action_robot_disconnected";

    private RobotConnectionStateListener mListener;

    public RobotConnectionStatusBroadcastReceiver(Context context, RobotConnectionStateListener listener) {
        this.mListener = listener;

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_ROBOT_CONNECTED);
        intentFilter.addAction(ACTION_ROBOT_DISCONNECTED);
        context.registerReceiver(this, intentFilter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_ROBOT_CONNECTED.equals(intent.getAction())) {
            mListener.robotConnected();
        } else if (ACTION_ROBOT_DISCONNECTED.equals(intent.getAction())) {
            mListener.robotDisconnected();
        }
    }
}