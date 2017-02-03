package com.snobot.vision_app.app2017.java_algorithm;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import com.snobot.vision.HslThreshold;
import com.snobot.vision.IVisionAlgorithm;

public class VisionAlgorithm2 implements IVisionAlgorithm
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
        OriginalImage, PostThreshold, MarkedUpImage
    }

    public static class TapeLocation
    {
        private double mAngle;
        private double mDistanceFromHoriz;
        private double mDistanceFromVert;

        public TapeLocation(double aAngle, double aDistanceFromHoriz, double aDistanceFromVert)
        {
            mAngle = aAngle;
            mDistanceFromHoriz = aDistanceFromHoriz;
            mDistanceFromVert = aDistanceFromVert;
        }
    }

    protected List<ProcessedImageListener> mUpdateListeners;
    protected BufferedImage mCurrentImage;
    protected GripPegAlgorithm mPegGripAlgorithm;
    protected GripPegAlgorithm mRopeGripAlgorithm;
    protected DisplayType mDisplayType;

    public VisionAlgorithm2()
    {
        mUpdateListeners = new ArrayList<>();
        mPegGripAlgorithm = new GripPegAlgorithm();
        mDisplayType = DisplayType.PostThreshold;
    }

    public void setDisplayType(DisplayType aDisplayType)
    {
        mDisplayType = aDisplayType;
    }

    public void setThresholds(HslThreshold aMin, HslThreshold aMax)
    {
        mPegGripAlgorithm.setThreshold(aMin, aMax);

        if (mCurrentImage != null)
        {
            processImage(mCurrentImage);
        }
    }

    public void processImage(BufferedImage originalImage)
    {
        mCurrentImage = originalImage;

        if (originalImage != null)
        {
            byte[] pixels = ((DataBufferByte) mCurrentImage.getRaster().getDataBuffer()).getData();
            Mat matImage = new Mat(originalImage.getHeight(), originalImage.getWidth(), CvType.CV_8UC3);
            matImage.put(0, 0, pixels);

            processImage(matImage);
        }
    }

    public void processImage(Mat aOriginalImage)
    {
        Mat displayImage = processPegImage(aOriginalImage);

        for (ProcessedImageListener listener : mUpdateListeners)
        {
            listener.onCalculation(scaleImage(aOriginalImage), scaleImage(displayImage));
        }
    }

    protected Mat processPegImage(Mat aOriginalImage)
    {
        mPegGripAlgorithm.process(aOriginalImage);

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


            System.out.println("Contour " + i);
            System.out.println("  Aspect Ratio         : " + sDF.format(aspectRatio));
            System.out.println("  Angle                : " + sDF.format(yawAngle));
            System.out.println("  Distance From Horz.  : Dist=" + sDF.format(distanceFromHorz));
            System.out.println("  Distance From Vert.  : Dist=" + sDF.format(distanceFromVert));

            targetInfos.add(new TapeLocation(yawAngle, distanceFromHorz, distanceFromVert));
        }

        Mat displayImage;

        switch (mDisplayType)
        {
            case PostThreshold:
            {
                displayImage = new Mat();
                Imgproc.cvtColor(mPegGripAlgorithm.hslThresholdOutput(), displayImage, Imgproc.COLOR_GRAY2RGB);
                break;
            }
            case MarkedUpImage:
            {
                displayImage = getMarkedUpImage(aOriginalImage, mPegGripAlgorithm.filterContoursOutput(), targetInfos);
                break;
            }
            case OriginalImage:
            default: // Intentional fallthrough
            {
                displayImage = aOriginalImage;
                break;
            }
        }
        
        return displayImage;
    }

    private Mat getMarkedUpImage(Mat aOriginal, ArrayList<MatOfPoint> aContours, List<TapeLocation> targetInfos)
    {
        Mat displayImage = new Mat();
        aOriginal.copyTo(displayImage);

        Core.line(displayImage, sCENTER_LINE_START, sCENTER_LINE_END, sCENTER_LINE_COLOR, 1);

        for (int i = 0; i < aContours.size(); ++i)
        {
            TapeLocation targetInfo = targetInfos.get(i);
            String textToDisplay = "Dist. " + sDF.format(targetInfo.mDistanceFromVert) + " Angle: " + sDF.format(targetInfo.mAngle);

            Scalar contourColor = sCONTOUR_COLORS[i % sCONTOUR_COLORS.length];
            Imgproc.drawContours(displayImage, aContours, i, contourColor, 3);
            Core.putText(displayImage, textToDisplay, new Point(20, 20 * i + 50), Core.FONT_HERSHEY_COMPLEX, .6, contourColor);
        }

        if (aContours.isEmpty())
        {
            Core.putText(displayImage, "No image detected", new Point(20, 50), Core.FONT_HERSHEY_COMPLEX, .6, sBLACK_COLOR);
        }

        return displayImage;
    }

    private Mat scaleImage(Mat input)
    {
        // Size size = new Size(500, 500);// the dst image size,e.g.100x100
        // Mat dst = new Mat();
        // Imgproc.resize(input, dst, size);
        //
        // return dst;

        return input;
    }

    @Override
    public void addListener(ProcessedImageListener imageListener)
    {
        mUpdateListeners.add(imageListener);
    }
}