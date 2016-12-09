package com.snobot.simulator.gui.joysticks;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JDialog;
import javax.swing.JTabbedPane;

import com.snobot.simulator.joysticks.ControllerConfiguration;
import com.snobot.simulator.joysticks.JoystickFactory;

public class JoystickManagerDialog extends JDialog
{

    private Map<String, JoystickTabPanel> mJoystickPanels;
    private SelectionPanel mSelectionPanel;
    private boolean mIsOpen;

    public JoystickManagerDialog()
    {
        setTitle("Joystick Manager");
        mIsOpen = true;

        initComponents();

        Thread t = new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                while (mIsOpen)
                {
                    for (JoystickTabPanel panel : mJoystickPanels.values())
                    {
                        panel.update();
                    }

                    try
                    {
                        Thread.sleep(100);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.setName("Joystick Updater");
        t.start();
    }

    private void initComponents()
    {
        // GraphicsDevice gd =
        // GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        // int width = gd.getDisplayMode().getWidth();
        // int height = gd.getDisplayMode().getHeight();
        // setSize(width - 100, height - 100);
        setSize(750, 600);

        mJoystickPanels = new HashMap<>();

        setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();

        JoystickFactory joystickFactory = JoystickFactory.get();

        Map<String, ControllerConfiguration> goodControllers = joystickFactory.getControllerConfiguration();

        for (Entry<String, ControllerConfiguration> pair : goodControllers.entrySet())
        {
            try
            {
                String controllerName = pair.getKey();
                ControllerConfiguration config = pair.getValue();

                JoystickTabPanel panel = new JoystickTabPanel(controllerName, config.mController, config.mSpecialization);
                mJoystickPanels.put(controllerName, panel);
                tabbedPane.add(controllerName, panel);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        mSelectionPanel = new SelectionPanel(goodControllers.keySet(), joystickFactory.getAll());

        add(tabbedPane, BorderLayout.CENTER);
        add(mSelectionPanel, BorderLayout.WEST);
    }

    public void close()
    {
        mIsOpen = false;
    }
}
