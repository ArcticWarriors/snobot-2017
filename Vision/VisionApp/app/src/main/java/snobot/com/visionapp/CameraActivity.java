package snobot.com.visionapp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CaptureRequest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import snobot.com.visionapp.camera.CameraRenderer;

import com.snobot.vision_app.app2017.R;
import com.snobot.vision_app.utils.MjpgServer;


public class CameraActivity extends Activity implements IVisionActivity {
    private static final String TAG = "CameraActivity";
    private static final int REQUEST_CAMERA_PERMISSION = 200;

    private static VisionRobotConnection sRobotConnection;

    private TextureView textureView;

    private CameraRenderer cameraRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(sRobotConnection == null)
        {
            sRobotConnection = new VisionRobotConnection(this);
            sRobotConnection.start();
        }

        setContentView(R.layout.activity_camera);
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

        cameraRenderer = new CameraRenderer(captureRequests, textureView);
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

    public void takePicture() {

//        ImageReader.OnImageAvailableListener imageListener = new ImageReader.OnImageAvailableListener() {
//            @Override
//            public void onImageAvailable(ImageReader reader) {
//                Image image = null;
//                try {
//                    image = reader.acquireLatestImage();
//                    ByteBuffer buffer = image.getPlanes()[0].getBuffer();
//                    byte[] bytes = new byte[buffer.capacity()];
//                    buffer.get(bytes);
//                    onImageBytes(bytes);
//                } finally {
//                    if (image != null) {
//                        image.close();
//                    }
//                }
//            }
//        };
//
//        cameraRenderer.runSingleCapture(imageListener);
    }
    private void openCamera() {
        MjpgServer.getInstance();

        // Add permission for camera and let user grant the permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CameraActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA_PERMISSION);
            return;
        }

        this.cameraRenderer.openCamera();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                // close the app
                Toast.makeText(CameraActivity.this, "Sorry!!!, you can't use this app without granting permission", Toast.LENGTH_LONG).show();
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
        super.onPause();
        Log.e(TAG, "onPause");

        cameraRenderer.closeCamera();
    }


    protected void onImageBytes(byte[] bytes)
    {
        MjpgServer.getInstance().update(bytes);
        if(sRobotConnection != null)
        {
            sRobotConnection.sendPictureTakenMessage();
        }
        Log.i(TAG, "Captured image");
    }
}