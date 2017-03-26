package com.snobot.vision_app.app2017.java_algorithm;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Pair;

import com.snobot.vision_app.app2017.VisionAlgorithmPreferences;
import com.snobot.vision_app.app2017.VisionRobotConnection;
import com.snobot.vision_app.app2017.java_algorithm.common.BaseJavaAlgorithm;
import com.snobot.vision_app.app2017.java_algorithm.common.GripPegAlgorithm;
import com.snobot.vision_app.app2017.java_algorithm.common.TapeLocation;
import com.snobot.vision_app.app2017.messages.out.TargetUpdateMessage;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by preiniger on 1/24/2017.
 */

public class JavaVisionAlgorithm extends BaseJavaAlgorithm
{

    private int cameraDirection;
    private VisionRobotConnection mRobotConnection;
    private VisionAlgorithmPreferences mPreferences;
    private String mImageDumpDir;
    private boolean mRecordingImages;
    private boolean mRobotConnected;

    public JavaVisionAlgorithm(VisionRobotConnection aRobotConnection, VisionAlgorithmPreferences aPreferences)
    {
        mRobotConnection = aRobotConnection;
        cameraDirection = CameraBridgeViewBase.CAMERA_ID_FRONT;
        mPreferences = aPreferences;

        mImageDumpDir = "SnobotVision/" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        mRecordingImages = false;
    }

    public Mat processImage(Bitmap aBitmap, long aImageTimestamp) {
        Mat mat = new Mat();
        Utils.bitmapToMat(aBitmap, mat);

        return processImage(mat, aImageTimestamp);
    }

    public Mat processImage(Mat aMat, long aSystemTimeNs) {

        Pair<Integer, Integer> hue = mPreferences.getHueThreshold();
        Pair<Integer, Integer> sat = mPreferences.getSatThreshold();
        Pair<Integer, Integer> lum = mPreferences.getLumThreshold();

        Pair<Integer, Integer> filterWidth = mPreferences.getFilterWidthThreshold();
        Pair<Integer, Integer> filterHeight = mPreferences.getFilterHeightThreshold();
        Pair<Integer, Integer> filterVertices = mPreferences.getFilterVerticesThreshold();
        Pair<Float, Float> filterRatio = mPreferences.getFilterRatioRange();

        mFilterParams.minWidth = filterWidth.first;
        mFilterParams.maxWidth = filterWidth.second;
        mFilterParams.minHeight = filterHeight.first;
        mFilterParams.maxHeight = filterHeight.second;
        mFilterParams.minVertices = filterVertices.first;
        mFilterParams.maxVertices = filterVertices.second;
        mFilterParams.minRatio = filterRatio.first;
        mFilterParams.maxRatio = filterRatio.second;

        mPegGripAlgorithm.setHslThreshold(hue.first, hue.second, sat.first, sat.second, lum.first, lum.second);

        Bitmap bitmap = Bitmap.createBitmap(aMat.cols(), aMat.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(aMat, bitmap);

        if(mRecordingImages && mRobotConnected)
        {
            writeImage(bitmap);
        }

        if(cameraDirection == CameraBridgeViewBase.CAMERA_ID_FRONT)
        {
            return processPegImage(aMat, aSystemTimeNs);
        }
        else
        {
            return processRopeImage(aMat, aSystemTimeNs);
        }
    }

    public void iterateDisplayType()
    {
        int nextIndex = mDisplayType.ordinal() + 1;

        mDisplayType = DisplayType.values()[nextIndex % DisplayType.values().length];
    }

    public void setRecording(boolean recording) {
        this.mRecordingImages = recording;
    }

    public void setRobotConnected(boolean isConnected)
    {
        mRobotConnected = isConnected;
    }

    public void setCameraDirection(int cameraDirection) {
        this.cameraDirection = cameraDirection;
    }

    protected void sendTargetInformation(Collection<TapeLocation> targetInfos, boolean aAmbigious, double aDistance, double aAngleToPeg, double aLatencySec)
    {
        List<TargetUpdateMessage.TargetInfo> targets = new ArrayList<>();

        if(!targetInfos.isEmpty())
        {
            aAngleToPeg = Double.isNaN(aAngleToPeg) ? 0 : aAngleToPeg;
            targets.add(new TargetUpdateMessage.TargetInfo(aAngleToPeg, aDistance, aAmbigious));
        }

        mRobotConnection.sendVisionUpdate(targets, aLatencySec);
    }

    private void writeImage(final Bitmap aImage)
    {
        long curTime = System.currentTimeMillis();

        File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File dir = new File (root.getAbsolutePath() + "/" + mImageDumpDir);
        if(!dir.exists())
        {
            dir.mkdirs();
        }

        final File file = new File(dir.getAbsolutePath(), curTime + ".jpg");

        Runnable imageSaver = new Runnable() {
            @Override
            public void run() {
                FileOutputStream out = null;
                try {
                    out = new FileOutputStream(file);
                    aImage.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    System.out.println("Saved file!");
                    out.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        new Thread(imageSaver).start();

    }
}