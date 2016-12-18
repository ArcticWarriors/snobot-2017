package com.snobot.simulator.motor_sim;

public interface IMotorSimulator
{

    void setAppliedVoltage(double speed);

    double getAppliedVoltage();

    double getVelocity();

    double getPosition();

    void reset();

    void update(double cycleTime);

}
