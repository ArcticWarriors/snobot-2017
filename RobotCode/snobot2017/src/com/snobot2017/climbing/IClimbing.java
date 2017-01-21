package com.snobot2017.climbing;

import com.snobot.lib.ISubsystem;

public interface IClimbing extends ISubsystem
{
    /**
     * Catch the Rope
     */
    void CatchRope();
    
    /**
     * Climb the Rope
     */
    void ClimbRope();
    
}
