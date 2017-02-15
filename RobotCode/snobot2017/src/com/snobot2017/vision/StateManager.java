package com.snobot2017.vision;

import java.util.Map.Entry;
import java.util.TreeMap;

public class StateManager
{
    private TreeMap<Double, SavedRobotState> mStateHistory;
    private TargetLocation mMostRecentTargetLocation;

    public StateManager()
    {
        mStateHistory = new TreeMap<>();
        mMostRecentTargetLocation = new TargetLocation();
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

    public void updateCameraFindings(double aTimestamp)
    {
        SavedRobotState stateAtTime = getStateHistory(aTimestamp);
        if (stateAtTime == null)
        {
            mMostRecentTargetLocation.mX = Double.NaN;
            mMostRecentTargetLocation.mY = Double.NaN;
            return;
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
}
