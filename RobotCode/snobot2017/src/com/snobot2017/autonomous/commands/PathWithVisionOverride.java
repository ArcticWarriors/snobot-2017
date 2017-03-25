package com.snobot2017.autonomous.commands;

import com.snobot2017.SnobotActor.ISnobotActor;
import com.snobot2017.autonomous.path.DriveStraightPathWithGyro;
import com.snobot2017.drivetrain.IDriveTrain;
import com.snobot2017.vision.VisionManager;

import edu.wpi.first.wpilibj.Timer;

public class PathWithVisionOverride extends Command
{
    private DriveStraightPathWithGyro mDriveStraightPathWithGyro;
    private VisionManager mVisionManager;
    private ISnobotActor mSnobotActor;
    private IDriveTrain mDrivetrain;
    private Timer mActingTimer;
    private boolean mOverridingPath;
    private boolean mFinished;
    
    public PathWithVisionOverride(Snobot2017 aSnobot, String aFilepath)
    {
        
    }

}
