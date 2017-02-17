package com.snobot2017.joystick;

import com.snobot.lib.ISubsystem;

/**
 * Joystick for interacting with the Gear Boss
 * 
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
     * Is the green light toggle on?
     * 
     * @return whether the green lights should be on
     */
    boolean greenLightOn();
    boolean blueLightOn();
    
    /**
     * Returns UP if up button is pressed, DOWN, if down button is pressed, and
     * NONE, if neither buttons are pressed
     * 
     * @return UP to move Gear Boss up, DOWN to move Gear Boss down, and NONE to
     *         do nothing
     */
    GearBossPositions moveGearBossToPosition();
}
