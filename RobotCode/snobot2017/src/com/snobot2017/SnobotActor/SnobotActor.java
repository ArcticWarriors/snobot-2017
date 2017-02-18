package com.snobot2017.SnobotActor;

import com.snobot.lib.InDeadbandHelper;
import com.snobot.lib.Utilities;
import com.snobot2017.SmartDashBoardNames;
import com.snobot2017.drivetrain.IDriveTrain;
import com.snobot2017.positioner.IPositioner;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SnobotActor implements ISnobotActor
{
    private IDriveTrain mDriveTrain;
    private IPositioner mPositioner;

    private InDeadbandHelper mInDeadbandHelper;

    private DistanceControlParams mDistanceControlParams;
    private TurnControlParams mTurningControlParams;
    private SmoothControlParams mSmoothControlParams;
    private ControlMode mControlMode;

    private class DistanceControlParams
    {
        double mGoalDistance;
        double mDrivingSpeed;
        double mDeadband;

        DistanceControlParams(double aGoalDistance, double aDrivingSpeed)
        {
            mGoalDistance = aGoalDistance;
            mDrivingSpeed = aDrivingSpeed;
            mDeadband = 6; // inches
        }
    }

    private class TurnControlParams
    {
        double mGoalAngle;
        double mTurningSpeed;
        double mDeadband;

        TurnControlParams(double aGoalAngle, double aTurningSpeed)
        {
            mGoalAngle = aGoalAngle;
            mTurningSpeed = aTurningSpeed;
            mDeadband = 5; // degrees
        }
    }

    private class SmoothControlParams
    {
        double mGoalX;
        double mGoalY;
        double mDeadband;
        double mSpeed;

        SmoothControlParams(double aGoalX, double aGoalY, double aSpeed)
        {
            mGoalX = aGoalX;
            mGoalY = aGoalY;
            mDeadband = 6; // inches
            mSpeed = aSpeed;
        }
    }

    private enum ControlMode
    {
        Off, Distance, Turning, PositionInSteps, SmoothTurning
    }

    private enum GoToPositionSubsteps
    {
        NoAction, Turning, Driving
    }

    private GoToPositionSubsteps mGoToPositionSubstep = GoToPositionSubsteps.NoAction;

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

        mInDeadbandHelper = new InDeadbandHelper(10);
        mControlMode = ControlMode.Off;
        mDistanceControlParams = new DistanceControlParams(0, 0);
        mTurningControlParams = new TurnControlParams(0, 0);
        mSmoothControlParams = new SmoothControlParams(0, 0, 0);
    }

    @Override
    public void init()
    {

    }

    @Override
    public void update()
    {

    }

    /**
     * Setting the goal for the driveDistance command
     * 
     * @param aDesiredDistance
     *            in inches
     */
    public void setDistanceGoal(double aDistance, double aGoalSpeed)
    {
        mControlMode = ControlMode.Distance;
        mDistanceControlParams = new DistanceControlParams(mPositioner.getTotalDistance() + aDistance, aGoalSpeed);
    }

    @Override
    public void setTurnGoal(double aAngle, double aGoalSpeed)
    {
        mControlMode = ControlMode.Turning;
        mTurningControlParams = new TurnControlParams(aAngle, aGoalSpeed);
    }

    @Override
    public void setGoToPositionInStepsGoal(double aX, double aY, double aSpeed)
    {
        mControlMode = ControlMode.PositionInSteps;
        mGoToPositionSubstep = GoToPositionSubsteps.Turning;

        double dx = aX - mPositioner.getXPosition();
        double dy = aY - mPositioner.getYPosition();

        double distanceAway = Math.sqrt(dx * dx + dy * dy);
        double goalAngle = Math.toDegrees(Math.atan2(dx, dy)); // Switched on
                                                               // purpose

        mDistanceControlParams = new DistanceControlParams(mPositioner.getTotalDistance() + distanceAway, aSpeed);
        mTurningControlParams = new TurnControlParams(goalAngle, aSpeed);
    }

    @Override
    public void setDriveSmoothlyToPositionGoal(double aX, double aY, double aSpeed)
    {
        mControlMode = ControlMode.SmoothTurning;

        mSmoothControlParams = new SmoothControlParams(aX, aY, aSpeed);
    }

    @Override
    public boolean executeControlMode()
    {
        boolean finished = false;

        switch (mControlMode)
        {
        case Off:
            finished = true;
            break;
        case Distance:
            finished = driveDistance();
            break;
        case Turning:
            finished = turnToAngle();
            break;
        case PositionInSteps:
            finished = driveToPositionInSteps();
            break;
        case SmoothTurning:
            finished = driveSmoothlyToPosition();
            break;
        default:
            break;
        }

        if (finished)
        {
            cancelAction();
        }

        return finished;
    }

    private double limitAngle(double aAngle)
    {
        aAngle %= 360;
        return (aAngle > 180) ? (aAngle - 360) : aAngle;
    }

    private boolean driveSmoothlyToPosition()
    {
        double dx = mSmoothControlParams.mGoalX - mPositioner.getXPosition();
        double dy = mSmoothControlParams.mGoalY - mPositioner.getYPosition();

        double distanceAway = Math.sqrt(dx * dx + dy * dy);

        // dx and dy are switched on purpose to make 0 degrees refer to up
        double goalAngle = Math.toDegrees(Math.atan2(dx, dy));
        double currentAngle = limitAngle(mPositioner.getOrientationDegrees());

        double rightError = limitAngle(goalAngle - currentAngle);
        double leftError = limitAngle(360 - rightError);
        double rightSpeed = 0.0;
        double leftSpeed = 0.0;

        if (rightError < leftError)
        {
            rightSpeed = (.3 - rightError * .005);
            leftSpeed = (.3 + rightError * .005);
        }
        else
        {
            rightSpeed = (.3 + leftError * .005);
            leftSpeed = (.3 - leftError * .005);
        }
        
        System.out.println(
                goalAngle
                + "\t" + currentAngle
                + "\t" + rightError
                + "\t" + leftError
                + "\t" + rightSpeed
                + "\t" + leftSpeed);

        // double AngleError = (goalAngle - (mPositioner.getOrientationDegrees()
        // % 360)) % 360;
        boolean isFinished = false;
        // System.out.println("Smooth " + " AE " + AngleError + " AG " +
        // goalAngle + " dx " + dx + " dy " + dy + " goalx " +
        // mSmoothControlParams.mGoalX
        // + " goaly "
        // + mSmoothControlParams.mGoalY + distanceAway);
        // System.out.println((mPositioner.getOrientationDegrees() % 360) + "\t"
        // + goalAngle);

        if (mInDeadbandHelper.isFinished(Math.abs(distanceAway) < mDistanceControlParams.mDeadband))
        {
            mDriveTrain.setLeftRightSpeed(0, 0);
            isFinished = true;
        }
        else if (distanceAway > 0)
        {
            mDriveTrain.setLeftRightSpeed(leftSpeed, rightSpeed);
        }
        // else
        // {
        // mDriveTrain.setLeftRightSpeed(-leftSpeed, -rightSpeed);
        // }

        return isFinished;
    }

    @Override
    public void control()
    {

    }

    @Override
    public void rereadPreferences()
    {

    }

    @Override
    public void updateSmartDashboard()
    {
        String actionName = "";
        switch (mControlMode)
        {
        case Distance:
            actionName = "Driving Distance";
            break;
        case Turning:
            actionName = "Turning";
            break;
        case PositionInSteps:
            actionName = "Go To Position";
            break;
        case Off:
        default:
            break;
        case SmoothTurning:
            actionName = "Smoothly Drive To Position";
            break;

        }
        SmartDashboard.putString(SmartDashBoardNames.sSNOBOT_ACTION, mGoToPositionSubstep.toString());
        SmartDashboard.putBoolean(SmartDashBoardNames.sIN_ACTION, isInAction());
        SmartDashboard.putString(SmartDashBoardNames.sSNOBOT_ACTION_NAME, actionName);
    }

    @Override
    public void updateLog()
    {

    }

    @Override
    public void stop()
    {
        cancelAction();
        mDriveTrain.stop();
    }

    @Override
    public boolean isInAction()
    {
        return mControlMode != ControlMode.Off;
    }

    private boolean turnToAngle()
    {
        double error = mTurningControlParams.mGoalAngle - mPositioner.getOrientationDegrees();
        double turnMeasure = error % 360;

        if ((turnMeasure) < 0)
        {
            turnMeasure = turnMeasure + 360;
            error = Utilities.boundAngleNeg180to180Degrees(error);
        }

        if (turnMeasure <= 180)
        {
            mDriveTrain.setLeftRightSpeed(mTurningControlParams.mTurningSpeed, -mTurningControlParams.mTurningSpeed);
        }
        else
        {
            mDriveTrain.setLeftRightSpeed(-mTurningControlParams.mTurningSpeed, mTurningControlParams.mTurningSpeed);
        }

        if (mInDeadbandHelper.isFinished(Math.abs(error) < mTurningControlParams.mDeadband))
        {
            mDriveTrain.setLeftRightSpeed(0, 0);
            return true;
        }

        return false;
    }

    private boolean driveDistance()
    {
        double error = mDistanceControlParams.mGoalDistance - mPositioner.getTotalDistance();
        boolean isFinished = false;

        if (mInDeadbandHelper.isFinished(Math.abs(error) < mDistanceControlParams.mDeadband))
        {
            mDriveTrain.setLeftRightSpeed(0, 0);
            isFinished = true;
        }
        else if (error > 0)
        {
            mDriveTrain.setLeftRightSpeed(mDistanceControlParams.mDrivingSpeed, mDistanceControlParams.mDrivingSpeed);
        }
        else
        {
            mDriveTrain.setLeftRightSpeed(-mDistanceControlParams.mDrivingSpeed, -mDistanceControlParams.mDrivingSpeed);
        }

        return isFinished;
    }

    private boolean driveToPositionInSteps()
    {
        boolean finished = false;

        switch (mGoToPositionSubstep)
        {
        case Turning:
        {
            boolean done = turnToAngle();
            if (done)
            {
                mGoToPositionSubstep = GoToPositionSubsteps.Driving;
            }
            break;
        }
        case Driving:
        {
            boolean done = driveDistance();
            if (done)
            {
                mGoToPositionSubstep = GoToPositionSubsteps.NoAction;
                finished = true;
            }
            break;
        }
        case NoAction:
        {
            finished = true;
            break;
        }
        }

        return finished;
    }

    @Override
    public void cancelAction()
    {
        mControlMode = ControlMode.Off;
        mGoToPositionSubstep = GoToPositionSubsteps.NoAction;
    }

}
