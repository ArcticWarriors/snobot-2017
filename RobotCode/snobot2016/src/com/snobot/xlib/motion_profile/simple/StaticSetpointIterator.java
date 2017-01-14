package com.snobot.xlib.motion_profile.simple;

import java.util.List;

/**
 * This setpoint iterator uses a pre-planed path. It will iterate over all of
 * the points in the path until the path has completed
 * 
 * @author PJ
 *
 */
public class StaticSetpointIterator implements ISetpointIterator
{
    /** The pre-planed path */
    private List<PathSetpoint> mListPoints;

    /**
     * The current index into the path. Increments with each call to
     * {@link #getNextSetpoint(double, double)}
     */
    private int mPathIndex = 0;

    public StaticSetpointIterator(PathConfig aConfig)
    {
        this(new PathGenerator().generate(aConfig));
    }

    public StaticSetpointIterator(List<PathSetpoint> aPath)
    {
        mListPoints = aPath;
        mPathIndex = 0;
    }

    @Override
    public PathSetpoint getNextSetpoint(double aPosition, double aVelocity, double aDt)
    {
        if (mPathIndex >= mListPoints.size())
        {
            return null;
        }

        PathSetpoint output = mListPoints.get(mPathIndex);
        ++mPathIndex;

        return output;
    }

    @Override
    public boolean isFinished()
    {
        return (mPathIndex >= mListPoints.size());
    }

    @Override
    public List<PathSetpoint> getIdealPath()
    {
        return mListPoints;
    }
}
