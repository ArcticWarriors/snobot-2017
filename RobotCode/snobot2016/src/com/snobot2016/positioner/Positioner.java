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
 * Monitors the robot's X/Y-position, orientation, total distance traveled,
 * change in distance traveled, and speed.
 * 
 * @author Alec/Andrew
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

    private double mVelocityX;
    private double mVelocityY;

    private Accelerometer mAccelerometer;
    double mErrorX;
    double mErrorY;
    double mErrorZ;

    // TODO Get rid of these when done testing
    double encoderXVelocity;
    double encoderYVelocity;
    double accelXVelocity;
    double accelYVelocity;
    double avgFiltXVelocity;
    double avgFiltYVelocity;
    double compFiltXVelocity;
    double compFiltYVelocity;
    double mAvgFiltXPos;
    double mAvgFiltYPos;
    double mCompFiltXPos;
    double mCompFiltYPos;

    boolean isFirstTime;

    static double mGtoIn_S2 = 386.0885826772;

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
     * @param aAccelerometer
     *            The robot's accelerometer
     */
    public Positioner(Gyro aGyro, IDriveTrain aDriveTrain, Logger aLogger, Accelerometer aAccelerometer)
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

        mVelocityX = 0;
        mVelocityY = 0;

        mAccelerometer = aAccelerometer;
        mErrorX = 0;
        mErrorY = 0;
        mErrorZ = 0;

        // TODO Get rid of these when done testing
        encoderXVelocity = 0;
        encoderYVelocity = 0;
        accelXVelocity = 0;
        accelYVelocity = 0;
        avgFiltXVelocity = 0;
        avgFiltYVelocity = 0;
        compFiltXVelocity = 0;
        compFiltYVelocity = 0;
        mAvgFiltXPos = 0;
        mAvgFiltYPos = 0;
        mCompFiltXPos = 0;
        mCompFiltYPos = 0;

        isFirstTime = true;
    }

    /**
     * Starts timer and adds headers to Logger.
     */
    @Override
    public void init()
    {
        mTimer.start();

        // calibrateAccel();

        mLogger.addHeader("X-coordinate");
        mLogger.addHeader("Y-coordinate");
        mLogger.addHeader("Orientation");
        mLogger.addHeader("Speed");

        mLogger.addHeader("Accel Error X");
        mLogger.addHeader("Accel Error Y");

        addFilterTestHeaders();
    }

    /**
     * Calculates the robot's current X/Y-position, orientation, distance
     * traveled, and speed.
     */
    @Override
    public void update()
    {
        double nowTime = mTimer.get();
        double deltaTime;
        if (isFirstTime)
        {
            deltaTime = nowTime;
            isFirstTime = false;
        }
        else
        {
            deltaTime = nowTime - mLastTime;
        }

        // Orientation
        mOrientation = (mGyro.getAngle() + mStartAngle);
        double orientationRadians = Math.toRadians(mOrientation);

        // ChangeInDistance and X/Y
        // TODO Need to account for slips when driving over defenses
        mTotalDistance = (mDriveTrain.getRightEncoderDistance() + mDriveTrain.getLeftEncoderDistance()) / 2;
        double deltaDistance = mTotalDistance - mLastDistance;
        double deltaDistanceX = deltaDistance * Math.sin(orientationRadians);
        double deltaDistanceY = deltaDistance * Math.cos(orientationRadians);
        mXPosition += deltaDistanceX;
        mYPosition += deltaDistanceY;

        encoderXVelocity = deltaDistanceX / deltaTime;
        encoderYVelocity = deltaDistanceY / deltaTime;

        // TODO Get rid of these when done testing
        mVelocityX = encoderXVelocity;
        mVelocityY = encoderYVelocity;

        double accelX = (mAccelerometer.getX() - mErrorX) * Math.sin(orientationRadians) * mGtoIn_S2;
        double accelY = (mAccelerometer.getY() - mErrorY) * Math.cos(orientationRadians) * mGtoIn_S2;

        accelXVelocity = accelX * deltaTime + mVelocityX;
        accelYVelocity = accelY * deltaTime + mVelocityY;

        averageFilter(encoderXVelocity, encoderYVelocity, accelXVelocity, accelYVelocity);

        mAvgFiltXPos += avgFiltXVelocity * deltaTime;
        mAvgFiltYPos += avgFiltYVelocity * deltaTime;

        complimentaryFilter(encoderXVelocity, encoderYVelocity, accelXVelocity, accelYVelocity);

        mCompFiltXPos += compFiltXVelocity * deltaTime;
        mCompFiltYPos += compFiltYVelocity * deltaTime;

        // Update

        // TODO Fix these when done testing
        mSpeed = (deltaDistance) / deltaTime;
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
        // TODO Are the encoders supposed to be reset twice?
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

        SmartDashboard.putNumber("Encoder X Velocity", encoderXVelocity);
        SmartDashboard.putNumber("Encoder Y Velocity", encoderYVelocity);

        SmartDashboard.putNumber("Accel X Velocity", accelXVelocity);
        SmartDashboard.putNumber("Accel Y Velocity", accelYVelocity);

        SmartDashboard.putNumber("Average Filter X", avgFiltXVelocity);
        SmartDashboard.putNumber("Average Filter Y", avgFiltYVelocity);

        SmartDashboard.putNumber("Complimentary Filter X", compFiltXVelocity);
        SmartDashboard.putNumber("Complimentary Filter Y", compFiltYVelocity);

        SmartDashboard.putNumber("Avg-Filter X-Pos", mAvgFiltXPos);
        SmartDashboard.putNumber("Avg-Filter Y-Pos", mAvgFiltYPos);

        SmartDashboard.putNumber("Comp-Filter X-Pos", mCompFiltXPos);
        SmartDashboard.putNumber("Comp-Filter Y-Pos", mCompFiltYPos);
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

        mLogger.updateLogger(mErrorX);
        mLogger.updateLogger(mErrorY);

        logFilterTest();
    }

    @Override
    public void stop()
    {

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

        SmartDashboard.putNumber("AccelError X", mErrorX);
        SmartDashboard.putNumber("AccelError Y", mErrorY);
        SmartDashboard.putNumber("AccelError Z", mErrorZ);
    }

    private void averageFilter(double aEncoderXVelocity, double aEncoderYVelocity, double aAccelXVelocity, double aAccelYVelocity)
    {

        avgFiltXVelocity = (aEncoderXVelocity + aAccelXVelocity) / 2;
        avgFiltYVelocity = (aEncoderYVelocity + aAccelYVelocity) / 2;
    }

    private void complimentaryFilter(double aEncoderXVelocity, double aEncoderYVelocity, double aAccelXVelocity, double aAccelYVelocity)
    {
        // TODO Make configurable
        double encoderWeight = .75;
        compFiltXVelocity = aEncoderXVelocity * encoderWeight + aAccelXVelocity * (1 - encoderWeight);
        compFiltYVelocity = aEncoderYVelocity * encoderWeight + aAccelYVelocity * (1 - encoderWeight);
    }

    // TODO Get rid of this when done testing
    private void addFilterTestHeaders()
    {
        mLogger.addHeader("Encoder X Velocity");
        mLogger.addHeader("Encoder Y Velocity");
        mLogger.addHeader("Accel X Velocity");
        mLogger.addHeader("Accel X Velocity");
        mLogger.addHeader("Average Filter X");
        mLogger.addHeader("Average Filter Y");
        mLogger.addHeader("Complimentary Filter X");
        mLogger.addHeader("Complimentary Filter Y");
    }

    // TODO Get rid of this when done testing
    private void logFilterTest()
    {
        mLogger.updateLogger(encoderXVelocity);
        mLogger.updateLogger(encoderYVelocity);
        mLogger.updateLogger(accelXVelocity);
        mLogger.updateLogger(accelYVelocity);
        mLogger.updateLogger(avgFiltXVelocity);
        mLogger.updateLogger(avgFiltYVelocity);
        mLogger.updateLogger(compFiltXVelocity);
        mLogger.updateLogger(compFiltYVelocity);

    }

    // TODO Get rid of this when done testing
    private void calcError()
    {
        mErrorX = mAccelerometer.getX();
        mErrorY = mAccelerometer.getY();
    }
}
