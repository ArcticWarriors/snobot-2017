package com.snobot.simulator.robot_sim;

import com.snobot.simulator.ASimulator;
import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.EncoderWrapper;
import com.snobot.simulator.module_wrapper.SpeedControllerWrapper;
import com.snobot.simulator.motor_sim.DcMotorModel;
import com.snobot.simulator.motor_sim.StaticLoadDcMotorSim;
import com.snobot.simulator.motor_sim.motors.MakeTransmission;
import com.snobot.simulator.motor_sim.motors.VexMotorFactory;
import com.snobot2017.PortMappings2017;

public class Snobot2017Simulator extends ASimulator
{

    public Snobot2017Simulator()
    {
        double load = .1;
        // double gearReduction = 1;
        double gearReduction = 10;

        EncoderWrapper leftEncoder = SensorActuatorRegistry.get().getEncoder(PortMappings2017.sLEFT_DRIVE_ENCODER_PORT_A,
                PortMappings2017.sLEFT_DRIVE_ENCODER_PORT_B);
        SpeedControllerWrapper leftSC = SensorActuatorRegistry.get().getSpeedControllers().get(PortMappings2017.sDRIVE_PWM_LEFT_A_PORT);
        DcMotorModel leftMotor = VexMotorFactory.makeCIMMotor();
        leftMotor = MakeTransmission.makeTransmission(leftMotor, 2, gearReduction, 1.0);
        leftSC.setMotorSimulator(new StaticLoadDcMotorSim(leftMotor, load));
        // leftSC.setMotorSimulator(new SimpleMotorSimulator(70));
        leftEncoder.setSpeedController(leftSC);

        EncoderWrapper rightEncoder = SensorActuatorRegistry.get().getEncoder(PortMappings2017.sRIGHT_DRIVE_ENCODER_PORT_A,
                PortMappings2017.sRIGHT_DRIVE_ENCODER_PORT_B);
        SpeedControllerWrapper rightSC = SensorActuatorRegistry.get().getSpeedControllers().get(PortMappings2017.sDRIVE_PWM_RIGHT_A_PORT);
        DcMotorModel rightMotor = VexMotorFactory.makeCIMMotor();
        rightMotor = MakeTransmission.makeTransmission(rightMotor, 2, gearReduction, 1.0);
        rightSC.setMotorSimulator(new StaticLoadDcMotorSim(rightMotor, load));
        // rightSC.setMotorSimulator(new SimpleMotorSimulator(70));
        rightEncoder.setSpeedController(rightSC);
    }
}
