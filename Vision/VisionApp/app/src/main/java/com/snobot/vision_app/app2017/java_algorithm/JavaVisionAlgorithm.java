package com.snobot.vision_app.app2017.java_algorithm;

import android.graphics.Bitmap;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by preiniger on 1/24/2017.
 */

public class JavaVisionAlgorithm
{
    private static final double sIMAGE_WIDTH = 640;
    private static final double sIMAGE_HEIGHT = 480;
    private static final double sHORIZONTAL_FOV_ANGLE = Math.toRadians(31.5);
    private static final double sVERTICAL_FOV_ANGLE = Math.toRadians(25.5);

    private static final double sTARGET_WIDTH = 2;
    private static final double sTARGET_HEIGHT = 5;

    private static final Point sCENTER_LINE_START = new Point(sIMAGE_WIDTH / 2, 0);
    private static final Point sCENTER_LINE_END = new Point(sIMAGE_WIDTH / 2, sIMAGE_HEIGHT);

    private static final Scalar sCENTER_LINE_COLOR = new Scalar(0, 255, 0);
    private static final Scalar sBLACK_COLOR = new Scalar(0, 0, 0);

    private static final Scalar[] sCONTOUR_COLORS = new Scalar[]{
            new Scalar(0, 0, 255),
            new Scalar(255, 0, 0),
            new Scalar(0, 255, 255)
    };

    private static final DecimalFormat sDF = new DecimalFormat("0.0000");

    public enum DisplayType
    {
        OriginalImage,
        PostThreshold,
        MarkedUpImage
    }

    public static class TapeLocation
    {
        private double mAngle;
        private double mDistanceFromHoriz;
        private double mDistanceFromVert;

        public TapeLocation(double aAngle, double aDistanceFromHoriz, double aDistanceFromVert) {
            mAngle = aAngle;
            mDistanceFromHoriz = aDistanceFromHoriz;
            mDistanceFromVert = aDistanceFromVert;
        }
    }

    private GripPegAlgorithm mPegGripAlgorithm;
    private GripPegAlgorithm mRopeGripAlgorithm;
    private DisplayType mDisplayType;
    private int cameraDirection;

    public JavaVisionAlgorithm()
    {
        mPegGripAlgorithm = new GripPegAlgorithm();
        mRopeGripAlgorithm = new GripPegAlgorithm();
        mDisplayType = DisplayType.OriginalImage;
        cameraDirection = CameraBridgeViewBase.CAMERA_ID_FRONT;
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

    public void setCameraDirection(int cameraDirection) {
        this.cameraDirection = cameraDirection;
    }



    public Mat processImage(Bitmap aBitmap) {
        Mat mat = new Mat();
        Utils.bitmapToMat(aBitmap, mat);

        return processImage(mat);
    }

    public Mat processImage(Mat mat) {

        if(cameraDirection == CameraBridgeViewBase.CAMERA_ID_FRONT)
        {
            return processPegImage(mat);
        }
        else
        {
            return processRopeImage(mat);
        }
    }


    protected Mat processPegImage(Mat aOriginal)
    {
        mPegGripAlgorithm.process(aOriginal);

        ArrayList<MatOfPoint> contours = mPegGripAlgorithm.filterContoursOutput();
        List<TapeLocation> targetInfos = new ArrayList<>(contours.size());

        for (int i = 0; i < contours.size(); ++i)
        {
            MatOfPoint contour = contours.get(i);
            Rect rect = Imgproc.boundingRect(contour);
            double aspectRatio = rect.width * 1.0 / rect.height;

            double distanceFromHorz = (sTARGET_WIDTH * sIMAGE_WIDTH) / (2 * rect.width * Math.tan(sHORIZONTAL_FOV_ANGLE));
            double distanceFromVert = (sTARGET_HEIGHT * sIMAGE_HEIGHT) / (2 * rect.height * Math.tan(sVERTICAL_FOV_ANGLE));

            // TODO I don't know if this will work...

            // Calculate the angle by calculating how far off the center the
            // bounding box is. Assume that the ratio of pixels to angle is
            // linear, meaning that it is off by the FOV
            double x_centroid = rect.x + rect.width / 2;
            double distanceFromCenterPixel = x_centroid - sIMAGE_WIDTH / 2;
            double percentOffCenter = distanceFromCenterPixel / sIMAGE_WIDTH * 100;
            double yawAngle = percentOffCenter * sHORIZONTAL_FOV_ANGLE;

            targetInfos.add(new TapeLocation(yawAngle, distanceFromHorz, distanceFromVert));
        }


        Mat displayImage;

        switch(mDisplayType) {
            case PostThreshold:
            {
                displayImage = new Mat();
                Imgproc.cvtColor(mPegGripAlgorithm.hslThresholdOutput(), displayImage, 9); //TODO magic number, should be CV_GRAY2RGBA but I can't find it
                break;
            }
            case MarkedUpImage:
            {
                displayImage = drawImageMarkup(aOriginal, mPegGripAlgorithm.filterContoursOutput(), targetInfos);
                break;
            }
            case OriginalImage:
            default: // Intentional fallthrough
            {
                displayImage = aOriginal;
                break;
            }

        }

        return displayImage;
    }

    private Mat drawImageMarkup(Mat aOriginal, ArrayList<MatOfPoint> aContours, List<TapeLocation> targetInfos)
    {
        Mat displayImage = new Mat();
        aOriginal.copyTo(displayImage);

        Imgproc.line(displayImage, sCENTER_LINE_START, sCENTER_LINE_END, sCENTER_LINE_COLOR, 1);

        for (int i = 0; i < aContours.size(); ++i)
        {
            TapeLocation targetInfo = targetInfos.get(i);
            String textToDisplay = "Dist. " + sDF.format(targetInfo.mDistanceFromVert) + " Angle: " + sDF.format(targetInfo.mAngle);

            Scalar contourColor = sCONTOUR_COLORS[i % sCONTOUR_COLORS.length];
            Imgproc.drawContours(displayImage, aContours, i, contourColor, 3);
            Imgproc.putText(displayImage, textToDisplay, new Point(20, 20 * i + 50), Core.FONT_HERSHEY_COMPLEX, .6, contourColor);
        }

        if (aContours.isEmpty())
        {
            Imgproc.putText(displayImage, "No image detected", new Point(20, 50), Core.FONT_HERSHEY_COMPLEX, .6, sBLACK_COLOR);
        }

        return displayImage;
    }

    protected Mat processRopeImage(Mat aOriginal)
    {
        mRopeGripAlgorithm.process(aOriginal);

        Mat output;

        switch(mDisplayType) {
            case PostThreshold:
            {
                output = new Mat();
                Imgproc.cvtColor(mRopeGripAlgorithm.hslThresholdOutput(), output, 9); //TODO magic number, should be CV_GRAY2RGBA but I can't find it
                break;
            }
            case OriginalImage:
            default: // Intentional fallthrough
            {
                output = aOriginal;
                break;
            }

        }

        return output;
    }


}
