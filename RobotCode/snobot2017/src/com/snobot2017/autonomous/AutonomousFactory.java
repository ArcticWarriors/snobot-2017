package com.snobot2017.autonomous;

import java.io.File;

import com.snobot.lib.autonomous.SnobotAutonCrawler;
import com.snobot2017.Properties2017;
import com.snobot2017.SmartDashBoardNames;
import com.snobot2017.Snobot2017;
import com.snobot2017.positioner.IPositioner;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class AutonomousFactory
{
    protected SendableChooser<File> mAutonModeChooser;
    protected SendableChooser<StartingPositions> mPositionChooser;
    protected ITable mAutoModeTable;

    protected CommandParser mCommandParser;
    
    protected IPositioner mPositioner;
    
    public enum StartingPositions
    {
        Red_Left, Red_Middle, Red_Right, Blue_Left, Blue_Middle, Blue_Right;
    }
    
    public AutonomousFactory(Snobot2017 aSnobot)
    {
        mCommandParser = new CommandParser(aSnobot);
        mAutoModeTable = NetworkTable.getTable(SmartDashBoardNames.sAUTON_TABLE_NAME);
        
        mPositioner = aSnobot.getPositioner();

        mAutonModeChooser = new SnobotAutonCrawler("").loadAutonFiles(Properties2017.sAUTON_DIRECTORY.getValue() + "/");

        SmartDashboard.putData(SmartDashBoardNames.sAUTON_CHOOSER, mAutonModeChooser);
        
        mPositionChooser = new SendableChooser<StartingPositions>();
        mPositionChooser.addObject("Red Left", StartingPositions.Red_Left);
        mPositionChooser.addObject("Red Middle", StartingPositions.Red_Middle);
        mPositionChooser.addObject("Red Right", StartingPositions.Red_Right);
        mPositionChooser.addObject("Blue Left", StartingPositions.Blue_Left);
        mPositionChooser.addObject("Blue Middle", StartingPositions.Blue_Middle);
        mPositionChooser.addObject("Blue Right", StartingPositions.Blue_Right);
        
        SmartDashboard.putData(SmartDashBoardNames.sPOSITION_CHOOSER, mPositionChooser);
        addListeners();    
        }

    public CommandGroup createAutonMode()
    {
        File selectedFile = mAutonModeChooser.getSelected();
        if (selectedFile != null)
        {
            setPosition();
            return mCommandParser.readFile(selectedFile.toString());
        }

        return null;

    }

    private void addListeners()
    {
        ITableListener buildAutonListener = new ITableListener()
        {

            @Override
            public void valueChanged(ITable arg0, String arg1, Object arg2, boolean arg3)
            {
                createAutonMode();
            }
        };

        ITableListener setPositionListener = new ITableListener()
        {
            @Override
            public void valueChanged(ITable arg0, String arg1, Object arg2, boolean arg3)
            {
                setPosition();
            }
        };

        ITableListener saveListener = new ITableListener()
        {

            @Override
            public void valueChanged(ITable arg0, String arg1, Object arg2, boolean arg3)
            {
                if (mAutoModeTable.getBoolean(SmartDashBoardNames.sSAVE_AUTON, false))
                {
                    mCommandParser.saveAutonMode();
                }
            }
        };
        
        mAutoModeTable.addTableListener(SmartDashBoardNames.sSAVE_AUTON, saveListener, true);
        mAutonModeChooser.getTable().addTableListener(buildAutonListener);
        mPositionChooser.getTable().addTableListener(setPositionListener);
    }

    private void setPosition()
    {
        StartingPositions startingPosition = mPositionChooser.getSelected();

        if (startingPosition != null)
        {
            switch (startingPosition)
            {
            case Red_Left:
                mPositioner.setPosition(-79.53, -334.81, 0);
                break;

            case Red_Middle:
                mPositioner.setPosition(0, -334.81, 0);
                break;

            case Red_Right:
                mPositioner.setPosition(43.02, -334.81, 0);
                break;

            case Blue_Left:
                mPositioner.setPosition(43.02, 334.81, 180);
                break;

            case Blue_Middle:
                mPositioner.setPosition(0, 334.81, 180);
                break;

            case Blue_Right:
                mPositioner.setPosition(-79.53, 334.81, 180);
                break;
            }
        }
    }
}
