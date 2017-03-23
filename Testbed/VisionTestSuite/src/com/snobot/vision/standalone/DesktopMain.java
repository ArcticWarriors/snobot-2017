package com.snobot.vision.standalone;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JDialog;
import javax.swing.JFrame;

import org.opencv.core.Core;
import org.yaml.snakeyaml.Yaml;

import com.snobot.vision.IVisionAlgorithm;
import com.snobot.vision_app.app2017.java_algorithm.VisionAlgorithm;

public class DesktopMain
{
//    private static final String sDEFAULT_IMAGE_PATH = "peg_test_20170126/image_config.yml";
    private static final String sDEFAULT_IMAGE_PATH = "peg_test_20170209/image_config.yml";

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException
    {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Yaml yaml = new Yaml();
        Map<String, Object> config = (Map<String, Object>) yaml.load(new FileInputStream(sDEFAULT_IMAGE_PATH));
        boolean oneAtATime = Boolean.parseBoolean(config.get("one_at_a_time").toString());
        String thresholdsFile = (String) config.get("threshold_config");

        List<String> files = new ArrayList<>();

        if (config.containsKey("image_dir"))
        {
            String dir = config.get("image_dir").toString();
            File[] filesList = new File(dir).listFiles();
            for (File file : filesList)
            {
                System.out.println(file);
                files.add(file.getAbsolutePath());
            }
        }
        else if (config.containsKey("images"))
        {
            for (String filepath : (List<String>) config.get("images"))
            {
                files.add(new File(filepath).getAbsolutePath());
            }
        }

        for (String file : files)
        {
            System.out.println(file);
            BufferedImage image = ImageIO.read(new File(file));

            IVisionAlgorithm algorithm = new VisionAlgorithm();
            VisionTestPanel testPanel = new VisionTestPanel(algorithm, thresholdsFile);
            testPanel.setOriginalImage(image);

            if (oneAtATime)
            {
                JDialog frame = new JDialog();
                frame.setTitle(file);
                frame.setModal(true);
                frame.setLayout(new BorderLayout());
                frame.add(testPanel, BorderLayout.CENTER);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
            else
            {
                JFrame frame = new JFrame();
                frame.setTitle(file);
                frame.setLayout(new BorderLayout());
                frame.add(testPanel, BorderLayout.CENTER);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        }

    }
}
