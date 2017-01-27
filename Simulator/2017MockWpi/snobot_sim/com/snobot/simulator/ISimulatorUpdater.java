package com.snobot.simulator;

import edu.wpi.first.wpilibj.RobotBase;

public interface ISimulatorUpdater
{

    public abstract void update();

    public abstract void setRobot(RobotBase mRobot);
}
