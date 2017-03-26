package com.snobot.vision;

import java.awt.image.BufferedImage;

import org.opencv.core.Mat;

import com.snobot.vision.standalone.SetThresholdListener;
import com.snobot.vision_app.app2017.java_algorithm.common.FilterParams;

public interface IVisionAlgorithm extends SetThresholdListener
{

    public interface ProcessedImageListener
    {
        public void onCalculation(Mat original, Mat postThreshold);
    }

    public void processImage(BufferedImage image);

    public void addListener(ProcessedImageListener imageListener);

    public void setFilterParams(FilterParams filterParams);

}
