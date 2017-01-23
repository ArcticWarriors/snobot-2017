package snobot.com.visionapp.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by PJ on 11/24/2016.
 */

public abstract class RobotConnection {

    private static final String sTAG = "RobotConnection";

    public static final int sDEFAULT_CONNECTION_PORT = 8254;
    public static final String sDEFAULT_CONNECTION_HOST = "localhost";
    public static final int sCONNECTION_THREAD_SLEEP_TIME = 100;
    public static final int sSEND_HEARTBEAT_PERIOD = 1000;
    public static final int sHEARTBEAT_TIMEOUT = sSEND_HEARTBEAT_PERIOD * 5;
    public static final int sWRITE_THREAD_TIMEOUT = sSEND_HEARTBEAT_PERIOD * 3;

    private int mSendPort;
    private String mSendAddress;
    private boolean mRunning = true;
    private boolean mConnected = false;
    volatile private Socket mSocket;
    private Thread mConnectionMonitorThread;
    private Thread mReadThr;
    private Thread mWriteThread;

    private long mLastHeartbeatSendTime = System.currentTimeMillis();
    protected long mLastHeartbeatReceiveTime = 0;

    private ArrayBlockingQueue<ByteBuffer> mMessageQueue = new ArrayBlockingQueue<>(30);


    public RobotConnection(String host, int port) {
        mSendAddress = host;
        mSendPort = port;
    }

    public RobotConnection() {
        this(sDEFAULT_CONNECTION_HOST, sDEFAULT_CONNECTION_PORT);
    }

    private class WriteThread implements Runnable {

        private static final String sTAG = "WriteThread";

        @Override
        public void run() {
            while (mRunning) {
                ByteBuffer nextToSend = null;
                try {
                    nextToSend = mMessageQueue.poll(sWRITE_THREAD_TIMEOUT, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    Log.e(sTAG, "Couldn't poll queue");
                }
                if (nextToSend == null) {
                    continue;
                }
                sendToWire(nextToSend);
            }
        }
    }


    private class ReadThread implements Runnable {

        private static final String sTAG = "ReadThread";


        @Override
        public void run() {
            while (mRunning) {
                if (mSocket != null || mConnected) {
                    BufferedReader reader;
                    try {
                        InputStream is = mSocket.getInputStream();
                        reader = new BufferedReader(new InputStreamReader(is));
                    } catch (IOException e) {
                        Log.e(sTAG, "Could not get input stream");
                        continue;
                    } catch (NullPointerException npe) {
                        Log.e(sTAG, "socket was null");
                        continue;
                    }
                    String jsonMessage = null;
                    try {
                        jsonMessage = reader.readLine();
                    } catch (IOException e) {
                        //Timeout is OK
                    }
                    if (jsonMessage != null) {
                        handleMessage(jsonMessage);
                    }
                } else {
                    try {
                        Thread.sleep(100, 0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private class ConnectionMonitor implements Runnable {

        @Override
        public void run() {
            while (mRunning) {
                try {
                    if (mSocket == null || !mSocket.isConnected() && !mConnected) {
                        tryConnect();
                        Thread.sleep(250, 0);
                    }

                    long now = System.currentTimeMillis();

                    if (now - mLastHeartbeatSendTime > sSEND_HEARTBEAT_PERIOD) {
                        sendHeartbeatMessage();
                        mLastHeartbeatSendTime = now;
                    }

                    long timeSinceHeartbeat = Math.abs(mLastHeartbeatReceiveTime - mLastHeartbeatSendTime);
                    if (timeSinceHeartbeat > sHEARTBEAT_TIMEOUT && mConnected) {
                        mConnected = false;
                        onRobotDisconnected();
                    }
                    if (timeSinceHeartbeat < sHEARTBEAT_TIMEOUT && !mConnected) {
                        mConnected = true;
                        onRobotConnected();
                    }

                    Thread.sleep(sCONNECTION_THREAD_SLEEP_TIME, 0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    synchronized private void tryConnect() {
        if (mSocket == null) {
            try {
                mSocket = new Socket(mSendAddress, mSendPort);
                mSocket.setSoTimeout(100);
            } catch (IOException e) {
                Log.w(sTAG, "Could not connect");
                mSocket = null;
            }
        }
    }

    synchronized private void stop() {
        mRunning = false;
        if (mConnectionMonitorThread != null && mConnectionMonitorThread.isAlive()) {
            try {
                mConnectionMonitorThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (mWriteThread != null && mWriteThread.isAlive()) {
            try {
                mWriteThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (mReadThr != null && mReadThr.isAlive()) {
            try {
                mReadThr.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    synchronized public void start() {
        mRunning = true;

        if (mWriteThread == null || !mWriteThread.isAlive()) {
            mWriteThread = new Thread(new WriteThread());
            mWriteThread.start();
        }

        if (mReadThr == null || !mReadThr.isAlive()) {
            mReadThr = new Thread(new ReadThread());
            mReadThr.start();
        }

        if (mConnectionMonitorThread == null || !mConnectionMonitorThread.isAlive()) {
            mConnectionMonitorThread = new Thread(new ConnectionMonitor());
            mConnectionMonitorThread.start();
        }
    }


    synchronized public void restart() {
        stop();
        start();
    }

    synchronized public boolean isConnected() {
        return mSocket != null && mSocket.isConnected() && mConnected;
    }

    private synchronized boolean sendToWire(ByteBuffer message) {
        if (mSocket != null && mSocket.isConnected()) {
            try {
                OutputStream os = mSocket.getOutputStream();
                os.write(message.array());
                return true;
            } catch (IOException e) {
                Log.w(sTAG, "Could not send data to socket, try to reconnect");
                mSocket = null;
            }
        }
        return false;
    }

    protected synchronized boolean send(ByteBuffer message) {
        return mMessageQueue.offer(message);
    }

    protected abstract void handleMessage(String message);

    protected abstract void onRobotConnected();

    protected abstract void onRobotDisconnected();

    protected abstract void sendHeartbeatMessage();

}
