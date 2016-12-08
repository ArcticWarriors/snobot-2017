package com.snobot.simulator.gui.joysticks;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SelectionPanel extends JPanel
{

    public SelectionPanel(List<String> aControllers)
    {
        setLayout(new GridLayout(0, 2, 0, 0));

        for (int i = 0; i < 6; ++i)
        {
            JLabel lblNewLabel = new JLabel("Joystick " + i);
            add(lblNewLabel);

			JComboBox<Object> comboBox = new JComboBox<Object>();

            comboBox.addItem("Null Joystick");
            for (String c : aControllers)
            {
                comboBox.addItem(c);
            }
            add(comboBox);
        }
    }

}
