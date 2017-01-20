package com.snobot2016.positioner;

import com.snobot.xlib.ISubsystem;

/**
 * 
 * @author Alec/Andrew
 *
 */
public interface IPositioner extends ISubsystem
{

    /**
     * 
     * @return returns the X position
     */
    public double getXPosition();

    /**
     * 
     * @return Returns the Y position
     */
    public double getYPosition();

    /**
     * 
     * @return Returns the orientation of the robot in degrees
     */
    public double getOrientationDegrees();

    /**
     * 
     * @return Returns the orientation of the robot in radians
     */
    public double getOrientationRadians();

    /**
     * 
     * @return Returns the total distance of the robot
     */
    public double getTotalDistance();

    /**
     * 
     * @param aX
     *          The X plane 
     * @param aY
     *          The Y plane
     * @param aAngle
     *              The Angle
     *              
     */
    public void setPosition(double aX, double aY, double aAngle);
}
