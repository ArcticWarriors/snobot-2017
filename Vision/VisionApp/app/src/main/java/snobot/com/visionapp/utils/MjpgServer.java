package snobot.com.visionapp.utils;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MjpgServer {

    private static final String sMESSAGE_BOUNDARY = "--boundary";
    private static final MjpgServer sInst = new MjpgServer();

    private static final String TAG = "MjpgServer";

    public static MjpgServer getInstance()
    {
        return sInst;
    }

    private ArrayList<Connection> mConnections = new ArrayList<>();
    private final Object mLock = new Object();

    private class Connection {

        private Socket mSocket;

        private Connection(Socket s) {
            mSocket = s;
        }

        private boolean isAlive() {
            return !mSocket.isClosed() && mSocket.isConnected();
        }

        private void start() {
            try {
                Log.i(TAG, "Starting a connection!");
                OutputStream stream = mSocket.getOutputStream();
                stream.write(("HTTP/1.0 200 OK\r\n" +
                        "Server: cheezyvision\r\n" +
                        "Cache-Control: no-cache\r\n" +
                        "Pragma: no-cache\r\n" +
                        "Connection: close\r\n" +
                        "Content-Type: multipart/x-mixed-replace;boundary=" + sMESSAGE_BOUNDARY + "\r\n").getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void writeImageUpdate(byte[] buffer) {
            if (!isAlive()) {
                return;
            }
            OutputStream stream;
            try {
                stream = mSocket.getOutputStream();

                String output;
                output = "\r\n" + sMESSAGE_BOUNDARY + "\r\n";
                output += "Content-type: image/jpeg\r\n";
                output += "Content-Length: " + buffer.length + "\r\n";
                output += "\r\n";

                stream.write(output.getBytes());
                stream.write(buffer);
                stream.flush();

            } catch (IOException e) {
                // There is a broken pipe exception being thrown here I cannot figure out.
            }
        }

    }

    private ServerSocket mServerSocket;
    private boolean mRunning;

    private MjpgServer() {
        try {
            mServerSocket = new ServerSocket(5800);
            mRunning = true;
            Runnable runner = new Runnable() {

                @Override
                public void run() {
                    while (mRunning) {
                        try {
                            Log.i(TAG, "Waiting for connections");
                            Socket s = mServerSocket.accept();
                            Log.i("MjpgServer", "Got a socket: " + s);
                            Connection c = new Connection(s);
                            synchronized (mLock) {
                                mConnections.add(c);
                            }
                            c.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            Thread runThread = new Thread(runner);
            runThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(byte[] bytes) {
        new SendUpdateTask().execute(bytes);
    }

    private class SendUpdateTask extends AsyncTask<byte[], Void, Void> {

        @Override
        protected Void doInBackground(byte[]... params) {
            threadSafeUpdate(params[0]);
            return null;
        }
    }

    private void threadSafeUpdate(byte[] bytes) {
        synchronized (mLock) {
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

}