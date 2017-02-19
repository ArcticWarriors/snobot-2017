package com.snobot2017.gearboss;

import com.snobot.lib.modules.ISubsystem;

/**
 * Interface controls gear position
 * 
 * @author Owner
 *
 */
public interface IGearBoss extends ISubsystem
{
    /**
     * Moves gear to its high position. This is to get the gear onto the peg.
     */
    void moveGearHigh();

    /**
     * This is to get the Gear Boss down and away from the gear. This is the
     * second part of the process of putting the gear on the peg.
     */
    void moveGearLow();

    /**
     * This returns the position of the gear bucket.
     * 
     * @return true for gear high and false for gear low
     */
    boolean isGearUp();
    
}
