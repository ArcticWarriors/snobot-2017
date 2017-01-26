package com.snobot.simulator.robot_sim.snobot2017;

import com.snobot.simulator.ISimulatorUpdater;
import com.snobot2017.Snobot2017;
import com.snobot2017.positioner.IPositioner;

import edu.wpi.first.wpilibj.RobotBase;

public class CameraSimulator implements ISimulatorUpdater
{
    private static class PegCoordinate
    {
        private String mName;
        private double mX;
        private double mY;
        private double mAngle;

        public PegCoordinate(String aName, double aX, double aY, double aAngle)
        {
            mName = aName;
            mX = aX;
            mY = aY;
            mAngle = aAngle;
        }

        @Override
        public String toString()
        {
            return "PegCoordinate [mName=" + mName + ", mX=" + mX + ", mY=" + mY + ", mAngle=" + mAngle + "]";
        }

    }

    private IPositioner mPositioner;

    private PegCoordinate mLoadingPeg;
    private PegCoordinate mCenterPeg;
    private PegCoordinate mBoilerPeg;

    private MockAppConnection mMockAppConnection;

    public CameraSimulator()
    {
        mLoadingPeg = new PegCoordinate("Loading", 20, 7, -45);
        mCenterPeg = new PegCoordinate("Center", 25, 0, 0);
        mBoilerPeg = new PegCoordinate("Boiler", 20, -7, 45);

        mMockAppConnection = new MockAppConnection();
    }

    @Override
    public void update()
    {

        if (mPositioner != null)
        {
            double robotX = mPositioner.getXPosition();
            double robotY = mPositioner.getYPosition();
            double robotAngle = mPositioner.getOrientationDegrees();

            PegCoordinate visiblePeg = getVisiblePeg(robotX, robotY, robotAngle);

            // System.out.println(visiblePeg);
        }
    }

    private PegCoordinate getVisiblePeg(double aRobotX, double aRobotY, double aRobotAngle)
    {
        PegCoordinate output;

        // Not past the peg, won't be able to see anything
        if (aRobotY < 20)
        {
            output = null;
        }

        // Facing the wrong way, won't be able to see anything
        else if (aRobotAngle > 180)
        {
            output = null;
        }
        else if (aRobotX < 5)
        {
            output = mLoadingPeg;
        }
        else if (aRobotX > 5)
        {
            output = mBoilerPeg;
        }
        else
        {
            output = mCenterPeg;
        }

        return output;
    }

    @Override
    public void setRobot(RobotBase aRobot)
    {
        mPositioner = ((Snobot2017) aRobot).getPositioner();
    }

}
