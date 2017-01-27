package com.snobot.vision.standalone.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderWithTextboxPanel extends JPanel
{
    private JTextField mTextField;
    private JSlider mSlider;

    public SliderWithTextboxPanel()
    {
        initComponents();

        mSlider.addChangeListener(new ChangeListener()
        {
            @Override
            public void stateChanged(ChangeEvent e)
            {
                mTextField.setText(String.valueOf(mSlider.getValue()));
            }
        });

        mTextField.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(KeyEvent ke)
            {
                String typed = mTextField.getText();
                mSlider.setValue(0);
                if (!typed.matches("\\d+") || typed.length() > 3)
                {
                    return;
                }
                int value = Integer.parseInt(typed);
                mSlider.setValue(value);
            }
        });
    }

    public void setValue(int value)
    {
        mSlider.setValue(value);
    }

    public int getValue()
    {
        return mSlider.getValue();
    }

    private void initComponents()
    {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]
        { 332, 85, 0 };
        gridBagLayout.rowHeights = new int[]
        { 0, 0 };
        gridBagLayout.columnWeights = new double[]
        { 1.0, 0.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[]
        { 0.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        mSlider = new JSlider();
        mSlider.setMinorTickSpacing(5);
        mSlider.setMajorTickSpacing(127);
        mSlider.setPaintLabels(true);
        mSlider.setPaintTicks(true);
        mSlider.setMaximum(255);
        GridBagConstraints gbc_slider = new GridBagConstraints();
        gbc_slider.fill = GridBagConstraints.HORIZONTAL;
        gbc_slider.insets = new Insets(0, 0, 0, 5);
        gbc_slider.gridx = 0;
        gbc_slider.gridy = 0;
        add(mSlider, gbc_slider);

        mTextField = new JTextField();
        GridBagConstraints gbc_mTextField = new GridBagConstraints();
        gbc_mTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_mTextField.gridx = 1;
        gbc_mTextField.gridy = 0;
        add(mTextField, gbc_mTextField);
        mTextField.setColumns(10);
    }

    public void addChangeListener(ChangeListener changeListener)
    {
        mSlider.addChangeListener(changeListener);
    }

}
