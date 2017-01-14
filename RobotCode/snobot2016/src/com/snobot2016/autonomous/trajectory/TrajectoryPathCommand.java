package com.snobot2016.autonomous.trajectory;

import java.util.ArrayList;
import java.util.List;

import com.snobot.xlib.Utilities;
import com.snobot.xlib.motion_profile.trajectory.IdealSplineSerializer;
import com.snobot.xlib.motion_profile.trajectory.SplineSegment;
import com.snobot2016.Properties2016;
import com.snobot2016.SmartDashBoardNames;
import com.snobot2016.drivetrain.IDriveTrain;
import com.snobot2016.positioner.IPositioner;
import com.team254.lib.trajectory.Path;
import com.team254.lib.trajectory.Trajectory;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;

public class TrajectoryPathCommand extends Command
{
    private IDriveTrain mDrivetrain;
    private IPositioner mPositioner;
    private TrajectoryFollower followerLeft = new TrajectoryFollower("left");
    private TrajectoryFollower followerRight = new TrajectoryFollower("right");
    private Path mPath;
    private double mStartingLeftDistance;
    private double mStartingRightDistance;
    private double mKTurn;

    private ITable mTable;

    private double mLastLeftDistance;
    private double mLastRightDistance;

    private String mSDIdealName;
    private String mSDRealName;

    public TrajectoryPathCommand(IDriveTrain aDrivetrain, IPositioner aPositioner, Path aPath)
    {
        mDrivetrain = aDrivetrain;
        mPositioner = aPositioner;
        mPath = aPath;

        mTable = NetworkTable.getTable(SmartDashBoardNames.sSPLINE_NAMESPACE);
        mSDIdealName = SmartDashBoardNames.sSPLINE_IDEAL_POINTS;
        mSDRealName = SmartDashBoardNames.sSPLINE_REAL_POINT;

        double kP = Properties2016.sDRIVE_PATH_KP.getValue();
        double kD = Properties2016.sDRIVE_PATH_KD.getValue();
        double kVelocity = Properties2016.sDRIVE_PATH_KV.getValue();
        double kAccel = Properties2016.sDRIVE_PATH_KA.getValue();

        mKTurn = Properties2016.sSPLINE_TURN_FACTOR.getValue();

        followerLeft.configure(kP, 0, kD, kVelocity, kAccel);
        followerRight.configure(kP, 0, kD, kVelocity, kAccel);

        followerLeft.reset();
        followerRight.reset();

        followerLeft.setTrajectory(aPath.getLeftWheelTrajectory());
        followerRight.setTrajectory(aPath.getRightWheelTrajectory());

        if (mTable.getString(mSDIdealName, "").isEmpty())
        {
            sendIdealPath();
        }
    }

    @Override
    protected void initialize()
    {
        mStartingLeftDistance = mDrivetrain.getLeftEncoderDistance();
        mStartingRightDistance = mDrivetrain.getRightEncoderDistance();

        sendIdealPath();
    }

    @Override
    protected void execute()
    {
        double dt = .02;

        double distanceL = mDrivetrain.getLeftEncoderDistance() - mStartingLeftDistance;
        double distanceR = mDrivetrain.getRightEncoderDistance() - mStartingRightDistance;

        double speedLeft = followerLeft.calculate(distanceL);
        double speedRight = followerRight.calculate(distanceR);

        double goalHeading = followerLeft.getHeading();
        double observedHeading = mPositioner.getOrientationDegrees();

        double angleDiff = Utilities.getDifferenceInAngleDegrees(observedHeading, goalHeading);

        double turn = mKTurn * angleDiff;

        mDrivetrain.setLeftRightSpeed(speedLeft + turn, speedRight - turn);

        SplineSegment segment = new SplineSegment();
        segment.mLeftSidePosition = distanceL;
        segment.mLeftSideVelocity = (distanceL - mLastLeftDistance) / dt;
        segment.mRightSidePosition = distanceR;
        segment.mRightSideVelocity = (distanceR - mLastRightDistance) / dt;
        segment.mRobotHeading = Utilities.boundAngleNeg180to180Degrees(observedHeading);
        segment.mAverageX = mPositioner.getXPosition();
        segment.mAverageY = mPositioner.getYPosition();

        String point_info = followerLeft.getCurrentSegment() + "," + IdealSplineSerializer.serializePathPoint(segment);

        mTable.putString(mSDRealName, point_info);

        mLastLeftDistance = distanceL;
        mLastRightDistance = distanceR;
    }

    @Override
    protected boolean isFinished()
    {
        return followerLeft.isFinishedTrajectory();
    }

    @Override
    protected void end()
    {
        mDrivetrain.stop();

    }

    @Override
    protected void interrupted()
    {

    }

    private void sendIdealPath()
    {
        List<SplineSegment> segments = new ArrayList<SplineSegment>();

        Trajectory left = mPath.getLeftWheelTrajectory();
        Trajectory right = mPath.getRightWheelTrajectory();

        for (int i = 0; i < left.getNumSegments(); ++i)
        {
            SplineSegment segment = new SplineSegment();
            segment.mLeftSidePosition = left.getSegment(i).pos;
            segment.mLeftSideVelocity = left.getSegment(i).vel;
            segment.mRightSidePosition = right.getSegment(i).pos;
            segment.mRightSideVelocity = right.getSegment(i).vel;
            segment.mRobotHeading = Utilities.boundAngleNeg180to180Degrees(right.getSegment(i).heading);
            segment.mAverageX = right.getSegment(i).y; // Flipped on purpose
            segment.mAverageY = right.getSegment(i).x; // Flipped on purpose

            segments.add(segment);
        }

        mTable.putString(mSDIdealName, IdealSplineSerializer.serializePath(segments));
    }

}
