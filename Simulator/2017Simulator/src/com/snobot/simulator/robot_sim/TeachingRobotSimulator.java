package com.snobot.simulator.robot_sim;

import com.snobot.simulator.ASimulator;
import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.AnalogWrapper;
import com.snobot.simulator.module_wrapper.EncoderWrapper;
import com.snobot.simulator.module_wrapper.SpeedControllerWrapper;
import com.snobot.simulator.module_wrapper.TankDriveGyroSimulator;
import com.snobot.simulator.motor_sim.SimpleMotorSimulator;

public class TeachingRobotSimulator extends ASimulator
{

    public TeachingRobotSimulator()
    {
        EncoderWrapper leftEncoder = SensorActuatorRegistry.get().getEncoder(4, 5);
        SpeedControllerWrapper leftSC = SensorActuatorRegistry.get().getSpeedControllers().get(0);
        leftSC.setMotorSimulator(new SimpleMotorSimulator(70));
        leftEncoder.setSpeedController(leftSC);

        EncoderWrapper rightEncoder = SensorActuatorRegistry.get().getEncoder(1, 2);
        SpeedControllerWrapper rightSC = SensorActuatorRegistry.get().getSpeedControllers().get(1);
        rightSC.setMotorSimulator(new SimpleMotorSimulator(70));
        rightEncoder.setSpeedController(rightSC);

        AnalogWrapper gyroAnalog;
        AnalogWrapper gyroSpi;

        gyroAnalog = SensorActuatorRegistry.get().getAnalog().get(0);
        gyroSpi = SensorActuatorRegistry.get().getAnalog().get(100);

        mSimulatorComponenets.add(new TankDriveGyroSimulator(leftEncoder, rightEncoder, gyroAnalog));
        mSimulatorComponenets.add(new TankDriveGyroSimulator(rightEncoder, leftEncoder, gyroSpi));

    }
}
