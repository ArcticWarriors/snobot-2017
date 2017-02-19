package com.snobot2017.positioner;

import com.snobot.lib.ISubsystem;
import com.snobot.lib.Logger;
import com.snobot.lib.Utilities;
import com.snobot2017.SmartDashBoardNames;
import com.snobot2017.drivetrain.IDriveTrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Monitors the robot's X/Y-position, orientation, total distance traveled,
 * change in distance traveled, and speed.
 * 
 * @author Andrew
 *
 */
public class Positioner implements ISubsystem, IPositioner
{
    private Timer mTimer;
    private IDriveTrain mDriveTrain;
    private Logger mLogger;
    private Gyro mGyro;

    private double mXPosition;
    private double mYPosition;
    private double mOrientation;
    private double mTotalDistance;

    private double mLastDistance;
    private double mLastTime;
    private double mSpeed;
    private double mStartAngle;

    /**
     * Creates a new Positioner object.
     * 
     * @param aGyro
     *            The Gyro to use.
     * @param aDriveTrain
     *            The DriveTrain to use.
     * @param aLogger
     *            The robot's Logger.
     */
    public Positioner(Gyro aGyro, IDriveTrain aDriveTrain, Logger aLogger)
    {
        mXPosition = 0;
        mYPosition = 0;
        mOrientation = 0;
        mTotalDistance = 0;
        mLastDistance = 0;
        mLastTime = 0;
        mSpeed = 0;
        mGyro = aGyro;
        mDriveTrain = aDriveTrain;
        mTimer = new Timer();
        mLogger = aLogger;
        mStartAngle = 0;
    }

    /**
     * Starts timer and adds headers to Logger.
     */
    @Override
    public void init()
    {
        mTimer.start();

        mLogger.addHeader("X-coordinate");
        mLogger.addHeader("Y-coordinate");
        mLogger.addHeader("Orientation");
        mLogger.addHeader("Speed");
    }

    /**
     * Calculates the robot's current X/Y-position, orientation, distance
     * traveled, and speed.
     */
    @Override
    public void update()
    {
        // Orientation
        mOrientation = (mGyro.getAngle() + mStartAngle);
        double orientationRadians = Math.toRadians(mOrientation);

        // ChangeInDistance and X/Y
      mTotalDistance = (mDriveTrain.getRightDistance() + mDriveTrain.getLeftDistance()) / 2.0f;
        double deltaDistance = mTotalDistance - mLastDistance;
        mXPosition += deltaDistance * Math.sin(orientationRadians);
        mYPosition += deltaDistance * Math.cos(orientationRadians);
        //System.out.println("Positioner " + mTotalDistance + " " + mDriveTrain.getRightDistance() + " " + mDriveTrain.getLeftDistance());
        
        // Update
        mSpeed = (deltaDistance) / (mTimer.get() - mLastTime);
        mLastTime = mTimer.get();
        mLastDistance = mTotalDistance;
    }

    @Override
    public double getXPosition()
    {
        return mXPosition;
    }

    @Override
    public double getYPosition()
    {
        return mYPosition;
    }

    @Override
    public double getOrientationDegrees()
    {
        return mOrientation;
    }

    @Override
    public double getOrientationRadians()
    {
        return Math.toRadians(mOrientation);
    }

    @Override
    public double getTotalDistance()
    {
        return mTotalDistance;
    }

    @Override
    public void setPosition(double aX, double aY, double aAngle)
    {
        mDriveTrain.resetEncoders();
        mXPosition = aX;
        mYPosition = aY;
        mStartAngle = aAngle;
        mGyro.reset();
        mDriveTrain.resetEncoders();

        mTotalDistance = 0;
        mLastDistance = 0;
        mLastTime = mTimer.get();
    }

    @Override
    public void control()
    {
        // Nothing
    }

    @Override
    public void rereadPreferences()
    {
        // Nothing
    }

    /**
     * Puts the robot's X/Y-position, orientation, and speed on the
     * SmartDashboard.
     */
    @Override
    public void updateSmartDashboard()
    {
        SmartDashboard.putNumber(SmartDashBoardNames.sX_POSITION, mXPosition);
        SmartDashboard.putNumber(SmartDashBoardNames.sY_POSITION, mYPosition);
        SmartDashboard.putNumber(SmartDashBoardNames.sORIENTATION, Utilities.boundAngle0to360Degrees(mOrientation));
        SmartDashboard.putNumber(SmartDashBoardNames.sSPEED, mSpeed);
    }

    /**
     * Sends the robot's X/Y-position, orientation, total distance traveled,
     * change in distance traveled, and speed to the logger.
     */
    @Override
    public void updateLog()
    {
        mLogger.updateLogger(mXPosition);
        mLogger.updateLogger(mYPosition);
        mLogger.updateLogger(mOrientation);
        mLogger.updateLogger(mSpeed);
    }

    @Override
    public void stop()
    {
        // Nothing
    }

}
