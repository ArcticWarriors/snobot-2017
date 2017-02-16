package com.visionTest.auto_turret;

import java.util.ArrayList;
import java.util.List;

import com.snobot2017.vision.StateManager;
import com.snobot2017.vision.TargetLocation;
import com.snobot2017.vision.messages.TargetUpdateMessage;
import com.snobot2017.vision.messages.TargetUpdateMessage.TargetInfo;

public class TestableStateManager extends StateManager
{

    public void setCurrentCameraState(double aTimestamp, double aAngle, double aDistance)
    {
        List<TargetInfo> targets = new ArrayList<>();
        targets.add(new TargetInfo(aAngle, aDistance));

        TargetUpdateMessage update = new TargetUpdateMessage(aTimestamp, targets);
        updateCameraFindings(update);
    }

    public TargetLocation getTargetLocation()
    {
        List<TargetLocation> targets = getTargets();
        return targets.isEmpty() ? null : targets.get(0);
    }

    // public double calculateDesiredAngleFromState()
    // {
    // SavedRobotState mostRecentState = mStateHistory.lastEntry().getValue();
    // TargetLocation targetLocation = getTargetLocation();
    //
    // double dx = targetLocation.mX - mostRecentState.mRobotX;
    // double dy = targetLocation.mY - mostRecentState.mRobotY;
    //
    // double angle = Math.toDegrees(Math.atan2(dx, dy));
    //
    // return angle;
    // }

}