package com.snobot.simulator.motor_sim;

public interface IMotorSimulator
{

    void setVoltagePercentage(double speed);

    double getVoltagePercentage();

    double getAcceleration();

    double getVelocity();

    double getPosition();

    void reset();

    void update(double cycleTime);

    public class NullMotorSimulator implements IMotorSimulator
    {
        @Override
        public void setVoltagePercentage(double speed)
        {
        }

        @Override
        public double getVoltagePercentage()
        {
            return 0;
        }

        @Override
        public double getAcceleration()
        {
            return 0;
        }

        @Override
        public double getVelocity()
        {
            return 0;
        }

        @Override
        public double getPosition()
        {
            return 0;
        }

        @Override
        public void reset()
        {
        }

        @Override
        public void update(double cycleTime)
        {
        }
    }
}
