package com.snobot.vision.standalone;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.opencv.core.Core;

import com.snobot.lib.vision.MjpegReceiver;
import com.snobot.lib.vision.MjpegReceiver.ImageReceiver;
import com.snobot.vision_app.app2017.java_algorithm.VisionAlgorithm2;

public class MjpegRecevierMain
{
    private MjpegReceiver reciever;
    private VisionTestPanel visionPanel;
    private VisionAlgorithm2 visionAlgorithm;

    private MjpegReceiver.ImageReceiver imageReciver = new ImageReceiver()
    {

        @Override
        public void onImage(byte[] aImageBytes)
        {
            BufferedImage image = null;
            try
            {
                image = ImageIO.read(new ByteArrayInputStream(aImageBytes));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            visionPanel.setOriginalImage(image);
        }
    };

    private WindowListener closingListener = new WindowAdapter()
    {
        @Override
        public void windowClosing(WindowEvent e)
        {
            reciever.stop();
            e.getWindow().dispose();
        }
    };

    public MjpegRecevierMain(String urlAddress, String thresholdConfigFile) throws FileNotFoundException
    {

        visionAlgorithm = new VisionAlgorithm2();
        reciever = new MjpegReceiver();
        visionPanel = new VisionTestPanel(visionAlgorithm, thresholdConfigFile);

        reciever.addImageReceiver(imageReciver);
        reciever.start(urlAddress);

        JFrame frame = new JFrame();

        frame.addWindowListener(closingListener);
        frame.setLayout(new BorderLayout());
        frame.add(visionPanel, BorderLayout.CENTER);
        frame.setSize(700, 540);
        frame.setVisible(true);
    }

    public static void main(String[] args) throws FileNotFoundException
    {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        String urlAddress = "http://127.0.0.1:5800";
        String thresholdConfig = "peg_test_20170202/threshold_config.yml";
        if (args.length == 1)
        {
            urlAddress = args[0];
        }
        new MjpegRecevierMain(urlAddress, thresholdConfig);
    }
}
