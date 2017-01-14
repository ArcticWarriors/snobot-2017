package com.snobot2016.camera;

import edu.wpi.first.wpilibj.image.HSLImage;

/**
 * Main interface for the Axis Camera
 *
 * @author Calvin Do
 * @author Adam Donle
 */

public interface ICamera
{
    /**
     * Will return the difference in angle from current angle (received from
     * gyro) and the target's angle.
     * 
     * @return mYaw
     */
    double getYaw();

    /**
     * Will return the distance from the camera to the target.
     * 
     * @return mDistanceToTarget
     */
    double getDistanceToTarget();

    /**
     * Creates a new image.
     * 
     * @return mNewImage
     */
    HSLImage getImage();

    /**
     * Replaces an existing image with a new image if there is a fresh image.
     * 
     * @return
     */
    boolean getUpdateImage();
}
