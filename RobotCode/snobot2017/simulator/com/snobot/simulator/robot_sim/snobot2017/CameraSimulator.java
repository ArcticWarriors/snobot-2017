package com.snobot.simulator.robot_sim.snobot2017;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.snobot.lib.Utilities;
import com.snobot.simulator.ISimulatorUpdater;
import com.snobot.simulator.robot_container.IRobotClassContainer;
import com.snobot.simulator.robot_container.JavaRobotContainer;
import com.snobot2017.positioner.IPositioner;

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

    private static class RobotState
    {
        double mX;
        double mY;
        double mAngle;

        public RobotState(double aX, double aY, double aAngle)
        {
            mX = aX;
            mY = aY;
            mAngle = aAngle;
        }

        @Override
        public String toString()
        {
            return "RobotState [mX=" + mX + ", mY=" + mY + ", mAngle=" + mAngle + "]";
        }

    }

    private static final double sMAX_VIEWABLE_ANGLE = 60;
    private static final double sFRAMES_PER_SECOND = 50;
    private static final double sLATENCY_MS = 0;

    private int mLoopsBetweenUpdates;
    private int mLoopsStale;
    private int mLoopCtr;

    private TreeMap<Integer, RobotState> mRobotPositionHistory;
    private List<PegCoordinate> mPegs;

    private IPositioner mPositioner;
    private MockAppConnection mMockAppConnection;

    public CameraSimulator()
    {
        // double loopTime = RobotStateSingletonJni.getCycleTime();
        double loopTime = .02;
        mLoopsBetweenUpdates = (int) Math.ceil((1.0 / sFRAMES_PER_SECOND) / loopTime);
        mLoopsStale = (int) Math.ceil((sLATENCY_MS * 1e-3) / loopTime);
        mRobotPositionHistory = new TreeMap<>();
        mLoopCtr = 0;

        mPegs = new ArrayList<>();
        mPegs.add(new PegCoordinate("Red Loading", -45, -200, 60 + 180, -1000, -1000, -42, -125));
        mPegs.add(new PegCoordinate("Red Center", 0, -230, 0 + 180, -100, -1000, 100, -240));
        mPegs.add(new PegCoordinate("Red Boiler", 45, -200, -60 + 180, 42, -1000, 1000, -125));
        mPegs.add(new PegCoordinate("Blue Loading", -45, 200, 60, -1000, 125, -42, 1000));
        mPegs.add(new PegCoordinate("Blue Center", 0, 230, 0, -1000, 230, 240, 1000));
        mPegs.add(new PegCoordinate("Blue Boiler", 45, 200, -60, 42, 125, 1000, 1000));

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
        if (mPositioner == null)
        {
            return;
        }


        RobotState currentState = new RobotState(mPositioner.getXPosition(), mPositioner.getYPosition(), mPositioner.getOrientationDegrees());
        mRobotPositionHistory.put(mLoopCtr, currentState);
        RobotState stateAtLatency = mRobotPositionHistory.getOrDefault(mLoopCtr - mLoopsStale, currentState);

        List<TargetInfo> targets = getVisiblePeg(stateAtLatency.mX, stateAtLatency.mY, stateAtLatency.mAngle);

        JSONArray targetJson = new JSONArray();

        for (int i = 0; i < targets.size(); ++i)
        {
            TargetInfo targetInfo = targets.get(i);
            JSONObject targetInfoJson = new JSONObject();
            targetInfoJson.put("angle", targetInfo.mAngle);
            targetInfoJson.put("distance", targetInfo.mDistance);
            targetInfoJson.put("ambiguous", i != 0);
            targetJson.add(targetInfoJson);
        }

        JSONObject jsonMessage = new JSONObject();
        jsonMessage.put("camera_latency_sec", sLATENCY_MS * 1e-3);
        jsonMessage.put("targets", targetJson);
        jsonMessage.put("type", "target_update");

        if (mLoopCtr % mLoopsBetweenUpdates == 0)
        {
//            System.out.println("Sending data " + currentState + ", " + stateAtLatency);
//            System.out.println("Sending message: LoopCtr: " + mLoopCtr + ", Delay " + mLoopsBetweenUpdates + ", Loops Stale: " + mLoopsStale);
            mMockAppConnection.send(jsonMessage);
        }
        ++mLoopCtr;
    }

    private List<TargetInfo> getVisiblePeg(double aRobotX, double aRobotY, double aRobotAngle)
    {
        List<TargetInfo> output = new ArrayList<>();

        for (PegCoordinate peg : mPegs)
        {
            TargetInfo targetInfo = calculateTargetInfo(aRobotX, aRobotY, aRobotAngle, peg);
            if (isTargetValid(targetInfo, aRobotX, aRobotY, aRobotAngle, peg))
            {
                output.add(targetInfo);
            }
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
//            System.out.println("Ditching " + aPeg.mName + " because of X " + aRobotX + "[" + aPeg.mMinX + ", " + aPeg.mMaxX + "]");
            return false;
        }

        if (aRobotY > aPeg.mMaxY || aRobotY < aPeg.mMinY)
        {
//            System.out.println("Ditching " + aPeg.mName + " because of Y " + aRobotY + "[" + aPeg.mMinY + ", " + aPeg.mMaxY + "]");
            return false;
        }

        if (Math.abs(aTargetInfo.mAngle) > sMAX_VIEWABLE_ANGLE)
        {
//            System.out.println("Ditching " + aPeg.mName + " because of Angle" + aTargetInfo.mAngle + "[" + sMAX_VIEWABLE_ANGLE + "]");
            return false;
        }

        return true;
    }

    @Override
    public void setRobot(IRobotClassContainer aRobot)
    {
        JavaRobotContainer container = (JavaRobotContainer) aRobot;
        // mPositioner = ((Snobot2017)
        // container.getJavaRobot()).getPositioner();
    }

}
