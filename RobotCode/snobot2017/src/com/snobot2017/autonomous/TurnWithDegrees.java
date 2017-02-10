package com.snobot2017.autonomous;

import com.snobot2017.SnobotActor.ISnobotActor;
import com.snobot2017.SnobotActor.SnobotActor;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Turns N degrees
 * 
 * @author Jeff
 *
 */
public class TurnWithDegrees extends Command
{
    private SnobotActor mSnobotActor;
    private double mSpeed;
    private double mTurnAngle;
    private boolean mFinished;

    /**
     * Constructor
     * 
     * @param aSpeed
     *            The speed to drive
     * @param aTurnAngle
     * @param aDriveTrain
     * @param aPositioner
     */
    public TurnWithDegrees(double aSpeed, double aTurnAngle, ISnobotActor aSnobotActor)
    {
        mTurnAngle = aTurnAngle;
        mSpeed = aSpeed;
        mFinished = false;
    }

    @Override
    protected void execute()
    {
        mFinished = mSnobotActor.turnToAngle(mTurnAngle, mSpeed);
    }

    @Override
    protected boolean isFinished()
    {
        return mFinished;
    }
}