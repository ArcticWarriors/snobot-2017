package com.snobot2017.autonomous;

import com.snobot2017.drivetrain.IDriveTrain;
import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 * Autonomous command that allows the robot to drive straight at a certain speed
 * for a certain period of time and stops it at the end
 * 
 * @author Nora
 *
 */
public class StupidDriveStraight extends TimedCommand
{
    private double mSpeed;
    private IDriveTrain mDriveTrain;

    /**
     * Constructor
     * 
     * @param aDriveTrain
     *            Controls the drive train which is the main driving component
     * @param aTimeout
     *            How long we will drive within the time constraints
     * @param aSpeed
     *            How fast we will drive with a value between -1 and 1
     */
    public StupidDriveStraight(IDriveTrain aDriveTrain, double aTimeout, double aSpeed)
    {
        super(aTimeout);
        mSpeed = aSpeed;
        mDriveTrain = aDriveTrain;
    }

    /**
     * Sets the speed for both motors to drive straight
     */
    @Override
    public void execute()
    {
        mDriveTrain.setLeftRightSpeed(mSpeed, mSpeed);
    }

    /**
     * This method is called when the time runs out when the time runs out and
     * the timer is stopped
     */
    @Override
    public void end()
    {
        mDriveTrain.setLeftRightSpeed(0, 0);
    }
}
