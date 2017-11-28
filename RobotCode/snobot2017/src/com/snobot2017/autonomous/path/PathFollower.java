package com.snobot2017.autonomous.path;

import com.snobot.lib.motion_profile.ISetpointIterator;
import com.snobot.lib.motion_profile.IdealPlotSerializer;
import com.snobot.lib.motion_profile.PathSetpoint;
import com.snobot2017.SmartDashBoardNames;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Follows an ideal path generated elsewhere
 * 
 * @author Andrew
 *
 */
public class PathFollower
{
    private static final NetworkTable sPATH_NETWORK_TABLE = NetworkTableInstance.getDefault().getTable(SmartDashBoardNames.sPATH_NAMESPACE);

    private ISetpointIterator mSetpointIterator;
    private double mKv;
    private double mKa;
    private double mKp;

    private int mPathPoint;
    private NetworkTableEntry mIdealPathPoints;
    private NetworkTableEntry mCurrentPathPoint;

    // private double mLastError;
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

        mIdealPathPoints = sPATH_NETWORK_TABLE.getEntry(SmartDashBoardNames.sPATH_IDEAL_POINTS);
        mCurrentPathPoint = sPATH_NETWORK_TABLE.getEntry(SmartDashBoardNames.sPATH_POINT);
    }

    public void init()
    {
        mIdealPathPoints.setString(IdealPlotSerializer.serializePath(mSetpointIterator.getIdealPath()));
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
            mCurrentPathPoint.setString(point_info);

            // mLastError = error;
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