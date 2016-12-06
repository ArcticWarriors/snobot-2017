package com.snobot.simulator.robot_sim;

import com.snobot.simulator.ASimulator;
import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.EncoderWrapper;
import com.snobot.simulator.module_wrapper.SpeedControllerWrapper;

public class TeachingRobotSimulator extends ASimulator
{

    public TeachingRobotSimulator()
    {
        EncoderWrapper enc = SensorActuatorRegistry.get().getEncoder(4, 5);
        SpeedControllerWrapper sc = SensorActuatorRegistry.get().getSpeedControllers().get(0);
        
        sc.setMotorParameters(70);
        enc.setSpeedController(sc);
        
    }
}
