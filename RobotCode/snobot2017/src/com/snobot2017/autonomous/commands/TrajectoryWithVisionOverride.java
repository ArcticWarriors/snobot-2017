package com.snobot2017.autonomous.commands;

import java.util.List;

import com.snobot2017.Snobot2017;
import com.snobot2017.SnobotActor.ISnobotActor;
import com.snobot2017.autonomous.trajectory.TrajectoryPathCommand;
import com.snobot2017.vision.TargetLocation;
import com.snobot2017.vision.VisionManager;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class TrajectoryWithVisionOverride extends Command
{
    private TrajectoryPathCommand mTrajectoryCommand;
    private VisionManager mVisionManager;
    private ISnobotActor mSnobotActor;
    private Timer mActingTimer;
    private boolean mOverridingTrajectory;
    private boolean mFinished;
    private double mOverridePercenteage;

    public TrajectoryWithVisionOverride(Snobot2017 aSnobot, TrajectoryPathCommand aTrajectoryCommand, double aOverridePercentage)
    {
        mTrajectoryCommand = aTrajectoryCommand;
        mVisionManager = aSnobot.getVisionManager();
        mSnobotActor = aSnobot.getSnobotActor();
        mOverridePercenteage = aOverridePercentage;
        mOverridingTrajectory = false;
        mFinished = false;
        mActingTimer = new Timer();
    }

    @Override
    protected void execute()
    {
        if (mOverridingTrajectory)
        {
            if (mActingTimer.get() < 3)
            {
                mFinished = mSnobotActor.executeControlMode();
            }
            else
            {
                mFinished = true;
            }
        }
        else
        {
            System.out.println("Doing trajectory");
            mTrajectoryCommand.execute();
            mFinished = mTrajectoryCommand.isFinished();

            double percentComplete = mTrajectoryCommand.getPercentComplete();

            if (percentComplete > mOverridePercenteage && mVisionManager.seesTarget())
            {
                List<TargetLocation> targets = mVisionManager.getLatestTargetInformation();
                TargetLocation target = targets.get(0);
                mSnobotActor.setGoToPositionSmoothlyGoal(target.mX, target.mY);
                mOverridingTrajectory = true;
                mActingTimer.start();
            }
        }
    }

    @Override
    protected boolean isFinished()
    {
        return mFinished;
    }
}
