package com.snobot.vision_app.app2017.java_algorithm;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
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

        // Mat postThreshold = new Mat();
        // originalImage.copyTo(postThreshold);
        // Core.polylines(postThreshold, pipeline.filterContoursOutput(), true,
        // new Scalar(255, 0, 0));

        Mat displayImage = mPipeline.hslThresholdOutput();

        DecimalFormat df = new DecimalFormat("0.0000");

        ArrayList<MatOfPoint> contours = mPipeline.filterContoursOutput();
        for (int i = 0; i < contours.size(); ++i)
        {
            MatOfPoint contour = contours.get(i);
            Rect rect = Imgproc.boundingRect(contour);
            double aspectRatio = rect.width * 1.0 / rect.height;

            double distanceFromHorz = (sTARGET_WIDTH * sIMAGE_WIDTH) / (2 * rect.width * Math.tan(sHORIZONTAL_FOV_ANGLE));
            double distanceFromVert = (sTARGET_HEIGHT * sIMAGE_HEIGHT) / (2 * rect.height * Math.tan(sVERTICAL_FOV_ANGLE));

            System.out.println("Contour " + i);
            System.out.println("  Aspect Ratio: " + df.format(aspectRatio));
            System.out.println("  Horizontal Calculations: Dist=" + df.format(distanceFromHorz));
            System.out.println("  Vertical Calculations  : Dist=" + df.format(distanceFromVert));
            // System.out.println(distanceFromHorz + ", " + distanceFromVert);
            // System.out.println(aspectRatio + "," + contour + ", " + rect);
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