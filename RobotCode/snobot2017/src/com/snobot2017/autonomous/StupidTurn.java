package com.snobot2017.autonomous;

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
    private boolean mDirection;
    private IDriveTrain mDriveTrain;

    /**
     * Constructor 
     * @param aSpeed  The speed to turn (positive for clockwise)
     * @param aDriveTrain The drivetrain to turn 
     * @param aTimeout The time to turn, in seconds
     * @param aDirection 
     */
    public StupidTurn(double aSpeed, IDriveTrain aDriveTrain, double aTimeout, boolean aDirection)
    {
        super(aTimeout);
        
        // TODO: andrew - remove direction
        mSpeed = aSpeed;
        mDriveTrain = aDriveTrain;
        mDirection = aDirection;
    }
    
    @Override
    public void execute()
    {
        if (mDirection == true)
        {
            mDriveTrain.setLeftRightSpeed(mSpeed, -mSpeed);
        }
        else
        {
            mDriveTrain.setLeftRightSpeed(-mSpeed, mSpeed);
        }
    }

}