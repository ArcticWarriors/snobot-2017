package com.snobot2017.vision;

import com.snobot.lib.ISubsystem;
import com.snobot.lib.vision.MjpegForwarder;
import com.snobot.lib.vision.MjpegReceiver;
import com.snobot2017.PortMappings2017;
import com.snobot2017.Properties2017;

public class VisionManager implements ISubsystem
{
    private VisionAdbServer mVisionServer;

    public VisionManager()
    {
        if (Properties2017.sENABLE_VISION.getValue())
        {
            mVisionServer = new VisionAdbServer(PortMappings2017.sADB_BIND_PORT, PortMappings2017.sAPP_MJPEG_PORT,
                    PortMappings2017.sAPP_MJPEG_FORWARDED_PORT);

            MjpegReceiver visionReceiver = new MjpegReceiver();
            visionReceiver.start("http://127.0.0.1:" + PortMappings2017.sAPP_MJPEG_FORWARDED_PORT);
            visionReceiver.addImageReceiver(new MjpegForwarder(12345));
        }
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

    }

    @Override
    public void rereadPreferences()
    {

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

    }

}
