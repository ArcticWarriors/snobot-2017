package com.snobot.vision_app.app2017.java_algorithm;

import android.graphics.Bitmap;
import android.util.Pair;

import com.snobot.vision_app.app2017.VisionAlgorithmPreferences;
import com.snobot.vision_app.app2017.VisionRobotConnection;
import com.snobot.vision_app.app2017.java_algorithm.common.BaseJavaAlgorithm;
import com.snobot.vision_app.app2017.java_algorithm.common.TapeLocation;
import com.snobot.vision_app.app2017.messages.TargetUpdateMessage;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by preiniger on 1/24/2017.
 */

public class JavaVisionAlgorithm extends BaseJavaAlgorithm
{

    private int cameraDirection;
    private VisionRobotConnection mRobotConnection;
    private VisionAlgorithmPreferences mPreferences;

    public JavaVisionAlgorithm(VisionRobotConnection aRobotConnection, VisionAlgorithmPreferences aPreferences)
    {
        mRobotConnection = aRobotConnection;
        cameraDirection = CameraBridgeViewBase.CAMERA_ID_FRONT;
        mPreferences = aPreferences;
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
        mPegGripAlgorithm.setHslThreshold(hue.first, hue.second, sat.first, sat.second, lum.first, lum.second);

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
}