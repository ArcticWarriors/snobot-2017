package com.snobot.simulator;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.RobotBase;

public abstract class ASimulator implements ISimulatorUpdater
{
    protected List<ISimulatorUpdater> mSimulatorComponenets;

    protected ASimulator()
    {
        mSimulatorComponenets = new ArrayList<>();
    }

    protected abstract void createSimulatorComponents();

    @Override
    public void update()
    {
        for (ISimulatorUpdater simulator : mSimulatorComponenets)
        {
            simulator.update();
        }
    }

    @Override
    public void setRobot(RobotBase aRobot)
    {
        for (ISimulatorUpdater simulator : mSimulatorComponenets)
        {
            simulator.setRobot(aRobot);
        }
    }
}
