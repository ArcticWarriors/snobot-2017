package com.visionTest.auto_turret;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

public class StateMangerTest_SimpleAngles
{
    private static double sEPSILON = .001;

    private TestableStateManager mStateManager;

    public StateMangerTest_SimpleAngles()
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
    public void testTargetStraightAhead()
    {
        double timestamp = 0;

        mStateManager.saveRobotState(timestamp, 0, 0, 0);
        mStateManager.setCurrentCameraState(timestamp, 0, 27);

        Assert.assertEquals(0, mStateManager.getTargetLocation().mX, sEPSILON);
        Assert.assertEquals(27, mStateManager.getTargetLocation().mY, sEPSILON);
        Assert.assertEquals(0, mStateManager.calculateDesiredAngleFromState(), sEPSILON);
    }

    @Test
    public void testRobotToLeftOfTarget()
    {
        double timestamp = 0;

        mStateManager.saveRobotState(timestamp, -13.5, 0, 0);
        mStateManager.setCurrentCameraState(timestamp, 26.565, 30.186);

        Assert.assertEquals(0, mStateManager.getTargetLocation().mX, sEPSILON);
        Assert.assertEquals(27, mStateManager.getTargetLocation().mY, sEPSILON);
        Assert.assertEquals(26.565, mStateManager.calculateDesiredAngleFromState(), sEPSILON);
    }

    @Test
    public void testRobotToRightOfTarget()
    {
        double timestamp = 0;

        mStateManager.saveRobotState(timestamp, 13.5, 0, 0);
        mStateManager.setCurrentCameraState(timestamp, -26.565, 30.186);

        Assert.assertEquals(0, mStateManager.getTargetLocation().mX, sEPSILON);
        Assert.assertEquals(27, mStateManager.getTargetLocation().mY, sEPSILON);
        Assert.assertEquals(-26.565, mStateManager.calculateDesiredAngleFromState(), sEPSILON);
    }

    @Test
    /**
     * - Robot to the left of the y-axis, pointing straight ahead. - Camera sees
     * the target at 45 degrees, 14.14 feet away ([10, 10] away, at [0, 10]) -
     * Camera update happens "immediately"
     * 
     * Expect the desired angle to match the cameras offset
     * 
     */
    public void testRobotAngle0_TurretAngle0_Calculation0()
    {
        double timestamp = 0;

        mStateManager.saveRobotState(timestamp, -10, 0, 0);
        mStateManager.setCurrentCameraState(timestamp, 45, 14.1421356237);

        Assert.assertEquals(0, mStateManager.getTargetLocation().mX, sEPSILON);
        Assert.assertEquals(10, mStateManager.getTargetLocation().mY, sEPSILON);
        Assert.assertEquals(45, mStateManager.calculateDesiredAngleFromState(), sEPSILON);
    }

    @Test
    /**
     * - Robot to the right of the y-axis, pointing straight ahead. - Camera
     * sees the target at -45 degrees, 14.14 feet away ([-10, 10] away, at [0,
     * 10])) - Camera update happens "immediately"
     * 
     * Expect the desired angle to match the cameras offset
     * 
     */
    public void testRobotAngle0_TurretAngle0_Calculation1()
    {
        double timestamp = 0;

        mStateManager.saveRobotState(timestamp, 10, 0, 0);
        mStateManager.setCurrentCameraState(timestamp, -45, 14.1421356237);

        Assert.assertEquals(0, mStateManager.getTargetLocation().mX, sEPSILON);
        Assert.assertEquals(10, mStateManager.getTargetLocation().mY, sEPSILON);
        Assert.assertEquals(-45, mStateManager.calculateDesiredAngleFromState(), sEPSILON);
    }

    @Test
    /**
     * 
     */
    public void testRobotAngle0_TurretAngle0_Calculation2()
    {
        double timestamp = 0;

        mStateManager.saveRobotState(timestamp, -5, 0, 0);
        mStateManager.setCurrentCameraState(timestamp, 30, 10);

        Assert.assertEquals(0.00, mStateManager.getTargetLocation().mX, sEPSILON);
        Assert.assertEquals(8.66, mStateManager.getTargetLocation().mY, sEPSILON);
        Assert.assertEquals(30, mStateManager.calculateDesiredAngleFromState(), sEPSILON);
    }

