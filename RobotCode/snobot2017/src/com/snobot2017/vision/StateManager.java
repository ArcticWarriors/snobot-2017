package com.snobot2017.vision;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

import com.snobot2017.Properties2017;
import com.snobot2017.vision.messages.TargetUpdateMessage;
import com.snobot2017.vision.messages.TargetUpdateMessage.TargetInfo;

public class StateManager
{
    protected TreeMap<Double, SavedRobotState> mStateHistory;
    protected List<TargetLocation> mMostRecentTargetLocations;

    public StateManager()
    {
        mStateHistory = new TreeMap<>();
        mMostRecentTargetLocations = new ArrayList<>();
    }

    /**
     * Saves the current location of the robot at the given time
     * 
     * @param aTime
     *            The current time
     * @param aRobotX
     *            The current X location, in inches
     * @param aRobotY
     *            The current Y location, in inches
     * @param aRobotAngle
     *            The current angle, in degrees
     */
    public void saveRobotState(double aTime, double aRobotX, double aRobotY, double aRobotAngle)
    {
        SavedRobotState state = new SavedRobotState();
        state.mRobotX = aRobotX;
        state.mRobotY = aRobotY;
        state.mAngle = aRobotAngle;

        mStateHistory.put(aTime, state);
    }

    public void updateCameraFindings(TargetUpdateMessage latestTargetUpdate)
    {
        mMostRecentTargetLocations.clear();

        double timestamp = latestTargetUpdate.getTimestamp();

        double cameraOffset = Properties2017.sCAMERA_LENS_DIST_FROM_CENTER.getValue();

        if (canCalculate(timestamp))
        {
            SavedRobotState stateAtTime = getStateHistory(timestamp);
            double robotAngleRad = Math.toRadians(stateAtTime.mAngle);

            for (TargetInfo cameraTarget : latestTargetUpdate.getTargets())
            {
                double targetDistance = cameraTarget.getDistance();
                double targetAngle = cameraTarget.getAngle();
                targetAngle += stateAtTime.mAngle;

                double angleRads = Math.toRadians(targetAngle);

                TargetLocation targetLocation = new TargetLocation();
                targetLocation.mX = stateAtTime.mRobotX + targetDistance * Math.sin(angleRads);
                targetLocation.mY = stateAtTime.mRobotY + targetDistance * Math.cos(angleRads);
                targetLocation.mAmbigious = cameraTarget.isAmbigious();

                double offset_dx = cameraOffset * Math.cos(robotAngleRad);
                double offset_dy = cameraOffset * Math.sin(robotAngleRad);

                // System.out.println("dx = " + offset_dx + ", dy =" +
                // offset_dy);

                targetLocation.mX += offset_dx;
                targetLocation.mY += offset_dy;

                mMostRecentTargetLocations.add(targetLocation);
            }
        }
    }

    public boolean canCalculate(double aTimestamp)
    {
        return getStateAtTime(aTimestamp) != null;
    }

    public SavedRobotState getStateHistory(double aTimestamp)
    {
        return getStateAtTime(aTimestamp).getValue();
    }

    private Entry<Double, SavedRobotState> getStateAtTime(double aTimestamp)
    {
        return mStateHistory.floorEntry(aTimestamp);
    }

    public List<TargetLocation> getTargets()
    {
        return mMostRecentTargetLocations;
    }
}
