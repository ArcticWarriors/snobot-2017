package com.snobot.sd.util;

import edu.wpi.first.smartdashboard.gui.StaticWidget;

/**
 *
 * @author PJ
 */
public abstract class AutoUpdateWidget extends StaticWidget
{
    private static final long serialVersionUID = -5324757383577336302L;

    private final BGThread mBgThread;
    private final long mUpdateMs;

    protected final boolean mDebug;

    public AutoUpdateWidget(boolean aDebug, long update_ms)
    {
        mDebug = aDebug;
        mUpdateMs = update_ms;

        mBgThread = new BGThread();
        mBgThread.start();
    }

    public class BGThread extends Thread
    {

        boolean destroyed = false;

        public BGThread()
        {
            super("Camera Background");
        }

        @Override
        public void run()
        {
            while (!destroyed)
            {
                try
                {
                    poll();
                    Thread.sleep(mUpdateMs);
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }

        }

        @Override
        public void destroy()
        {
            destroyed = true;
        }
    }

    protected abstract void poll() throws Exception;
}
