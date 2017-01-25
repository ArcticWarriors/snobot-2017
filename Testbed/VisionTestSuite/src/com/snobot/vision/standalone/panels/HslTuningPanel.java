package com.snobot.vision.standalone.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.snobot.vision.HslThreshold;
import com.snobot.vision.standalone.SetThresholdListener;

public class HslTuningPanel extends JPanel
{
    private SliderWithTextboxPanel minHueWidget;
    private SliderWithTextboxPanel maxHueWidget;
    private SliderWithTextboxPanel minSatWidget;
    private SliderWithTextboxPanel maxSatWidget;
    private SliderWithTextboxPanel minLumWidget;
    private SliderWithTextboxPanel maxLumWidget;

    private SetThresholdListener listener;

    public HslTuningPanel()
    {
        initComponents();

        minHueWidget.setValue(0);
        maxHueWidget.setValue(255);
        minSatWidget.setValue(0);
        maxSatWidget.setValue(255);
        minLumWidget.setValue(0);
        maxLumWidget.setValue(255);

        minHueWidget.addChangeListener(changeListener);
        maxHueWidget.addChangeListener(changeListener);
        minSatWidget.addChangeListener(changeListener);
        maxSatWidget.addChangeListener(changeListener);
        minLumWidget.addChangeListener(changeListener);
        maxLumWidget.addChangeListener(changeListener);

        changeListener.stateChanged(null);

    }

    public void setListener(SetThresholdListener aListener)
    {
        listener = aListener;
    }

    public HslThreshold getMinThreshold()
    {
        return new HslThreshold(minHueWidget.getValue(), minSatWidget.getValue(), minLumWidget.getValue());
    }

    public HslThreshold getMaxThreshold()
    {
        return new HslThreshold(maxHueWidget.getValue(), maxSatWidget.getValue(), maxLumWidget.getValue());
    }

    public void setThresholds(HslThreshold minThreshold, HslThreshold maxThreshold)
    {
        minHueWidget.setValue(minThreshold.hue);
        minSatWidget.setValue(minThreshold.sat);
        minLumWidget.setValue(minThreshold.lum);
        maxHueWidget.setValue(maxThreshold.hue);
        maxSatWidget.setValue(maxThreshold.sat);
        maxLumWidget.setValue(maxThreshold.lum);
    }

    private ChangeListener changeListener = new ChangeListener()
    {

        @Override
        public void stateChanged(ChangeEvent e)
        {
            if (listener != null)
            {
                listener.setThresholds(getMinThreshold(), getMaxThreshold());
            }
        }
    };

    private void initComponents()
    {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]
        { 43, 358, 346, 0 };
        gridBagLayout.rowHeights = new int[]
        { 27, 0, 26, 0, 0 };
        gridBagLayout.columnWeights = new double[]
        { 0.0, 1.0, 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[]
        { 0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        JLabel lblMin = new JLabel("min");
        GridBagConstraints gbc_lblMin = new GridBagConstraints();
        gbc_lblMin.insets = new Insets(0, 0, 5, 5);
        gbc_lblMin.gridx = 1;
        gbc_lblMin.gridy = 0;
        add(lblMin, gbc_lblMin);

        JLabel lblMax = new JLabel("max");
        GridBagConstraints gbc_lblMax = new GridBagConstraints();
        gbc_lblMax.insets = new Insets(0, 0, 5, 0);
        gbc_lblMax.gridx = 2;
        gbc_lblMax.gridy = 0;
        add(lblMax, gbc_lblMax);

        JLabel lblHue = new JLabel("Hue");
        GridBagConstraints gbc_lblHue = new GridBagConstraints();
        gbc_lblHue.fill = GridBagConstraints.BOTH;
        gbc_lblHue.insets = new Insets(0, 0, 5, 5);
        gbc_lblHue.gridx = 0;
        gbc_lblHue.gridy = 1;
        add(lblHue, gbc_lblHue);

        JLabel lblSat = new JLabel("Sat");
        GridBagConstraints gbc_lblSat = new GridBagConstraints();
        gbc_lblSat.fill = GridBagConstraints.BOTH;
        gbc_lblSat.insets = new Insets(0, 0, 5, 5);
        gbc_lblSat.gridx = 0;
        gbc_lblSat.gridy = 2;
        add(lblSat, gbc_lblSat);

        JLabel lblLum = new JLabel("Lum");
        GridBagConstraints gbc_lblLum = new GridBagConstraints();
        gbc_lblLum.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblLum.insets = new Insets(0, 0, 0, 5);
        gbc_lblLum.gridx = 0;
        gbc_lblLum.gridy = 3;
        add(lblLum, gbc_lblLum);

        minHueWidget = new SliderWithTextboxPanel();
        GridBagConstraints gbc_minHueWidget = new GridBagConstraints();
        gbc_minHueWidget.insets = new Insets(0, 0, 5, 5);
        gbc_minHueWidget.fill = GridBagConstraints.BOTH;
        gbc_minHueWidget.gridx = 1;
        gbc_minHueWidget.gridy = 1;
        add(minHueWidget, gbc_minHueWidget);

        maxHueWidget = new SliderWithTextboxPanel();
        GridBagConstraints gbc_maxHueWidget = new GridBagConstraints();
        gbc_maxHueWidget.insets = new Insets(0, 0, 5, 0);
        gbc_maxHueWidget.fill = GridBagConstraints.BOTH;
        gbc_maxHueWidget.gridx = 2;
        gbc_maxHueWidget.gridy = 1;
        add(maxHueWidget, gbc_maxHueWidget);

        minSatWidget = new SliderWithTextboxPanel();
        GridBagConstraints gbc_minSatWidget = new GridBagConstraints();
        gbc_minSatWidget.insets = new Insets(0, 0, 5, 5);
        gbc_minSatWidget.fill = GridBagConstraints.BOTH;
        gbc_minSatWidget.gridx = 1;
        gbc_minSatWidget.gridy = 2;
        add(minSatWidget, gbc_minSatWidget);

        maxSatWidget = new SliderWithTextboxPanel();
        GridBagConstraints gbc_maxSatWidget = new GridBagConstraints();
        gbc_maxSatWidget.insets = new Insets(0, 0, 5, 0);
        gbc_maxSatWidget.fill = GridBagConstraints.BOTH;
        gbc_maxSatWidget.gridx = 2;
        gbc_maxSatWidget.gridy = 2;
        add(maxSatWidget, gbc_maxSatWidget);

        minLumWidget = new SliderWithTextboxPanel();
        GridBagConstraints gbc_minLumWidget = new GridBagConstraints();
        gbc_minLumWidget.insets = new Insets(0, 0, 0, 5);
        gbc_minLumWidget.fill = GridBagConstraints.BOTH;
        gbc_minLumWidget.gridx = 1;
        gbc_minLumWidget.gridy = 3;
        add(minLumWidget, gbc_minLumWidget);

        maxLumWidget = new SliderWithTextboxPanel();
        GridBagConstraints gbc_maxLumWidget = new GridBagConstraints();
        gbc_maxLumWidget.fill = GridBagConstraints.BOTH;
        gbc_maxLumWidget.gridx = 2;
        gbc_maxLumWidget.gridy = 3;
        add(maxLumWidget, gbc_maxLumWidget);
    }

}
