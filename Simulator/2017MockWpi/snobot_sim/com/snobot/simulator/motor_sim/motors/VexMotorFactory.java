package com.snobot.simulator.motor_sim.motors;

import com.snobot.simulator.motor_sim.DcMotorModel;

public class VexMotorFactory
{
    private final static double NOMINAL_VOLTAGE = 12;

    public static DcMotorModel makeRS775()
    {
        final double FREE_SPEED_RPM = 13050;
        final double FREE_CURRENT = 2.7;
        final double STALL_TORQUE = .72;
        final double STALL_CURRENT = 97;

        return new DcMotorModel(NOMINAL_VOLTAGE, FREE_SPEED_RPM, FREE_CURRENT, STALL_TORQUE, STALL_CURRENT, 0);
    }

    public static DcMotorModel makeCIM()
    {
        final double FREE_SPEED_RPM = 5330;
        final double FREE_CURRENT = 2.7;
        final double STALL_TORQUE = 2.41;
        final double STALL_CURRENT = 131;

        return new DcMotorModel(NOMINAL_VOLTAGE, FREE_SPEED_RPM, FREE_CURRENT, STALL_TORQUE, STALL_CURRENT, 0);
    }
}
