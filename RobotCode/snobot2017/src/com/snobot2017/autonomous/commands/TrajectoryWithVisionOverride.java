package com.snobot2017.autonomous.commands;

import java.util.List;

import com.snobot2017.Properties2017;
import com.snobot2017.Snobot2017;
import com.snobot2017.SnobotActor.ISnobotActor;
import com.snobot2017.autonomous.trajectory.TrajectoryPathCommand;
import com.snobot2017.vision.TargetLocation;
import com.snobot2017.vision.VisionManager;
import com.team254.lib.trajectory.Path;
import com.team254.lib.trajectory.io.TextFileDeserializer;

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

    public TrajectoryWithVisionOverride(Snobot2017 aSnobot, String aFile)
    {
        String pathFile = Properties2017.sAUTON_PATH_DIRECTORY.getValue() + "/" + aFile.trim();
        TextFileDeserializer deserializer = new TextFileDeserializer();
        Path p = deserializer.deserializeFromFile(pathFile);

        mTrajectoryCommand = new TrajectoryPathCommand(aSnobot.getDriveTrain(), aSnobot.getPositioner(), p);
        mVisionManager = aSnobot.getVisionManager();
        mSnobotActor = aSnobot.getSnobotActor();
        mOverridingTrajectory = false;
        mFinished = false;
        mActingTimer = new Timer();
    }

    @Override
    protected void execute()
    {
        if (mOverridingTrajectory)
        {
            mFinished = mSnobotActor.executeControlMode();
            if(mActingTimer.get() > 4)
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

            if (percentComplete > Properties2017.sTRAJECTORY_OVERRIDE_PERCENTAGE.getValue() && mVisionManager.seesTarget())
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
