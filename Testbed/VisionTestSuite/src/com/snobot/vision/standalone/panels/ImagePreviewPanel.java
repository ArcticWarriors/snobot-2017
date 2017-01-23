package com.snobot.vision.standalone.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.opencv.core.Mat;

import com.snobot.vision.VisionAlgorithm.ProcessedImageListener;
import com.snobot.vision.standalone.OpenCvUtilities;

public class ImagePreviewPanel extends JPanel implements ProcessedImageListener
{
    private JLabel originalImageLabel;
    private JLabel thresholdImageLabel;

    public ImagePreviewPanel()
    {
        initComponents();
    }
    
    @Override
    public void onCalculation(Mat original, Mat postThreshold)
    {
        originalImageLabel.setIcon(new ImageIcon(OpenCvUtilities.matToImage(original)));
        thresholdImageLabel.setIcon(new ImageIcon(OpenCvUtilities.matToImage(postThreshold)));
    }

    public BufferedImage getOriginalImage()
    {
        BufferedImage output = null;

        Icon icon = originalImageLabel.getIcon();
        if (icon != null)
        {
            output = (BufferedImage) ((ImageIcon) icon).getImage();
        }

        return output;
    }

    private void initComponents()
    {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]
        { 400, 400 };
        gridBagLayout.rowHeights = new int[]
        { 0, 0 };
        gridBagLayout.columnWeights = new double[]
        { 1.0, 1.0 };
        gridBagLayout.rowWeights = new double[]
        { 0.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        originalImageLabel = new JLabel();

        GridBagConstraints gbc_originalImageLabel = new GridBagConstraints();
        gbc_originalImageLabel.anchor = GridBagConstraints.NORTHWEST;
        gbc_originalImageLabel.insets = new Insets(0, 0, 0, 5);
        gbc_originalImageLabel.gridx = 0;
        gbc_originalImageLabel.gridy = 0;
        add(originalImageLabel, gbc_originalImageLabel);
        thresholdImageLabel = new JLabel();
        GridBagConstraints gbc_thresholdImageLabel = new GridBagConstraints();
        gbc_thresholdImageLabel.anchor = GridBagConstraints.NORTHWEST;
        gbc_thresholdImageLabel.gridx = 1;
        gbc_thresholdImageLabel.gridy = 0;
        add(thresholdImageLabel, gbc_thresholdImageLabel);
    }
}
