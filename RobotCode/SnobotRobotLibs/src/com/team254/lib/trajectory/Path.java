package com.team254.lib.trajectory;

import com.team254.lib.trajectory.Trajectory.Segment;

/**
 * Base class for an autonomous path.
 * 
 * @author Jared341
 */
public class Path
{
    protected Trajectory.WheelPair mWheelPair;
    protected String mName;

    public Path(String name, Trajectory.WheelPair go_left_pair)
    {
        mName = name;
        mWheelPair = go_left_pair;
    }

    public String getName()
    {
        return mName;
    }

    public Trajectory getLeftWheelTrajectory()
    {
        return mWheelPair.mLeftWheel;
    }

    public Trajectory getRightWheelTrajectory()
    {
        return mWheelPair.mRightWheel;
    }

    public double getEndHeading()
    {
        int numSegments = getLeftWheelTrajectory().getNumSegments();
        Segment lastSegment = getLeftWheelTrajectory().getSegment(numSegments - 1);
        return lastSegment.heading;
    }
}
