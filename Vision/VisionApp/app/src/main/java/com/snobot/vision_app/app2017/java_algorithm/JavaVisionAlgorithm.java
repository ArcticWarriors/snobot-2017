package com.snobot.vision_app.app2017.java_algorithm;

import android.graphics.Bitmap;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayOutputStream;

/**
 * Created by preiniger on 1/24/2017.
 */

public class JavaVisionAlgorithm
{
    public enum DisplayType
    {
        OriginalImage,
        PostThreshold,
        PostContour
    }

    private VisionGripAlgorithm mGrip;
    private DisplayType mDisplayType;

    public JavaVisionAlgorithm()
    {
        mGrip = new VisionGripAlgorithm();
        mDisplayType = DisplayType.OriginalImage;
    }

    public void setDisplayType(DisplayType aDisplayType)
    {
        mDisplayType = aDisplayType;
    }

    public void iterateDisplayType()
    {
        int nextIndex = mDisplayType.ordinal() + 1;

        mDisplayType = DisplayType.values()[nextIndex % DisplayType.values().length];
    }


    public Mat processImage(Bitmap aBitmap) {
        Mat mat = new Mat();
        Utils.bitmapToMat(aBitmap, mat);

        return processImage(mat);
    }

    public Mat processImage(Mat mat)
    {
        mGrip.process(mat);


        Mat output;

        switch(mDisplayType) {
            case PostThreshold:
            {
                output = new Mat();
                Imgproc.cvtColor(mGrip.hslThresholdOutput(), output, 9); //TODO magic number, should be CV_GRAY2RGBA but I can't find it
                break;
            }
//            case PostContour:
//            {
//                output = new Mat();
//                Imgproc.cvtColor(mGrip.findContoursOutput().get(0), output, 9); //TODO magic number, should be CV_GRAY2RGBA but I can't find it
//                break;
//            }
            case OriginalImage:
            default: // Intentional fallthrough
            {
                output = mat;
                break;
            }

        }

        return output;
    }


}
