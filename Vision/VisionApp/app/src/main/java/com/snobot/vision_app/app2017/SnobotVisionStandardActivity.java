package com.snobot.vision_app.app2017;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureRequest;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.snobot.vision_app.app2017.java_algorithm.JavaVisionAlgorithm;
import com.snobot.vision_app.standard_renderer.CameraRenderer;
import com.snobot.vision_app.utils.MjpgServer;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class SnobotVisionStandardActivity extends Activity implements VisionRobotConnection.IVisionActivity, CameraRenderer.ImageHandler {

    private static final String TAG = "CameraActivity";
    private static final int REQUEST_CAMERA_PERMISSION = 200;

    private static VisionRobotConnection sRobotConnection;

    private TextureView textureView;

    private CameraRenderer cameraRenderer;
    private JavaVisionAlgorithm visionAlgorithm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(sRobotConnection == null)
        {
            sRobotConnection = new VisionRobotConnection(this);
            sRobotConnection.start();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snobot_vision_standard);
        textureView = (TextureView) findViewById(R.id.texture);
        assert textureView != null;
        textureView.setSurfaceTextureListener(textureListener);

        Button takePictureButton = (Button) findViewById(R.id.btn_takepicture);
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

        Map<CaptureRequest.Key, Object> captureRequests = new HashMap<>();
        captureRequests.put(CaptureRequest.CONTROL_MODE, CaptureRequest.CONTROL_MODE_OFF);

        captureRequests.put(CaptureRequest.CONTROL_MODE, CaptureRequest.CONTROL_MODE_OFF);
        captureRequests.put(CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE, CaptureRequest.CONTROL_VIDEO_STABILIZATION_MODE_OFF);
        captureRequests.put(CaptureRequest.LENS_OPTICAL_STABILIZATION_MODE, CaptureRequest.LENS_OPTICAL_STABILIZATION_MODE_OFF);
        captureRequests.put(CaptureRequest.SENSOR_EXPOSURE_TIME, 1000000L);
        captureRequests.put(CaptureRequest.LENS_FOCUS_DISTANCE, .2f);

        if (!OpenCVLoader.initDebug()) {
            Log.e(this.getClass().getSimpleName(), "  OpenCVLoader.initDebug(), not working.");
        } else {
            Log.d(this.getClass().getSimpleName(), "  OpenCVLoader.initDebug(), working.");
        }

        cameraRenderer = new CameraRenderer(this, captureRequests, textureView);
        visionAlgorithm = new JavaVisionAlgorithm();

    }

    TextureView.SurfaceTextureListener textureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            openCamera();
        }
        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        }
        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }
        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }
    };

    protected void takePicture() {

        cameraRenderer.runSingleCapture();
    }
    private void openCamera() {
        MjpgServer.getInstance();

        // Add permission for camera and let user grant the permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SnobotVisionStandardActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA_PERMISSION);
            return;
        }

        this.cameraRenderer.openCamera(CameraCharacteristics.LENS_FACING_FRONT);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                // close the app
                Toast.makeText(SnobotVisionStandardActivity.this, "Sorry!!!, you can't use this app without granting permission", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
        if (textureView.isAvailable()) {
            openCamera();
        } else {
            textureView.setSurfaceTextureListener(textureListener);
        }
    }
    @Override
    protected void onPause() {
        Log.e(TAG, "onPause");

        cameraRenderer.closeCamera();
        super.onPause();
    }

    @Override
    public void useCamera(int aCameraId) {
        Toast.makeText(SnobotVisionStandardActivity.this, "Switching camera is not supported!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void iterateDisplayType() {
        visionAlgorithm.iterateDisplayType();
    }

    @Override
    public void handleImage(Bitmap aBitmap) {

        if(visionAlgorithm != null) {
            Mat userImage = visionAlgorithm.processImage(aBitmap);


            Bitmap bitmap = Bitmap.createBitmap(userImage.cols(), userImage.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(userImage, bitmap);

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);

            MjpgServer.getInstance().update(os.toByteArray());
        }
        Log.i(TAG, "Captured image");
    }
}
