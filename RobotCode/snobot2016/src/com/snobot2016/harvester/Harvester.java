package com.snobot2016.harvester;

import com.snobot.xlib.Logger;
import com.snobot2016.Properties2016;
import com.snobot2016.SmartDashBoardNames;
import com.snobot2016.joystick.IOperatorJoystick;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Author Jeffrey/Michael creates harvester for Snobot
 * 
 */
public class Harvester implements IHarvester
{
    private SpeedController mHarvesterPivotMotor;
    private SpeedController mHarvesterRollerMotor;
    private double mRoller;
    private double mPivot;
    private IOperatorJoystick mOperatorJoystick;
    private Logger mLogger;
    private AnalogInput mHarvesterPot;
    private double mHarvesterPotVoltage;
    private boolean mGoodToRaiseHarvester;
    private boolean mGoodToLowerHarvester;
    private double mHarvesterPotPercentage;
    private double mHarvesterPivotSpeed;

    public Harvester(SpeedController aHarvesterRollerMotor, SpeedController aHarvesterPivotMotor, IOperatorJoystick aOperatorJoystick, Logger aLogger,
            AnalogInput aHarvesterPot)
    {
        mHarvesterRollerMotor = aHarvesterRollerMotor;
        mHarvesterPivotMotor = aHarvesterPivotMotor;
        mOperatorJoystick = aOperatorJoystick;
        mLogger = aLogger;
        mHarvesterPot = aHarvesterPot;

    }

    @Override
    public void init()
    {
        mLogger.addHeader("PivotSpeed");
        mLogger.addHeader("RollerSpeed");
        mLogger.addHeader("HarvesterPotVoltage");
        mLogger.addHeader("HarvesterPercentage");
    }

    @Override
    public void update()
    {
        mHarvesterPotVoltage = mHarvesterPot.getVoltage();
        mGoodToRaiseHarvester = (mHarvesterPotVoltage > Properties2016.sMIN_HARVESTER_POT_VOLTAGE.getValue());
        mGoodToLowerHarvester = (mHarvesterPotVoltage < Properties2016.sMAX_HARVESTER_POT_VOLTAGE.getValue());
        mHarvesterPotPercentage = 100 - (((mHarvesterPotVoltage - Properties2016.sMIN_HARVESTER_POT_VOLTAGE.getValue())
                / (Properties2016.sMAX_HARVESTER_POT_VOLTAGE.getValue() - Properties2016.sMIN_HARVESTER_POT_VOLTAGE.getValue())) * 100);

    }

    @Override
    public void control()
    {
        controlIntake();
        controlPivot();

    }

    private void controlIntake()
    {
        double rollerSpeed = mOperatorJoystick.getHarvesterIntakeSpeed();
        setRollerMotorSpeed(rollerSpeed);
    }

    private void controlPivot()
    {

        if (mOperatorJoystick.moveHarvesterToUpPosition())
        {
            moveToPercentage(100);
        }
        else if (mOperatorJoystick.moveHarvesterToDownPosition())
        {
            moveToPercentage(0);
        }
        // If neither button is pressed, fall back on the override
        else
        {
            setPivotMotorSpeed(mOperatorJoystick.getHarvestorTiltOverrideSpeed());
        }
    }

    @Override
    public void rereadPreferences()
    {

    }

    @Override
    public void updateSmartDashboard()
    {
        // displays pivot and roller motor on SmartDashboard
        SmartDashboard.putNumber(SmartDashBoardNames.sHARVESTER_PIVOT_MOTOR, mPivot);
        SmartDashboard.putNumber(SmartDashBoardNames.sHARVESTER_ROLLER_MOTOR, mRoller);
        SmartDashboard.putNumber(SmartDashBoardNames.sHARVESTER_POT_PERCENTAGE, this.percentageLowered());
        SmartDashboard.putNumber(SmartDashBoardNames.sHARVESTER_POT_VOLTAGE, mHarvesterPotVoltage);
    }

    @Override
    public void updateLog()
    {
        mLogger.updateLogger(mPivot);
        mLogger.updateLogger(mRoller);
        mLogger.updateLogger(mHarvesterPotVoltage);
        mLogger.updateLogger(percentageLowered());
    }

    @Override
    public void stop()
    {
        stopRoller();
        stopHarvester();
    }

    @Override
    public void raiseHarvester()
    {
        if (goodToRaise())
        {
            setPivotMotorSpeed(-1);
        }
    }

    @Override
    public void lowerHarvester()
    {
        if (goodToLower())
        {
            setPivotMotorSpeed(1);
        }
    }

    @Override
    public void rollIn()
    {
        setRollerMotorSpeed(-1);
    }

    @Override
    public void rollOut()
    {
        setRollerMotorSpeed(1);
    }

    @Override
    public void setRollerMotorSpeed(double aRollerSpeed)
    {
        mRoller = aRollerSpeed;
        mHarvesterRollerMotor.set(aRollerSpeed);
    }

    @Override
    public void setPivotMotorSpeed(double aPivotSpeed)
    {
        mPivot = aPivotSpeed;
        mHarvesterPivotMotor.set(aPivotSpeed);
    }

    @Override
    public boolean goodToRaise()
    {
        return mGoodToRaiseHarvester;
    }

    @Override
    public boolean goodToLower()
    {
        return mGoodToLowerHarvester;
    }

    @Override
    public double percentageLowered()
    {
        return mHarvesterPotPercentage;
    }

    @Override
    public void stopHarvester()
    {
        setPivotMotorSpeed(0);
    }

    @Override
    public void stopRoller()
    {
        setRollerMotorSpeed(0);
    }

    @Override
    public boolean moveToPercentage(double aPotGoal)
    {
        boolean raise = true;
        double error = aPotGoal - percentageLowered();
        mHarvesterPivotSpeed = Properties2016.sHARVESTER_POT_KP.getValue() * error;
        System.out.println("Error: " + error + ", " + mHarvesterPivotSpeed + ", " + goodToRaise() + ", " + goodToLower());
        if (mHarvesterPivotSpeed > 0)
        {
            raise = false;
        }
        if (raise)
        {
            if (goodToRaise())
            {
                if (Math.abs(error) < 10)
                {
                    stopHarvester();
                    return true;
                }
                else
                {
                    setPivotMotorSpeed(mHarvesterPivotSpeed);
                    return false;
                }
            }
        }
        else
        {
            if (goodToLower())
            {
                if (Math.abs(error) < 10)
                {
                    stopHarvester();
                    return true;
                }
                else
                {
                    setPivotMotorSpeed(mHarvesterPivotSpeed);
                    return false;
                }
            }
        }
        return false;
    }

}
