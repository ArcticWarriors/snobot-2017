package com.snobot.vision_app.app2017;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import com.snobot.vision_app.app2017.java_algorithm.JavaVisionAlgorithm;
import com.snobot.vision_app.opengl_renderer.VisionTrackerGLSurfaceView;
import com.snobot.vision_app.utils.MjpgServer;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.io.ByteArrayOutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * Created by PJ on 1/23/2017.
 */

public class SnobotVisionGLSurfaceView extends VisionTrackerGLSurfaceView {

    private JavaVisionAlgorithm mVisionAlgorithm;

    public SnobotVisionGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setVisionAlgorithm(JavaVisionAlgorithm aVisionAlgorithm)
    {
        mVisionAlgorithm = aVisionAlgorithm;
    }

    @Override
    protected void processTexture(int texIn, int texOut, int width, int height, long aSystemTimeNs) {

        int size = 1228800; // TODO magic number
        byte[] byteBuff = new byte[size];
        ByteBuffer screenData = ByteBuffer.wrap(byteBuff);
        GLES20.glReadPixels(0, 0, width, height, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, screenData);
        Mat originalMat = new Mat(height, width, CvType.CV_8UC4);
        originalMat.put(0, 0, screenData.array());

        ByteBuffer outputTextureData;
        Mat imageToDisplay;

//        if(mVisionAlgorithm != null) {
//            imageToDisplay = mVisionAlgorithm.processImage(originalMat, aSystemTimeNs);
//            byte[] byteBuff2 = new byte[size];
//            imageToDisplay.get(0, 0, byteBuff2);
//            outputTextureData = ByteBuffer.wrap(byteBuff2);
//        }
//        else
        {
            imageToDisplay = originalMat;
            outputTextureData = screenData;
        }

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texOut);
        GLES20.glTexSubImage2D(GLES20.GL_TEXTURE_2D, 0, 0, 0, width, height, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, outputTextureData);


        // Convert to JPEG and sent to server
        Bitmap bitmap = Bitmap.createBitmap(imageToDisplay.cols(), imageToDisplay.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(imageToDisplay, bitmap);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, os);

        MjpgServer.getInstance().update(os.toByteArray());
    }

    @Override
    protected TextView getFpsTextView()
    {
        return (TextView) ((Activity) getContext()).findViewById(R.id.fps_text_view);
    }
}
