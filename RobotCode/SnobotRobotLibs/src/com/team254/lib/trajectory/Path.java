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

    public void makeBackwards()
    {
        Trajectory leftTrajectory = mWheelPair.mLeftWheel.copy();
        Trajectory rightTrajectory = mWheelPair.mRightWheel.copy();

        for (int i = 0; i < leftTrajectory.getNumSegments(); ++i)
        {
            Segment segment = leftTrajectory.getSegment(i);
            segment.acc *= -1;
            segment.vel *= -1;
            segment.pos *= -1;
            segment.heading -= Math.PI;
        }

        for (int i = 0; i < rightTrajectory.getNumSegments(); ++i)
        {
            Segment segment = rightTrajectory.getSegment(i);
            segment.acc *= -1;
            segment.vel *= -1;
            segment.pos *= -1;
            segment.heading -= Math.PI;
        }

        mWheelPair.mLeftWheel = leftTrajectory;
        mWheelPair.mRightWheel = rightTrajectory;
    }
}
