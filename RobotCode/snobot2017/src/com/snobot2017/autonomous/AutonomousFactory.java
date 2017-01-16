package com.snobot2017.autonomous;

import java.io.File;

import com.snobot.lib.autonomous.SnobotAutonCrawler;
import com.snobot2017.Properties2017;
import com.snobot2017.SmartDashBoardNames;
import com.snobot2017.Snobot2017;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class AutonomousFactory
{
    protected SendableChooser<File> mSendableChooser;

    protected CommandParser mCommandParser;

    public AutonomousFactory(Snobot2017 aSnobot)
    {
        mCommandParser = new CommandParser(aSnobot);

        mSendableChooser = new SnobotAutonCrawler("").loadAutonFiles(Properties2017.sAUTON_DIRECTORY.getValue() + "/");

        SmartDashboard.putData(SmartDashBoardNames.sAUTON_CHOOSER, mSendableChooser);
        addListeners();
    }

    public CommandGroup createAutonMode()
    {
        return mCommandParser.readFile(mSendableChooser.getSelected().toString());
    }

    private void addListeners()
    {
        mSendableChooser.getTable().addTableListener(new ITableListener()
        {

            @Override
            public void valueChanged(ITable arg0, String arg1, Object arg2, boolean arg3)
            {
                createAutonMode();
            }
        });
    }
}
