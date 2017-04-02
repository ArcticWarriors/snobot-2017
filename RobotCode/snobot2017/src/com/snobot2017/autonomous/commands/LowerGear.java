package com.snobot2017.autonomous.commands;

import com.snobot2017.gearboss.IGearBoss;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 * Drops the gear boss down
 * 
 * @author Nora
 *
 */
public class LowerGear extends TimedCommand
{
    private IGearBoss mGearBoss;

    /**
     * Constructor
     * 
     * @param aGearBoss
     *            The gear boss to lower the gear
     * @param aTimeOut
     *            The time to lower the gear
     */
    public LowerGear(IGearBoss aGearBoss, double aTimeOut)
    {
        super(aTimeOut);
        mGearBoss = aGearBoss;
    }

    @Override
    protected void initialize()
    {
        System.out.println("ScoreGear: Lower Gear");
    }

    @Override
    protected void execute()
    {
        mGearBoss.moveGearLow();
    }

    @Override
    protected void end()
    {
        System.out.println("ScoreGear: End");
    }
}
