package com.snobot2017;

import com.snobot2017.SnobotActor.ISnobotActor;
import com.snobot2017.drivetrain.IDriveTrain;
import com.snobot2017.gearboss.IGearBoss;
import com.snobot2017.positioner.IPositioner;
import com.snobot2017.vision.VisionManager;

public interface ISnobot2017
{
    /**
     * Returns the class that controls the robots drivetrain
     * 
     * @return The robots drive train
     */
    public IDriveTrain getDriveTrain();

    /**
     * Returns the IGearBoss for the robot
     * 
     * @return IGearBoss
     */
    public IGearBoss getGearBoss();

    /**
     * Returns the Robots position
     * 
     * @return IPositioner
     */
    public IPositioner getPositioner();

    /**
     * Returns the SnobotActor
     * 
     * @return ISnobotActor
     */
    public ISnobotActor getSnobotActor();

    public VisionManager getVisionManager();
}
