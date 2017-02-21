package com.snobot.vision_app.app2017.java_algorithm;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import com.snobot.vision.HslThreshold;
import com.snobot.vision.IVisionAlgorithm;
import com.snobot.vision_app.app2017.java_algorithm.common.BaseJavaAlgorithm;
import com.snobot.vision_app.app2017.java_algorithm.common.TapeLocation;

public class VisionAlgorithm extends BaseJavaAlgorithm implements IVisionAlgorithm
{
    protected List<ProcessedImageListener> mUpdateListeners;
    protected BufferedImage mCurrentImage;

    public VisionAlgorithm()
    {
        mUpdateListeners = new ArrayList<>();
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
        Mat displayImage = processPegImage(aOriginalImage, 0);

        for (ProcessedImageListener listener : mUpdateListeners)
        {
            listener.onCalculation(scaleImage(aOriginalImage), scaleImage(displayImage));
        }
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

    @Override
    protected void sendTargetInformation(Collection<TapeLocation> targetInfos, boolean aAmbigious, double aDistance, double aAngleToPeg,
            double aLatencySec)
    {
    }
}