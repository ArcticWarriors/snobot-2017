package com.snobot.vision_app.app2017.java_algorithm;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import com.snobot.vision.HslThreshold;
import com.snobot.vision.IVisionAlgorithm;

public class VisionAlgorithm implements IVisionAlgorithm
{
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

        if (originalImage != null)
        {
            byte[] pixels = ((DataBufferByte) currentImage.getRaster().getDataBuffer()).getData();
            Mat matImage = new Mat(originalImage.getHeight(), originalImage.getWidth(), CvType.CV_8UC3);
            matImage.put(0, 0, pixels);

            processImage(matImage);
        }
    }
    
    private Scalar contourColor = new Scalar(255, 0, 0);
    private GripPegAlgorithm mGripAlgorithm = new GripPegAlgorithm();
    public void processImage(Mat originalImage)
    {
        mGripAlgorithm.process(originalImage);

        Mat outputImage = new Mat();
        originalImage.copyTo(outputImage);
        Imgproc.polylines(outputImage, mGripAlgorithm.filterContoursOutput(), true, contourColor);
        
        List<Double> distance_vertical = new ArrayList<>();
        List<Double> distance_horizontal = new ArrayList<>();
        List<Integer> centroid_of_bounding_box_X = new ArrayList<>();
        
        double image_height = originalImage.size().height;
        double image_width = originalImage.size().width;
        
        double vertical_fov_angle = Math.toRadians(49.48/2);
        double horizontal_FOV_angle = Math.toRadians(62.69/2);
        double centroid_of_image_X = image_width/2;
        
        //Based on receiving 2 contours only.
        for(MatOfPoint contour : mGripAlgorithm.filterContoursOutput())
        {
            Rect trial = Imgproc.boundingRect(contour);
            
            //Get the X-Value of the contours bounding box center
            centroid_of_bounding_box_X.add(trial.x + (trial.width/2));
            
            //Calculate distance from the camera to the target based on height and width
            double distance_temporary;
            distance_temporary = (5*image_height)/(2*trial.height*Math.tan(vertical_fov_angle));
            distance_vertical.add(distance_temporary);
            distance_temporary = (2*image_width)/(2*trial.width*Math.tan(horizontal_FOV_angle));
            distance_horizontal.add(distance_temporary);
        }
        
        //Angle to the peg
        double peg_X = (centroid_of_bounding_box_X.get(0) + centroid_of_bounding_box_X.get(1))/2;
        double peg_to_center_of_image_pixels = centroid_of_image_X - peg_X;
        double angle_to_the_peg = -Math.toDegrees(Math.atan((peg_to_center_of_image_pixels/centroid_of_image_X)*Math.tan(Math.toRadians(horizontal_FOV_angle))));
        
        System.out.println("Contour 0" + "\n\tAngle Peg\t\t: " + angle_to_the_peg + "\n\tDistance from Horz.\t: " + distance_horizontal.get(0) + "\n\tDistance from Vert.\t: " + distance_vertical.get(0));
        System.out.println("Contour 1" + "\n\tAngle Peg\t\t: " + angle_to_the_peg + "\n\tDistance from Horz.\t: " + distance_horizontal.get(1) + "\n\tDistance from Vert.\t: " + distance_vertical.get(1));
        
        //Average distance from the camera to the target (right target width/height average distance)
        double distance_from_camera_to_target = (distance_vertical.get(1) + distance_horizontal.get(1))/2;
//        System.out.println("Distance: " + distance_from_camera_to_target);
        
        for(ProcessedImageListener listener : mUpdateListeners)
        {
            listener.onCalculation(originalImage, outputImage);
        }
    }

    @Override
    public void addListener(ProcessedImageListener imageListener)
    {
        mUpdateListeners.add(imageListener);
    }
}
