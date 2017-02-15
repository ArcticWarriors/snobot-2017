package com.snobot2017.autonomous;

import com.snobot2017.SnobotActor.ISnobotActor;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Turns N degrees
 * 
 * @author Jeff
 *
 */
public class TurnWithDegrees extends Command
{
    private ISnobotActor mSnobotActor;
    private boolean mFinished;

    /**
     * Constructor 
     * 
     * @param aSpeed The speed to drive
     * @param aTurnAngle
     * @param aDriveTrain
     * @param aPositioner
     */
    public TurnWithDegrees(double aSpeed, double aTurnAngle, ISnobotActor aSnobotActor)
    {
        mSnobotActor = aSnobotActor;
        mSnobotActor.setTurnGoal(aTurnAngle, aSpeed);

        mFinished = false;
    }

    @Override
    protected void execute()
    {
        mFinished = mSnobotActor.executeControlMode();
    }

    @Override
    protected boolean isFinished()
    {
        return mFinished;
        
    }
}