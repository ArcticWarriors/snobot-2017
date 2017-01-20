package com.snobot2016.autonomous;

import com.snobot.xlib.motion_profile.simple.ISetpointIterator;
import com.snobot.xlib.motion_profile.simple.PathConfig;
import com.snobot.xlib.motion_profile.simple.StaticSetpointIterator;
import com.snobot2016.autonomous.path.DriveStraightPath;
import com.snobot2016.autonomous.path.DriveTurnPath;
import com.snobot2016.drivetrain.IDriveTrain;
import com.snobot2016.positioner.IPositioner;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * A class that uses the motion profile pather to go to an (X,Y) position
 * 
 * @author Andrew
 */
public class GoToXYPath extends Command
{
    private IDriveTrain mDriveTrain;
    private IPositioner mPositioner;
    private double mFinalXCoor;
    private double mFinalYCoor;
    private double mCurrentX;
    private double mCurrentY;
    private double mTurnDegrees;
    private double mDriveDistance;
    private double mMaxTurnVel;
    private double mMaxTurnAccel;
    private double mMaxDriveVel;
    private double mMaxDriveAccel;
    private Command mDriveTurnPath;
    private Command mDriveStraightPath;
    private CommandGroup mCommandGroup;
    private PathConfig mTurnPathConfig;
    private PathConfig mDrivePathConfig;
    private ISetpointIterator mTurnSetpointIterator;
    private ISetpointIterator mDriveSetpointIterator;

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
     * @param aMaxVel
     *            The maximum velocity for the robot for the command iteration
     * @param aMaxAccel
     *            The maximum acceleration for this command iteration
     */
    public GoToXYPath(IDriveTrain aDriveTrain, IPositioner aPositioner, double aXCoor, double aYCoor, double aMaxTurnVel, double aMaxTurnAccel,
            double aMaxDriveVel, double aMaxDriveAccel)
    {
        mDriveTrain = aDriveTrain;
        mPositioner = aPositioner;
        mFinalXCoor = aXCoor;
        mFinalYCoor = aYCoor;
        mMaxTurnVel = aMaxTurnVel;
        mMaxTurnAccel = aMaxTurnAccel;
        mMaxDriveVel = aMaxDriveVel;
        mMaxDriveAccel = aMaxDriveAccel;

    }

    /**
     * Init- calculates the drive distance and turn degrees, plugs them into a
     * path command, and adds it to the command group.
     */
    @Override
    protected void initialize()
    {
        mCurrentX = mPositioner.getXPosition();
        double ChangeInX = mFinalXCoor - mCurrentX;

        mCurrentY = mPositioner.getYPosition();
        double ChangeInY = mFinalYCoor - mCurrentY;

        mDriveDistance = Math.sqrt((Math.pow((ChangeInX), 2)) + (Math.pow((ChangeInY), 2)));
        mTurnDegrees = Math.toDegrees(Math.atan2((ChangeInX), (ChangeInY)));

        mTurnPathConfig = new PathConfig(mTurnDegrees, mMaxTurnVel, mMaxTurnAccel, .02);
        mTurnSetpointIterator = new StaticSetpointIterator(mTurnPathConfig);
        mDriveTurnPath = new DriveTurnPath(mDriveTrain, mPositioner, mTurnSetpointIterator);

        mDrivePathConfig = new PathConfig(mDriveDistance, mMaxDriveVel, mMaxDriveAccel, .02);
        mDriveSetpointIterator = new StaticSetpointIterator(mDrivePathConfig);
        mDriveStraightPath = new DriveStraightPath(mDriveTrain, mPositioner, mDriveSetpointIterator);

        mCommandGroup = new CommandGroup();
        mCommandGroup.addSequential(mDriveTurnPath);
        mCommandGroup.addSequential(mDriveStraightPath);
    }

    /**
     * Execute part- starts the command group
     */
    @Override
    protected void execute()
    {
        mCommandGroup.start();
    }

    /**
     * returns the negation of whether or not the command group is still running
     */
    @Override
    protected boolean isFinished()
    {
        return !mCommandGroup.isRunning();
    }

    @Override
    protected void end()
    {
        mDriveTrain.stop();
    }

    @Override
    protected void interrupted()
    {

    }

}
