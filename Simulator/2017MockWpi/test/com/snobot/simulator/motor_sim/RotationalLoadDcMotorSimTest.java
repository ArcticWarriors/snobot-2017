package com.snobot.simulator.motor_sim;

import org.junit.Test;

import com.snobot.simulator.module_wrapper.SpeedControllerWrapper;
import com.snobot.simulator.motor_sim.motors.MakeTransmission;
import com.snobot.simulator.motor_sim.motors.PublishedMotorFactory;

public class RotationalLoadDcMotorSimTest
{
    private DcMotorModel getSingle775WithTransmission(int numMotors, double effiecency)
    {
        return MakeTransmission.makeTransmission(PublishedMotorFactory.makeRS775(), numMotors, 10.0, effiecency);
    }

    @Test
    public void testMotor()
    {
        int motors = 1;
        double efficiency = .1;
        double load = 109;
        double centerOfMass = 0;

        SpeedControllerWrapper wrapper = new SpeedControllerWrapper(0);
        IMotorSimulator motorSim = new RotationalLoadDcMotorSim(getSingle775WithTransmission(motors, efficiency), wrapper, load, centerOfMass);
        wrapper.setMotorSimulator(motorSim);

        for (int i = 0; i < 10; ++i)
        {
            wrapper.set(.5);

            if (i % 1 == 0)
            {
                System.out.print("Time: " + 0.01 * i);
                System.out.print(", Position: " + wrapper.getPosition());
                System.out.print(", Velocity: " + wrapper.getVelocity());
                // System.out.println(", Current: " + rs775.getCurrent());
                System.out.println();
            }
        }

        System.out.println(motorSim);
    }

}
