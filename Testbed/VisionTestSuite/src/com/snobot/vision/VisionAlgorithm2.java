package com.snobot.vision;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class VisionAlgorithm2 implements IVisionAlgorithm
{
    protected List<ProcessedImageListener> mUpdateListeners;
    protected BufferedImage mCurrentImage;
    protected RopeGripPipeline mPipeline;

    public VisionAlgorithm2()
    {
        mUpdateListeners = new ArrayList<>();
        mPipeline = new RopeGripPipeline();
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

        // Mat displayImage = mPipeline.blurOutput();
        // Mat displayImage = mPipeline.hslThresholdOutput();
        // Mat displayImage = mPipeline.cvDilateOutput();
        Mat displayImage = mPipeline.cvErodeOutput();
        // Mat displayImage = mPipeline.cvErode1Output();

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
