package com.snobot.simulator.robot_sim.snobot2017;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.logging.Logger;

import org.json.simple.JSONObject;

/**
 * Created by PJ on 11/24/2016.
 */

public class MockAppConnection
{
    private static final Logger sLOGGER = Logger.getLogger("MockAppConnection");

    private static final int sSEND_HEARTBEAT_PERIOD = 1000;

    private boolean mRunning = true;
    private Thread mWriteThread;

    private Socket mSocket;

    public MockAppConnection()
    {

    }

    public void start()
    {
        try
        {
            mSocket = new Socket("localhost", 8254);
            mWriteThread = new Thread(new WriteThread(), "MockWriteThread");
            mWriteThread.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private class WriteThread implements Runnable
    {
        @Override
        public void run()
        {
            while (mRunning)
            {
                try
                {
                    Thread.sleep(sSEND_HEARTBEAT_PERIOD);
                    sendHeartbeatMessage();
                }
                catch (InterruptedException e)
                {
                    sLOGGER.severe("Couldn't poll queue");
                }
            }
        }
    }


    private synchronized boolean sendToWire(ByteBuffer message)
    {
        if (mSocket != null)
        {
            try
            {
                OutputStream os = mSocket.getOutputStream();
                os.write(message.array());
                return true;
            }
            catch (IOException e)
            {
                sLOGGER.warning("Could not send data to socket, try to reconnect");
                mSocket = null;
            }
        }
        return false;
    }

    protected void sendHeartbeatMessage()
    {
        String message = "{\"type\": \"heartbeat\"}\n";
        sendToWire(ByteBuffer.wrap(message.getBytes()));
    }

    public void send(JSONObject aObject)
    {
        String message = aObject.toJSONString() + "\n";
        sendToWire(ByteBuffer.wrap(message.getBytes()));
    }

}
