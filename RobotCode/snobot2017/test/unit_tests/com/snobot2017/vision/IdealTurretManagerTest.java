package com.snobot2017.vision;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import com.snobot.test.utilities.BaseSimulatorTest;

public class IdealTurretManagerTest extends BaseSimulatorTest
{
    private StateManager mStateManager;

    public IdealTurretManagerTest()
    {
        mStateManager = new StateManager();
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
    public void testHistoryManager()
    {
        double timestamp0 = 1234;
        double timestamp0_5 = 1235;
        double timestamp1 = 1236;
        double timestamp2 = 1238;

        double robot_x = 1;
        double robot_y = 2;
        double robot_angle = 45;
        SavedRobotState state_at_time;

        // Nothing has been filled in yet, we should expect invalid data
        Assert.assertFalse(mStateManager.canCalculate(timestamp0));

        // Add data for t0
        mStateManager.saveRobotState(timestamp0, robot_x, robot_y, robot_angle);

        Assert.assertTrue(mStateManager.canCalculate(timestamp0));
        state_at_time = mStateManager.getStateHistory(timestamp0);
        Assert.assertNotNull(state_at_time);
        Assert.assertEquals(1, state_at_time.mRobotX, 0);
        Assert.assertEquals(2, state_at_time.mRobotY, 0);
        Assert.assertEquals(45, state_at_time.mAngle, 0);

        // At t1, we have not updated the state, so we should get the same value
        Assert.assertTrue(mStateManager.canCalculate(timestamp1));
        state_at_time = mStateManager.getStateHistory(timestamp1);
        Assert.assertNotNull(state_at_time);
        Assert.assertEquals(1, state_at_time.mRobotX, 0);
        Assert.assertEquals(2, state_at_time.mRobotY, 0);
        Assert.assertEquals(45, state_at_time.mAngle, 0);

        // Add data for t1
        robot_x = 123;
        robot_y = 321;
        robot_angle = -50;
        mStateManager.saveRobotState(timestamp1, robot_x, robot_y, robot_angle);

        // Got data between t0 and t1... I want to "round down" and get the older data
        Assert.assertTrue(mStateManager.canCalculate(timestamp0_5));
        state_at_time = mStateManager.getStateHistory(timestamp0_5);
        Assert.assertNotNull(state_at_time);
        Assert.assertEquals(1, state_at_time.mRobotX, 0);
        Assert.assertEquals(2, state_at_time.mRobotY, 0);
        Assert.assertEquals(45, state_at_time.mAngle, 0);

        // Got data at t1
        Assert.assertTrue(mStateManager.canCalculate(timestamp1));
        state_at_time = mStateManager.getStateHistory(timestamp1);
        Assert.assertNotNull(state_at_time);
        Assert.assertEquals(123, state_at_time.mRobotX, 0);
        Assert.assertEquals(321, state_at_time.mRobotY, 0);
        Assert.assertEquals(-50, state_at_time.mAngle, 0);

        // Got data before we before we updated the state (t2)
        Assert.assertTrue(mStateManager.canCalculate(timestamp2));
        state_at_time = mStateManager.getStateHistory(timestamp2);
        Assert.assertNotNull(state_at_time);
        Assert.assertEquals(123, state_at_time.mRobotX, 0);
        Assert.assertEquals(321, state_at_time.mRobotY, 0);
        Assert.assertEquals(-50, state_at_time.mAngle, 0);

        // Got a retrograde update
        Assert.assertTrue(mStateManager.canCalculate(timestamp0));
        state_at_time = mStateManager.getStateHistory(timestamp0);
        Assert.assertNotNull(state_at_time);
        Assert.assertEquals(1, state_at_time.mRobotX, 0);
        Assert.assertEquals(2, state_at_time.mRobotY, 0);
        Assert.assertEquals(45, state_at_time.mAngle, 0);
    }

}
