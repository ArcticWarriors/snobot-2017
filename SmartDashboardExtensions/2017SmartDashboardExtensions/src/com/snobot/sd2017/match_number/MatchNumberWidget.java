package com.snobot.sd2017.match_number;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JTextField;

import com.snobot.sd.util.AutoUpdateWidget;

import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.robot.Robot;

public class MatchNumberWidget extends AutoUpdateWidget
{
    public static final String NAME = "2017 Match Number";
    private static final String sLOG_DIRECTORY = "C:/Users/Public/Documents/FRC/Log Files/";

    private JTextField mMatchDisplay;
    private Pattern mMatchNumberPattern = Pattern.compile("Qualification");
    // private Pattern mMatchNumberPattern =
    // Pattern.compile("Qualification|Elimination|Practice|None");
    // |Elimination|Practice|None

    public MatchNumberWidget()
    {
        this(false);
    }

    public MatchNumberWidget(boolean aDebug)
    {
        super(aDebug, 2000);

        mMatchDisplay = new JTextField("");
        mMatchDisplay.setEnabled(false);
        
        setLayout(new BorderLayout());

        add(new JLabel("Match Number"), BorderLayout.NORTH);
        add(mMatchDisplay, BorderLayout.CENTER);
    }

    @Override
    public void init()
    {

    }

    @Override
    public void propertyChanged(Property property)
    {

    }

    @Override
    protected void poll() throws Exception
    {
        String output = "";

        File mostRecentFile = lastFileModified(sLOG_DIRECTORY);

        if (mostRecentFile != null)
        {
            try
            {
                BufferedReader br = new BufferedReader(new FileReader(mostRecentFile));

                String line;

                while ((line = br.readLine()) != null)
                {
                    Pattern p = Pattern.compile("(Qualification|Elimination|Practice|None) - ([0-9]+)");
                    Matcher m = p.matcher(line);

                    if (m.find())
                    {
                        output = m.group(1) + "_" + m.group(2);
                    }
                }

                br.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            if (mMatchDisplay != null)
            {
                mMatchDisplay.setText(output);
                Robot.getTable().putString("MatchNumber", output);
            }
        }
    }

    private File lastFileModified(String dir)
    {
        File fl = new File(dir);
        File[] files = fl.listFiles(new FileFilter()
        {
            public boolean accept(File file)
            {
                return file.getName().endsWith(".dsevents");
            }
        });
        long lastMod = Long.MIN_VALUE;
        File choice = null;
        for (File file : files)
        {
            if (file.lastModified() > lastMod)
            {
                choice = file;
                lastMod = file.lastModified();
            }
        }
        return choice;
    }

    // public static void main(String[] args)
    // {
    // JFrame frame = new JFrame();
    // frame.add(new MatchNumberWidget());
    // frame.setVisible(true);
    // frame.pack();
    // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // }
}
