package snobot.com.visionapp.camera;

import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.ImageReader;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Created by PJ on 11/21/2016.
 */

public class CameraRenderer {

    private static final String TAG = "CameraRenderer";

    private TextureView view;
    private String cameraId;
    private Size imageDimension;
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;
    private CameraDevice cameraDevice;
    private CaptureRequest.Builder mPreviewRequestBuilder;
    private CameraCaptureSession cameraCaptureSessions;
    private Map<CaptureRequest.Key, Object> captureRequests;

    /**
     * A {@link Semaphore} to prevent the app from exiting before closing the camera.
     */
    private Semaphore mCameraOpenCloseLock = new Semaphore(1);

    public CameraRenderer(Map<CaptureRequest.Key, Object> captureRequests, TextureView view)
    {
        this.captureRequests = captureRequests;
        this.view = view;
        cameraId = null;
    }

    private final CameraDevice.StateCallback stateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(@NonNull CameraDevice camera) {
            //This is called when the camera is open
            mCameraOpenCloseLock.release();
            Log.e(TAG, "onOpened");
            cameraDevice = camera;
            createCameraPreviewSession();
        }
        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {
            mCameraOpenCloseLock.release();
            cameraDevice.close();
            cameraDevice = null;
        }
        @Override
        public void onError(@NonNull CameraDevice camera, int error) {
            mCameraOpenCloseLock.release();
            cameraDevice.close();
            cameraDevice = null;
        }
    };

    private void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("Camera Background");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }

    private void stopBackgroundThread() {
        mBackgroundThread.quitSafely();
        try {
            mBackgroundThread.join();
            mBackgroundThread = null;
            mBackgroundHandler = null;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void createCameraPreviewSession() {
        try {

            int width = imageDimension.getWidth();
            int height = imageDimension.getHeight();
            ImageReader reader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1);
            ImageReader.OnImageAvailableListener imageListener = new ImageReader.OnImageAvailableListener() {

                @Override
                public void onImageAvailable(ImageReader reader) {
                    Log.i(TAG, "On image");
                }
            };
            reader.setOnImageAvailableListener(imageListener, mBackgroundHandler);


            SurfaceTexture texture = view.getSurfaceTexture();
            assert texture != null;
            texture.setDefaultBufferSize(imageDimension.getWidth(), imageDimension.getHeight());
            Surface surface = new Surface(texture);
            mPreviewRequestBuilder = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            mPreviewRequestBuilder.addTarget(surface);
            mPreviewRequestBuilder.addTarget(reader.getSurface());

            for(Map.Entry<CaptureRequest.Key, Object> pair : captureRequests.entrySet())
            {
                captureRequests.put(pair.getKey(), pair.getValue());
            }
            cameraDevice.createCaptureSession(Arrays.asList(surface, reader.getSurface()), new CameraCaptureSession.StateCallback(){
                @Override
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    //The camera is already closed
                    if (null == cameraDevice) {
                        return;
                    }
                    // When the session is ready, we start displaying the preview.
                    cameraCaptureSessions = cameraCaptureSession;


                    try {
                        mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);

//                        CameraCaptureSession.CaptureCallback xxx = new CameraCaptureSession.CaptureCallback() {
//                            @Override
//                            public void onCaptureStarted(CameraCaptureSession session, CaptureRequest request, long timestamp, long frameNumber) {
//                                super.onCaptureStarted(session, request, timestamp, frameNumber);
//                            }
//                        };
                        cameraCaptureSessions.setRepeatingRequest(mPreviewRequestBuilder.build(), null, mBackgroundHandler);
                        Log.i(TAG, "CameraPreviewSession has been started");
                    } catch (CameraAccessException e) {
                        Log.e(TAG, "createCaptureSession failed");
                    }

//                    updatePreview();
                }
                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                    Toast.makeText(view.getContext(), "Configuration change", Toast.LENGTH_SHORT).show();
                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void openCamera() {

        CameraManager manager = (CameraManager) view.getContext().getSystemService(Context.CAMERA_SERVICE);
        Log.e(TAG, "is camera open");
        try {

            if (!mCameraOpenCloseLock.tryAcquire(2500, TimeUnit.MILLISECONDS)) {
                throw new RuntimeException("Time out waiting to lock camera opening.");
            }

            cameraId = manager.getCameraIdList()[0];

            for(String cameraIdIter : manager.getCameraIdList())
            {
                CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraIdIter);
                if(characteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT)
                {
                    cameraId = cameraIdIter;
                    break;
                }
            }
            CameraCharacteristics characteristics = manager.getCameraCharacteristics(cameraId);



            StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
            assert map != null;
            imageDimension = new Size(640, 320);
            manager.openCamera(cameraId, stateCallback, null);
        } catch (CameraAccessException | SecurityException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to lock camera opening.", e);
        }


        startBackgroundThread();
        Log.e(TAG, "openCamera X");
    }

    private void updatePreview() {
        if(null == cameraDevice) {
            Log.e(TAG, "updatePreview error, return");
        }
        mPreviewRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
        try {
            cameraCaptureSessions.setRepeatingRequest(mPreviewRequestBuilder.build(), null, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void closeCamera() {
        try {
            mCameraOpenCloseLock.acquire();

            if (null != cameraDevice) {
                cameraDevice.close();
                cameraDevice = null;
            }

            if(mBackgroundThread != null) {
                stopBackgroundThread();
            }

        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to lock camera closing.", e);
        } finally {
            mCameraOpenCloseLock.release();
        }
    }


//    public void runSingleCapture(ImageReader.OnImageAvailableListener imageListener)
//    {
//        int width = imageDimension.getWidth();
//        int height = imageDimension.getHeight();
//
//        try {
//            ImageReader reader = ImageReader.newInstance(width, height, ImageFormat.JPEG, 1);
//            List<Surface> outputSurfaces = new ArrayList<>(2);
//            outputSurfaces.add(reader.getSurface());
//            outputSurfaces.add(new Surface(view.getSurfaceTexture()));
//
//            int captureType;
//
//            if(SINGLE_CAPTURE)
//            {
//                captureType = CameraDevice.TEMPLATE_STILL_CAPTURE;
//            }
//            else
//            {
//                captureType = CameraDevice.TEMPLATE_PREVIEW;
//            }
//
//            final CaptureRequest.Builder captureBuilder = cameraDevice.createCaptureRequest(captureType);
//            captureBuilder.addTarget(reader.getSurface());
////            captureBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
//            captureBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
//
//            reader.setOnImageAvailableListener(imageListener, mBackgroundHandler);
//
//            cameraDevice.createCaptureSession(outputSurfaces, new CameraCaptureSession.StateCallback() {
//                @Override
//                public void onConfigured(@NonNull CameraCaptureSession session) {
//
//                    if(SINGLE_CAPTURE) {
//                        try {
//                            session.capture(captureBuilder.build(), captureListener, mBackgroundHandler);
//                        } catch (CameraAccessException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    else
//                    {
//
//                    }
//                }
//                @Override
//                public void onConfigureFailed(@NonNull CameraCaptureSession session) {
//                }
//            }, mBackgroundHandler);
//        } catch (CameraAccessException e) {
//            e.printStackTrace();
//        }
//    }

    private final CameraCaptureSession.CaptureCallback captureListener = new CameraCaptureSession.CaptureCallback() {

        private void process(CaptureResult result) {

        }

        @Override
        public void onCaptureProgressed(@NonNull CameraCaptureSession session,
                                        @NonNull CaptureRequest request,
                                        @NonNull CaptureResult partialResult) {
            process(partialResult);
        }

        @Override
        public void onCaptureCompleted(@NonNull CameraCaptureSession session,
                                       @NonNull CaptureRequest request,
                                       @NonNull TotalCaptureResult result) {
            process(result);
        }
    };
}
