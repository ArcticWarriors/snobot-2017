package com.snobot.lib.vision;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MjpegReceiver
{
    private static final int[] START_BYTES = new int[]{ 0xFF, 0xD8 };
    private static final byte[] END_BYTES = "--boundary".getBytes();
    private boolean mRunning;
    private int mReadTimeout;
    private List<ImageReceiver> mImageRecievers;

    public interface ImageReceiver
    {
        public void onImage(byte[] image);
    }

    public MjpegReceiver()
    {
        mImageRecievers = new ArrayList<>();
        mReadTimeout = 0;
    }

    public void addImageReceiver(ImageReceiver imageReceiver)
    {
        mImageRecievers.add(imageReceiver);
    }

    /**
     * Starts the receiving thread. Thread will run until stop() is called
     * 
     * @param imageUrl
     *            The URL to connect to
     */
    public void start(String imageUrl)
    {
        mRunning = true;

        new Thread(new ReceiveThread(imageUrl), "MjpegReciever").start();
    }

    /**
     * Stops the receiving thread
     */
    public void stop()
    {
        mRunning = false;
    }

    /**
     * Sets the read timeout for the HTTP Socket to use. This takes affect the
     * next time a connection is made, so if you are already connected nothing
     * will happen until the next time connection is lost
     * 
     * @param aTimeout
     *            The timeout in milliseconds. 0 means infinite
     */
    public void setReadTimeout(int aTimeout)
    {
        mReadTimeout = aTimeout;
    }

    private byte[] parseImage(InputStream stream, ByteArrayOutputStream imageBuffer) throws IOException
    {
//        System.out.println("Buffer size : " + stream.available());
        imageBuffer.reset();
        for (int i = 0; i < START_BYTES.length;)
        {
            int b = stream.read();
            if (b == START_BYTES[i])
            {
                i++;
            }
            else
            {
                i = 0;
            }
        }
//        System.out.println("Got start bytes...");

        for (int i = 0; i < START_BYTES.length; ++i)
        {
            imageBuffer.write(START_BYTES[i]);
        }

        for (int i = 0; i < END_BYTES.length;)
        {
            if (imageBuffer.size() > 1000000)
            {
                imageBuffer.close();
                return null;
            }
            int b = stream.read();
            imageBuffer.write(b);
            if (b == END_BYTES[i])
            {
                i++;
            }
            else
            {
                i = 0;
            }
        }

        byte[] imageBytes = imageBuffer.toByteArray();
        return imageBytes;

    }

    private void publishImage(byte[] imageData)
    {
        for (ImageReceiver recv : mImageRecievers)
        {
            recv.onImage(imageData);
        }
    }

    private class ReceiveThread implements Runnable
    {
        private final String mImageUrl;
        private boolean mConnected;

        public ReceiveThread(String aImageUrl)
        {
            mImageUrl = aImageUrl;
        }

        @Override
        public void run()
        {
            System.out.println("Attempting to connect to camera at " + mImageUrl);
            while (mRunning)
            {
                try
                {
                    ByteArrayOutputStream imageBuffer = new ByteArrayOutputStream();
                    InputStream stream = null;

                    URL url = new URL(mImageUrl);
                    HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setReadTimeout(mReadTimeout);
                    stream = urlConn.getInputStream();
                    mConnected = true;

                    while (mRunning && mConnected)
                    {
                        byte[] imageData = parseImage(stream, imageBuffer);
                        publishImage(imageData);
                    }
                }
                catch (SocketException e)
                {
                    mConnected = false;
                    publishImage(null);
                }
                catch (Exception e)
                {
                    System.err.println("Could not save image : " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    };
}
