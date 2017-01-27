package com.snobot2017.climbing;

import com.snobot.lib.ISubsystem;

public interface IClimbing extends ISubsystem
{
    /**
     * Catch the Rope
     */
    void catchRope();

    /**
     * Climb the Rope
     */
    void climbRope();

}
