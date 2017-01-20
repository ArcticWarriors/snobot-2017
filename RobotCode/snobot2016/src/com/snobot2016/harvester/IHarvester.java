/**
 * 
 */
package com.snobot2016.harvester;

import com.snobot.xlib.ISubsystem;

/**
 * Author Jeffrey/Michael
 *
 * interface for harvester class
 *
 */
public interface IHarvester extends ISubsystem
{
    /**
     * Lowers the Harvester in a position able to acquire BOULDERS
     */
    void lowerHarvester();

    /**
     * Raises the Harvester for the beginning of the match
     */
    void raiseHarvester();

    /**
     * Turns the roller on so that the robot can acquire BOULDERS
     */
    void rollIn();

    /**
     * Reverses the roller so that the BOULDERS can be scored
     */
    void rollOut();

    /**
     * 
     * Sets the Roller Motor Speed
     * 
     * @param aRollerSpeed
     */
    void setRollerMotorSpeed(double aRollerSpeed);

    /**
     * 
     * Sets the Pivot Motor Speed
     * 
     * @param aPivotSpeed
     */
    void setPivotMotorSpeed(double aPivotSpeed);

    /**
     * Checks if the current position of the potentiometer will be able to move
     * in the raise direction
     * 
     * @return True if possible to raise
     */
    boolean goodToRaise();

    /**
     * Checks if the current position of the potentiometer will be able to move
     * in the lower direction
     * 
     * @return True if possible to lower
     */
    boolean goodToLower();

    /**
     * Converts the the current voltage out of the maximum voltage into a
     * percentage (for widget usage)
     * 
     * @return A percentage from 0 to 100 that represents how lowered the
     *         Harvester is.
     */
    double percentageLowered();

    /**
     * Sets the harvester to 0 speed
     */
    void stopHarvester();

    /**
     * Sets the roller to 0 speed
     */
    void stopRoller();

    /**
     * Raises or Lowers the harvester until it hits the limit, if angle is
     * reached, return true
     * 
     * @param aPotGoal
     * @return True if the goal percentage is reached (within deadband)
     */
    boolean moveToPercentage(double aPotGoal);

}
