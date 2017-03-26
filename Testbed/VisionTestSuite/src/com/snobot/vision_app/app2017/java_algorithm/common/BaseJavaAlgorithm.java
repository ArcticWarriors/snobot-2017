package com.snobot.vision_app.app2017.java_algorithm.common;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
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

    private enum FilterResult
    {
        Success, BadWidth, BadHeight, BadVertices, BadArea, BadPerimeter, BadSolidarity, BadAspectRatio
    }

    protected GripPegAlgorithm mPegGripAlgorithm;
    protected GripRopeAlgorithm mRopeGripAlgorithm;
    protected DisplayType mDisplayType;
    protected FilterParams mFilterParams;

    // Saved for speed
    private List<FilterPair> mRealTargets;
    private List<FilterPair> mFilteredTargets;
    private Set<TapeLocation> mTargetInfos;

    public BaseJavaAlgorithm()
    {
        mPegGripAlgorithm = new GripPegAlgorithm();
        mRopeGripAlgorithm = new GripRopeAlgorithm();
        mDisplayType = DisplayType.MarkedUpImage;
        mFilterParams = new FilterParams();

        mRealTargets = new ArrayList<>();
        mFilteredTargets = new ArrayList<>();
        mTargetInfos = new TreeSet<>(new TargetComparators.AspectRatioComparator());
    }

    public void setDisplayType(DisplayType aDisplayType)
    {
        mDisplayType = aDisplayType;
    }

    public void setFilterParams(FilterParams aFilterParams)
    {
        mFilterParams = aFilterParams;
    }

    protected Mat processPegImage(Mat aOriginalImage, long aSystemTimeNs)
    {
        mPegGripAlgorithm.process(aOriginalImage);

        List<FilterPair> contours = filterContours(mPegGripAlgorithm.findContoursOutput());
        mTargetInfos.clear();
        mFilteredTargets.clear();

        for (int i = 0; i < contours.size(); ++i)
        {
            FilterPair filterPair = contours.get(i);

            if (filterPair.mResult != FilterResult.Success)
            {
                mFilteredTargets.add(filterPair);
                continue;
            }

            MatOfPoint contour = filterPair.mContour;
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

            mTargetInfos.add(new TapeLocation(contour, yawAngle, distanceFromHorz, distanceFromVert, aspectRatio));
        }

        // Reported numbers
        double distance = 0;
        double angle_to_the_peg = Double.NaN;
        boolean ambgiuous = true;

        double centroid_of_image_X = sIMAGE_WIDTH/2;
        if (mTargetInfos.size() >= 2)
        {
            Iterator<TapeLocation> targetIterator = mTargetInfos.iterator();

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
        else if (mTargetInfos.size() == 1)
        {
            Iterator<TapeLocation> targetIterator = mTargetInfos.iterator();
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
            displayImage = getMarkedUpPegImage(aOriginalImage, mTargetInfos, distance, angle_to_the_peg);
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
        sendTargetInformation(mTargetInfos, ambgiuous, distance, angle_to_the_peg, latencySec);

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
    

    private class FilterPair
    {
        private MatOfPoint mContour;
        private FilterResult mResult;

        public FilterPair(MatOfPoint aContour, FilterResult aResult)
        {
            mContour = aContour;
            mResult = aResult;
        }
    }

    /**
     * Filters out contours that do not meet certain criteria.
     * @param inputContours is the input list of contours
     * @param output is the the output list of contours
     * @param minArea is the minimum area of a contour that will be kept
     * @param minPerimeter is the minimum perimeter of a contour that will be kept
     * @param minWidth minimum width of a contour
     * @param maxWidth maximum width
     * @param minHeight minimum height
     * @param maxHeight maximimum height
     * @param Solidity the minimum and maximum solidity of a contour
     * @param minVertexCount minimum vertex Count of the contours
     * @param maxVertexCount maximum vertex Count
     * @param minRatio minimum ratio of width to height
     * @param maxRatio maximum ratio of width to height
     */
    private List<FilterPair> filterContours(List<MatOfPoint> inputContours)
    {
        final MatOfInt hull = new MatOfInt();
        mRealTargets.clear();
        //operation
        for (int i = 0; i < inputContours.size(); i++)
        {
            FilterResult filterResult = FilterResult.Success;

            final MatOfPoint contour = inputContours.get(i);
            final Rect bb = Imgproc.boundingRect(contour);
            if (bb.width < mFilterParams.minWidth || bb.width > mFilterParams.maxWidth)
            {
                filterResult = FilterResult.BadWidth;
                continue;
            }
            if (bb.height < mFilterParams.minHeight || bb.height > mFilterParams.maxHeight)
            {
                filterResult = FilterResult.BadHeight;
                continue;
            }
            final double area = Imgproc.contourArea(contour);
            if (area < mFilterParams.minArea)
            {
                filterResult = FilterResult.BadArea;
                continue;
            }
            if (Imgproc.arcLength(new MatOfPoint2f(contour.toArray()), true) < mFilterParams.minPerimeter)
            {
                filterResult = FilterResult.BadPerimeter;
                continue;
            }
            Imgproc.convexHull(contour, hull);
            MatOfPoint mopHull = new MatOfPoint();
            mopHull.create((int) hull.size().height, 1, CvType.CV_32SC2);
            for (int j = 0; j < hull.size().height; j++)
            {
                int index = (int) hull.get(j, 0)[0];
                double[] point = new double[]{ contour.get(index, 0)[0], contour.get(index, 0)[1] };
                mopHull.put(j, 0, point);
            }
            final double solid = 100 * area / Imgproc.contourArea(mopHull);
            if (solid < mFilterParams.minContoursSolidity || solid > mFilterParams.maxContoursSolidity)
            {
                filterResult = FilterResult.BadSolidarity;
                continue;
            }
            if (contour.rows() < mFilterParams.minVertices || contour.rows() > mFilterParams.maxVertices)
            {
                filterResult = FilterResult.BadVertices;
                continue;
            }
            final double ratio = bb.width / (double) bb.height;
            if (ratio < mFilterParams.minRatio || ratio > mFilterParams.maxRatio)
            {
                filterResult = FilterResult.BadAspectRatio;
                continue;
            }
            mRealTargets.add(new FilterPair(contour, filterResult));
        }

        return mRealTargets;
    }
}
