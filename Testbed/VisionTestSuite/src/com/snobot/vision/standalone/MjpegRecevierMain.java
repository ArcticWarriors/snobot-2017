package com.snobot.vision.standalone;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;

import javax.swing.JFrame;

import org.opencv.core.Core;

import com.snobot.vision.VisionAlgorithm;
import com.snobot.vision.standalone.MjpegReceiver.ImageReceiver;

public class MjpegRecevierMain
{
    private MjpegReceiver reciever;
    private VisionTestPanel visionPanel;
    private VisionAlgorithm visionAlgorithm;

    private MjpegReceiver.ImageReceiver imageReciver = new ImageReceiver()
    {

        @Override
        public void onImage(BufferedImage image)
        {
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

    @SuppressWarnings("unchecked")
    public MjpegRecevierMain(String urlAddress, String thresholdConfigFile) throws FileNotFoundException
    {

        visionAlgorithm = new VisionAlgorithm();
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
        String thresholdConfig = "config/test_images_original_threshold.yml";
        if (args.length == 1)
        {
            urlAddress = args[0];
        }
        new MjpegRecevierMain(urlAddress, thresholdConfig);
    }
}
