package com.snobot2017.vision;

import com.snobot.lib.ISubsystem;
import com.snobot2017.PortMappings2017;
import com.snobot2017.Properties2017;
import com.snobot2017.joystick.IDriverJoystick;
import com.snobot2017.joystick.IOperatorJoystick;
import com.snobot2017.joystick.IVisionJoystick;
import com.snobot2017.vision.VisionAdbServer.CameraFacingDirection;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class VisionManager implements ISubsystem
{
    private VisionAdbServer mVisionServer;
    
    IVisionJoystick mOperatorJoystick;
    
    public VisionManager(IVisionJoystick aOperatorJoystick)
    {
        if (Properties2017.sENABLE_VISION.getValue())
        {
            mVisionServer = new VisionAdbServer(PortMappings2017.sADB_BIND_PORT, PortMappings2017.sAPP_MJPEG_PORT,
                    PortMappings2017.sAPP_MJPEG_FORWARDED_PORT);
        }
        mOperatorJoystick = aOperatorJoystick;
    }

    @Override
    public void init()
    {
        // mVisionServer.restartApp();

        // TODO Add log headers
    }

    @Override
    public void update()
    {

    }

    @Override
    public void control()
    {
        if(mOperatorJoystick.iterateAppView())
        {
            mVisionServer.iterateShownImage();
        }
        else if(mOperatorJoystick.switchToFrontCamera())
        {
            mVisionServer.setCameraDirection(CameraFacingDirection.Front);
        }
        else if (mOperatorJoystick.switchToRearCamera())
        {
            mVisionServer.setCameraDirection((CameraFacingDirection.Rear));
        }
        else if(mOperatorJoystick.restartApp())
        {
            mVisionServer.restartApp();
        }
    }

    @Override
    public void rereadPreferences()
    {

    }

    @Override
    public void updateSmartDashboard()
    {
        SmartDashboard.putBoolean("App Connected", mVisionServer.isConnected());
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

}
