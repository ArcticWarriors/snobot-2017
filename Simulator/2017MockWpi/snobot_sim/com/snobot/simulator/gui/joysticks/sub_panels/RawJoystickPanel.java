package com.snobot.simulator.gui.joysticks.sub_panels;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import net.java.games.input.Component;
import net.java.games.input.Controller;

public class RawJoystickPanel extends JPanel
{
    private Controller mJoystick;
    private JPanel digitalPanel;
    private JPanel analogPanel;
    private List<AnalogControllerInputPanel> mAnalogDisplays;
    private List<DigitalControllerInputPanel> mDigitalDisplays;
    private List<Component> mAnalogComponents;
    private List<Component> mDigitalComponents;

    public RawJoystickPanel(Controller aJoystick)
    {
        initComponents();
        setBackground(Color.green);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(analogPanel);
        add(digitalPanel);

        mJoystick = aJoystick;

        if (mJoystick != null)
        {
            mAnalogDisplays = new ArrayList<>();
            mDigitalDisplays = new ArrayList<>();
            mAnalogComponents = new ArrayList<>();
            mDigitalComponents = new ArrayList<>();

            Component[] components = mJoystick.getComponents();
            for (int j = 0; j < components.length; j++)
            {
                Component component = components[j];

                if (component.isAnalog())
                {
                    mAnalogComponents.add(component);
                }
                else
                {
                    mDigitalComponents.add(component);
                }
            }
            

            for (int i = 0; i < mAnalogComponents.size(); ++i)
            {
                AnalogControllerInputPanel panel = new AnalogControllerInputPanel(i);
                mAnalogDisplays.add(panel);
                analogPanel.add(panel);
            }
            for (int i = 0; i < mDigitalComponents.size(); ++i)
            {
                DigitalControllerInputPanel panel = new DigitalControllerInputPanel(i);
                mDigitalDisplays.add(panel);
                digitalPanel.add(panel);
            }
        }
    }

    public void updateDisplay()
    {
        mJoystick.poll();
        for (int i = 0; i < mAnalogComponents.size(); ++i)
        {
            float rawValue = mAnalogComponents.get(i).getPollData();

            AnalogControllerInputPanel panel = mAnalogDisplays.get(i);
            panel.setValue((int) (rawValue * 127));
        }
        for (int i = 0; i < mDigitalComponents.size(); ++i)
        {
            float rawValue = mDigitalComponents.get(i).getPollData();

            DigitalControllerInputPanel panel = mDigitalDisplays.get(i);
            panel.setValue(rawValue == 1);
        }
    }


    private void initComponents()
    {
        digitalPanel = new JPanel();
        analogPanel = new JPanel();
    }


}
