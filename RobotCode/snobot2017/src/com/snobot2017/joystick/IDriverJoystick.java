package com.snobot2017.joystick;

import com.snobot.lib.modules.IJoystick;

/**
 * Joystick for interacting with the drivetrain
 * @author jbnol
 *
 */
public interface IDriverJoystick extends IJoystick
{

    /**
     * 
     * @return Returns the right speed
     */
    double getRightSpeed();

    /**
     * 
     * @return Returns the left speed
     */
    double getLeftSpeed();

    /**
     * 
     * @return Returns the arcade power
     */
    double getArcadePower();

    /**
     * 
     * @return Turn Radius in Arcade mode
     */
    double getArcadeTurn();

    /**
     * 
     * @return Is Arcade mode enabled
     */
    boolean isArcadeMode();

}
