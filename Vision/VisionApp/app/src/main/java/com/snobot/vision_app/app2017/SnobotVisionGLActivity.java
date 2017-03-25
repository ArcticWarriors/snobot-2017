package com.snobot.vision_app.app2017;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.snobot.vision_app.app2017.broadcastReceivers.RobotConnectionStateListener;
import com.snobot.vision_app.app2017.broadcastReceivers.RobotConnectionStatusBroadcastReceiver;
import com.snobot.vision_app.app2017.java_algorithm.JavaVisionAlgorithm;
import com.snobot.vision_app.utils.MjpgServer;

import org.opencv.android.OpenCVLoader;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SnobotVisionGLActivity extends Activity implements VisionRobotConnection.IVisionActivity, RobotConnectionStateListener {
    private static final String TAG = "CameraActivity";
    private static final int REQUEST_CAMERA_PERMISSION = 200;

    private VisionRobotConnection mRobotConnection;

    private SnobotVisionGLSurfaceView mView;
    private JavaVisionAlgorithm mAlgorithm;

    private VisionAlgorithmPreferences mPreferences;

    private RobotConnectionStatusBroadcastReceiver mRobotConnectionBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRobotConnection = new VisionRobotConnection(this);
        mRobotConnection.start();

        mPreferences = new VisionAlgorithmPreferences(this);
        mRobotConnectionBroadcastReceiver = new RobotConnectionStatusBroadcastReceiver(this, this);

        if (!OpenCVLoader.initDebug()) {
            Log.e(this.getClass().getSimpleName(), "  OpenCVLoader.initDebug(), not working.");
        } else {
            Log.d(this.getClass().getSimpleName(), "  OpenCVLoader.initDebug(), working.");
        }

        setContentView(R.layout.activity_snobot_vision_gl);

        openCamera();
    }
    @Override
    protected void onPause() {
        Log.i(TAG, "onPause");
        if (mView != null) {
            mView.onPause();
        }

        super.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();

        Log.i("VisionActivity", "onResume " + mView);
        if (mView != null) {
            mView.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mRobotConnectionBroadcastReceiver);
    }

    private void openCamera() {
        MjpgServer.getInstance();

        // Add permission for camera and let user grant the permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SnobotVisionGLActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA_PERMISSION);
            return;
        }

        mAlgorithm = new JavaVisionAlgorithm(mRobotConnection, mPreferences);
        mAlgorithm.setDisplayType(JavaVisionAlgorithm.DisplayType.MarkedUpImage);

        mView = (SnobotVisionGLSurfaceView) findViewById(R.id.texture);
        mView.setCameraTextureListener(mView);
        mView.setVisionAlgorithm(mAlgorithm);
    }

    @Override
    public void useCamera(int aCameraId)
    {
        mAlgorithm.setCameraDirection(aCameraId);
        mView.setCameraIndex(aCameraId);
    }


    public void openBottomSheet(View v)
    {
        final View view = getLayoutInflater().inflate(R.layout.algorithm_settings, null);
        LinearLayout container = (LinearLayout) view.findViewById(R.id.popup_window);
        container.getBackground().setAlpha(20);


        final Dialog dialog = new Dialog(this);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        final EditText hueMin = (EditText) view.findViewById(R.id.hueMinValue);
        final EditText hueMax = (EditText) view.findViewById(R.id.hueMaxValue);
        final EditText satMin = (EditText) view.findViewById(R.id.satMinValue);
        final EditText satMax = (EditText) view.findViewById(R.id.satMaxValue);
        final EditText lumMin = (EditText) view.findViewById(R.id.lumMinValue);
        final EditText lumMax = (EditText) view.findViewById(R.id.lumMaxValue);

        populateRangePair(hueMin, hueMax, mPreferences.getHueThreshold());
        populateRangePair(satMin, satMax, mPreferences.getSatThreshold());
        populateRangePair(lumMin, lumMax, mPreferences.getLumThreshold());

        Button restoreButton = (Button) view.findViewById(R.id.restoreAlgorithimDefaultsButton);
        restoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPreferences.restoreDefaults();
                dialog.dismiss();
            }
        });

        Button saveButton = (Button) view.findViewById(R.id.saveAlgorithmSettingsButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPreferences.setHueThreshold(getRangePair(hueMin, hueMax));
                mPreferences.setSatThreshold(getRangePair(satMin, satMax));
                mPreferences.setLumThreshold(getRangePair(lumMin, lumMax));
                dialog.dismiss();
            }
        });
    }

    private Pair<Integer, Integer> getRangePair(EditText aMinText, EditText aMaxText)
    {
        int min = Integer.parseInt(aMinText.getText().toString());
        int max = Integer.parseInt(aMaxText.getText().toString());

        return new Pair<>(min, max);
    }

    private void populateRangePair(EditText aMinText, EditText aMaxText, Pair<Integer, Integer> aThreshold)
    {
        aMinText.setText("" + aThreshold.first);
        aMaxText.setText("" + aThreshold.second);
    }

    public void showViewOptions(View v)
    {

    }

    @Override
    public void iterateDisplayType() {
        mAlgorithm.iterateDisplayType();
    }

    @Override
    public void setRecording(final boolean aRecord) {
        mAlgorithm.setRecording(aRecord);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String toast = aRecord ? "Recording Images" : "Not Recording Images";
                Toast.makeText(SnobotVisionGLActivity.this, toast, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Attempt at using Volume Buttons to take pictures.
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                Log.i("Debug1", "Reading the volume UP button");
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                Log.i("Debug3", "Reading the volume DOWN button");
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }

    @Override
    public void robotConnected() {
        View  connectionStateView = findViewById(R.id.connectionState);
        Toast.makeText(this, "Connected to Robot", Toast.LENGTH_SHORT).show();
        connectionStateView.setBackgroundColor(ContextCompat.getColor(this, R.color.app_connected));
        mAlgorithm.setRobotConnected(true);
    }

    @Override
    public void robotDisconnected() {
        View  connectionStateView = findViewById(R.id.connectionState);
        Toast.makeText(this, "Lost connection to Robot", Toast.LENGTH_SHORT).show();
        connectionStateView.setBackgroundColor(ContextCompat.getColor(this, R.color.app_disconnected));
        mAlgorithm.setRobotConnected(false);
    }
}
