package com.snobot.lib.vision;

import com.snobot.lib.vision.MjpegReceiver.ImageReceiver;

public class MjpegForwarder implements ImageReceiver
{
    private int mBindPort;

    public MjpegForwarder(int aBindPort)
    {
        mBindPort = aBindPort;
        MjpgServer.getInstance(mBindPort);
    }

    @Override
    public void onImage(byte[] imageBytes)
    {
        MjpgServer.getInstance(mBindPort).update(imageBytes);
    }

}
