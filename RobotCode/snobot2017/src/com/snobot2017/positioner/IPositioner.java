package com.snobot2017.positioner;

import com.snobot.lib.ISubsystem;

public interface IPositioner extends ISubsystem
{

    /**
     * @return The robot's current X-position.
     */
    public double getXPosition();

    /**
     * @return The robot's current Y-position.
     */
    public double getYPosition();

    /**
     * @return The robot's current orientation in degrees.
     */
    public double getOrientationDegrees();

    /**
     * @return the robot's current orientation in radians.
     */
    public double getOrientationRadians();

    /**
     * @return The total distance traversed by the robot.
     */
    public double getTotalDistance();

    /**
     * Sets the starting position of the robot
     * 
     * @param aX
     *            The starting X position
     * @param aY
     *            The starting Y Position
     * @param aAngle
     *            The starting Angle (in degrees)
     */
    public void setPosition(double aX, double aY, double aAngle);
}
