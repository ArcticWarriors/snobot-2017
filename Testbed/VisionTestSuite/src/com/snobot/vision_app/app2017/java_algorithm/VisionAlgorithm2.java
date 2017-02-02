package com.snobot.vision_app.app2017.java_algorithm;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
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

    private static final List<Scalar> sCONTOUR_COLORS = Arrays.asList(new Scalar[] { 
            new Scalar(0,   0,   0), 
            new Scalar(255, 0,   0), 
            new Scalar(0,   255, 255) 
    });

    protected List<ProcessedImageListener> mUpdateListeners;
    protected BufferedImage mCurrentImage;
    protected GripPegAlgorithm mPipeline;

    public VisionAlgorithm2()
    {
        mUpdateListeners = new ArrayList<>();
        mPipeline = new GripPegAlgorithm();
    }

    public void setThresholds(HslThreshold aMin, HslThreshold aMax)
    {
        mPipeline.setThreshold(aMin, aMax);

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

    public void processImage(Mat originalImage)
    {
        mPipeline.process(originalImage);

//        Mat displayImage = new Mat();
//        Imgproc.cvtColor(mPipeline.hslThresholdOutput(), displayImage, Imgproc.COLOR_GRAY2BGR);

        Mat displayImage = new Mat();
        originalImage.copyTo(displayImage);

        Core.line(displayImage, sCENTER_LINE_START, sCENTER_LINE_END, sCENTER_LINE_COLOR, 1);

        DecimalFormat df = new DecimalFormat("0.0000");

        ArrayList<MatOfPoint> contours = mPipeline.filterContoursOutput();
        for (int i = 0; i < contours.size(); ++i)
        {
            Imgproc.drawContours(displayImage, contours, i, sCONTOUR_COLORS.get(i % sCONTOUR_COLORS.size()), 3);

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
            System.out.println("  Aspect Ratio         : " + df.format(aspectRatio));
            System.out.println("  Angle                : " + df.format(yawAngle));
            System.out.println("  Distance From Horz.  : Dist=" + df.format(distanceFromHorz));
            System.out.println("  Distance From Vert.  : Dist=" + df.format(distanceFromVert));
        }

        for (ProcessedImageListener listener : mUpdateListeners)
        {
            listener.onCalculation(scaleImage(originalImage), scaleImage(displayImage));
        }
    }

    private Mat scaleImage(Mat input)
    {
        Size size = new Size(500, 500);// the dst image size,e.g.100x100
        Mat dst = new Mat();
        Imgproc.resize(input, dst, size);

        return dst;
    }

    @Override
    public void addListener(ProcessedImageListener imageListener)
    {
        mUpdateListeners.add(imageListener);
    }
}