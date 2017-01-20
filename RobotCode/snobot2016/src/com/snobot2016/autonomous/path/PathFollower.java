package com.snobot2016.autonomous.path;

import com.snobot.xlib.motion_profile.simple.ISetpointIterator;
import com.snobot.xlib.motion_profile.simple.IdealPlotSerializer;
import com.snobot.xlib.motion_profile.simple.PathSetpoint;
import com.snobot2016.SmartDashBoardNames;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;

/**
 * Follows an ideal path generated elsewhere
 * 
 * @author Andrew
 *
 */
public class PathFollower
{
    private ISetpointIterator mSetpointIterator;
    private double mKv;
    private double mKa;
    private double mKp;

    private int mPathPoint;
    private ITable mTable;

    private double mLastError;
    private double mLastPosition;

    /**
     * 
     * @param aSetpointIterator
     *            Setpoint iterator to follow points
     * @param aKV
     *            The multiplier for Velocity
     * @param aKA
     *            the multiplier for acceleration
     * @param aKP
     *            the multiplier for position
     */
    public PathFollower(ISetpointIterator aSetpointIterator, double aKV, double aKA, double aKP)
    {
        mSetpointIterator = aSetpointIterator;
        mKv = aKV;
        mKa = aKA;
        mKp = aKP;

        mTable = NetworkTable.getTable(SmartDashBoardNames.sPATH_NAMESPACE);
    }

    public void init()
    {
        mTable.putString(SmartDashBoardNames.sPATH_IDEAL_POINTS, IdealPlotSerializer.serializePath(mSetpointIterator.getIdealPath()));
    }

    public double calcMotorSpeed(double aCurrPosition)
    {
        if (mSetpointIterator.isFinished())
        {
            return 0;
        }
        else
        {
            double dt = .02;
            double velocity = (aCurrPosition - mLastPosition) / dt;

            PathSetpoint setpoint = mSetpointIterator.getNextSetpoint(0, 0, .02);
            PathSetpoint realPoint = new PathSetpoint(setpoint.mSegment, dt, aCurrPosition, velocity, 0);

            double error = setpoint.mPosition - aCurrPosition;
            double p_term = mKp * error;
            double v_term = mKv * setpoint.mVelocity;
            double a_term = mKa * setpoint.mAcceleration;

            double output = v_term + a_term + p_term;

            System.out.println(
                    "Current: " + aCurrPosition  + ", " + 
                    "Error: " + error + ", " + 
 "Vel: " + velocity + ", " + 
                    "p: " + p_term + ", " + 
                    "v: " + v_term + ", " + 
                    "a: " + a_term + ", " + 
                    "output: " + output);
            

            // Update smart dashbaord
            String point_info = mPathPoint + "," + IdealPlotSerializer.serializePathPoint(realPoint);
            mTable.putString(SmartDashBoardNames.sPATH_POINT, point_info);

            mLastError = error;
            mLastPosition = aCurrPosition;
            ++mPathPoint;

            return output;
        }
    }

    public boolean isFinished()
    {
        return mSetpointIterator.isFinished();
    }
}