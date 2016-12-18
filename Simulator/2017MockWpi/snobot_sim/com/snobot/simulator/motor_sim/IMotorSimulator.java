package com.snobot.simulator.motor_sim;

public interface IMotorSimulator
{

    void setVoltagePercentage(double speed);

    double getVoltagePercentage();

    double getVelocity();

    double getPosition();

    void reset();

    void update(double cycleTime);

}
