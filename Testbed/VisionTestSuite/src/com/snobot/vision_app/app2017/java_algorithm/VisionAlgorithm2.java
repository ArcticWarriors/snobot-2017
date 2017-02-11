package com.snobot.vision_app.app2017.java_algorithm;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

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
        OriginalImage, PostThreshold, MarkedUpImage
    }

    public static class TapeLocation
    {
        private MatOfPoint mContour;
        private double mAngle;
        private double mDistanceFromHoriz;
        private double mDistanceFromVert;
        private double mAspectRatio;

        public TapeLocation(MatOfPoint aContour, double aAngle, double aDistanceFromHoriz, double aDistanceFromVert, double aAspectRatio)
        {
            mContour = aContour;
            mAngle = aAngle;
            mDistanceFromHoriz = aDistanceFromHoriz;
            mDistanceFromVert = aDistanceFromVert;
            mAspectRatio = aAspectRatio;
        }

        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            long temp;
            temp = Double.doubleToLongBits(mAngle);
            result = prime * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(mAspectRatio);
            result = prime * result + (int) (temp ^ (temp >>> 32));
            result = prime * result + ((mContour == null) ? 0 : mContour.hashCode());
            temp = Double.doubleToLongBits(mDistanceFromHoriz);
            result = prime * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(mDistanceFromVert);
            result = prime * result + (int) (temp ^ (temp >>> 32));
            return result;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            TapeLocation other = (TapeLocation) obj;
            if (Double.doubleToLongBits(mAngle) != Double.doubleToLongBits(other.mAngle))
                return false;
            if (Double.doubleToLongBits(mAspectRatio) != Double.doubleToLongBits(other.mAspectRatio))
                return false;
            if (mContour == null)
            {
                if (other.mContour != null)
                    return false;
            }
            else if (!mContour.equals(other.mContour))
                return false;
            if (Double.doubleToLongBits(mDistanceFromHoriz) != Double.doubleToLongBits(other.mDistanceFromHoriz))
                return false;
            if (Double.doubleToLongBits(mDistanceFromVert) != Double.doubleToLongBits(other.mDistanceFromVert))
                return false;
            return true;
        }

        @Override
        public String toString()
        {
            return "TapeLocation [mAngle=" + mAngle + ", mDistanceFromHoriz=" + mDistanceFromHoriz + ", mDistanceFromVert="
                    + mDistanceFromVert + ", mAspectRatio=" + mAspectRatio + "]";
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
        mDisplayType = DisplayType.MarkedUpImage;
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
    
    class AspectRatioComparator implements Comparator<TapeLocation>
    {
        @Override
        public int compare(TapeLocation o1, TapeLocation o2)
        {
            double aspectRatioDifference_1 = Math.abs(0.4 - o1.mAspectRatio);
            double aspectRatioDifference_2 = Math.abs(0.4 - o2.mAspectRatio);
            
            if(aspectRatioDifference_1>aspectRatioDifference_2)
                return 1;
            else 
                return -1;
        }
    }
    
    class AngleComparator implements Comparator<TapeLocation>
    {
        @Override
        public int compare(TapeLocation o1, TapeLocation o2)
        {
            if(o1.mAngle>o2.mAngle)
                return 1;
            else
                return -1;
        }
    }
    
    protected Mat processPegImage(Mat aOriginalImage)
    {
        mPegGripAlgorithm.process(aOriginalImage);

        ArrayList<MatOfPoint> contours = mPegGripAlgorithm.filterContoursOutput();
        Set<TapeLocation> targetInfos = new TreeSet<>(new AspectRatioComparator());
        
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

            System.out.println("Contour " + i);
            System.out.println("  Aspect Ratio         : " + sDF.format(aspectRatio));
            System.out.println("  Angle                : " + sDF.format(yawAngle));
            System.out.println("  Distance From Horz.  : Dist=" + sDF.format(distanceFromHorz));
            System.out.println("  Distance From Vert.  : Dist=" + sDF.format(distanceFromVert));

            targetInfos.add(new TapeLocation(contours.get(i), yawAngle, distanceFromHorz, distanceFromVert, aspectRatio));
        }
        
        double angle_to_the_peg = Double.NaN;
        double centroid_of_image_X = sIMAGE_WIDTH/2;
        Iterator<TapeLocation> targetIterator = targetInfos.iterator();
        if(targetInfos.size()>=2)
        {
            Rect one = Imgproc.boundingRect(targetIterator.next().mContour);
            Rect two = Imgproc.boundingRect(targetIterator.next().mContour);
            double centroid_of_bounding_box_one = one.x + (one.width/2);
            double centroid_of_bounding_box_two = two.x + (two.width/2);
            double peg_X = (centroid_of_bounding_box_one + centroid_of_bounding_box_two)/2;
            double peg_to_center_of_image_pixels = centroid_of_image_X-peg_X;
            
            double angle_to_peg_RAD = Math.atan((peg_to_center_of_image_pixels/centroid_of_image_X) * Math.tan(sHORIZONTAL_FOV_ANGLE));
            angle_to_the_peg = Math.toDegrees(angle_to_peg_RAD);
            System.out.println("ANGLE: " + angle_to_the_peg);
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
                displayImage = getMarkedUpImage(aOriginalImage, targetInfos, angle_to_the_peg);
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

    private Mat getMarkedUpImage(Mat aOriginal, Collection<TapeLocation> aTargetInfos, double aAngle_to_the_peg)
    {
        Mat displayImage = new Mat();
        aOriginal.copyTo(displayImage);
        
        String text_angle;
        if(Double.isNaN(aAngle_to_the_peg))
        {
            text_angle = "No angle detected";
        }
        else
        {
            text_angle = "Angle To Peg: " + sDF.format(aAngle_to_the_peg);
        }
        Imgproc.line(displayImage, sCENTER_LINE_START, sCENTER_LINE_END, sCENTER_LINE_COLOR, 1);
        Imgproc.putText(displayImage, text_angle, new Point(20, 20), Core.FONT_HERSHEY_COMPLEX, .6, sWHITE_COLOR);
        
        int ctr = 0;
        for (TapeLocation targetInfo : aTargetInfos)
        {
            String textToDisplay = "Dist. " + sDF.format(targetInfo.mDistanceFromVert) + " Angle: " + sDF.format(targetInfo.mAngle);

            Scalar contourColor = sCONTOUR_COLORS[ctr % sCONTOUR_COLORS.length];
            Imgproc.drawContours(displayImage, Arrays.asList(targetInfo.mContour), 0, contourColor, 3);
            Imgproc.putText(displayImage, textToDisplay, new Point(20, 20 * ctr + 50), Core.FONT_HERSHEY_COMPLEX, .6, contourColor);
            ++ctr;
        }

        if (aTargetInfos.isEmpty())
        {
            Imgproc.putText(displayImage, "No image detected", new Point(20, 50), Core.FONT_HERSHEY_COMPLEX, .6, sBLACK_COLOR);
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