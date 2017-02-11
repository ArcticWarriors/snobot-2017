package com.snobot2017.joystick;

import com.snobot.lib.ISubsystem;

/**
 * Joystick for interacting with the Gear Boss
 * @author jbnol
 *
 */
public interface IOperatorJoystick extends ISubsystem
{
    enum GearBossPositions
    {
        NONE, UP, DOWN
    }

    /**
     * Returns the speed for the climbing mechanism
     * 
     * @return Climbing Speed
     */
    double getTakeOffSpeed();

    /**
     * Is the catch rope button pressed?
     * 
     * @return true if pressed
     */
    boolean isCatchRope();

    /**
     * Is the climb button pressed?
     * 
     * @return true if pressed
     */
    boolean isClimb();

    /**
     * Returns UP if up button is pressed, DOWN, if down button is pressed, and
     * NONE, if neither buttons are pressed
     * 
     * @return UP to move Gear Boss up, DOWN to move Gear Boss down, and NONE to
     *         do nothing
     */
    GearBossPositions moveGearBossToPosition();

    /**
     * returns if operators wants to go to peg or not
     * 
     * @return
     */
    boolean driveToPeg();
}
