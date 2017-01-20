package com.snobot2016.drivetrain;

import com.snobot.xlib.Logger;
import com.snobot2016.SmartDashBoardNames;
import com.snobot2016.joystick.IDriverJoystick;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * 
 * @author Team 174
 * 
 * The base for the Snobot drive train
 *
 */
public abstract class ASnobotDrivetrain implements IDriveTrain
{
    protected final SpeedController mLeftMotor;
    protected final SpeedController mRightMotor;
    protected final IDriverJoystick mDriveJoystick;
    protected final RobotDrive mRobotDrive;
    protected final Logger mLogger;

   /**
    * 
    * @param aLeftMotor
    *                  The First Left Motor
    * @param aLeftMotorB
    *                   The Second Left Motor
    * @param aRightMotor
    *                   The First Right Motor
    * @param aRightMotorB
    *                    The Second Right Motor
    * @param aDriverJoyStick
    *                       The Driver Joystick
    * @param aLogger
    *               The Logger
    *               
    */
    public ASnobotDrivetrain(SpeedController aLeftMotor, SpeedController aLeftMotorB, SpeedController aRightMotor, SpeedController aRightMotorB,
            IDriverJoystick aDriverJoyStick, Logger aLogger)
    {
        mLeftMotor = aLeftMotor;
        mRightMotor = aRightMotor;
        mDriveJoystick = aDriverJoyStick;
        mLogger = aLogger;

        if (aLeftMotorB != null && aRightMotorB != null)
        {
            mRobotDrive = new RobotDrive(aLeftMotor, aLeftMotorB, aRightMotor, aRightMotorB);
        }
        else
        {
            mRobotDrive = new RobotDrive(aLeftMotor, aRightMotor);
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
    }

    @Override
    public void update()
    {

    }

    @Override
    public void control()
    {
        if (mDriveJoystick.isArcadeMode())
        {
            mRobotDrive.arcadeDrive(mDriveJoystick.getArcadePower(), mDriveJoystick.getArcadeTurn());
        }
        else
        {
            mRobotDrive.tankDrive(-mDriveJoystick.getLeftSpeed(), -mDriveJoystick.getRightSpeed(), true);
        }
    }

    @Override
    public void rereadPreferences()
    {

    }

    @Override
    public void updateSmartDashboard()
    {
        SmartDashboard.putNumber(SmartDashBoardNames.sLEFT_DRIVE_MOTOR_ENCODER, getLeftEncoderDistance());
        SmartDashboard.putNumber(SmartDashBoardNames.sRIGHT_DRIVE_MOTOR_ENCODER, getRightEncoderDistance());
        SmartDashboard.putNumber(SmartDashBoardNames.sLEFT_DRIVE_MOTOR_SPEED, mLeftMotor.get());
        SmartDashboard.putNumber(SmartDashBoardNames.sRIGHT_DRIVE_MOTOR_SPEED, mRightMotor.get());
    }

    @Override
    public void updateLog()
    {
        mLogger.updateLogger(getLeftEncoderDistance());
        mLogger.updateLogger(getRightEncoderDistance());
        mLogger.updateLogger(mLeftMotor.get());
        mLogger.updateLogger(mRightMotor.get());
    }

    @Override
    public void stop()
    {
        setLeftRightSpeed(0, 0);
    }

    @Override
    public void setLeftRightSpeed(double left, double right)
    {
        mRobotDrive.setLeftRightMotorOutputs(left, right);
    }

}
