package com.snobot2017.drivetrain;

import com.snobot.lib.Logger;
import com.snobot2017.SmartDashBoardNames;
import com.snobot2017.autologger.AutoLogger;
import com.snobot2017.joystick.IDriverJoystick;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class ASnobotDrivetrain<SpeedControllerType extends SpeedController> implements IDriveTrain
{
    protected final SpeedControllerType mLeftMotor;
    protected final SpeedControllerType mRightMotor;
    protected final IDriverJoystick mDriverJoystick;

    protected final Logger mLogger;
    protected final AutoLogger mAutoLogger;

    protected final RobotDrive mRobotDrive;

    protected double mLeftMotorSpeed;
    protected double mRightMotorSpeed;

    protected double mRightMotorDistance;
    protected double mLeftMotorDistance;

    public ASnobotDrivetrain(
            SpeedControllerType aFrontLeftMotor, 
            SpeedControllerType aRearLeftMotor, 
            SpeedControllerType aFrontRightMotor,
            SpeedControllerType aRearRightMotor, 
            IDriverJoystick aDriverJoystick, 
            Logger aLogger, 
            AutoLogger aAutoLogger)
    {
        mAutoLogger = aAutoLogger;

        mLeftMotor = aFrontLeftMotor;
        mRightMotor = aFrontRightMotor;
        
        mDriverJoystick = aDriverJoystick;
        mLogger = aLogger;

        if (aRearLeftMotor != null && aRearRightMotor != null)
        {
            mRobotDrive = new RobotDrive(aFrontLeftMotor, aRearLeftMotor, aFrontRightMotor, aRearRightMotor);
        }
        else
        {
            mRobotDrive = new RobotDrive(aFrontLeftMotor, aFrontRightMotor);
        }

        mRobotDrive.setSafetyEnabled(false);
    }

    @Override
    public void init()
    {
        mLogger.addHeader("LeftEncoderDistance");
        mLogger.addHeader("RightEncoderDistance");
        mLogger.addHeader("LeftMotorSpeed");
        mLogger.addHeader("RightMotorSpeed");

        mAutoLogger.addHeader("LeftMotorSpeed");
        mAutoLogger.addHeader("RightMotorSpeed");
    }

    @Override
    public void control()
    {
        setLeftRightSpeed(mDriverJoystick.getLeftSpeed(), mDriverJoystick.getRightSpeed());
    }

    @Override
    public void rereadPreferences()
    {
        // Nothing
    }

    @Override
    public void updateSmartDashboard()
    {
        SmartDashboard.putNumber(SmartDashBoardNames.sLEFT_DRIVE_MOTOR_ENCODER, mLeftMotorDistance);
        SmartDashboard.putNumber(SmartDashBoardNames.sRIGHT_DRIVE_MOTOR_ENCODER, mRightMotorDistance);
        SmartDashboard.putNumber(SmartDashBoardNames.sLEFT_DRIVE_MOTOR_SPEED, mLeftMotorSpeed);
        SmartDashboard.putNumber(SmartDashBoardNames.sRIGHT_DRIVE_MOTOR_SPEED, mRightMotorSpeed);
    }

    @Override
    public void updateLog()
    {
        mLogger.updateLogger(mLeftMotorDistance);
        mLogger.updateLogger(mRightMotorDistance);
        mLogger.updateLogger(mLeftMotorSpeed);
        mLogger.updateLogger(mRightMotorSpeed);
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

    @Override
    public void setLeftRightSpeed(double aLeftSpeed, double aRightSpeed)
    {
        mLeftMotorSpeed = aLeftSpeed;
        mRightMotorSpeed = aRightSpeed;
        mRobotDrive.setLeftRightMotorOutputs(mLeftMotorSpeed, mRightMotorSpeed);
    }

    @Override
    public void stop()
    {
        setLeftRightSpeed(0, 0);
    }

}
