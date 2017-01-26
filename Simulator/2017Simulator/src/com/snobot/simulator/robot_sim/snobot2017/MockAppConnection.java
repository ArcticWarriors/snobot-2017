package com.snobot.simulator.robot_sim.snobot2017;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.logging.Logger;

import com.snobot2017.PortMappings2017;

/**
 * Created by PJ on 11/24/2016.
 */

public class MockAppConnection
{

    private static final Logger sLOGGER = Logger.getLogger("MockAppConnection");

    private static final int sSEND_HEARTBEAT_PERIOD = 1000;

    private boolean mRunning = true;
    private Thread mWriteThread;

    private InetAddress mHostAddress;
    private DatagramSocket mSocket;

    public MockAppConnection()
    {
        try
        {
            mHostAddress = InetAddress.getByName("localhost");
            mSocket = new DatagramSocket(13000, mHostAddress);
            mWriteThread = new Thread(new WriteThread(), "MockWriteThread");
            mWriteThread.start();
        }
        catch (SocketException | UnknownHostException e)
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
                byte[] buffer = message.array();
                DatagramPacket msg = new DatagramPacket(buffer, buffer.length, mHostAddress, PortMappings2017.sADB_BIND_PORT);
                mSocket.send(msg);
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
        String message = "heartbeat\n";
        sendToWire(ByteBuffer.wrap(message.getBytes()));
    }

}
