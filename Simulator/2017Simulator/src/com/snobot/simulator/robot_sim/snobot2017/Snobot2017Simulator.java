package com.snobot.simulator.robot_sim.snobot2017;

import com.snobot.simulator.ASimulator;
import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.module_wrapper.AnalogWrapper;
import com.snobot.simulator.module_wrapper.EncoderWrapper;
import com.snobot.simulator.module_wrapper.SpeedControllerWrapper;
import com.snobot.simulator.module_wrapper.TankDriveGyroSimulator;
import com.snobot.simulator.motor_sim.DcMotorModel;
import com.snobot.simulator.motor_sim.StaticLoadDcMotorSim;
import com.snobot.simulator.motor_sim.motors.MakeTransmission;
import com.snobot.simulator.motor_sim.motors.VexMotorFactory;
import com.snobot2017.PortMappings2017;

public class Snobot2017Simulator extends ASimulator
{
    private static final boolean sSIMULATE_CAMERA = false;

    public Snobot2017Simulator()
    {

    }

    @Override
    protected void createSimulatorComponents()
    {
        double drivetrainLoad = .6;
        // double gearReduction = 1;
        double drivetrainReduction = 11.43;
        double drivetrainSpeedLossFactor = .81;
        double drivetrainWheelDiameter = 6;

        EncoderWrapper leftEncoder = SensorActuatorRegistry.get().getEncoder(PortMappings2017.sLEFT_DRIVE_ENCODER_PORT_A,
                PortMappings2017.sLEFT_DRIVE_ENCODER_PORT_B);
        SpeedControllerWrapper leftSC = SensorActuatorRegistry.get().getSpeedControllers().get(PortMappings2017.sDRIVE_PWM_LEFT_A_PORT);
        DcMotorModel leftMotor = VexMotorFactory.makeCIMMotor();
        leftMotor = MakeTransmission.makeTransmission(leftMotor, 2, drivetrainReduction, 1.0);
        leftMotor.setInverted(false);
        leftSC.setMotorSimulator(new StaticLoadDcMotorSim(leftMotor, drivetrainLoad, drivetrainWheelDiameter / 2 * drivetrainSpeedLossFactor));
        leftEncoder.setSpeedController(leftSC);

        EncoderWrapper rightEncoder = SensorActuatorRegistry.get().getEncoder(PortMappings2017.sRIGHT_DRIVE_ENCODER_PORT_A,
                PortMappings2017.sRIGHT_DRIVE_ENCODER_PORT_B);
        SpeedControllerWrapper rightSC = SensorActuatorRegistry.get().getSpeedControllers().get(PortMappings2017.sDRIVE_PWM_RIGHT_A_PORT);
        DcMotorModel rightMotor = VexMotorFactory.makeCIMMotor();
        rightMotor = MakeTransmission.makeTransmission(rightMotor, 2, drivetrainReduction, 1.0);
        rightMotor.setInverted(true);
        rightSC.setMotorSimulator(new StaticLoadDcMotorSim(rightMotor, drivetrainLoad, drivetrainWheelDiameter / 2 * drivetrainSpeedLossFactor));
        rightEncoder.setSpeedController(rightSC);
        
        AnalogWrapper gyroWrapper = SensorActuatorRegistry.get().getAnalog().get(100);
        TankDriveGyroSimulator drivetrainSim = new TankDriveGyroSimulator(leftEncoder, rightEncoder, gyroWrapper);
        drivetrainSim.setTurnKp(400 / 12.0);
        mSimulatorComponenets.add(drivetrainSim);
        
        if (sSIMULATE_CAMERA)
        {
            CameraSimulator cameraSimulator = new CameraSimulator();
            mSimulatorComponenets.add(cameraSimulator);
        }
    }
}
