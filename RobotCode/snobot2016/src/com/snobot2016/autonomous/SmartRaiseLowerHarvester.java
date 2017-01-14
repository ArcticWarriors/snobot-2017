package com.snobot2016.autonomous;

import com.snobot2016.harvester.IHarvester;

import edu.wpi.first.wpilibj.command.Command;

/**
 * 
 * @author Calvin
 * 
 * This will raise or lower the harvester in conjunction with where it is located
 *
 */
public class SmartRaiseLowerHarvester extends Command
{

    private IHarvester mHarvester; 
    private boolean mFinished;
    private boolean mRaise;
    
    /**
     * Initializes class variables using outside parameters.
     * 
     * @param aHarvester
     *                  The Harvester
     * @param aRaiseOrLower
     *                 Pass in "Raise" or "Lower" the harvester
     */
    public SmartRaiseLowerHarvester(IHarvester aHarvester, String aRaiseOrLower)
    {
        mRaise = aRaiseOrLower.equalsIgnoreCase("Raise");
        mHarvester = aHarvester;
        mFinished = false;
        
        
        if (!aRaiseOrLower.equals("Raise") && !aRaiseOrLower.equals("Lower"))
        {
            throw new UnsupportedOperationException(aRaiseOrLower + " is not equal to Raise or Lower.");
        }
    }
    
    @Override
    protected void initialize()
    {
        
    }

    @Override
    protected void execute()
    {
       if(mRaise)
       {
           mHarvester.raiseHarvester();
           if(!mHarvester.goodToRaise())
           {
               mFinished = true;
           }
       }
       else
       {
           mHarvester.lowerHarvester();
           if(!mHarvester.goodToLower())
           {
               mFinished = true;
           }
       }
        
    }

    @Override
    protected boolean isFinished()
    {
        // TODO Auto-generated method stub
        return mFinished;
    }

    @Override
    protected void end()
    {
        mHarvester.stopHarvester();
        
    }

    @Override
    protected void interrupted()
    {
        
        
    }

}
