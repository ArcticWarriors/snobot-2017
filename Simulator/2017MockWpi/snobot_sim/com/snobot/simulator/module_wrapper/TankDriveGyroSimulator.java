package com.snobot.simulator.module_wrapper;

import com.snobot.simulator.ISimulatorUpdater;

import edu.wpi.first.wpilibj.RobotBase;

public class TankDriveGyroSimulator implements ISimulatorUpdater
{

    private EncoderWrapper mLeftEncoder;
    private EncoderWrapper mRightEncoder;
    private AnalogWrapper mGyroPort;

    private double mAngle; // degrees
    private boolean mIsSetup;

    private boolean mIsLeftReversed;
    private boolean mIsRightReversed;

    private double mKP;

    public TankDriveGyroSimulator(EncoderWrapper aLeftEncoder, EncoderWrapper aRightEncoder, AnalogWrapper aGyroPort)
    {
        mLeftEncoder = aLeftEncoder;
        mRightEncoder = aRightEncoder;
        mGyroPort = aGyroPort;

        mIsSetup = mLeftEncoder != null && mRightEncoder != null && mGyroPort != null;

        // TODO I have no idea what this black magic number means
        // mKP = 22.0 / 12.0;
        mKP = 110.0 / 12.0;

        if (!mIsSetup)
        {
            System.err.println("Can't simulate gyro, some inputs are null");
        }
    }

    public void setTurnKp(double aKp)
    {
        mKP = aKp;
    }

    public void setIsReverse(boolean isLeftReversed, boolean isRightReversed)
    {
        mIsLeftReversed = isLeftReversed;
        mIsRightReversed = isRightReversed;
    }

    @Override
    public void update()
    {

        if (mIsSetup)
        {

            double rightDist = mRightEncoder.getDistance();
            double leftDist = mLeftEncoder.getDistance();

            if (mIsLeftReversed == true)
            {
                leftDist *= -1;
            }
            if (mIsRightReversed == true)
            {
                rightDist *= -1;
            }

            mAngle = (leftDist - rightDist) / (3.14159 * mKP) * (180.0);

            mGyroPort.setAccumulator(mAngle);
            // System.out.println("SIMULATOR : angle=" + mAngle + ", right=" +
            // rightDist + ", left=" + leftDist);
        }
    }

    @Override
    public void setRobot(RobotBase mRobot)
    {

    }
}