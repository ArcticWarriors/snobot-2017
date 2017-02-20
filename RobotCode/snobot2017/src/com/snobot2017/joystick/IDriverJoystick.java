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

}
