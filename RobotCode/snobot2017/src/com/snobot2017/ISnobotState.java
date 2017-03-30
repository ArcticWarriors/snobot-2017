package com.snobot2017;

/**
 * Simple interface to give the state of the snobot.
 * 
 * @author team-174
 *
 */
public interface ISnobotState
{
    /**
     * Is snobot in autonomous mode
     * 
     * @return true if in autonomous mode
     */
    boolean isAutonomous();

    /**
     * Is snobot in teleop (operator control) mode
     * 
     * @return true if in autonomous mode
     */
    boolean isOperatorControl();
}
