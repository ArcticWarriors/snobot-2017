package com.snobot.simulator.motor_sim;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.snobot.simulator.motor_sim.motors.MakeTransmission;
import com.snobot.simulator.motor_sim.motors.PublishedMotorFactory;

//https://github.com/Team254/Sim-FRC-2015/blob/master/test/com/team254/frc2015/sim/TestDCMotor.java
public class DcMotorModelTest
{

    private final double EPS = 1E-9;

    @Test
    public void testGettersSetters()
    {
        // Make a new motor.
        DcMotorModel motor = new DcMotorModel(0, 0, 0, 0, 0, 0);

        // Check initial conditions.
        assertEquals(motor.getPosition(), 0.0, EPS);
        assertEquals(motor.getVelocity(), 0.0, EPS);
        assertEquals(motor.getCurrent(), 0.0, EPS);

        // Reset initial conditions.
        motor.reset(1.0, 2.0, 3.0);
        assertEquals(motor.getPosition(), 1.0, EPS);
        assertEquals(motor.getVelocity(), 2.0, EPS);
        assertEquals(motor.getCurrent(), 3.0, EPS);
    }

    @Test
    public void testRS775_6VSmallLoad()
    {
        DcMotorModel rs775 = MakeTransmission.makeTransmission(PublishedMotorFactory.makeRS775(), 1, 10.0, 1.0);

        // Apply a positive voltage and small load.
        System.out.println("Voltage=6V, Load=.01 kg*m^2");
        for (int i = 0; i < 1000; ++i)
        {
            rs775.step(6.0, 0.01, 0.0, 0.01);

            if (i % 100 == 0)
            {
                System.out.print("Time: " + 0.01 * i);
                System.out.print(", Position: " + rs775.getPosition());
                System.out.print(", Velocity: " + rs775.getVelocity());
                System.out.println(", Current: " + rs775.getCurrent());
            }
        }
        // We expect negligible final current, and a final velocity of ~68.04
        // rad/sec.
        assertEquals(rs775.getCurrent(), 0.0, 1E-3);
        assertEquals(rs775.getVelocity(), 68.06, 1E-2);

    }

    @Test
    public void testRS775_12VSmallLoad()
    {
        DcMotorModel rs775 = MakeTransmission.makeTransmission(PublishedMotorFactory.makeRS775(), 1, 10.0, 1.0);

        // Apply a larger voltage.
        System.out.println("Voltage=12V, Load=.01 kg*m^2");
        for (int i = 0; i < 1000; ++i)
        {
            rs775.step(12.0, 0.01, 0.0, 0.01);

            if (i % 100 == 0)
            {
                System.out.print("Time: " + 0.01 * i);
                System.out.print(", Position: " + rs775.getPosition());
                System.out.print(", Velocity: " + rs775.getVelocity());
                System.out.println(", Current: " + rs775.getCurrent());
            }
        }

        // We expect negligible final current, and a final velocity of ~2 *
        // 68.04 rad/sec.
        assertEquals(rs775.getCurrent(), 0.0, 1E-3);
        assertEquals(rs775.getVelocity(), 68.04 * 2, 1E-1);

    }

