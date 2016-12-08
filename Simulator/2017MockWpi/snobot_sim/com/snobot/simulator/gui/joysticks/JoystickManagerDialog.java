package com.snobot.simulator.gui.joysticks;

import java.awt.BorderLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JTabbedPane;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class JoystickManagerDialog extends JDialog
{

    private List<JoystickTabPanel> mJoystickPanels = new ArrayList<>();
    private SelectionPanel mSelectionPanel;

    public JoystickManagerDialog()
    {
        setTitle("Joystick Manager");

        initComponents();

        Thread t = new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                while (true)
                {
                    for (JoystickTabPanel panel : mJoystickPanels)
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
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        // int width = gd.getDisplayMode().getWidth();
        // int height = gd.getDisplayMode().getHeight();
        // setSize(width - 100, height - 100);
        setSize(750, 600);

        mJoystickPanels = new ArrayList<>();

        setLayout(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();

        List<net.java.games.input.Controller.Type> typesToIgnore = Arrays.asList(net.java.games.input.Controller.Type.UNKNOWN,
                net.java.games.input.Controller.Type.MOUSE);
        
        Map<String, Integer> deviceCounter = new LinkedHashMap<>();

        Controller[] availableControllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        List<String> goodControllers = new ArrayList<>();

        for (Controller controller : availableControllers)
        {
            if (typesToIgnore.contains(controller.getType()))
            {
                continue;
            }
            
            if (!deviceCounter.containsKey(controller.getName()))
            {
                deviceCounter.put(controller.getName(), 0);
            }

            int deviceCtr = deviceCounter.get(controller.getName());
            String controllerName = controller.getName() + deviceCtr;

            deviceCounter.put(controller.getName(), deviceCtr + 1);
            goodControllers.add(controllerName);
            
            try
            {
                JoystickTabPanel panel = new JoystickTabPanel(controller);
                mJoystickPanels.add(panel);
                tabbedPane.add(controller.getName() + " " + deviceCtr, panel);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        mSelectionPanel = new SelectionPanel(goodControllers);

        add(tabbedPane, BorderLayout.CENTER);
        add(mSelectionPanel, BorderLayout.WEST);
    }
}
