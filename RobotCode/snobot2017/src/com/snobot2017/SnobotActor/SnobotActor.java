package com.snobot2017.SnobotActor;

import com.snobot.lib.InDeadbandHelper;
import com.snobot2017.drivetrain.IDriveTrain;
import com.snobot2017.positioner.IPositioner;

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
    
    /**
     * Constructor
     * 
     * @param aDriveTrain
     * @param aPositioner
     */
    public SnobotActor(IDriveTrain aDriveTrain, IPositioner aPositioner)
    {
        mDriveTrain = aDriveTrain;
        mPositioner = aPositioner;
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

    
    public void DriveToGoal()
    {
        
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

        System.out.println(error);

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
        // TODO Auto-generated method stub

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
}
