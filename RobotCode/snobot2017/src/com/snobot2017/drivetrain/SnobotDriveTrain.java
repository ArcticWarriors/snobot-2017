package com.snobot2017.drivetrain;

import com.snobot.lib.Logger;
import com.snobot2017.SmartDashBoardNames;
import com.snobot2017.joystick.IDriverJoystick;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    private final IDriverJoystick mDriverJoystick;
    private final Encoder mLeftDriveEncoder;
    private final Encoder mRightDriveEncoder;

    public SnobotDriveTrain(SpeedController aFrontLeftMotor, SpeedController aRearLeftMotor, SpeedController aFrontRightMotor,
            SpeedController aRearRightMotor, IDriverJoystick aDriverJoystick, Logger aLogger, Encoder aLeftDriveEncoder, Encoder aRightDriveEncoder)
    {
        mFrontLeftMotor = aFrontLeftMotor;
        mRearLeftMotor = aRearLeftMotor;
        mFrontRightMotor = aFrontRightMotor;
        mRearRightMotor = aRearRightMotor;
        mRobotDrive = new RobotDrive(mFrontLeftMotor, mRearLeftMotor, mFrontRightMotor, mRearRightMotor);
        mLogger = aLogger;
        mDriverJoystick = aDriverJoystick;
        mLeftDriveEncoder = aLeftDriveEncoder;
        mRightDriveEncoder = aRightDriveEncoder;
        
        mRobotDrive.setSafetyEnabled(false);
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
        setLeftRightSpeed(mDriverJoystick.getLeftSpeed(), mDriverJoystick.getRightSpeed());
    }

    @Override
    public void rereadPreferences()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateSmartDashboard()
    {
        SmartDashboard.putNumber(SmartDashBoardNames.sLEFT_DRIVE_MOTOR_ENCODER, getLeftDistance());
        SmartDashboard.putNumber(SmartDashBoardNames.sRIGHT_DRIVE_MOTOR_ENCODER, getRightDistance());
        SmartDashboard.putNumber(SmartDashBoardNames.sLEFT_DRIVE_MOTOR_SPEED, mFrontLeftMotor.get());
        SmartDashboard.putNumber(SmartDashBoardNames.sRIGHT_DRIVE_MOTOR_SPEED, mFrontRightMotor.get());
    }

    @Override
    public void updateLog()
    {
        mLogger.updateLogger(getLeftDistance());
        mLogger.updateLogger(getRightDistance());
        mLogger.updateLogger(mFrontLeftMotor.get());
        mLogger.updateLogger(mFrontRightMotor.get());
    }

    @Override
    public void stop()
    {
        setLeftRightSpeed(0, 0);
    }

    @Override
    public double getRightDistance()
    {
        return mRightDriveEncoder.getDistance();
    }

    @Override
    public double getLeftDistance()
    {
        return mLeftDriveEncoder.getDistance();
    }

    public void resetEncoders()
    {
        mLeftDriveEncoder.reset();
        mRightDriveEncoder.reset();
    }

    @Override
    public void setLeftRightSpeed(double aLeftSpeed, double aRightSpeed)
    {
        mRobotDrive.setLeftRightMotorOutputs(aLeftSpeed, aRightSpeed);
    }

}
