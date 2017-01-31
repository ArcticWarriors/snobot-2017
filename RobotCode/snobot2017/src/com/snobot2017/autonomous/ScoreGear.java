package com.snobot2017.autonomous;

import com.snobot2017.gearboss.IGearBoss;

import edu.wpi.first.wpilibj.command.TimedCommand;

public class ScoreGear extends TimedCommand
{
    private IGearBoss mGearBoss;
    

    public ScoreGear(IGearBoss aGearBoss, double aTimeOut)
    {
        super(aTimeOut);
        mGearBoss = aGearBoss;
        
    }

    protected void execute()
    {
        mGearBoss.moveGearLow();
    }
}
