package com.snobot2017.joystick;

import com.snobot.lib.IJoystick;

/**
 * Joystick for interacting with the Gear Boss
 * 
 * @author jbnol
 *
 */
public interface IOperatorJoystick extends IJoystick
{
    enum GearBossPositions
    {
        NONE, UP, DOWN
    }

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

    /**
     * Should the green light be on?
     * 
     * @return True if the light should be on
     */
    boolean blueLightOn();

    /**
     * Is the sphincter open?
     * 
     * @return true if the sphincter is open
     */
    boolean isPooperOpen();

    /**
     * Returns UP if up button is pressed, DOWN, if down button is pressed, and
     * NONE, if neither buttons are pressed
     * 
     * @return UP to move Gear Boss up, DOWN to move Gear Boss down, and NONE to
     *         do nothing
     */
    GearBossPositions moveGearBossToPosition();
}
