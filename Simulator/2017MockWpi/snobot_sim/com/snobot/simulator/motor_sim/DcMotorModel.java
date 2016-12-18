package com.snobot.simulator.motor_sim;

//https://github.com/Team254/Sim-FRC-2015/blob/master/src/com/team254/frc2015/sim/DCMotor.java
public class DcMotorModel
{

    // Motor constants
    public final double mKT;
    public final double mKV;
    public final double mResistance;
    public final double mMotorInertia;

    // Current motor state
    protected double mPosition;
    protected double mVelocity;
    protected double mCurrent;

    /**
     * Simulate a simple DC motor.
     * 
     * @param kt
     *            Torque constant (N*m / amp)
     * @param kv
     *            Voltage constant (rad/sec / V)
     * @param resistance
     *            (ohms)
     * @param inertia
     *            (kg*m^2)
     */
    public DcMotorModel(double kt, double kv, double resistance, double inertia)
    {
        mKT = kt;
        mKV = kv;
        mResistance = resistance;
        mMotorInertia = inertia;
    }

    /**
     * Simulate a simple DC motor.
     * 
     * @param kt
     *            Torque constant (N*m / amp)
     * @param kv
     *            Voltage constant (rad/sec / V)
     * @param resistance
     *            (ohms)
     */
    public DcMotorModel(double kt, double kv, double resistance)
    {
        this(kt, kv, resistance, 0);
    }

    /**
     * Reset the motor to a specified state.
     * 
     * @param position
     * @param velocity
     * @param current
     */
    public void reset(double position, double velocity, double current)
    {
        mPosition = position;
        mVelocity = velocity;
        mCurrent = current;
    }

    /**
     * Simulate applying a given voltage and load for a specified period of
     * time.
     * 
     * @param load
     *            Load applied to the motor (kg*m^2)
     * @param acceleration
     *            The external torque applied (ex. due to gravity) (N*m)
     * @param timestep
     *            How long the input is applied (s)
     */
    protected void step(double applied_voltage, double load, double external_torque, double timestep)
    {
        /*
         * Using the 971-style first order system model. V = I * R + Kv * w
         * torque = Kt * I
         * 
         * V = torque / Kt * R + Kv * w torque = J * dw/dt + external_torque
         * 
         * dw/dt = (V - Kv * w) * Kt / (R * J) - external_torque / J
         */
        load += mMotorInertia;
        double acceleration = (applied_voltage - mVelocity / mKV) * mKT / (mResistance * load) + external_torque / load;
        mVelocity += acceleration * timestep;
        mPosition += mVelocity * timestep + .5 * acceleration * timestep * timestep;
        mCurrent = load * acceleration * Math.signum(applied_voltage) / mKT;
    }

    public double getPosition()
    {
        return mPosition;
    }

    public double getVelocity()
    {
        return mVelocity;
    }

    public double getCurrent()
    {
        return mCurrent;
    }

}
