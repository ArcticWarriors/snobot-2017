package com.snobot.lib.vision;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Logger;

public class MjpgServer
{
    private static final Logger sLOGGER = Logger.getLogger("MjpgServer");
    private static final String K_BOUNDARY = "boundary";
    private static final int sDEFAULT_PORT = 5800;

    private static MjpgServer sInst = null;

    public static MjpgServer getInstance()
    {
        return getInstance(sDEFAULT_PORT);
    }

    public static MjpgServer getInstance(int aPort)
    {
        if (sInst == null)
        {
            sInst = new MjpgServer(aPort);
        }
        else if (sInst.mBindPort != aPort)
        {
            throw new IllegalArgumentException("Mismatching bind port! Original: " + sInst.mBindPort + ", New: " + aPort);
        }
        return sInst;
    }

    private ArrayList<Connection> mConnections = new ArrayList<>();
    private Object mLock = new Object();

    private class Connection
    {

        private Socket mSocket;

        public Connection(Socket s)
        {
            mSocket = s;
        }

        public boolean isAlive()
        {
            return !mSocket.isClosed() && mSocket.isConnected();
        }

        public void start()
        {
            try
            {
                sLOGGER.info("Starting a connection!");
                OutputStream stream = mSocket.getOutputStream();
                stream.write(("HTTP/1.0 200 OK\r\n" + "Server: cheezyvision\r\n" + "Cache-Control: no-cache\r\n" + "Pragma: no-cache\r\n"
                        + "Connection: close\r\n" + "Content-Type: multipart/x-mixed-replace;boundary=--" + K_BOUNDARY + "\r\n").getBytes());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        public void writeImageUpdate(byte[] buffer)
        {
            if (!isAlive())
            {
                return;
            }
            OutputStream stream = null;
            try
            {
                stream = mSocket.getOutputStream();

                String output;
                output = "\r\n--" + K_BOUNDARY + "\r\n";
                output += "Content-type: image/jpeg\r\n";
                output += "Content-Length: " + buffer.length + "\r\n";
                output += "\r\n";

                stream.write(output.getBytes());
                stream.write(buffer);
                stream.flush();

            }
            catch (IOException e)
            {
                // There is a broken pipe exception being thrown here I cannot
                // figure out.
            }
        }

    }

    private final int mBindPort;
    private ServerSocket mServerSocket;
    private boolean mRunning;
    private Thread mRunThread;

    private MjpgServer(int aBindPort)
    {
        mBindPort = aBindPort;
        try
        {
            mServerSocket = new ServerSocket(mBindPort);
            mRunning = true;
            mRunThread = new Thread(runner);
            mRunThread.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void update(byte[] bytes)
    {
        if (bytes == null)
        {
            return;
        }

        synchronized (mLock)
        {
            ArrayList<Integer> badIndices = new ArrayList<>(mConnections.size());
            try
            {
                for (int i = 0; i < mConnections.size(); i++)
                {
                    Connection c = mConnections.get(i);
                    if (c == null || !c.isAlive())
                    {
                        badIndices.add(i);
                    }
                    else
                    {
                        c.writeImageUpdate(bytes);
                    }
                }
                for (int i : badIndices)
                {
                    mConnections.remove(i);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    Runnable runner = new Runnable()
    {

        @Override
        public void run()
        {
            while (mRunning)
            {
                try
                {
                    sLOGGER.info("Waiting for connections");
                    Socket s = mServerSocket.accept();
                    sLOGGER.info("Got a socket: " + s);
                    Connection c = new Connection(s);
                    synchronized (mLock)
                    {
                        mConnections.add(c);
                    }
                    c.start();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    };
}