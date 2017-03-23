package com.snobot.sd2017.robot;

import javax.swing.JFrame;

public class StandaloneRobotDrawer
{

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Test Robot");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        RobotDrawer panel = new RobotDrawer();

        panel.setSpoolMotorSpeed(.7);
        panel.setGearBossUp(true);
        panel.setFuelSpchingerOpen(false);

        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
    }

}
