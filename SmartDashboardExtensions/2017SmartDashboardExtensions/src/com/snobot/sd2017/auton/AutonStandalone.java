package com.snobot.sd2017.auton;

import javax.swing.JFrame;

public class AutonStandalone
{

    public static void main(String[] args)
    {
        JFrame frame = new JFrame("Test Auton");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        AutonPanel panel = new AutonPanel();

        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
