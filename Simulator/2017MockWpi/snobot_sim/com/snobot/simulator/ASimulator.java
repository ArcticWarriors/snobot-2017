package com.snobot.simulator;

import java.util.ArrayList;
import java.util.List;

import com.snobot.simulator.module_wrapper.SpeedControllerWrapper;

import edu.wpi.first.wpilibj.RobotBase;

public abstract class ASimulator implements ISimulatorUpdater
{
    private static final Object sUPDATE_MUTEX = new Object();

    private static final double sMOTOR_UPDATE_FREQUENCY = .02;

    protected List<ISimulatorUpdater> mSimulatorComponenets;

    protected ASimulator()
    {
        mSimulatorComponenets = new ArrayList<>();
        updateMotorsThread.start();
    }

    protected abstract void createSimulatorComponents();

    @Override
    public void update()
    {
        synchronized (sUPDATE_MUTEX)
        {
            for (ISimulatorUpdater simulator : mSimulatorComponenets)
            {
                simulator.update();
            }
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

    protected Thread updateMotorsThread = new Thread(new Runnable()
    {

        @Override
        public void run()
        {
            while (true)
            {
                synchronized (sUPDATE_MUTEX)
                {
                    for (SpeedControllerWrapper x : SensorActuatorRegistry.get().getSpeedControllers().values())
                    {
                        x.update(sMOTOR_UPDATE_FREQUENCY);
                    }
                }

                try
                {
                    Thread.sleep((long) (sMOTOR_UPDATE_FREQUENCY * 1000));
                }
                catch (InterruptedException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }, "MotorUpdater");
}