    @Test
    public void testRS775_12VLargeLoad()
    {
        DcMotorModel rs775 = MakeTransmission.makeTransmission(PublishedMotorFactory.makeRS775(), 1, 10.0, 1.0);

        System.out.println("Voltage=12V, Load=1.0 kg*m^2");
        for (int i = 0; i < 1000; ++i)
        {
            rs775.step(12.0, 1.0, 0.0, 0.01);

            if (i % 100 == 0)
            {
                System.out.print("Time: " + 0.01 * i);
                System.out.print(", Position: " + rs775.getPosition());
                System.out.print(", Velocity: " + rs775.getVelocity());
                System.out.println(", Current: " + rs775.getCurrent());
            }
        }

        // This is slower, so 1000 iterations isn't enough to get to steady
        // state
        assertEquals(rs775.getCurrent(), 48.758, 1E-3);
        assertEquals(rs775.getVelocity(), 59.59, 1E-1);
    }
    @Test
    public void testDoubleRS775_100Efficiency_12VLargeLoad()
    {
        DcMotorModel rs775 = MakeTransmission.makeTransmission(PublishedMotorFactory.makeRS775(), 2, 10.0, 1.0);

        System.out.println("(2 motors) Voltage=12V, Load=1.0 kg*m^2");
        for (int i = 0; i < 1000; ++i)
        {
            rs775.step(12.0, 1.0, 0.0, 0.01);

            if (i % 100 == 0)
            {
                System.out.print("Time: " + 0.01 * i);
                System.out.print(", Position: " + rs775.getPosition());
                System.out.print(", Velocity: " + rs775.getVelocity());
                System.out.println(", Current: " + rs775.getCurrent());
            }
        }

        // We expect the two motor version to move faster than the single motor
        // version.
        assertEquals(rs775.getCurrent(), 17.378, 1E-3);
        assertEquals(rs775.getVelocity(), 122.517, 1E-1);
    }
    @Test
    public void testDoubleRS775_80Efficiency_12VLargeLoad()
    {
        DcMotorModel rs775 = MakeTransmission.makeTransmission(PublishedMotorFactory.makeRS775(), 2, 10.0, 0.8);

        System.out.println("(2 motors, 80% efficient) Voltage=12V, Load=1.0 kg*m^2");
        for (int i = 0; i < 1000; ++i)
        {
            rs775.step(12.0, 1.0, 0.0, 0.01);

            if (i % 100 == 0)
            {
                System.out.print("Time: " + 0.01 * i);
                System.out.print(", Position: " + rs775.getPosition());
                System.out.print(", Velocity: " + rs775.getVelocity());
                System.out.println(", Current: " + rs775.getCurrent());
            }
        }

        assertEquals(rs775.getCurrent(), 27.540, 1E-3);
        assertEquals(rs775.getVelocity(), 114.545, 1E-1);
        // We expect the less efficient version to be slower.
        // assert (rs775.getVelocity() + EPS < final_velocity);
        // assert (rs775.getPosition() + EPS < final_position);
    }

    @Test
    public void testRS775_Neg12VSmallLoad()
    {
        DcMotorModel rs775 = MakeTransmission.makeTransmission(PublishedMotorFactory.makeRS775(), 1, 10.0, 1.0);

        // Go in reverse.
        System.out.println("Voltage=-12V, Load=1.0 kg*m^2");
        for (int i = 0; i < 1000; ++i)
        {
            rs775.step(-12.0, 1.0, 0.0, 0.01);

            if (i % 100 == 0)
            {
                System.out.print("Time: " + 0.01 * i);
                System.out.print(", Position: " + rs775.getPosition());
                System.out.print(", Velocity: " + rs775.getVelocity());
                System.out.println(", Current: " + rs775.getCurrent());
            }
        }
        assertEquals(rs775.getCurrent(), 48.758, 1E-3);
        assertEquals(rs775.getVelocity(), -59.590, 1E-1);

    }

    @Test
    public void testGravity()
    {
        DcMotorModel rs775 = MakeTransmission.makeTransmission(PublishedMotorFactory.makeRS775(), 1, 10.0, 1.0);
        System.out.println("Voltage=12V, Load=0.4 kg*m^2, .2m pulley against gravity");
        for (int i = 0; i < 1000; ++i)
        {
            // Assume pulling against gravity.
            // Load is a 1kg mass on .2m pulley.
            rs775.step(12.0, 0.04, -9.8 / .2, 0.01);

            if (i % 100 == 0)
            {
                System.out.print("Time: " + 0.01 * i);
                System.out.print(", Position: " + rs775.getPosition());
                System.out.print(", Velocity: " + rs775.getVelocity());
                System.out.println(", Current: " + rs775.getCurrent());
            }
        }

        System.out.println("Voltage=-12V, Load=0.4 kg*m^2, .2m pulley with gravity");
        rs775.reset(0, 0, 0);
        for (int i = 0; i < 1000; ++i)
        {
            // Assume pulling with gravity.
            // Load is a 1kg mass on .2m pulley.
            rs775.step(-12.0, 0.04, -9.8 / .2, 0.01);

            if (i % 100 == 0)
            {
                System.out.print("Time: " + 0.01 * i);
                System.out.print(", Position: " + rs775.getPosition());
                System.out.print(", Velocity: " + rs775.getVelocity());
                System.out.println(", Current: " + rs775.getCurrent());
            }
        }
    }
}
