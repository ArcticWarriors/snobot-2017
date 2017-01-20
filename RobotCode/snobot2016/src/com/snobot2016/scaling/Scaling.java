package com.snobot2016.scaling;

import com.snobot.xlib.Logger;
import com.snobot2016.Properties2016;
/**
 * Author Jeffrey/Michael
 * class for scaling arm of the robot
 * creates auto-climb feature
 * 
 */
import com.snobot2016.SmartDashBoardNames;
import com.snobot2016.joystick.IOperatorJoystick;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * 
 * @author jbnol_000
 * 
 * Class for the Scaling arm of the Snobot
 *
 */
public class Scaling implements IScaling
{
    private Logger mLogger;
    private SpeedController mScaleMoveMotor;
    private SpeedController mScaleTiltMotor;
    private IOperatorJoystick mJoystick;
    private Timer mTimer;
    private boolean mAmIClimbing;
    private AnalogInput mTiltPot; // Tilt Motor Potentiometer
    private double mScaleTiltAngle; // Current Potentiometer Angle
    private boolean mIsScalingMechanismUp; // Is scaling up
    private boolean mIsScalingMechanismDown; // Is scaling down
    private AnalogInput mExtensionPot; // Extension Potentiometer
    private double mExtended; // Current Potentiometer Distance Extended
    private double mExtensionVoltage;
    private boolean mSafeToRaise;
    private boolean mSafeToLower;
    
    /** error margin to find if scale tilt is near max */
    private static final double sHIGH_SCALE_TILT_MARGINAL_ERROR = 5;

    /** error margin to find if scale tilt is near min */
    private static final double sLOW_SCALE_TILT_MARGINAL_ERROR = 5;
/**
 * 
 * @param aScaleMoveMotor
 *                      The Extension motor of the Snobot scaler
 * @param aScaleTiltMotor
 *                      The Tilt motor of the Snobot scaler
 * @param aOperatorJoystick
 *                        The operator Joystick
 * @param aLogger
 *              Logs the movement of the Scaling arm
 * @param aTiltPot
 *                 Tilt potentiometer
 * @param aExtensionPot
 *                      Extension potentiometer
 * 
 */
    public Scaling(SpeedController aScaleMoveMotor, SpeedController aScaleTiltMotor, IOperatorJoystick aOperatorJoystick, Logger aLogger,
            AnalogInput aTiltPot, AnalogInput aExtensionPot)
    {
        mScaleMoveMotor = aScaleMoveMotor;
        mScaleTiltMotor = aScaleTiltMotor;
        mJoystick = aOperatorJoystick;
        mLogger = aLogger;
        mTimer = new Timer();
        mTiltPot = aTiltPot; // Tilt Potentiometer
        mExtensionPot = aExtensionPot; // Extension Potentiometer

    }

    @Override
    public void init()
    {
        mLogger.addHeader("ScaleExtensionMotorSpeed");
        mLogger.addHeader("ScaleTiltMotorSpeed");
        mLogger.addHeader("IsScalingMechanismUp");
        mLogger.addHeader("IsScalingMechanismDown");
        mLogger.addHeader("ScaleTiltAngle");
        mLogger.addHeader("PercentageExtended");
        mLogger.addHeader("ExtensionPotVoltage");
    }

    @Override
    public void update()
    {
        double high_angle = Properties2016.sSCALE_HIGH_ANGLE.getValue();
        double low_angle = Properties2016.sSCALE_LOW_ANGLE.getValue();
        calculateAngle(mTiltPot.getVoltage());

        if (mScaleTiltAngle <= (high_angle + sHIGH_SCALE_TILT_MARGINAL_ERROR) || mScaleTiltAngle >= (high_angle - sHIGH_SCALE_TILT_MARGINAL_ERROR))
        {
            mIsScalingMechanismUp = true;
            mIsScalingMechanismDown = false;
        }
        else if ((mScaleTiltAngle <= (low_angle + sLOW_SCALE_TILT_MARGINAL_ERROR) || mScaleTiltAngle >= (low_angle - sLOW_SCALE_TILT_MARGINAL_ERROR)))
        {
            mIsScalingMechanismUp = false;
            mIsScalingMechanismDown = true;
        }
        else
        {
            mIsScalingMechanismDown = false;
            mIsScalingMechanismUp = false;
        }

        {
            mExtensionVoltage = mExtensionPot.getVoltage();
            mExtended = 100 - (((mExtensionVoltage - Properties2016.sMIN_SCALE_EXTENSION_POT_VOLTAGE.getValue())
                    / (Properties2016.sMAX_SCALE_EXTENSION_POT_VOLTAGE.getValue() - Properties2016.sMIN_SCALE_EXTENSION_POT_VOLTAGE.getValue()))
                    * 100);
        }

        mSafeToRaise = mScaleTiltAngle < high_angle;
        mSafeToLower = mScaleTiltAngle > low_angle;
    }

    @Override
    public void control()
    {
        controlTilt();
        controlClimb();
    }

    private void controlClimb()
    {
        setScaleSpeedMove(mJoystick.getScaleMoveSpeed());
    }

