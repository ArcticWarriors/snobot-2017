package com.snobot2016.autonomous;

import com.snobot2016.Properties2016;
import com.snobot2016.drivetrain.IDriveTrain;
import com.snobot2016.positioner.IPositioner;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * This class decides which low goal to drive to and moves to it.
 * 
 * @author Andrew
 *
 */
public class GoToLowGoal extends Command
{
    private IPositioner mPositioner;
    private IDriveTrain mDriveTrain;
    // private boolean mGoRight;
    // private Command mGoToXYPath;
    // private double mXPosition;
    // private double mYPosition;
    private double mMaxTurnVel;
    private double mMaxTurnAccel;
    private double mMaxDriveVel;
    private double mMaxDriveAccel;
    private CommandGroup mCommandGroup;

    /**
     * 
     * @param aPositioner
     *            The Robot's positioner
     * @param aDriveTrain
     *            The robot's drive train
     * @param aMaxTurnVel
     *            The maximum speed you want the robot to travel at when turning
     * @param aMaxTurnAccel
     *            The maximum speed you want the robot to accelerate at when
     *            turning
     * @param aMaxDriveVel
     *            The maximum speed you want the robot to travel at when driving
     * @param aMaxDriveAccel
     *            The maximum speed you want the robot to accelerate at when
     *            driving
     */
    public GoToLowGoal(IPositioner aPositioner, IDriveTrain aDriveTrain, double aMaxTurnVel, double aMaxTurnAccel, double aMaxDriveVel,
            double aMaxDriveAccel)
    {
        mDriveTrain = aDriveTrain;
        mPositioner = aPositioner;
        mMaxTurnVel = aMaxTurnVel;
        mMaxTurnAccel = aMaxTurnAccel;
        mMaxDriveVel = aMaxDriveVel;
        mMaxDriveAccel = aMaxDriveAccel;

    }

    /**
     * Init- if statement to decide which low goal to go to; also adds the
     * correct GoToXYPath command
     */
    @Override
    protected void initialize()
    {
        double mYPosition = Properties2016.sLOW_GOAL_Y.getValue();
        double mXPosition = Properties2016.sLOW_GOAL_X.getValue();
        if (mPositioner.getXPosition() < 0)
        {
            mXPosition = -mXPosition;
        }
        GoToXYPath drive_close_to_goal = new GoToXYPath(mDriveTrain, mPositioner, (mXPosition + 15), (mYPosition - 30), mMaxTurnVel, mMaxTurnAccel,
                mMaxDriveVel,
                mMaxDriveAccel);
        GoToXYPath drive_to_goal = new GoToXYPath(mDriveTrain, mPositioner, mXPosition, mYPosition, mMaxTurnVel, mMaxTurnAccel, mMaxDriveVel, mMaxDriveAccel);

        mCommandGroup = new CommandGroup();
        mCommandGroup.addSequential(drive_close_to_goal);
        mCommandGroup.addSequential(drive_to_goal);
    }

    /**
     * Starts the command group
     */
    @Override
    protected void execute()
    {
        mCommandGroup.start();
    }

    /**
     * Returns whether or not the command group is finished
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
