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
    private double mFrontLeftDriveMotorSpeed;
    private double mFrontRightDriveMotorSpeed;
    private double mRearLeftDriveMotorSpeed;
    private double mRearRightDriveMotorSpeed;
    private double mRightMotorDistance;
    private double mLeftMotorDistance;

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
        mFrontLeftDriveMotorSpeed = mFrontLeftMotor.get();
        mFrontRightDriveMotorSpeed = mFrontRightMotor.get();
        mLeftMotorDistance = mLeftDriveEncoder.getDistance();
        mRightMotorDistance = mRearRightMotor.get();
        mRearLeftDriveMotorSpeed = mRearLeftMotor.get();
        
    }

    @Override
    public void control()
    {
        setLeftRightSpeed(mDriverJoystick.getLeftSpeed(), mDriverJoystick.getRightSpeed());
    }

    @Override
    public void rereadPreferences()
    {
        

    }

    @Override
    public void updateSmartDashboard()
    {
        SmartDashboard.putNumber(SmartDashBoardNames.sLEFT_DRIVE_MOTOR_ENCODER, mLeftMotorDistance);
        SmartDashboard.putNumber(SmartDashBoardNames.sRIGHT_DRIVE_MOTOR_ENCODER, mRightMotorDistance);
        SmartDashboard.putNumber(SmartDashBoardNames.sFRONT_LEFT_DRIVE_MOTOR_SPEED, mFrontLeftDriveMotorSpeed);
        SmartDashboard.putNumber(SmartDashBoardNames.sFRONT_RIGHT_DRIVE_MOTOR_SPEED, mFrontRightDriveMotorSpeed);
        SmartDashboard.putNumber(SmartDashBoardNames.sREAR_RIGHT_DRIVE_MOTOR_SPEED, mRearRightDriveMotorSpeed);
        SmartDashboard.putNumber(SmartDashBoardNames.sREAR_LEFT_DRIVE_MOTOR_SPEED, mRearLeftDriveMotorSpeed);
        
       
        }

    @Override
    public void updateLog()
    {
        mLogger.updateLogger(mLeftMotorDistance);
        mLogger.updateLogger(mRightMotorDistance);
        mLogger.updateLogger(mFrontLeftDriveMotorSpeed);
        mLogger.updateLogger(mRearLeftDriveMotorSpeed);
        mLogger.updateLogger(mFrontRightDriveMotorSpeed);
        mLogger.updateLogger(mRearLeftDriveMotorSpeed);       
    }

    @Override
    public void stop()
    {
        setLeftRightSpeed(0, 0);
    }

    @Override
    public double getRightDistance()
    {
        return mRightMotorDistance;
    }

    @Override
    public double getLeftDistance()
    {
        return mLeftMotorDistance;
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
