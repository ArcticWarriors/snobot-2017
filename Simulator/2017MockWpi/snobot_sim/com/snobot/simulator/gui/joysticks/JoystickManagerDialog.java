package com.snobot.simulator.gui.joysticks;

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

    public JoystickManagerDialog()
    {
        setTitle("Joystick Manager");

        initComponents();
    }

    private void initComponents()
    {
        JTabbedPane tabbedPane = new JTabbedPane();

        List<net.java.games.input.Controller.Type> typesToIgnore = Arrays.asList(net.java.games.input.Controller.Type.UNKNOWN,
                net.java.games.input.Controller.Type.MOUSE);
        
        Map<String, Integer> deviceCounter = new LinkedHashMap<>();

        Controller[] availableControllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

        List<JoystickTabPanel> panels = new ArrayList<>();

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
            deviceCounter.put(controller.getName(), deviceCtr + 1);
            
            try
            {
                JoystickTabPanel panel = new JoystickTabPanel(controller);
                panels.add(panel);
                tabbedPane.add(controller.getName() + " " + deviceCtr, panel);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        add(tabbedPane);

        Thread t = new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                while (true)
                {
                    for (JoystickTabPanel panel : panels)
                    {
                        panel.update();
                    }

                    try
                    {
                        Thread.sleep(100);
                    }
                    catch (InterruptedException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        t.setName("Joystick Updater");
        t.start();

        // tabbedP
    }
}
