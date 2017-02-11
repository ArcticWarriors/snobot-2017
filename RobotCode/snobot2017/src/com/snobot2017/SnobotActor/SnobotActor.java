package com.snobot2017.SnobotActor;

import com.snobot.lib.InDeadbandHelper;
import com.snobot2017.SmartDashBoardNames;
import com.snobot2017.drivetrain.IDriveTrain;
import com.snobot2017.joystick.IOperatorJoystick;
import com.snobot2017.positioner.IPositioner;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SnobotActor implements ISnobotActor
{
    private InDeadbandHelper mInDeadbandHelper = new InDeadbandHelper(10);
    private IDriveTrain mDriveTrain;
    private IPositioner mPositioner;
    private double mDistance;
    private double mRemainingDistance;
    private double mDesiredDistance;
    private double mCurrentDistance;
    private double mGoalSpeed;
    private IOperatorJoystick mOperatorJoystick;
    private boolean mInAction;
    private double mAngle;
    private double mSpeed;
    private String mActionName;

    private enum DriveToPegStates
    {
        NoAction, Turning, Driving
    }

    private DriveToPegStates mDriveToPegStates = DriveToPegStates.NoAction;

    /**
     * Constructor
     * 
     * @param aDriveTrain
     * @param aPositioner
     */
    public SnobotActor(IDriveTrain aDriveTrain, IPositioner aPositioner, IOperatorJoystick aOperatorJoystick)
    {
        mDriveTrain = aDriveTrain;
        mPositioner = aPositioner;
        mOperatorJoystick = aOperatorJoystick;
        mActionName = "";
    }

    /**
     * Setting the goal for the driveDistance command
     * 
     * @param aDesiredDistance
     *            in inches
     */
    public void setGoal(double aDesiredDistance, double aGoalSpeed)
    {
        mDesiredDistance = mPositioner.getTotalDistance() + aDesiredDistance;
        mGoalSpeed = aGoalSpeed;

    }

    @Override
    public void setGoal(double aAngle, double aGoalSpeed, double aDistance)
    {
        mAngle = aAngle;
        mGoalSpeed = aGoalSpeed;
        mDesiredDistance = mPositioner.getTotalDistance() + aDistance;
    }

    public void driveToPeg()
    {
        if (!mInAction)
        {
            // temporary until vision manager is working
            setGoal(45, .5, 100);
            mInAction = true;
            mDriveToPegStates = DriveToPegStates.Turning;
        }

        switch (mDriveToPegStates)
        {
        case Turning:
        {
            boolean done = turnToAngle(mAngle, mGoalSpeed);
            if (done)
            {
                mDriveToPegStates = DriveToPegStates.Driving;
            }
            break;
        }
        case Driving:
        {
            boolean done = driveDistance();
            if (done)
            {
                mDriveToPegStates = DriveToPegStates.NoAction;
            }
            break;
        }
        case NoAction:
        {
            break;
        }
        }
    }

    public boolean turnToAngle(double aAngle, double aSpeed)
    {
        double error = aAngle - mPositioner.getOrientationDegrees();
        double turnMeasure = error % 360;

        if ((turnMeasure) < 0)
        {
            turnMeasure = turnMeasure + 360;
        }

        if (turnMeasure <= 180)
        {
            mDriveTrain.setLeftRightSpeed(aSpeed, -aSpeed);
        }
        else
        {
            mDriveTrain.setLeftRightSpeed(-aSpeed, aSpeed);
        }

        if (mInDeadbandHelper.isFinished(Math.abs(error) < 5))
        {
            mDriveTrain.setLeftRightSpeed(0, 0);
            return true;
        }

        return false;
    }

    public boolean driveDistance()
    {
        mCurrentDistance = mPositioner.getTotalDistance();
        double error = mDesiredDistance - mCurrentDistance;
        boolean isFinished = false;

        // System.out.println(error);

        if (mInDeadbandHelper.isFinished(Math.abs(error) < 6))
        {
            mDriveTrain.setLeftRightSpeed(0, 0);
            isFinished = true;
        }
        else if (error > 0)
        {
            mDriveTrain.setLeftRightSpeed(mGoalSpeed, mGoalSpeed);
        }
        else
        {
            mDriveTrain.setLeftRightSpeed(-mGoalSpeed, -mGoalSpeed);
        }
        return isFinished;
    }

    @Override
    public void init()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void update()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void control()
    {
        if (mOperatorJoystick.driveToPeg())
        {
            mActionName = "Drive To Peg";
            driveToPeg();
        }

        else
        {
            mInAction = false;
            mActionName = "";
        }

    }

    @Override
    public void rereadPreferences()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateSmartDashboard()
    {
        SmartDashboard.putString(SmartDashBoardNames.sSNOBOT_ACTION, mDriveToPegStates.toString());
        SmartDashboard.putBoolean(SmartDashBoardNames.sIN_ACTION, mInAction);
        SmartDashboard.putString(SmartDashBoardNames.sSNOBOT_ACTION_NAME, mActionName);
    }

    @Override
    public void updateLog()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void stop()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean InAction()
    {
        return mInAction;
    }
}
