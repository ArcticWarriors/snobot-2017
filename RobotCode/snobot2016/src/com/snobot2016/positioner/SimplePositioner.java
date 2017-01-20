package com.snobot2016.positioner;

import com.snobot.xlib.ISubsystem;
import com.snobot.xlib.Logger;
import com.snobot.xlib.Utilities;
import com.snobot2016.SmartDashBoardNames;
import com.snobot2016.drivetrain.IDriveTrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Monitors the robot's X/Y-position, orientation, total distance traveled,
 * change in distance traveled, and speed.
 * 
 * @author Alec/Andrew
 *
 */
public class SimplePositioner implements ISubsystem, IPositioner
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

    // private

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
    public SimplePositioner(Gyro aGyro, IDriveTrain aDriveTrain, Logger aLogger)
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
        // TODO Need to account for slips when driving over defenses
        mTotalDistance = (mDriveTrain.getRightEncoderDistance() + mDriveTrain.getLeftEncoderDistance()) / 2;
        double deltaDistance = mTotalDistance - mLastDistance;
        mXPosition += deltaDistance * Math.sin(orientationRadians);
        mYPosition += deltaDistance * Math.cos(orientationRadians);

        // Update
        mSpeed = (deltaDistance) / (mTimer.get() - mLastTime);
        mLastTime = mTimer.get();
        mLastDistance = mTotalDistance;
    }

    /**
     * @return The robot's current X-position.
     */
    public double getXPosition()
    {
        return mXPosition;
    }

    /**
     * @return The robot's current Y-position.
     */
    public double getYPosition()
    {
        return mYPosition;
    }

    /**
     * @return The robot's current orientation in degrees.
     */
    public double getOrientationDegrees()
    {
        return mOrientation;
    }

    /**
     * @return the robot's current orientation in radians.
     */
    public double getOrientationRadians()
    {
        return Math.toRadians(mOrientation);
    }

    /**
     * @return The total distance traversed by the robot.
     */
    public double getTotalDistance()
    {
        return mTotalDistance;
    }

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

    }

    @Override
    public void rereadPreferences()
    {

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

    }

}
