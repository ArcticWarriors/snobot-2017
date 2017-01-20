package com.snobot.lib.external_connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class RobotConnectionServer
{
    protected static final Logger sLOGGER = Logger.getLogger("RobotConnectionServer");

    private ServerSocket mServerSocket;
    private boolean mIsConnected = false;

    private boolean mRunning = true;
    private ArrayList<ServerThread> mServerThreads = new ArrayList<>();

    public RobotConnectionServer(int bindPort)
    {
        try
        {
            mServerSocket = new ServerSocket(bindPort);

            new Thread(mConnectionThread, "RobotConnectionServer::ConnectionMonitor").start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public boolean isConnected()
    {
        return mIsConnected;
    }

    protected void send(ByteBuffer buffer)
    {
        for (ServerThread thread : mServerThreads)
        {
            if (thread.isAlive())
            {
                thread.send(buffer);
            }
        }
    }

    private class ServerThread implements Runnable
    {
        private Socket mSocket;

        public ServerThread(Socket socket)
        {
            mSocket = socket;
        }

        public void send(ByteBuffer message)
        {
            if (mSocket != null && mSocket.isConnected())
            {
                try
                {
                    OutputStream os = mSocket.getOutputStream();
                    os.write(message.array());
                }
                catch (IOException e)
                {
                    sLOGGER.log(Level.SEVERE, "Could not send data to socket", e);
                }
            }
        }

        public boolean isAlive()
        {
            return mSocket != null && mSocket.isConnected() && !mSocket.isClosed();
        }

        @Override
        public void run()
        {
            if (mSocket == null)
            {
                return;
            }
            try
            {
                InputStream is = mSocket.getInputStream();
                byte[] buffer = new byte[2048];
                int read;
                while (mSocket.isConnected() && (read = is.read(buffer)) != -1)
                {
                    double timestamp = getTimestamp();
                    String messageRaw = new String(buffer, 0, read);
                    String[] messages = messageRaw.split("\n");
                    for (String message : messages)
                    {
                        handleMessage(message, timestamp);
                    }
                }
                sLOGGER.log(Level.INFO, "Socked Disconnected: " + mSocket);
                mServerThreads.remove(this);
            }
            catch (IOException e)
            {
                // Timeout is OK
                sLOGGER.log(Level.INFO, "Socked Disconnected (timeout): " + mSocket);
                mServerThreads.remove(this);
            }

            if (mSocket != null)
            {
                try
                {
                    mSocket.close();
                }
                catch (IOException e)
                {
                    sLOGGER.log(Level.SEVERE, "Could not close socket", e);
                }
            }
        }
    }

    private Runnable mConnectionThread = new Runnable()
    {

        @Override
        public void run()
        {
            while (mRunning)
            {
                try
                {
                    Socket p = mServerSocket.accept();
                    sLOGGER.log(Level.INFO, "Accepted Socket: " + p);

                    ServerThread s = new ServerThread(p);
                    new Thread(s, "RobotConnectionServer::ServerConnection").start();
                    mServerThreads.add(s);
                }
                catch (IOException e)
                {
                    sLOGGER.log(Level.SEVERE, "Issue accepting incoming sockets", e);
                }
                finally
                {
                    try
                    {
                        Thread.sleep(100);
                    }
                    catch (InterruptedException e)
                    {
                        sLOGGER.log(Level.SEVERE, "Interrupted", e);
                    }
                }
            }
        }
    };

    public abstract void handleMessage(String message, double timestamp);

    protected abstract double getTimestamp();
}
