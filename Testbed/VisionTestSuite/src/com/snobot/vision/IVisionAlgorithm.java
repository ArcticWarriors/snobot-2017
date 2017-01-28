package com.snobot.vision;

import java.awt.image.BufferedImage;

import org.opencv.core.Mat;

import com.snobot.vision.standalone.SetThresholdListener;

public interface IVisionAlgorithm extends SetThresholdListener
{

    public interface ProcessedImageListener
    {
        public void onCalculation(Mat original, Mat postThreshold);
    }

    public void processImage(BufferedImage image);

    public void addListener(ProcessedImageListener imageListener);

}
