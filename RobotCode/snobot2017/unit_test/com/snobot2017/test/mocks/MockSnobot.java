package com.snobot2017.test.mocks;

import com.snobot2017.ISnobot2017;
import com.snobot2017.SnobotActor.ISnobotActor;
import com.snobot2017.drivetrain.IDriveTrain;
import com.snobot2017.gearboss.IGearBoss;
import com.snobot2017.positioner.IPositioner;
import com.snobot2017.vision.VisionManager;

public class MockSnobot implements ISnobot2017
{

    @Override
    public IDriveTrain getDriveTrain()
    {
        return null;
    }

    @Override
    public IGearBoss getGearBoss()
    {
        return null;
    }

    @Override
    public IPositioner getPositioner()
    {
        return null;
    }

    @Override
    public ISnobotActor getSnobotActor()
    {
        return null;
    }

    @Override
    public VisionManager getVisionManager()
    {
        return null;
    }

}
