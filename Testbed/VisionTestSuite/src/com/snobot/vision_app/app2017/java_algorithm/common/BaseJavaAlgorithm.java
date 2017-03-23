package com.snobot.vision_app.app2017.java_algorithm.common;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

/**
 * Created by PJ on 2/20/2017.
 */

public abstract class BaseJavaAlgorithm {

    private static final double sIMAGE_WIDTH = 640;
    private static final double sIMAGE_HEIGHT = 480;
    private static final double sHORIZONTAL_FOV_ANGLE = Math.toRadians(62.69 / 2);
    private static final double sVERTICAL_FOV_ANGLE = Math.toRadians(49.48 / 2);

    private static final double sTARGET_WIDTH = 2;
    private static final double sTARGET_HEIGHT = 5;

    private static final Point sCENTER_LINE_START = new Point(sIMAGE_WIDTH / 2, 0);
    private static final Point sCENTER_LINE_END = new Point(sIMAGE_WIDTH / 2, sIMAGE_HEIGHT);

    private static final Scalar sCENTER_LINE_COLOR = new Scalar(0, 255, 0);
    private static final Scalar sBLACK_COLOR = new Scalar(0, 0, 0);
    private static final Scalar sWHITE_COLOR = new Scalar( 255, 255, 255);

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

    protected GripPegAlgorithm mPegGripAlgorithm;
    protected GripRopeAlgorithm mRopeGripAlgorithm;
    protected DisplayType mDisplayType;

    public BaseJavaAlgorithm()
    {
        mPegGripAlgorithm = new GripPegAlgorithm();
        mRopeGripAlgorithm = new GripRopeAlgorithm();
        mDisplayType = DisplayType.MarkedUpImage;
    }

    public void setDisplayType(DisplayType aDisplayType)
    {
        mDisplayType = aDisplayType;
    }


    protected Mat processPegImage(Mat aOriginalImage, long aSystemTimeNs)
    {
        mPegGripAlgorithm.process(aOriginalImage);

        ArrayList<MatOfPoint> contours = mPegGripAlgorithm.filterContoursOutput();
        Set<TapeLocation> targetInfos = new TreeSet<>(new TargetComparators.AspectRatioComparator());

        for (int i = 0; i < contours.size(); ++i)
        {
            MatOfPoint contour = contours.get(i);
            Rect rect = Imgproc.boundingRect(contour);
            double aspectRatio = rect.width * 1.0 / rect.height;

            double distanceFromHorz = (sTARGET_WIDTH * sIMAGE_WIDTH) / (2 * rect.width * Math.tan(sHORIZONTAL_FOV_ANGLE));
            double distanceFromVert = (sTARGET_HEIGHT * sIMAGE_HEIGHT) / (2 * rect.height * Math.tan(sVERTICAL_FOV_ANGLE));

            // Calculate the angle by calculating how far off the center the
            // bounding box is. Assume that the ratio of pixels to angle is
            // linear, meaning that it is off by the FOV
            double x_centroid = rect.x + rect.width / 2;
            double distanceFromCenterPixel = x_centroid - sIMAGE_WIDTH / 2;
            double percentOffCenter = distanceFromCenterPixel / sIMAGE_WIDTH * 100;
            double yawAngle = percentOffCenter * sHORIZONTAL_FOV_ANGLE;

            targetInfos.add(new TapeLocation(contours.get(i), yawAngle, distanceFromHorz, distanceFromVert, aspectRatio));
        }

        // Reported numbers
        double distance = 0;
        double angle_to_the_peg = Double.NaN;
        boolean ambgiuous = true;

        double centroid_of_image_X = sIMAGE_WIDTH/2;
        if(targetInfos.size()>=2)
        {
            Iterator<TapeLocation> targetIterator = targetInfos.iterator();

            TapeLocation target1 = targetIterator.next();
            TapeLocation target2 = targetIterator.next();

            Rect one = Imgproc.boundingRect(target1.getContour());
            Rect two = Imgproc.boundingRect(target2.getContour());
            double centroid_of_bounding_box_one = one.x + (one.width/2);
            double centroid_of_bounding_box_two = two.x + (two.width/2);
            double peg_X = (centroid_of_bounding_box_one + centroid_of_bounding_box_two)/2;
            double peg_to_center_of_image_pixels = centroid_of_image_X-peg_X;

            double angle_to_peg_RAD = Math.atan((peg_to_center_of_image_pixels/centroid_of_image_X) * Math.tan(sHORIZONTAL_FOV_ANGLE));
            angle_to_the_peg = Math.toDegrees(angle_to_peg_RAD);

            distance = (target1.getPreferredDistance() + target2.getPreferredDistance()) / 2;
            ambgiuous = false;
        }
        else if(targetInfos.size() == 1)
        {
            Iterator<TapeLocation> targetIterator = targetInfos.iterator();
            TapeLocation target = targetIterator.next();

            angle_to_the_peg = target.getAngle();
            distance = target.getPreferredDistance();
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
            displayImage = getMarkedUpPegImage(aOriginalImage, targetInfos, distance, angle_to_the_peg);
            break;
        }
        case OriginalImage:
        default: // Intentional fallthrough
        {
            displayImage = aOriginalImage;
            break;
        }
        }

        long currentTime = System.nanoTime();
        double latencySec = (currentTime - aSystemTimeNs) / 1e9;
        sendTargetInformation(targetInfos, ambgiuous, distance, angle_to_the_peg, latencySec);

        return displayImage;
    }

    protected Mat processRopeImage(Mat aOriginal, long aSystemTimeNs)
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

    private Mat getMarkedUpPegImage(Mat aOriginal, Collection<TapeLocation> aTargetInfos, double aDistance, double aOverallAngle)
    {
        Mat displayImage = new Mat();
        aOriginal.copyTo(displayImage);

        String overallInformation;
        if(Double.isNaN(aOverallAngle))
        {
            overallInformation = "No angle detected";
        }
        else
        {
            overallInformation = "Dist: " + sDF.format(aDistance) + ", Angle: " + sDF.format(aOverallAngle);
        }
        Imgproc.line(displayImage, sCENTER_LINE_START, sCENTER_LINE_END, sCENTER_LINE_COLOR, 1);
        Imgproc.putText(displayImage, overallInformation, new Point(20, 20), Core.FONT_HERSHEY_COMPLEX, .6, sWHITE_COLOR);

        int ctr = 0;
        for (TapeLocation targetInfo : aTargetInfos)
        {
            String textToDisplay = "Dist. " + sDF.format(targetInfo.getPreferredDistance()) + " Angle: " + sDF.format(targetInfo.getAngle());

            Scalar contourColor = sCONTOUR_COLORS[ctr % sCONTOUR_COLORS.length];
            Imgproc.drawContours(displayImage, Arrays.asList(targetInfo.getContour()), 0, contourColor, 3);
            Imgproc.putText(displayImage, textToDisplay, new Point(20, 20 * ctr + 50), Core.FONT_HERSHEY_COMPLEX, .6, contourColor);
            ++ctr;
        }

        if (aTargetInfos.isEmpty())
        {
            Imgproc.putText(displayImage, "No image detected", new Point(20, 50), Core.FONT_HERSHEY_COMPLEX, .6, sBLACK_COLOR);
        }

        return displayImage;
    }

    protected abstract void sendTargetInformation(Collection<TapeLocation> targetInfos, boolean aAmbigious, double aDistance, double aAngleToPeg, double aLatencySec);
}
