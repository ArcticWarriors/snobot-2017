package com.snobot2017.autonomous;

import java.io.File;

import com.snobot.lib.autonomous.SnobotAutonCrawler;
import com.snobot2017.Properties2017;
import com.snobot2017.SmartDashBoardNames;
import com.snobot2017.Snobot2017;
import com.snobot2017.autonomous.AutonomousFactory.StartingPositions;
import com.snobot2017.positioner.IPositioner;
import com.snobot2017.positioner.Positioner;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class AutonomousFactory
{
    protected SendableChooser<File> mSendableChooser;
    protected SendableChooser<StartingPositions> mSendablePositioner;

    protected CommandParser mCommandParser;
    
    protected IPositioner mPositioner;
    
    public enum StartingPositions
    {
        Red_Left, Red_Middle, Red_Right, Blue_Left, Blue_Middle, Blue_Right;
    }
    
    public AutonomousFactory(Snobot2017 aSnobot)
    {
        mCommandParser = new CommandParser(aSnobot);
        
        mPositioner = aSnobot.getPositioner();

        mSendableChooser = new SnobotAutonCrawler("").loadAutonFiles(Properties2017.sAUTON_DIRECTORY.getValue() + "/");

        SmartDashboard.putData(SmartDashBoardNames.sAUTON_CHOOSER, mSendableChooser);
        
        mSendablePositioner = new SendableChooser<StartingPositions>();
        mSendablePositioner.addObject("Red Left", StartingPositions.Red_Left);
        mSendablePositioner.addObject("Red Middle", StartingPositions.Red_Middle);
        mSendablePositioner.addObject("Red Right", StartingPositions.Red_Right);
        mSendablePositioner.addObject("Blue Left", StartingPositions.Blue_Left);
        mSendablePositioner.addObject("Blue Middle", StartingPositions.Blue_Middle);
        mSendablePositioner.addObject("Blue Right", StartingPositions.Blue_Right);
        
        SmartDashboard.putData(SmartDashBoardNames.sPOSITION_CHOOSER, mSendablePositioner);
        addListeners();    
        }

    public CommandGroup createAutonMode()
    {
        File selectedFile = mSendableChooser.getSelected();
        if (selectedFile != null)
        {
            return mCommandParser.readFile(selectedFile.toString());
        }

        return null;

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
        
        mSendablePositioner.getTable().addTableListener(new ITableListener()
        {

            @Override
            public void valueChanged(ITable arg0, String arg1, Object arg2, boolean arg3)
            {
                switch(mSendablePositioner.getSelected())
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

                System.out.println(mSendablePositioner.getSelected() + " " + mPositioner.getXPosition() + ", " + mPositioner.getYPosition());
            }
        });
    }
}
