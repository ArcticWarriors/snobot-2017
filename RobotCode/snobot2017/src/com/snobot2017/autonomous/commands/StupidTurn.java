package com.snobot2017.autonomous.commands;

import com.snobot2017.drivetrain.IDriveTrain;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 * Turns the robot for X seconds at Y Speed
 * 
 * @author jbnol
 *
 */
public class StupidTurn extends TimedCommand
{
    private double mSpeed;
    private IDriveTrain mDriveTrain;

    /**
     * Constructor 
     * @param aSpeed  The speed to turn (positive for clockwise)
     * @param aDriveTrain The drivetrain to turn 
     * @param aTimeout The time to turn, in seconds
     * @param aDirection 
     */
    public StupidTurn(double aSpeed, IDriveTrain aDriveTrain, double aTimeout)
    {
        super(aTimeout);

        mSpeed = aSpeed;
        mDriveTrain = aDriveTrain;
    }
    
    @Override
    public void execute()
    {
        mDriveTrain.setLeftRightSpeed(mSpeed, mSpeed);
    }

}