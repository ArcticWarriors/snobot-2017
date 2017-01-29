package com.snobot.vision_app.app2017;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.snobot.vision_app.app2017.java_algorithm.JavaVisionAlgorithm;
import com.snobot.vision_app.opengl_renderer.VisionTrackerGLSurfaceView;
import com.snobot.vision_app.utils.MjpgServer;

import org.opencv.android.OpenCVLoader;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SnobotVisionGLActivity extends Activity implements VisionRobotConnection.IVisionActivity {
    private static final String TAG = "CameraActivity";
    private static final int REQUEST_CAMERA_PERMISSION = 200;

    private static VisionRobotConnection sRobotConnection;

    private SnobotVisionGLSurfaceView mView;
    private JavaVisionAlgorithm mAlgorithm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(sRobotConnection == null)
        {
            sRobotConnection = new VisionRobotConnection(this);
            sRobotConnection.start();
        }

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

    private void openCamera() {
        MjpgServer.getInstance();

        // Add permission for camera and let user grant the permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SnobotVisionGLActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA_PERMISSION);
            return;
        }

        mAlgorithm = new JavaVisionAlgorithm();

        mView = (SnobotVisionGLSurfaceView) findViewById(R.id.texture);
        mView.setCameraTextureListener(mView);
        mView.setVisionAlgorithm(mAlgorithm);

        mAlgorithm.setDisplayType(JavaVisionAlgorithm.DisplayType.PostThreshold);
    }

    @Override
    public void useCamera(int aCameraId) {
        mAlgorithm.setCameraDirection(aCameraId);
        mView.setCameraIndex(aCameraId);
    }

    @Override
    public void iterateDisplayType() {
        mAlgorithm.iterateDisplayType();
    }
}
