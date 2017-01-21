package com.snobot2017.joystick;

import com.snobot.lib.ISubsystem;

public interface IOperatorJoystick extends ISubsystem
{
    enum GearBossPositions {
        NONE, UP, DOWN
    }
    
    /**
     * Returns the speed for the climbing mechanism
     * 
     * @return Climbing Speed
     */
    double getTakeOffSpeed();
    
    /**
     * Returns UP if up button is pressed, 
     * DOWN, if down button is pressed, and 
     * NONE, if neither buttons are pressed
     * @return UP to move Gear Boss up, DOWN to move Gear Boss down, and NONE to do nothing
     */
    GearBossPositions moveGearBossToPosition();
}
