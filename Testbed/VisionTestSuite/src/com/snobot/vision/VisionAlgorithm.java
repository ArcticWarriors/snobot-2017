package com.snobot.vision;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class VisionAlgorithm implements com.snobot.vision.standalone.SetThresholdListener
{
    public interface ProcessedImageListener
    {
        public void onCalculation(Mat original, Mat postThreshold);
    }

    protected List<ProcessedImageListener> mUpdateListeners;
    protected Scalar minThreshold;
    protected Scalar maxThreshold;
    protected BufferedImage currentImage;

    public VisionAlgorithm()
    {
        mUpdateListeners = new ArrayList<>();

        minThreshold = new Scalar(0, 0, 0);
        maxThreshold = new Scalar(255, 255, 255);
    }

    public void setThresholds(HslThreshold aMin, HslThreshold aMax)
    {
        minThreshold = new Scalar(aMin.hue, aMin.sat, aMin.lum);
        maxThreshold = new Scalar(aMax.hue, aMax.sat, aMax.lum);

        if (currentImage != null)
        {
            processImage(currentImage);
        }
    }

    public void processImage(BufferedImage originalImage)
    {
        currentImage = originalImage;
        byte[] pixels = ((DataBufferByte) originalImage.getRaster().getDataBuffer()).getData();
        Mat matImage = new Mat(originalImage.getHeight(), originalImage.getWidth(), CvType.CV_8UC3);
        matImage.put(0, 0, pixels);

        processImage(matImage);
    }

    public void processImage(Mat originalImage)
    {
        // Convert image to HSL
        Mat hslImage = new Mat(originalImage.height(), originalImage.width(), CvType.CV_8UC1);
        Imgproc.cvtColor(originalImage, hslImage, Imgproc.COLOR_RGBA2RGB);
        Imgproc.cvtColor(hslImage, hslImage, Imgproc.COLOR_RGB2HSV);

        // Run thresholding
        Mat postThreshold = new Mat(originalImage.height(), originalImage.width(), CvType.CV_8UC1);
        Core.inRange(hslImage, minThreshold, maxThreshold, postThreshold);

        for (ProcessedImageListener listener : mUpdateListeners)
        {
            listener.onCalculation(originalImage, postThreshold);
        }
    }

    public void addListener(ProcessedImageListener imageListener)
    {
        mUpdateListeners.add(imageListener);
    }
}
