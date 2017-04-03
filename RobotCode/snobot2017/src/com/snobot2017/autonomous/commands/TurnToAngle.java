package com.snobot2017.autonomous.commands;

import com.snobot2017.SnobotActor.ISnobotActor;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Turns N degrees
 * 
 * @author Jeff
 *
 */
public class TurnToAngle extends Command
{
    private ISnobotActor mSnobotActor;
    private double mTurnAngle;
    private double mSpeed;
    protected boolean mFinished;

    /**
     * Constructor
     * 
     * @param aSpeed
     *            The speed to drive
     * @param aTurnAngle
     * @param aDriveTrain
     * @param aPositioner
     */
    public TurnToAngle(double aSpeed, double aTurnAngle, ISnobotActor aSnobotActor)
    {
        mSnobotActor = aSnobotActor;
        mSpeed = aSpeed;
        mTurnAngle = aTurnAngle;

        mFinished = false;
    }

    @Override
    protected void initialize()
    {
        mSnobotActor.setTurnGoal(mTurnAngle, mSpeed);
        System.out.println("TurnWithDegrees: " + mSpeed + ", " + mTurnAngle);
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

    @Override
    protected void end()
    {
        System.out.println("TurnWithDegrees: END");
    }
}