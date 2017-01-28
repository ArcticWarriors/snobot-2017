package com.snobot.vision;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class VisionAlgorithm2 implements IVisionAlgorithm
{
    protected List<ProcessedImageListener> mUpdateListeners;
    protected BufferedImage mCurrentImage;
    protected GripPipeline mPipeline;

    public VisionAlgorithm2()
    {
        mUpdateListeners = new ArrayList<>();
        mPipeline = new GripPipeline();
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

    private static final double sHORIZONTAL_FOV = Math.toRadians(31.5);
    private static final double sVERTICAL_FOV = Math.toRadians(25.5);
    private static final double tapeWidth = 2;
    private static final double tapeHeight = 5;
    private static final double fovWidthPixels = 640;
    private static final double fovHeightPixels = 480;

    public void processImage(Mat originalImage)
    {
        originalImage = scaleImage(originalImage);
        mPipeline.process(originalImage);

//        Mat postThreshold = new Mat();
//        originalImage.copyTo(postThreshold);
//        Core.polylines(postThreshold, pipeline.filterContoursOutput(), true, new Scalar(255, 0, 0));
        
        Mat displayImage = mPipeline.cvErode1Output();

        for (MatOfPoint contour : mPipeline.filterContoursOutput())
        {
            Rect rect = Imgproc.boundingRect(contour);
            double aspectRatio = rect.width * 1.0 / rect.height;

            double distanceFromHorz = (tapeWidth * fovWidthPixels) / (2 * rect.width * Math.tan(sHORIZONTAL_FOV));
            double distanceFromVert = (tapeHeight * fovHeightPixels) / (2 * rect.height * Math.tan(sVERTICAL_FOV));
            
            System.out.println(distanceFromHorz + ", " + distanceFromVert);
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
