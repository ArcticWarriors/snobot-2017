package com.visionTest.auto_turret;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class StateMangerTest_DelayedUpdates
{


    private static double sEPSILON = .001;

    private TestableStateManager mStateManager;

    public StateMangerTest_DelayedUpdates()
    {
        mStateManager = new TestableStateManager();
    }

    @Rule
    public TestName name = new TestName();

    @Before
    public void temp()
    {
        System.out.println("\n\nStarting Test:" + name.getMethodName());
        System.out.println("------------------------------------------------------------");
    }

    @Test
    /**
    */
    public void delayedAngleCalculation0()
    {
        double timestamp0 = 0;
        double timestamp1 = 1;

        mStateManager.saveRobotState(timestamp0, -10, 0, 0);
        mStateManager.setCurrentCameraState(timestamp0, 45, 14.1421356237);
        Assert.assertEquals(0.0, mStateManager.getTargetLocation().mX, sEPSILON);
        Assert.assertEquals(10.00, mStateManager.getTargetLocation().mY, sEPSILON);

        // No state update
        mStateManager.saveRobotState(timestamp1, 10, 0, 0);
        Assert.assertEquals(0.0, mStateManager.getTargetLocation().mX, sEPSILON);
        Assert.assertEquals(10.00, mStateManager.getTargetLocation().mY, sEPSILON);
    }

    @Test
    /**
    */
    public void delayedAngleCalculation1()
    {
        double timestamp0 = 0;
        double timestamp1 = 1;
        double timestamp2 = 2;
        double timestamp3 = 3;

        mStateManager.saveRobotState(timestamp0, 0, 0, 0);
        mStateManager.setCurrentCameraState(timestamp0, 0, 100);
        Assert.assertEquals(0.0, mStateManager.getTargetLocation().mX, sEPSILON);
        Assert.assertEquals(100.0, mStateManager.getTargetLocation().mY, sEPSILON);

        // No camera update
        mStateManager.saveRobotState(timestamp1, 0, 0, 15);
        Assert.assertEquals(0.000, mStateManager.getTargetLocation().mX, sEPSILON);
        Assert.assertEquals(100.0, mStateManager.getTargetLocation().mY, sEPSILON);

        mStateManager.saveRobotState(timestamp2, 0, 0, 45);
        Assert.assertEquals(0.000, mStateManager.getTargetLocation().mX, sEPSILON);
        Assert.assertEquals(100.0, mStateManager.getTargetLocation().mY, sEPSILON);

        mStateManager.saveRobotState(timestamp3, 0, 0, 90);
        Assert.assertEquals(0.000, mStateManager.getTargetLocation().mX, sEPSILON);
        Assert.assertEquals(100.0, mStateManager.getTargetLocation().mY, sEPSILON);
    }

    @Test
    public void delayedAngleCalculation2()
    {
        double timestamp0 = 0;
        double timestamp1 = 1;
        double timestamp2 = 2;

        mStateManager.saveRobotState(timestamp0, 0, 0, 0);
        mStateManager.saveRobotState(timestamp1, 0, 0, 15);
        mStateManager.saveRobotState(timestamp2, 0, 0, 45);

        mStateManager.setCurrentCameraState(timestamp0, 45, 100);
        Assert.assertEquals(70.71, mStateManager.getTargetLocation().mX, sEPSILON);
        Assert.assertEquals(70.71, mStateManager.getTargetLocation().mY, sEPSILON);

        mStateManager.setCurrentCameraState(timestamp1, 45, 100);
        Assert.assertEquals(86.602, mStateManager.getTargetLocation().mX, sEPSILON);
        Assert.assertEquals(50.000, mStateManager.getTargetLocation().mY, sEPSILON);

        mStateManager.setCurrentCameraState(timestamp2, 45, 100);
        Assert.assertEquals(100.0, mStateManager.getTargetLocation().mX, sEPSILON);
        Assert.assertEquals(0.000, mStateManager.getTargetLocation().mY, sEPSILON);
    }

    @Test
    public void delayedAngleCalculation3()
    {

        for (int i = 0; i < 10; ++i)
        {
            mStateManager.saveRobotState(i, 0, 0, i * 5);
        }

        mStateManager.setCurrentCameraState(1, 45, 100);

        Assert.assertEquals(76.604, mStateManager.getTargetLocation().mX, sEPSILON);
        Assert.assertEquals(64.278, mStateManager.getTargetLocation().mY, sEPSILON);

        System.out.println(mStateManager.getTargetLocation());
    }
}
