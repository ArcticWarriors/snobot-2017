package com.snobot.xlib.motion_profile.simple;

import java.util.List;

/**
 * Interface used to get a list of setpoints used to move a path
 * 
 * @author PJ
 *
 */
public interface ISetpointIterator
{
    /**
     * Gets the next setpoint to drive to for the current iteration
     * 
     * @param aPosition
     *            The current position (distance or angle) of the robot
     * @param aVelocity
     *            The current velocity (ft/s or deg/s) of the robot
     * @param aDt
     *            The ideal delta-time between loops (likely the roboRIO time,
     *            20ms)
     * 
     * @return Returns the next setpoint to drive to, or NULL if there isn't one
     */
    PathSetpoint getNextSetpoint(double aPosition, double aVelocity, double aDt);

    /**
     * Indicates that you are finished with the list
     * 
     * @return True if finished, false otherwise
     */
    public boolean isFinished();

    /**
     * Gets the ideal path. That is, the path calculated based on the PathConfig
     * used to construct the iterator
     * 
     * @return The ideal path
     */
    public List<PathSetpoint> getIdealPath();
}
