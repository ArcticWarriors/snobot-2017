package com.snobot.vision_app.app2017.java_algorithm;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
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
    private double coord_plane_distance_of_robot_from_target;
    public void processImage(Mat originalImage)
    {
        mGripAlgorithm.process(originalImage);

        Mat outputImage = new Mat();
        originalImage.copyTo(outputImage);
        Core.polylines(outputImage, mGripAlgorithm.filterContoursOutput(), true, contourColor);
        
        List<Double> distance_heights = new ArrayList<>();
        List<Double> distance_widths = new ArrayList<>();
        List<Double> distance_between_centroid_and_image_center = new ArrayList<>();
        double pixel_height = originalImage.size().height;
        double height_ANGLE = 49.48/2;
        double pixel_width = originalImage.size().width;
        double width_ANGLE = 62.69/2;
        double height_of_the_target_point_from_camera = 0; //TODO Based on the placement of camera on the robot
        for(MatOfPoint contour : mGripAlgorithm.filterContoursOutput())
        {
            //TODO Use rotated rectangle for more accuracy?
            Rect trial = Imgproc.boundingRect(contour);
            
            //Calculate distance between the centroid of the bounding box and the center of the image
            double centroid_of_bounding_box_X = trial.x + (trial.width/2);
            double centroid_of_image_X = pixel_width/2;
            
            if(centroid_of_image_X>centroid_of_bounding_box_X)
            {
                distance_between_centroid_and_image_center.add(centroid_of_image_X-centroid_of_bounding_box_X);
            }
            else 
            {
                distance_between_centroid_and_image_center.add(centroid_of_bounding_box_X-centroid_of_image_X);
            }
            //TODO Calculate angle based on distance between centroid and the distance from the robot to the thing.
            
            //Calculate distance from the camera to the target based on height and width
            double distance_temporary = (5*pixel_height)/(2*trial.height*Math.tan(Math.toRadians(height_ANGLE)));
            distance_heights.add(distance_temporary);
            
            distance_temporary = (2*pixel_width)/(2*trial.width*Math.tan(Math.toRadians(width_ANGLE)));
            distance_widths.add(distance_temporary);
        }
        
        System.out.println(distance_between_centroid_and_image_center);
        
        double distance_from_camera_to_target;
        
        //TODO use the best contour based on aspect ratio, or rightmost.
        
        //Average distance from the camera to the target
        //TODO Use some other way becasue this may not be accurate
        distance_from_camera_to_target = ((distance_heights.get(1)+distance_widths.get(1))/2);
        coord_plane_distance_of_robot_from_target = Math.sqrt(Math.pow(distance_from_camera_to_target, 2)-Math.pow(height_of_the_target_point_from_camera, 2));
        
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
