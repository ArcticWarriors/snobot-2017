package com.snobot.xlib.motion_profile.simple;

public class PathConfig
{
    /** The desired ending setpoint for the robot (distance or angle) */
    public double mEndpoint;

    /** The maximum velocity to drive the robot (ft/s or deg/s) */
    public double mMaxVelocity;

    /** The maximum acceleration to drive with (ft/s/s, deg/s/s) */
    public double mMaxAcceleration;

    /** The expected time between loops */
    public double mExpectedDt;

    public PathConfig()
    {
        this(0, 0, 0, 0);
    }

    public PathConfig(double aEndpoint, double aMaxVel, double aMaxAccel, double aDt)
    {
        mEndpoint = aEndpoint;
        mMaxVelocity = aMaxVel;
        mMaxAcceleration = aMaxAccel;
        mExpectedDt = aDt;
    }
}
