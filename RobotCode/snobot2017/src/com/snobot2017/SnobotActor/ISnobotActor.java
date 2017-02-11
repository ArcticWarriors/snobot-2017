package com.snobot2017.SnobotActor;

import com.snobot.lib.ISubsystem;

public interface ISnobotActor extends ISubsystem
{
    /**
     * turns to desired angle
     * 
     * @param aAngle
     * @param aSpeed
     * @return
     */
    boolean turnToAngle(double aAngle, double aSpeed);

    /**
     * drives to desired distance
     * 
     * @return
     */
    boolean driveDistance();

    /**
     * sets destination for driveDistance command
     * 
     * @param aDesiredDistance
     * @param aGoalSpeed
     */
    void setGoal(double aDesiredDistance, double aGoalSpeed);

    /**
     * Set goal for setting an angle and driving a distance
     * 
     * @param aAngle
     * @param aSpeed
     * @param aDistance
     */
    void setGoal(double aAngle, double aSpeed, double aDistance);

    /**
     * Asking the SnobotActor if its in an action
     * 
     * @return If in an Action or not
     */
    boolean InAction();
}
