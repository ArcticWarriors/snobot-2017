package com.snobot2017.SnobotActor;

import com.snobot.lib.InDeadbandHelper;
import com.snobot.lib.Utilities;
import com.snobot2017.Properties2017;
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

        SmoothControlParams(double aGoalX, double aGoalY)
        {
            mGoalX = aGoalX;
            mGoalY = aGoalY;
            mDeadband = 6; // inches
        }
    }

    private enum ControlMode
    {
        Off, Distance, Turning, PositionInSteps, PositionSmooth
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
        mSmoothControlParams = new SmoothControlParams(0, 0);
    }

    @Override
    public void initializeLogHeaders()
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
    public void setGoToPositionSmoothlyGoal(double aX, double aY)
    {
        mControlMode = ControlMode.PositionSmooth;
        mSmoothControlParams = new SmoothControlParams(aX, aY);
        System.out.println("setGoToPositionSmoothlyGoal " + aX + ", " + aY);
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
        case PositionSmooth:
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

    @Override
    public void control()
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
        case PositionSmooth:
            actionName = "Smoothly Drive To Position";
            break;
        case Off:
        default:
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

        System.out.println("TurnToAngle: " + mTurningControlParams.mGoalAngle + ", " + mPositioner.getOrientationDegrees() + ", " + turnMeasure);

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
            System.out.println("TurnToAngle: turnToAngle: FINISHED");
            mDriveTrain.stop();
            cancelAction();
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

    private boolean driveSmoothlyToPosition()
    {
        /*************** Proportional filter values **************************/
        double distanceKp = Properties2017.sDRIVE_TO_POSITION_DISTANCE_KP.getValue();
        double turnKp = Properties2017.sDRIVE_TO_POSITION_ANGLE_KP.getValue();
        // Overriding for testing.
        distanceKp = 0.01;

        // Can be one now because error is already between -1 and 1
        turnKp = 1.0;

        // Additional values we use and should probably add to the properties
        // Complimentary Filter factor
        double filterA = 0.40;

        // The distance away from the target that we stop correcting for angle
        // errors. should probably be more (2x) than the dead band because while
        // hovering in the deadband the angle error would be overly
        // proportionally
        // affecting the speed adjustments.
        double angleChangeCutoff = 6.0;

        // Minimum speed to drive straight. Might allow us to go a little
        // further
        // at the end so we don't stop short.
        double distanceSpeedMinimum = 0.0;

        /*************** Distance error calculation **************************/
        // Note this calculation gives absolute distance error to target
        // but that means that 6 before, 6 after and 6 to the side of the
        // target all look the same to this method. They are not the same!
        // So maybe this needs to be fixed.
        double dx = mSmoothControlParams.mGoalX - mPositioner.getXPosition();
        double dy = mSmoothControlParams.mGoalY - mPositioner.getYPosition();

        double distanceError = Math.sqrt(dx * dx + dy * dy);
        // distanceError += Properties2017.sSNOBOT_FUDGE_FACTOR.getValue();

        /******************* Determine normalized angle error ****************/
        // dx and dy are switched on purpose to make 0 degrees refer to up.
        double angleToTarget = Math.toDegrees(Math.atan2(dx, dy));
        double angleError = angleToTarget - mPositioner.getOrientationDegrees();
        double rawAngleError = Utilities.boundAngleNeg180to180Degrees(angleError);
        // dividing by 180 to get a normalized value between -1 and 1 (for -180
        // and 180)
        // double adjustedAngleError =
        // Utilities.boundAngleNeg180to180Degrees(angleError) / 180.0;
        double adjustedAngleError = Utilities.boundAngleNeg180to180Degrees(angleError) / 90.0;

        /**************** Determining the angle and distance speeds **********/
        // Not changing angle speed factor after X (12) inches
        // angle speed factor already limited to -1 and 1 because of
        // normalization above.
        double angleErrorSpeed = ((distanceError < angleChangeCutoff) ? 0 : adjustedAngleError * turnKp);
        // Limit the distance speed factor to 0 to 1 (remember distance error is
        // always positive).
        double distanceErrorSpeed = Math.min(1, (distanceError * distanceKp) + distanceSpeedMinimum);

        /********** Complimentary Filter for left and right speed ************/
        // "Complimentary Filter" option:
        // filterA determines percentage of distance verses angle correction to
        // use in adjustments.
        // double filterA = .40; From above.
        // This option provides speed always <= 1. Straight away drive speed
        // limited to FilterA.
        // This option can take distance or angle errors take precedence
        // depending on value of filterA. But only really useful if filterA is
        // small (angle error is more important). However, in this case, max
        // forward speed is greatly reduced (max is filterA) on a straight away.
        double leftAdjustment_a = filterA * distanceErrorSpeed + (1 - filterA) * angleErrorSpeed;
        double rightAdjustment_a = filterA * distanceErrorSpeed - (1 - filterA) * angleErrorSpeed;

        /****** Proportional by angle error for left and right speed **********/
        // Proportional by angle error option:
        // filterA determines percentage of distance verses angle correction to
        // use in adjustments.
        // This option is provides speed always <= 1. Straight away drive speed
        // not limited (maxs at 1).
        // This option takes angle error speed as the primary driver when large
        // but lets distance take over when angle error is small.
        double filter_p = 1 - Math.abs(angleErrorSpeed);
        double leftAdjustment_p = filter_p * distanceErrorSpeed + angleErrorSpeed;
        double rightAdjustment_p = filter_p * distanceErrorSpeed - angleErrorSpeed;

        /*********************************************************************/

        /*************** Set left and right speed ****************************/
        // Original Option:
        // This option might result in a max speed of up to 2 which loses
        // information and may not take angle error into account under some,
        // commonly occurring, circumstances.
        double leftSpeed = distanceErrorSpeed + angleErrorSpeed;
        double rightSpeed = distanceErrorSpeed - angleErrorSpeed;

        System.out.println("DE: " + distanceError + ", DB: " + mSmoothControlParams.mDeadband + ", DES: " + distanceErrorSpeed + ", ATT: "
                + angleToTarget + ", RAE: " + rawAngleError + ", AAE: " + adjustedAngleError + ", AE: " + angleError + ", AES: " + angleErrorSpeed
                + ", L: " + leftSpeed + ", R: " + rightSpeed + ", Lp: " + leftAdjustment_p + ", Rp: " + rightAdjustment_p + ", La: "
                + leftAdjustment_a + ", Ra: " + rightAdjustment_a);

        /************** Check if finished ************************************/
        boolean isFinished = false;
        if (mInDeadbandHelper.isFinished(Math.abs(distanceError) < mSmoothControlParams.mDeadband))
        {
            System.out.println("***************** driveSmoothlyToPosition Finished!!! *******************");
            mDriveTrain.stop();
            isFinished = true;
            cancelAction();
        }
        else
        {
            // Original Option
            // mDriveTrain.setLeftRightSpeed(leftSpeed, rightSpeed);

            // "Complimentary Filter" Option
            // mDriveTrain.setLeftRightSpeed(leftAdjustment_a,
            // rightAdjustment_a);

            // Proportional to angle error Option.
            mDriveTrain.setLeftRightSpeed(leftAdjustment_p, rightAdjustment_p);
        }

        return isFinished;
    }

    @Override
    public void cancelAction()
    {
        mControlMode = ControlMode.Off;
        mGoToPositionSubstep = GoToPositionSubsteps.NoAction;
    }

    @Override
    public void setSmoothGoalDeadband(double aDeadbandDistance)
    {
        mSmoothControlParams.mDeadband = aDeadbandDistance;
    }

}
