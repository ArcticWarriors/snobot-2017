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

    public byte[] processImage(Bitmap aBitmap)
    {
        Mat mat = new Mat();
        Utils.bitmapToMat(aBitmap, mat);

        mGrip.process(mat);

        Mat outputMat = mat;

        Bitmap bitmap = Bitmap.createBitmap(outputMat.cols(), outputMat.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(outputMat, bitmap);

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);

        return os.toByteArray();
    }


}
