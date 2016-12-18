package com.snobot.simulator.motor_sim.motors;

import com.snobot.simulator.motor_sim.DcMotorModel;

public class MotorFactory
{
    public static DcMotorModel makeRS775()
    {
        final double KT = 0.009; // 9 mNm / A
        final double KV = 1083.0 * (Math.PI * 2.0) / 60.0; // 1083 rpm/V in
                                                           // rad/sec/V
        final double RESISTANCE = (18.0 / 130.0); // Rated for 130A stall @ 18V
        final double INERTIA = 1.20348237e-5; // 127g cylinder @ 1.084" diameter
        return new DcMotorModel(KT, KV, RESISTANCE, INERTIA);
    }

    public static DcMotorModel makeRS550()
    {
        final double KT = 0.004862;
        final double KV = 1608.0 * (Math.PI * 2.0) / 60.0;
        final double RESISTANCE = (12.0 / 85.0);
        final double INERTIA = 0; // TODO(jared): Measure this
        return new DcMotorModel(KT, KV, RESISTANCE, INERTIA);
    }
}
