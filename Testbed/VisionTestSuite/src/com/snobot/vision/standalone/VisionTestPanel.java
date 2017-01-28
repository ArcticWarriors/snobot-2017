package com.snobot.vision.standalone;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import com.snobot.vision.HslThreshold;
import com.snobot.vision.VisionAlgorithm;
import com.snobot.vision.standalone.panels.HslTuningPanel;
import com.snobot.vision.standalone.panels.ImagePreviewPanel;

public class VisionTestPanel extends JPanel
{
    // private JLabel originalImageLabel;
    // private JLabel thresholdImageLabel;
    private VisionAlgorithm algorithm = new VisionAlgorithm();
    private ImagePreviewPanel imagePreview;
    private HslTuningPanel tuningPanel;

    @SuppressWarnings("unchecked")
    public VisionTestPanel(VisionAlgorithm aAlgorithm, String thresholdConfigFile)
    {
        algorithm = aAlgorithm;

        initComponents();

        try
        {
            Yaml yaml = new Yaml();
            Map<String, Map<String, Object>> thresholdConfig = (Map<String, Map<String, Object>>) yaml.load(new FileInputStream(thresholdConfigFile));
            HslThreshold minThreshold = (HslThreshold) thresholdConfig.get("thresholds").get("min");
            HslThreshold maxThreshold = (HslThreshold) thresholdConfig.get("thresholds").get("max");

            tuningPanel.setThresholds(minThreshold, maxThreshold);
        }
        catch (FileNotFoundException ex)
        {
            ex.printStackTrace();
        }
    }

    private void onSaveOriginalPressed()
    {
        BufferedImage original = imagePreview.getOriginalImage();

        if (original == null)
        {
            JOptionPane.showMessageDialog(this, "No image has been received");
        }
        else
        {
            JFileChooser fc = new JFileChooser("./config");
            int result = fc.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION)
            {
                try
                {
                    ImageIO.write(original, "jpg", fc.getSelectedFile());
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

    }

    private void onSaveThresholdPressed()
    {
        HslThreshold minThresh = tuningPanel.getMinThreshold();
        HslThreshold maxThresh = tuningPanel.getMaxThreshold();

        JFileChooser fc = new JFileChooser("./config");
        int result = fc.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION)
        {
            Map<String, Object> thresholdConfig = new HashMap<String, Object>();
            thresholdConfig.put("min", minThresh);
            thresholdConfig.put("max", maxThresh);

            Map<String, Object> config = new HashMap<String, Object>();
            config.put("thresholds", thresholdConfig);

            DumperOptions options = new DumperOptions();
            options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
            Yaml yaml = new Yaml(options);
            try
            {
                yaml.dump(config, new FileWriter(fc.getSelectedFile()));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void setOriginalImage(BufferedImage image)
    {
        algorithm.processImage(image);
    }

    private void initComponents()
    {
        setLayout(new BorderLayout());

        tuningPanel = new HslTuningPanel();
        imagePreview = new ImagePreviewPanel();

        add(imagePreview, BorderLayout.CENTER);
        add(tuningPanel, BorderLayout.NORTH);

        algorithm.addListener(imagePreview);
        tuningPanel.setListener(algorithm);

        JPanel btnPanel = new JPanel();
        add(btnPanel, BorderLayout.SOUTH);

        JButton btnSaveOriginal = new JButton("Save Original");
        btnSaveOriginal.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onSaveOriginalPressed();
            }
        });
        btnPanel.add(btnSaveOriginal);

        JButton btnSaveThreshold = new JButton("Save Threshold");
        btnSaveThreshold.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onSaveThresholdPressed();
            }
        });
        btnPanel.add(btnSaveThreshold);

    }

}
