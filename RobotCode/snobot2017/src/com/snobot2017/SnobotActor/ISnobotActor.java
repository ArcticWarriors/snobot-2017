package com.snobot2017.SnobotActor;

import com.snobot.lib.ISubsystem;

public interface ISnobotActor extends ISubsystem
{
    /**
     * Sets the goal for driving a distance
     * 
     * @param aDesiredDistance
     *            The distance to drive, in inches
     * @param aGoalSpeed
     *            The speed (0-1) to drive
     */
    void setDistanceGoal(double aDesiredDistance, double aGoalSpeed);

    /**
     * Set goal for turning an angle
     * 
     * @param aAngle
     *            The angle to to turn to, in degrees
     * @param aSpeed
     *            The speed (0-1) to drive
     */
    void setTurnGoal(double aAngle, double aSpeed);

    /**
     * Sets the goal for going to an XY coordinate, in steps. That is, Turn
     * towards the point, then drive
     * 
     * @param aX
     *            The X Position to go to, in inches
     * @param aY
     *            The Y Position to go to, in inches
     * @param aSpeed
     *            The speed (0-1) to drive at
     */
    void setGoToPositionInStepsGoal(double aX, double aY, double aSpeed);

    /**
     * Sets the goal for going to an XY coordinate and drives/turns to point at
     * the same time.
     * 
     * @param aX
     *            The X Position to go to, in inches
     * @param aY
     *            The Y Position to go to, in inches
     */
    void setDriveSmoothlyToPositionGoal(double aX, double aY);

    /**
     * Executes the currently selected control mode. Returns true when it is
     * finished. If no control mode is running, it will return true
     * 
     * @return True if the control mode is finished
     */
    boolean executeControlMode();

    /**
     * Asking the SnobotActor if its in an action
     * 
     * @return If in an Action or not
     */
    boolean isInAction();

    /**
     * Cancels any running actions
     */
    void cancelAction();
}
