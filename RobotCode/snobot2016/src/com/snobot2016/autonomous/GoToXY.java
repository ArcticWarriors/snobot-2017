package com.snobot2016.autonomous;

import com.snobot2016.drivetrain.IDriveTrain;
import com.snobot2016.positioner.IPositioner;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Directs the robot towards the given X/Y-coordinate.
 * 
 * @author Alec/Andrew
 *
 */
public class GoToXY extends Command
{
    private IDriveTrain mDriveTrain;
    private IPositioner mPositioner;
    private double mFinalXCoor;
    private double mFinalYCoor;
    private double mCurrentX;
    private double mCurrentY;
    private double mTurnDegrees;
    private double mDriveDistance;
    private double mSpeed;
    private Command mTurnWithDegrees;
    private Command mDriveStraightADistance;
    private CommandGroup mCommandGroup;

    /**
     * Creates a GoToXY Command.
     * 
     * @param aDriveTrain
     *            The robot's DriveTrain.
     * @param aPositioner
     *            The robot's Positioner.
     * @param aXCoor
     *            The desired final X-coordinate.
     * @param aYCoor
     *            The desired final Y-coordinate.
     * @param aSpeed
     *            The desired speed (-1 - 1).
     */
    public GoToXY(IDriveTrain aDriveTrain, IPositioner aPositioner, double aXCoor, double aYCoor, double aSpeed)
    {
        mDriveTrain = aDriveTrain;
        mPositioner = aPositioner;
        mFinalXCoor = aXCoor;
        mFinalYCoor = aYCoor;
        mSpeed = aSpeed;

    }

    /**
     * Calculates distance to travel and proper orientation then creates
     * DriveStraightADistance and TurnWithDegrees Commands, adds them to a
     * CommandGroup, then starts the CommandGroup.
     */
    @Override
    protected void initialize()
    {

        mCommandGroup = new CommandGroup();
        mCurrentX = mPositioner.getXPosition();
        mCurrentY = mPositioner.getYPosition();
        mDriveDistance = Math.sqrt((Math.pow((mFinalXCoor - mCurrentX), 2)) + (Math.pow((mFinalYCoor - mCurrentY), 2)));
        mTurnDegrees = Math.toDegrees(Math.atan2((mFinalXCoor - mCurrentX), (mFinalYCoor - mCurrentY)));
        mTurnWithDegrees = new TurnWithDegrees(mDriveTrain, mPositioner, mTurnDegrees, mSpeed);
        System.out.println(mTurnDegrees);
        mDriveStraightADistance = new DriveStraightADistance(mDriveTrain, mPositioner, mDriveDistance, mSpeed);
        mCommandGroup.addSequential(mTurnWithDegrees);
        mCommandGroup.addSequential(mDriveStraightADistance);
        mCommandGroup.start();

    }

    @Override
    protected void execute()
    {

    }

    /**
     * Checks if the CommandGroup is finished running.
     */
    @Override
    protected boolean isFinished()
    {
        if (!mCommandGroup.isRunning())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * Stops the CommandGroup and the robot.
     */
    @Override
    protected void end()
    {
        mCommandGroup.cancel();
        mDriveTrain.setLeftRightSpeed(0, 0);
    }

    @Override
    protected void interrupted()
    {

    }

}
