package com.snobot2017.drivetrain;

import com.snobot.lib.Logger;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;

public class SnobotDriveTrain implements IDriveTrain
{
    private double mLeftSpeed;
    private double mRightSpeed;
    private final SpeedController mFrontLeftMotor;
    private final SpeedController mRearLeftMotor;
    private final SpeedController mFrontRightMotor;
    private final SpeedController mRearRightMotor;
    private final Logger mLogger;
    private final RobotDrive mRobotDrive;

    public SnobotDriveTrain(SpeedController aFrontLeftMotor, SpeedController aRearLeftMotor, SpeedController aFrontRightMotor,
            SpeedController aRearRightMotor, Logger aLogger)
    {
        mFrontLeftMotor = aFrontLeftMotor;
        mRearLeftMotor = aRearLeftMotor;
        mFrontRightMotor = aFrontRightMotor;
        mRearRightMotor = aRearRightMotor;
        mRobotDrive = new RobotDrive(mFrontLeftMotor, mRearLeftMotor, mFrontRightMotor, mRearRightMotor);
        mLogger = aLogger;
    }

    @Override
    public void init()
    {
        mLogger.addHeader("LeftEncoderDistance");
        mLogger.addHeader("RightEncoderDistance");
        mLogger.addHeader("FrontLeftMotorSpeed");
        mLogger.addHeader("RearLeftMotorSpeed");
        mLogger.addHeader("FrontRightMotorSpeed");
        mLogger.addHeader("RearRightMotorSpeed");
    }

    @Override
    public void update()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void control()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void rereadPreferences()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateSmartDashboard()
    {
        // SmartDashboard.putNumber(SmartDashBoardNames.sLEFT_DRIVE_MOTOR_ENCODER,
        // getLeftEncoderDistance());
        // SmartDashboard.putNumber(SmartDashBoardNames.sRIGHT_DRIVE_MOTOR_ENCODER,
        // getRightEncoderDistance());
        // SmartDashboard.putNumber(SmartDashBoardNames.sLEFT_DRIVE_MOTOR_SPEED,
        // mLeftMotor.get());
        // SmartDashboard.putNumber(SmartDashBoardNames.sRIGHT_DRIVE_MOTOR_SPEED,
        // mRightMotor.get());
    }

    @Override
    public void updateLog()
    {
        mLogger.updateLogger(getLeftDistance());
        mLogger.updateLogger(getRightDistance());
        mLogger.updateLogger(mFrontLeftMotor.get());
        mLogger.updateLogger(mRearLeftMotor.get());
        mLogger.updateLogger(mFrontRightMotor.get());
        mLogger.updateLogger(mRearRightMotor.get());
    }

    @Override
    public void stop()
    {
        setLeftRightSpeed(0, 0);
    }

    @Override
    public double getRightDistance()
    {
        return 0;
    }

    @Override
    public double getLeftDistance()
    {
        return 0;
    }

    @Override
    public void setLeftRightSpeed(double aLeftSpeed, double aRightSpeed)
    {
        mRobotDrive.setLeftRightMotorOutputs(aLeftSpeed, aRightSpeed);
    }
}
