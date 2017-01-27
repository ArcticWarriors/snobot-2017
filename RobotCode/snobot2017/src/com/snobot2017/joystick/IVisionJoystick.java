package com.snobot2017.joystick;

import com.snobot.lib.ISubsystem;

public interface IVisionJoystick extends ISubsystem
{
    /**
     * @return true if App View should be changed
     */
    boolean iterateAppView();
    
    boolean switchToFrontCamera();
    
    boolean switchToRearCamera();
    
    boolean restartApp();
}
