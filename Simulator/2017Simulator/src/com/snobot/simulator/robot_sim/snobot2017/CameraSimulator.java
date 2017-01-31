package com.snobot.simulator.robot_sim.snobot2017;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.snobot.lib.Utilities;
import com.snobot.simulator.ISimulatorUpdater;
import com.snobot2017.Snobot2017;
import com.snobot2017.positioner.IPositioner;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.Timer;

public class CameraSimulator implements ISimulatorUpdater
{
    private static class PegCoordinate
    {
        private String mName;
        private double mX;
        private double mY;
        private double mAngle;

        private double mMinX = -100000;
        private double mMinY = -100000;
        private double mMaxX = 100000;
        private double mMaxY = 100000;

        public PegCoordinate(String aName, double aX, double aY, double aAngle, double aMinX, double aMinY, double aMaxX, double aMaxY)
        {
            mName = aName;
            mX = aX;
            mY = aY;
            mAngle = aAngle;

            mMinX = aMinX;
            mMinY = aMinY;
            mMaxX = aMaxX;
            mMaxY = aMaxY;
        }

        @Override
        public String toString()
        {
            return "PegCoordinate [mName=" + mName + ", mX=" + mX + ", mY=" + mY + ", mAngle=" + mAngle + "]";
        }

    }

    private static final double sMAX_VIEWABLE_ANGLE = 60;

    private IPositioner mPositioner;
    private MockAppConnection mMockAppConnection;

    private PegCoordinate mLoadingPeg;
    private PegCoordinate mCenterPeg;
    private PegCoordinate mBoilerPeg;

    public CameraSimulator()
    {
        mLoadingPeg = new PegCoordinate("Loading", -45, -200,  60 + 180, -1000, -1000,  -42, -125);
        mCenterPeg  = new PegCoordinate("Center",    0, -230,   0 + 180,  -100, -1000,  100, -240);
        mBoilerPeg  = new PegCoordinate("Boiler",   45, -200, -60 + 180,    42, -1000, 1000, -125);

        mMockAppConnection = new MockAppConnection();
        mMockAppConnection.start();
    }

    private static class TargetInfo
    {
        public double mDistance;
        public double mAngle;

        @Override
        public String toString()
        {
            return "TargetInfo [mDistance=" + mDistance + ", mAngle=" + mAngle + "]";
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void update()
    {

        if (mPositioner != null)
        {
            double robotX = mPositioner.getXPosition();
            double robotY = mPositioner.getYPosition();
            double robotAngle = mPositioner.getOrientationDegrees();

            List<TargetInfo> targets = getVisiblePeg(robotX, robotY, robotAngle);

            JSONArray targetJson = new JSONArray();

            for (TargetInfo targetInfo : targets)
            {
                JSONObject targetInfoJson = new JSONObject();
                targetInfoJson.put("angle", targetInfo.mAngle);
                targetInfoJson.put("distance", targetInfo.mDistance);
                targetJson.add(targetInfoJson);
            }

            JSONObject jsonMessage = new JSONObject();
            jsonMessage.put("timestamp", Timer.getFPGATimestamp());
            jsonMessage.put("targets", targetJson);
            jsonMessage.put("type", "target_update");

            mMockAppConnection.send(jsonMessage);
        }
    }

    private List<TargetInfo> getVisiblePeg(double aRobotX, double aRobotY, double aRobotAngle)
    {
        List<TargetInfo> output = new ArrayList<>();

        TargetInfo targetInfo;

        targetInfo = calculateTargetInfo(aRobotX, aRobotY, aRobotAngle, mLoadingPeg);
        if (isTargetValid(targetInfo, aRobotX, aRobotY, aRobotAngle, mLoadingPeg))
        {
            output.add(targetInfo);
        }

        targetInfo = calculateTargetInfo(aRobotX, aRobotY, aRobotAngle, mBoilerPeg);
        if (isTargetValid(targetInfo, aRobotX, aRobotY, aRobotAngle, mBoilerPeg))
        {
            output.add(targetInfo);
        }

        targetInfo = calculateTargetInfo(aRobotX, aRobotY, aRobotAngle, mCenterPeg);
        if (isTargetValid(targetInfo, aRobotX, aRobotY, aRobotAngle, mCenterPeg))
        {
            output.add(targetInfo);
        }

        return output;
    }

    private TargetInfo calculateTargetInfo(double aRobotX, double aRobotY, double aRobotAngle, PegCoordinate aPegLocation)
    {
        TargetInfo info = new TargetInfo();

        double dx = aPegLocation.mX - aRobotX;
        double dy = aPegLocation.mY - aRobotY;
        info.mDistance = Math.sqrt(dx * dx + dy * dy);
        info.mAngle = Math.toDegrees(Math.atan2(dx, dy));
        info.mAngle -= aRobotAngle;
        info.mAngle = Utilities.boundAngleNeg180to180Degrees(info.mAngle);

        return info;
    }

    private boolean isTargetValid(TargetInfo aTargetInfo, double aRobotX, double aRobotY, double aRobotAngle, PegCoordinate aPeg)
    {



        if (aRobotX > aPeg.mMaxX || aRobotX < aPeg.mMinX)
        {
//            System.out.println("Ditching " + aPeg.mName + " because of X " + aRobotX);
            return false;
        }

        if (aRobotY > aPeg.mMaxY || aRobotY < aPeg.mMinY)
        {
//            System.out.println("Ditching " + aPeg.mName + " because of Y " + aRobotY);
            return false;
        }

        if (Math.abs(aTargetInfo.mAngle) > sMAX_VIEWABLE_ANGLE)
        {
//            System.out.println("Ditching " + aPeg.mName + " because of Angle " + aTargetInfo.mAngle);
            return false;
        }

        return true;
    }

    @Override
    public void setRobot(RobotBase aRobot)
    {
        mPositioner = ((Snobot2017) aRobot).getPositioner();
    }

}
