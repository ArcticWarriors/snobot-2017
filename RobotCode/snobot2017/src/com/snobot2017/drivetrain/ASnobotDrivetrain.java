package com.snobot2017.drivetrain;

import com.snobot.lib.logging.ILogger;
import com.snobot2017.SmartDashBoardNames;
import com.snobot2017.SnobotActor.ISnobotActor;
import com.snobot2017.joystick.IDriverJoystick;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Base class for drivetrains.  Handles most of the interaction with drivetrain
 * @author PJ
 *
 * @param <SpeedControllerType>
 */
public abstract class ASnobotDrivetrain<SpeedControllerType extends SpeedController> implements IDriveTrain
{
    protected final SpeedControllerType mLeftMotor;
    protected final SpeedControllerType mRightMotor;
    protected final IDriverJoystick mDriverJoystick;

    protected final ILogger mLogger;

    protected final RobotDrive mRobotDrive;

    protected double mLeftMotorSpeed;
    protected double mRightMotorSpeed;

    protected double mRightMotorDistance;
    protected double mLeftMotorDistance;
    
    protected int mRightEncoderRaw;
    protected int mLeftEncoderRaw;
    
    private ISnobotActor mSnobotActor;

    public ASnobotDrivetrain(
            SpeedControllerType aLeftMotor, SpeedControllerType aRightMotor,
            IDriverJoystick aDriverJoystick, 
            ILogger aLogger)
    {
        mLeftMotor = aLeftMotor;
        mRightMotor = aRightMotor;
        mRobotDrive = new RobotDrive(mLeftMotor, mRightMotor);
        
        mDriverJoystick = aDriverJoystick;
        mLogger = aLogger;

        mRobotDrive.setSafetyEnabled(false);
    }

    @Override
    public void initializeLogHeaders()
    {
        mLogger.addHeader("LeftEncoderDistance");
        mLogger.addHeader("RightEncoderDistance");
        mLogger.addHeader("LeftMotorSpeed");
        mLogger.addHeader("RightMotorSpeed");
    }

    @Override
    public void control()
    {
        if ((mSnobotActor == null) || !mSnobotActor.isInAction())
        {
            setLeftRightSpeed(mDriverJoystick.getLeftSpeed(), mDriverJoystick.getRightSpeed());
        }
    }

    @Override
    public void updateSmartDashboard()
    {
        SmartDashboard.putNumber(SmartDashBoardNames.sLEFT_DRIVE_ENCODER_RAW, mLeftEncoderRaw);
        SmartDashboard.putNumber(SmartDashBoardNames.sRIGHT_DRIVE_ENCODER_RAW, mRightEncoderRaw);
        SmartDashboard.putNumber(SmartDashBoardNames.sRIGHT_DRIVE_ENCODER_DISTANCE, mRightMotorDistance);
        SmartDashboard.putNumber(SmartDashBoardNames.sLEFT_DRIVE_ENCODER_DISTANCE, mLeftMotorDistance);
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
    public double getLeftMotorSpeed()
    {
        return mLeftMotorSpeed;
    }
    
    @Override
    public double getRightMotorSpeed()
    {
        return mRightMotorSpeed;
        
    }

    @Override
    public void stop()
    {
        setLeftRightSpeed(0, 0);
    }

    @Override
    public void setSnobotActor(ISnobotActor aSnobotActor)
    {
        mSnobotActor = aSnobotActor;

    }

}
