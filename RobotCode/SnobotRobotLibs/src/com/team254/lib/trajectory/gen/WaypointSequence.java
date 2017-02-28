package com.team254.lib.trajectory.gen;

import java.util.ArrayList;
import java.util.List;

/**
 * A WaypointSequence is a sequence of Waypoints. #whatdidyouexpect
 *
 * @author Art Kalb
 * @author Stephen Pinkerton
 * @author Jared341
 */
public class WaypointSequence
{

    public static class Waypoint
    {

        public Waypoint(double x, double y, double theta)
        {
            // Switched on purpose, poofs code wants it this way
            this.x = y;
            this.y = x;
            this.theta = Math.toRadians(theta);
        }

        public Waypoint(Waypoint tocopy)
        {
            this.x = tocopy.x;
            this.y = tocopy.y;
            this.theta = tocopy.theta;
        }

        public double x;
        public double y;
        public double theta;

        @Override
        public String toString()
        {
            return "Waypoint [x=" + x + ", y=" + y + ", theta=" + Math.toDegrees(theta) + "]";
        }

    }

    List<Waypoint> mWaypoints;

    public WaypointSequence()
    {
        mWaypoints = new ArrayList<>();
    }

    public void addWaypoint(Waypoint aWaypoint)
    {
        mWaypoints.add(aWaypoint);
    }

    public int getNumWaypoints()
    {
        return mWaypoints.size();
    }

    public Waypoint getWaypoint(int index)
    {
        return mWaypoints.get(index);
    }

    @Override
    public String toString()
    {
        return "WaypointSequence [mWaypoints=" + mWaypoints + "]";
    }
}