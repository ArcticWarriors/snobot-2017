package com.snobot2017.autonomous.commands;

import java.util.List;

import com.snobot2017.SnobotActor.ISnobotActor;
import com.snobot2017.vision.TargetLocation;
import com.snobot2017.vision.VisionManager;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command drives to a Peg using the vision manager and the snobot actor.
 * 
 * @author Team174
 *
 */
public class DriveToPegUsingVision extends Command
{
    private VisionManager mVisionManager;
    private ISnobotActor mSnobotActor;
    private boolean mFinished;
    private double mDeadBandDistance;

    public DriveToPegUsingVision(VisionManager aVisionManager, ISnobotActor aSnobotActor, double aDeadBandDistance)
    {
        mSnobotActor = aSnobotActor;
        mVisionManager = aVisionManager;
        mDeadBandDistance = aDeadBandDistance;
    }

    @Override
    protected void initialize()
    {
        System.out.println("DriveToPegUsingVision: Initialize");

        // On initialize make sure the snobot actor is not in any previous
        // actions
        mSnobotActor.cancelAction();

        // Set dead band in command file to be negative if you
        // want to make sure you go at least as far as the
        // vision manager says to go.
        mSnobotActor.setSmoothGoalDeadband(mDeadBandDistance);
        mFinished = false;

        System.out.println("DriveToPegUsingVision: INITIALIZED");
    }

    @Override
    protected void execute()
    {
        // If the vision manager sees a target the Go To Position Smoothly
        // goal will be set.

        // As long as the vision manager sees a target it will update the
        // goal.

        if (mVisionManager.seesTarget())
        {
            List<TargetLocation> mTargetLocations = mVisionManager.getLatestTargetInformation();
            mSnobotActor.setGoToPositionSmoothlyGoal(mTargetLocations.get(0).mX, mTargetLocations.get(0).mY);
        }

        // Once the goal is set for the first time the snobot actor
        // will be in that action until it is finished whether the vision
        // manager sees the target or not.
        if (mSnobotActor.isInAction())
        {
            mFinished = mSnobotActor.executeControlMode();

        }
    }

    @Override
    protected boolean isFinished()
    {
        return mFinished;
    }

    @Override
    protected void end()
    {
        System.out.println("DriveToPegUsingVision: END");
    }
}
