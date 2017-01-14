package com.snobot2016.positioner;

import com.snobot.xlib.ISubsystem;
import com.snobot.xlib.Logger;
import com.snobot.xlib.Utilities;
import com.snobot2016.SmartDashBoardNames;
import com.snobot2016.drivetrain.IDriveTrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Monitors the robot's X/Y-position, orientation, total distance traveled, and
 * speed.
 * 
 * @author Alec/Andrew
 *
 */
public class IMUPositioner implements IPositioner, ISubsystem
{
    private double mXPosition;
    private double mYPosition;
    private double mOrientation;
    private double mTotalDistance;
    private double mChangeInDistance;
    private Timer mTimer;
    private Gyro mGyro;
    private Accelerometer mAccelerometer;
    private IDriveTrain mDriveTrain;
    private double mVelocityX;
    private double mVelocityY;
    private double mSpeed;
    public Logger mLogger;
    private double mOffset;
    private double mLastTime;
    double mErrorX;
    double mErrorY;
    double mErrorZ;
    boolean mbFirstRun = true;

    static double mGtoIn = 386.0885826772;

    /**
     * Creates a new Positioner object.
     * 
     * @param aGyro
     *            The Gyro to use.
     * @param aAccelerometer
     *            The accelerometer to use.
     * @param aDriveTrain
     *            The DriveTrain to use.
     * @param aLogger
     *            The robot's Logger.
     */
    public IMUPositioner(Gyro aGyro, Accelerometer aAccelerometer, IDriveTrain aDriveTrain, Logger aLogger)
    {
        mGyro = aGyro;
        mAccelerometer = aAccelerometer;
        mDriveTrain = aDriveTrain;
        mLogger = aLogger;
        mTimer = new Timer();
    }

    /**
     * Starts timer and adds headers to Logger.
     */
    @Override
    public void init()
    {
        mOffset = 0;
        mXPosition = 0;
        mYPosition = 0;
        mOrientation = 0;
        mTotalDistance = 0;
        mChangeInDistance = 0;
        mVelocityX = 0;
        mVelocityY = 0;
        mSpeed = 0;
        mLastTime = 0;
        mErrorX = 0;
        mErrorY = 0;
        mErrorZ = 0;

        mTimer.start();

        mLogger.addHeader("X-coordinate");
        mLogger.addHeader("Y-coordinate");
        mLogger.addHeader("Orientation");
        mLogger.addHeader("Speed");

        mGyro.calibrate();
        calibrateAccel();
    }

    /**
     * Calculates the robot's current X/Y-position, orientation, distance
     * traveled, and speed.
     */
    @Override
    public void update()
    {
        // Update time period
        double nowTime = mTimer.get();

        // Want to start at equal now/last time if it's the first time.
        if (mbFirstRun)
        {
            mLastTime = nowTime;
            mbFirstRun = false;
        }
        double deltaTime = nowTime - mLastTime;

        // Update values from sensors
        double accelX = (mAccelerometer.getX() * mGtoIn) - mErrorX;
        double accelY = (mAccelerometer.getY() * mGtoIn) - mErrorY;
        mLastTime = nowTime;

        SmartDashboard.putNumber("Accel Y", accelY);
        SmartDashboard.putNumber("Accel X", accelX);

        mOrientation = Utilities.boundAngle0to360Degrees(mGyro.getAngle() + mOffset);
        double orientationRadians = Math.toRadians(mOrientation);

        // Calculate Distance
        double distanceX = calcComponentDistance(accelX, mVelocityX, deltaTime);
        mVelocityX += (accelX * deltaTime);

        double distanceY = calcComponentDistance(accelY, mVelocityY, deltaTime);
        mVelocityY += (accelY * deltaTime);

        double thisDistance = combineComponentVectors(distanceX, distanceY);
        mTotalDistance += thisDistance;
        mSpeed = combineComponentVectors(mVelocityX, mVelocityY);

        mXPosition += thisDistance * Math.cos(orientationRadians);
        mYPosition += thisDistance * Math.sin(orientationRadians);

        SmartDashboard.putNumber("Accel Z", mAccelerometer.getZ());
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
        SmartDashboard.putNumber(SmartDashBoardNames.sORIENTATION, mOrientation);
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

    /**
     * @return The robot's current X-position.
     */
    @Override
    public double getXPosition()
    {
        return mXPosition;
    }

    /**
     * @return The robot's current Y-position.
     */
    @Override
    public double getYPosition()
    {
        return mYPosition;
    }

    /**
     * @return The robot's current orientation in degrees.
     */
    @Override
    public double getOrientationDegrees()
    {
        return mOrientation;
    }

    /**
     * @return the robot's current orientation in radians.
     */
    @Override
    public double getOrientationRadians()
    {
        return Math.toRadians(mOrientation);
    }

    /**
     * @return The total distance traversed by the robot.
     */
    @Override
    public double getTotalDistance()
    {
        return mTotalDistance;
    }

    @Override
    public void setPosition(double aX, double aY, double aAngle)
    {
        mXPosition = aX;
        mYPosition = aY;
        mOffset = aAngle - mOrientation;
    }

    private double calcComponentDistance(double accelComponent, double velocityComponent, double time)
    {
        return (velocityComponent * time) + (.5 * accelComponent * Math.pow(time, 2));
    }

    private double combineComponentVectors(double x, double y)
    {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    /**
     * Calibrate the accelerometer by taking readings over a period of time and
     * averaging the error. The robot must be stationary during this time
     * period.
     */
    private void calibrateAccel()
    {
        int samplesTaken = 0;
        mErrorX = 0;
        mErrorY = 0;
        mErrorZ = 0;
        double startTime = mTimer.get();
        while (mTimer.get() <= 4 + startTime)
        {
            mErrorX += mAccelerometer.getX();
            mErrorY += mAccelerometer.getY();
            mErrorZ += mAccelerometer.getZ();
            samplesTaken++;
        }
        // TODO For some reason the X error is not actually what we are seeing.
        mErrorX = mErrorX / samplesTaken;
        mErrorY = mErrorY / samplesTaken;
        mErrorZ = mErrorZ / samplesTaken;

        mErrorX *= mGtoIn;
        mErrorY *= mGtoIn;
        mErrorZ *= mGtoIn;

        SmartDashboard.putNumber("AccelError X", mErrorX);
        SmartDashboard.putNumber("AccelError Y", mErrorY);
        SmartDashboard.putNumber("AccelError Z", mErrorZ);
    }

}