    private void controlTilt()
    {
        if (mJoystick.isScaleGoToGroundPressed())
        {
            goToPosition(ScaleAngles.Ground);
        }
        else if (mJoystick.isScaleGoToHookPositionPressed())
        {
            goToPosition(ScaleAngles.Hook);
        }
        else if (mJoystick.isScaleMoveForIntakePressed())
        {
            goToPosition(ScaleAngles.MoveForIntake);
        }
        else if (mJoystick.isScaleGoToVerticalPressed())
        {
            goToPosition(ScaleAngles.Vertical);
        }
        // No buttons are pressed, check the override
        else
        {
            double joystickSpeed = mJoystick.getScaleTiltOverrideSpeed();
            if (joystickSpeed < 0)
            {
                joystickSpeed = joystickSpeed * 0.25;
            }
            else if (joystickSpeed > 0)
            {
                joystickSpeed = joystickSpeed * 0.5;
            }
            setScaleSpeedTilt(joystickSpeed);
        }
    }

    @Override
    public void rereadPreferences()
    {

    }

    @Override
    public void updateSmartDashboard()
    {
        // puts scale motor, tilt motor, current angle and timer on
        // SmartDashboard
        SmartDashboard.putNumber(SmartDashBoardNames.sSCALE_MOVE_MOTOR, mScaleMoveMotor.get());
        SmartDashboard.putNumber(SmartDashBoardNames.sSCALE_TILT_MOTOR, mScaleTiltMotor.get());
        SmartDashboard.putNumber(SmartDashBoardNames.sSCALE_TILT_POT_VOLTAGE, mTiltPot.getVoltage());
        SmartDashboard.putNumber(SmartDashBoardNames.sSCALNG_CURRENT_ANGLE, getAngle());
        SmartDashboard.putNumber(SmartDashBoardNames.sTIMER, mTimer.get());
        SmartDashboard.putNumber(SmartDashBoardNames.sSCALE_CURRENT_POSITION, percentageScaled());
        SmartDashboard.putNumber(SmartDashBoardNames.sSCALE_EXTENSION_POT, mExtensionPot.getVoltage());
    }

    @Override
    public void updateLog()
    {
        mLogger.updateLogger(mScaleMoveMotor.get());
        mLogger.updateLogger(mScaleTiltMotor.get());
        mLogger.updateLogger(mIsScalingMechanismUp);
        mLogger.updateLogger(mIsScalingMechanismDown);
        mLogger.updateLogger(mScaleTiltAngle);
        mLogger.updateLogger(mExtended);
        mLogger.updateLogger(mExtensionVoltage);

    }

    @Override
    public void stop()
    {
        setScaleSpeedMove(0);
        setScaleSpeedTilt(0);
    }

    @Override
    public void raiseScaleExtensionLadder()
    {
        setScaleSpeedMove(1);
    }

    @Override
    public void lowerScaleExtensionLadder()
    {
        setScaleSpeedMove(-1);
    }

    public void setScaleSpeedMove(double aSpeedMove)
    {
        mScaleMoveMotor.set(aSpeedMove);
    }

    public void setScaleSpeedTilt(double aSpeedTilt)
    {
        mScaleTiltMotor.set(aSpeedTilt);
    }

    public void calculateAngle(double voltage)
    {
        double high_angle = Properties2016.sSCALE_HIGH_ANGLE.getValue();
        double low_angle = Properties2016.sSCALE_LOW_ANGLE.getValue();
        double high_volts = Properties2016.sSCALE_HIGH_VOLTAGE.getValue();
        double low_volts = Properties2016.sSCALE_LOW_VOLTAGE.getValue();

        mScaleTiltAngle = ((high_angle - low_angle) / (high_volts - low_volts)) * (voltage - low_volts);
        // Grabs properties of minimum and maximum configs for potentiometer,
        // Obtains the angle of the scaling arm, given the voltage of
        // a configured potentiometer
    }

    public double getAngle()
    {
        return mScaleTiltAngle;
    }

    @Override
    public void raiseScaleTiltMechanism()
    {
        setScaleSpeedTilt(1);
    }

    @Override
    public void lowerScaleTiltMechanism()
    {
        setScaleSpeedTilt(-1);
    }

    @Override
    public boolean goToPosition(ScaleAngles goal)
    {
        double goalAngle = goal.getDesiredAngle();
        double kP = Properties2016.sK_P_SCALE_TILT_ANGLE.getValue();
        double error = (goalAngle - mScaleTiltAngle);
        double mSpeed = (error * kP);

        System.out.println(" The goal angle is: " + goalAngle + ". The current angle is: " + mScaleTiltAngle);
        if (mSpeed > 0)
        {
            if (safeToRaise())
            {
                mScaleTiltMotor.set(mSpeed);
            }
        }
        else
        {
            if (safeToLower())
            {
                mScaleTiltMotor.set(mSpeed);
            }
        }
        return (Math.abs(error) < 5);
    }

    @Override
    public double percentageScaled()
    {
        return mExtended;
    }

    @Override
    public boolean safeToRaise()
    {
        return mSafeToRaise;
    }

    @Override
    public boolean safeToLower()
    {
        return mSafeToLower;
    }

}
