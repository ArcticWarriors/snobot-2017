package com.snobot2017.autonomous;

import com.snobot2017.gearboss.IGearBoss;

import edu.wpi.first.wpilibj.command.Command;

public class ScoreGear extends Command
{
        private IGearBoss mGearBoss;

        public ScoreGear (IGearBoss aGearBoss)
        {
            mGearBoss=aGearBoss;
        }

        @Override
        protected boolean isFinished()
        {
            return mGearBoss.getGearHeight()== false;
        }
       
        protected void execute() 
        {
            mGearBoss.moveGearLow();
        }
}
