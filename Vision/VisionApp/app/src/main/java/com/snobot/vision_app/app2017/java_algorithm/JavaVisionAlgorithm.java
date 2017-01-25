package com.snobot.vision_app.app2017.java_algorithm;

import android.graphics.Bitmap;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.io.ByteArrayOutputStream;

/**
 * Created by preiniger on 1/24/2017.
 */

public class JavaVisionAlgorithm
{
    private VisionGripAlgorithm mGrip;

    public JavaVisionAlgorithm()
    {
        mGrip = new VisionGripAlgorithm();
    }

    public Mat processImage(Bitmap aBitmap) {
        Mat mat = new Mat();
        Utils.bitmapToMat(aBitmap, mat);

        return processImage(mat);
    }

    public Mat processImage(Mat mat)
    {
        mGrip.process(mat);

        return mat;
    }


}
