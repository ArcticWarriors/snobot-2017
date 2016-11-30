package com.snobot2017.vision;

import com.snobot.xlib.ISubsystem;
import com.snobot2017.joystick.IDriverJoystick;

public class VisionManager implements ISubsystem
{
    private IDriverJoystick mDriverJoystick;
    private VisionAdbServer mVisionServer;
    private boolean mShouldTakePicture;

    public VisionManager(IDriverJoystick aDriverJoystick, VisionAdbServer aVisionServer)
    {
        mDriverJoystick = aDriverJoystick;
        mVisionServer = aVisionServer;
    }

    @Override
    public void init()
    {

    }

    @Override
    public void update()
    {
        mShouldTakePicture = mDriverJoystick.isTakePicture();
    }

    @Override
    public void control()
    {
        if (mShouldTakePicture)
        {
            System.out.println("Should be taking picture");
            mVisionServer.takePicture();
        }
    }

    @Override
    public void rereadPreferences()
    {

    }

    @Override
    public void updateSmartDashboard()
    {

    }

    @Override
    public void updateLog()
    {

    }

    @Override
    public void stop()
    {

    }

}
