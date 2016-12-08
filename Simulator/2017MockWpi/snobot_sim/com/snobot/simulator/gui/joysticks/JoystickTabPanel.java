package com.snobot.simulator.gui.joysticks;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.snobot.simulator.gui.joysticks.sub_panels.RawJoystickPanel;
import com.snobot.simulator.gui.joysticks.sub_panels.WrappedJoystickPanel;
import com.snobot.simulator.gui.joysticks.sub_panels.XboxPanel;
import com.snobot.simulator.joysticks.IMockJoystick;
import com.snobot.simulator.joysticks.joystick_specializations.GenericGamepadJoystick;
import com.snobot.simulator.joysticks.joystick_specializations.KeyboardJoystick;
import com.snobot.simulator.joysticks.joystick_specializations.NullJoystick;
import com.snobot.simulator.joysticks.joystick_specializations.Ps4Joystick;

import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;

public class JoystickTabPanel extends JPanel
{
    private RawJoystickPanel mRawPanel;

    private JComboBox<String> mSelectInterperetTypeBox;
    private WrappedJoystickPanel mWrappedPanel;
    private XboxPanel mXboxPanel;

    private Controller mController;

    public JoystickTabPanel(Controller aController) throws IOException
    {
        mController = aController;

        initComponents();
    }

    private void initComponents() throws IOException
    {
        JTabbedPane tabbedPane = new JTabbedPane();
        mSelectInterperetTypeBox = new JComboBox<>();

        // Raw Panel
        mRawPanel = new RawJoystickPanel(mController);
        tabbedPane.add("Raw", mRawPanel);

        // Wrapped Panel
        mWrappedPanel = new WrappedJoystickPanel();
        tabbedPane.add("Wrapped", mWrappedPanel);

        // XBOX Panel
        mXboxPanel = new XboxPanel();
        tabbedPane.add("As XBOX", mXboxPanel);

        // Main View
        setLayout(new BorderLayout());
        add(mSelectInterperetTypeBox, BorderLayout.NORTH);
        add(tabbedPane, BorderLayout.CENTER);

        if (mController.getType() == Type.KEYBOARD)
        {
            mSelectInterperetTypeBox.addItem("Keyboard");
        }
        else
        {
            mSelectInterperetTypeBox.addItem("PS4");
            mSelectInterperetTypeBox.addItem("Generic Gamepad");
        }

        mSelectInterperetTypeBox.addItemListener(new ItemListener()
        {

            @Override
            public void itemStateChanged(ItemEvent e)
            {
                if (e.getStateChange() == ItemEvent.SELECTED)
                {
                    handleWrapperSelected(e.getItem().toString());
                }
            }
        });

        handleWrapperSelected(mSelectInterperetTypeBox.getItemAt(0));
    }

    private void handleWrapperSelected(String aType)
    {
        IMockJoystick wrappedJoystick = null;

        switch (aType)
        {
        case "Keyboard":
            wrappedJoystick = new KeyboardJoystick(mController);
            break;
        case "PS4":
            wrappedJoystick = new Ps4Joystick(mController);
            break;
        case "Generic Gamepad":
            wrappedJoystick = new GenericGamepadJoystick(mController);
            break;
        }

        if (wrappedJoystick == null)
        {
            wrappedJoystick = new NullJoystick();
        }

        mWrappedPanel.setJoystick(wrappedJoystick);
        mXboxPanel.setJoystick(wrappedJoystick);
    }

    public void update()
    {
        mRawPanel.updateDisplay();
        mWrappedPanel.updateDisplay();
        mXboxPanel.updateDisplay();
    }

}
