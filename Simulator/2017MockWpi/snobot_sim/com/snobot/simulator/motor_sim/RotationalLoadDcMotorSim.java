package com.snobot.simulator.motor_sim;

import com.snobot.simulator.module_wrapper.SpeedControllerWrapper;

public class RotationalLoadDcMotorSim extends BaseDcMotorSimulator
{

    protected final SpeedControllerWrapper mSpeedController;
    protected final double mLoad;
    protected final double mCenterOfMass;

    public RotationalLoadDcMotorSim(
            DcMotorModel aModel,
            SpeedControllerWrapper aSpeedController,
            double aLoad,
            double aCenterOfMass)
    {
        super(aModel);

        mSpeedController = aSpeedController;
        mLoad = aLoad;
        mCenterOfMass = aCenterOfMass;
    }


    @Override
    public void update(double cycleTime)
    {
        double dynamicLoad = mLoad * Math.sin(mSpeedController.getPosition());
        System.out.println(dynamicLoad);

        mMotorModel.step(mVoltagePercentage * 12, dynamicLoad, 0, cycleTime);
    }
}
