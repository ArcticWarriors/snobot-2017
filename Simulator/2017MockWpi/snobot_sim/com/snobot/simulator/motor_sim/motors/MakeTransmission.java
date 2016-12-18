package com.snobot.simulator.motor_sim.motors;

import com.snobot.simulator.motor_sim.DcMotorModel;

public class MakeTransmission
{
    public static DcMotorModel makeTransmission(
            DcMotorModel motor, 
            int num_motors,
            double gear_reduction, 
            double efficiency) 
    {
        return new DcMotorModel(
                num_motors * gear_reduction * efficiency * motor.mKT, motor.mKV / gear_reduction, 
                motor.mResistance / num_motors,
                motor.mMotorInertia * num_motors * gear_reduction * gear_reduction);
    }
}
