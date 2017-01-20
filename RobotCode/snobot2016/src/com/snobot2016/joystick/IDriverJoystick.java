package com.snobot2016.joystick;

import com.snobot.xlib.ISubsystem;

/**
 * 
 * @author jbnol_000
 * 
 * Interface for the Driver Joystick
 *
 */
public interface IDriverJoystick extends ISubsystem
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
     * @return Returns the power of arcade drive
     */
    double getArcadePower();
    
    /**
     * 
     * @return Returns the turn for the Arcade drive
     */
    double getArcadeTurn();
    
    /**
     * 
     * @return Tells whether or not the Arcade mode is on or not
     */
    boolean isArcadeMode();
}
