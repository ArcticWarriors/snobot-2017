package com.snobot2017.vision;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.snobot.lib.modules.ISubsystem;
import com.snobot.lib.vision.MjpegForwarder;
import com.snobot.lib.vision.MjpegReceiver;
import com.snobot2017.PortMappings2017;
import com.snobot2017.Properties2017;
import com.snobot2017.SmartDashBoardNames;
import com.snobot2017.SnobotActor.ISnobotActor;
import com.snobot2017.joystick.IVisionJoystick;
import com.snobot2017.positioner.IPositioner;
import com.snobot2017.vision.VisionAdbServer.CameraFacingDirection;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionManager implements ISubsystem
{
    private VisionAdbServer mVisionServer;
    private IVisionJoystick mOperatorJoystick;
    private ISnobotActor mSnobotActor;
    private IPositioner mPositioner;
    private StateManager mStateManager;
    private List<TargetLocation> mLatestTargetInformation;
    private String mTargetMessage;
    private RobotBase mSnobotState;

    public VisionManager(IPositioner aPositioner, ISnobotActor aSnobotActor, IVisionJoystick aOperatorJoystick, RobotBase aSnobotState)
    {
        if (Properties2017.sENABLE_VISION.getValue())
        {
            mVisionServer = new VisionAdbServer(PortMappings2017.sADB_BIND_PORT, PortMappings2017.sAPP_MJPEG_PORT, PortMappings2017.sAPP_MJPEG_PORT);
        }

        mPositioner = aPositioner;
        mSnobotActor = aSnobotActor;
        mOperatorJoystick = aOperatorJoystick;
        mSnobotState = aSnobotState;

        mStateManager = new StateManager();
        mLatestTargetInformation = new ArrayList<>();
        mTargetMessage = "";

        MjpegForwarder forwarder = new MjpegForwarder(PortMappings2017.sAPP_MJPEG_FORWARDED_PORT);

        MjpegReceiver receiver = new MjpegReceiver();
        receiver.addImageReceiver(forwarder);
        receiver.start("http://127.0.0.01:" + PortMappings2017.sAPP_MJPEG_PORT);
    }

    @Override
    public void initializeLogHeaders()
    {
        // mVisionServer.restartApp();

        // TODO Add log headers
    }

    @Override
    public void update()
    {
        double x = mPositioner.getXPosition();
        double y = mPositioner.getYPosition();
        double angle = mPositioner.getOrientationDegrees();
        double timestamp = mVisionServer.getTimestamp();

        mStateManager.saveRobotState(timestamp, x, y, angle);

        if (mVisionServer.hasFreshImage())
        {
            updateTargetInformation(timestamp);
        }
    }

    @SuppressWarnings("unchecked")
    private void updateTargetInformation(double aTimestamp)
    {
        mStateManager.updateCameraFindings(mVisionServer.getLatestTargetUpdate());
        mLatestTargetInformation.clear();

        SavedRobotState robotState = mStateManager.getStateHistory(aTimestamp);

        JSONObject targetUpdateJson = new JSONObject();
        JSONArray targets = new JSONArray();

        for (TargetLocation targetInfo : mStateManager.getTargets())
        {
            TargetLocation target = new TargetLocation();
            target.mX = targetInfo.mX;
            target.mY = targetInfo.mY;
            target.mAmbigious = targetInfo.mAmbigious;
            mLatestTargetInformation.add(target);

            JSONObject jsonTarget = new JSONObject();
            jsonTarget.put("x", target.mX);
            jsonTarget.put("y", target.mY);
            jsonTarget.put("ambiguous", target.mAmbigious);
            targets.add(jsonTarget);
        }

        targetUpdateJson.put("robot_x", robotState.mRobotX);
        targetUpdateJson.put("robot_y", robotState.mRobotY);
        targetUpdateJson.put("targets", targets);

        mTargetMessage = targetUpdateJson.toJSONString();

        double tooCloseDistance = Properties2017.sVISION_TOO_CLOSE_DISTANCE.getValue();
        if (mSnobotState.isOperatorControl() && mSnobotActor.isInAction())
        {
            if (!mLatestTargetInformation.isEmpty())
            {
                TargetLocation target = mLatestTargetInformation.get(0);
                double dx = target.mX - robotState.mRobotX;
                double dy = target.mY - robotState.mRobotY;
                double distance = Math.sqrt(dx * dx + dy * dy);
                System.out.println("Vision: Distance: " + distance + ", Ambiguous = " + (target.mAmbigious));

                if (!target.mAmbigious && (tooCloseDistance < distance))
                {
                    mSnobotActor.setGoToPositionSmoothlyGoal(target.mX, target.mY);
                }
                else
                {
                    System.out.println("Ignoring update!!!!!!!!");
                    mLatestTargetInformation.clear();
                }
            }
            else
            {
                System.out.println("Target list is empty!!!!!!!!!!!!!!!");
            }
        }
    }

    @Override
    public void control()
    {
        if (mOperatorJoystick.iterateAppView())
        {
            mVisionServer.iterateShownImage();
        }
        else if (mOperatorJoystick.switchToFrontCamera())
        {
            mVisionServer.setCameraDirection(CameraFacingDirection.Front);
        }
        else if (mOperatorJoystick.switchToRearCamera())
        {
            mVisionServer.setCameraDirection((CameraFacingDirection.Rear));
        }
        else if (mOperatorJoystick.restartApp())
        {
            mVisionServer.restartApp();
        }

        if (mOperatorJoystick.driveToPositionInSteps())
        {
            if (!mSnobotActor.isInAction())
            {
                if (!mLatestTargetInformation.isEmpty())
                {
                    // On the first update, send the target even if it is
                    // ambiguous
                    TargetLocation target = mLatestTargetInformation.get(0);
                    mSnobotActor.setGoToPositionSmoothlyGoal(target.mX, target.mY);
                }
            }
            boolean finished = mSnobotActor.executeControlMode();
            if (finished)
            {
                mOperatorJoystick.turnOffActions();
            }
        }
        else if (mOperatorJoystick.driveSmoothlyToPosition())
        {
            if (!mSnobotActor.isInAction())
            {
                if (!mLatestTargetInformation.isEmpty())
                {
                    // On the first update, send the target even if it is
                    // ambiguous
                    TargetLocation target = mLatestTargetInformation.get(0);
                    mSnobotActor.setGoToPositionSmoothlyGoal(target.mX, target.mY);
                }
            }

            boolean finished = mSnobotActor.executeControlMode();
            if (finished)
            {
                mOperatorJoystick.turnOffActions();
            }
        }
        else
        {
            mSnobotActor.cancelAction();
            mOperatorJoystick.turnOffActions();
        }

    }

    public boolean seesTarget()
    {
        return !mLatestTargetInformation.isEmpty();
    }

    public List<TargetLocation> getLatestTargetInformation()
    {
        return mLatestTargetInformation;
    }

    @Override
    public void updateSmartDashboard()
    {
        SmartDashboard.putBoolean(SmartDashBoardNames.sVISION_APP_CONNECTED, mVisionServer.isConnected());
        SmartDashboard.putString(SmartDashBoardNames.sVISION_TARGETS, mTargetMessage);

        // if (mLatestUpdate == null)
        // {
        // SmartDashboard.putString(SmartDashBoardNames.sVISION_TARGETS,
        // mTargetMessage);
        // }
        // else
        // {
        // SmartDashboard.putString(SmartDashBoardNames.sVISION_TARGETS,
        // mLatestUpdate.toJsonString());
        // }
    }

    @Override
    public void updateLog()
    {
        // TODO Auto-generated method stub
    }

    @Override
    public void stop()
    {

    }

    public void startRecordingImages(String aName)
    {
        String matchInfo = SmartDashboard.getString("MatchNumber", "");
        mVisionServer.sendStartRecordingMessage(matchInfo + "_" + aName);
    }

    public void stopRecordingImages()
    {
        mVisionServer.sendStopRecordingMessage();
    }

}
