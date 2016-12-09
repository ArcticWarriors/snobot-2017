package com.snobot.simulator.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.snobot.simulator.RobotStateSingleton;
import com.snobot.simulator.gui.joysticks.JoystickManagerDialog;

import edu.wpi.first.wpilibj.Timer;

public class SimulatorFrame extends JFrame
{

    private GraphicalSensorDisplayPanel mBasicPanel;
    private EnablePanel mEnablePanel;
    private JButton mConfigureJoysticksBtn;

    public SimulatorFrame()
    {
        initComponenents();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        RobotStateSingleton.get().addLoopListener(new RobotStateSingleton.LoopListener()
        {

            @Override
            public void looped()
            {
                mBasicPanel.update();
                mEnablePanel.setTime(Timer.getMatchTime());
            }
        });
    }

    private void initComponenents()
    {
        mBasicPanel = new GraphicalSensorDisplayPanel();
        mEnablePanel = new EnablePanel();
        mConfigureJoysticksBtn = new JButton("Configure Joysticks");

        mEnablePanel.addStateChangedListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                RobotStateSingleton.get().setDisabled(!mEnablePanel.isEnabled());
                RobotStateSingleton.get().setAutonomous(mEnablePanel.isAuton());
            }
        });

        mConfigureJoysticksBtn.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e)
            {
                showJoystickDialog();
            }
        });

        add(mBasicPanel, BorderLayout.CENTER);
        add(mEnablePanel, BorderLayout.NORTH);
        add(mConfigureJoysticksBtn, BorderLayout.SOUTH);

        RobotStateSingleton.get().setDisabled(false);
        RobotStateSingleton.get().setAutonomous(false);

        mEnablePanel.setRobotEnabled(true);
        RobotStateSingleton.get().setDisabled(false);
    }

    private void showJoystickDialog()
    {
        JoystickManagerDialog dialog = new JoystickManagerDialog();
        dialog.setModal(true);
        dialog.setVisible(true);
    }
}