    @Test
    /**
     * 
     */
    public void testRobotAngle0_TurretAngle0_Calculation3()
    {
        double timestamp = 0;

        mStateManager.saveRobotState(timestamp, 5, 0, 0);
        mStateManager.setCurrentCameraState(timestamp, -30, 10);

        Assert.assertEquals(0.00, mStateManager.getTargetLocation().mX, sEPSILON);
        Assert.assertEquals(8.66, mStateManager.getTargetLocation().mY, sEPSILON);
        Assert.assertEquals(-30, mStateManager.calculateDesiredAngleFromState(), sEPSILON);
    }

    @Test
    /**
     * 
     */
    public void testRobotAngle0_TurretAngle0_Calculation4()
    {
        double timestamp = 0;

        mStateManager.saveRobotState(timestamp, -10, 0, 0);
        mStateManager.setCurrentCameraState(timestamp, 60, 11.547);

        Assert.assertEquals(0.000, mStateManager.getTargetLocation().mX, sEPSILON);
        Assert.assertEquals(5.773, mStateManager.getTargetLocation().mY, sEPSILON);
        Assert.assertEquals(60, mStateManager.calculateDesiredAngleFromState(), sEPSILON);
    }

    @Test
    /**
     * 
     */
    public void testRobotAngle0_TurretAngle0_Calculation5()
    {
        double timestamp = 0;

        mStateManager.saveRobotState(timestamp, 10, 0, 0);
        mStateManager.setCurrentCameraState(timestamp, -60, 11.547);

        Assert.assertEquals(0.000, mStateManager.getTargetLocation().mX, sEPSILON);
        Assert.assertEquals(5.773, mStateManager.getTargetLocation().mY, sEPSILON);
        Assert.assertEquals(-60, mStateManager.calculateDesiredAngleFromState(), sEPSILON);
    }

    @Test
    /**
     * 
     */
    public void testRobotAngleSkewed_TurretAngle0_Calculation0()
    {
        double timestamp = 0;

        mStateManager.saveRobotState(timestamp, -10, 0, 45);
        mStateManager.setCurrentCameraState(timestamp, 0, 14.1421356237);

        Assert.assertEquals(45, mStateManager.calculateDesiredAngleFromState(), sEPSILON);
    }

    @Test
    /**
     * 
     */
    public void testRobotAngleSkewed_TurretAngle0_Calculation1()
    {
        double timestamp = 0;

        mStateManager.saveRobotState(timestamp, 5, 0, -30);
        mStateManager.setCurrentCameraState(timestamp, 0, 10);

        Assert.assertEquals(-30, mStateManager.calculateDesiredAngleFromState(), sEPSILON);
    }

    @Test
    /**
     * 
     */
    public void testRobotAngle0_TurretAngleSkewed_Calculation0()
    {
        double timestamp = 0;

        mStateManager.saveRobotState(timestamp, -10, 0, 0);
        mStateManager.setCurrentCameraState(timestamp, 0, 14.1421356237);

        Assert.assertEquals(45, mStateManager.calculateDesiredAngleFromState(), sEPSILON);
    }

    @Test
    /**
     * 
     */
    public void testRobotAngle0_TurretAngleSkewed_Calculation1()
    {
        double timestamp = 0;

        mStateManager.saveRobotState(timestamp, 5, 0, 0);
        mStateManager.setCurrentCameraState(timestamp, 0, 10);

        Assert.assertEquals(-30, mStateManager.calculateDesiredAngleFromState(), sEPSILON);
    }

    @Test
    /**
     * 
     */
    public void testRobotAngleSkewed_TurretAngleSkewed_Calculation0()
    {
        double timestamp = 0;

        mStateManager.saveRobotState(timestamp, 10, 0, -25);
        mStateManager.setCurrentCameraState(timestamp, 0, 11.547);

        Assert.assertEquals(0.000, mStateManager.getTargetLocation().mX, sEPSILON);
        Assert.assertEquals(5.773, mStateManager.getTargetLocation().mY, sEPSILON);
        Assert.assertEquals(-60, mStateManager.calculateDesiredAngleFromState(), sEPSILON);
    }

    @Test
    /**
     * 
     */
    public void testRobotAngleSkewed_TurretAngleSkewed_Calculation1()
    {
        double timestamp = 0;

        mStateManager.saveRobotState(timestamp, 10, 17.5, -25);
        mStateManager.setCurrentCameraState(timestamp, 45, 20);

        Assert.assertEquals(4.823, mStateManager.getTargetLocation().mX, sEPSILON);
        Assert.assertEquals(36.818, mStateManager.getTargetLocation().mY, sEPSILON);
        Assert.assertEquals(-15, mStateManager.calculateDesiredAngleFromState(), sEPSILON);
    }
}
