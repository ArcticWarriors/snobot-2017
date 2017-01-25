package com.snobot.lib.vision;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

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
    public void onImage(BufferedImage image)
    {
        try
        {
            if (image != null)
            {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", baos);
                baos.flush();
                byte[] imageInByte = baos.toByteArray();

                MjpgServer.getInstance(mBindPort).update(imageInByte);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
