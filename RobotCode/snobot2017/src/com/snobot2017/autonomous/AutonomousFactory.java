package com.snobot2017.autonomous;

import java.io.File;

import com.snobot.lib.autonomous.SnobotAutonCrawler;
import com.snobot2017.Properties2017;
import com.snobot2017.SmartDashBoardNames;
import com.snobot2017.Snobot2017;
import com.snobot2017.joystick.IDriverJoystick;
import com.snobot2017.positioner.IPositioner;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class AutonomousFactory
{
    private static final double sY_START = 336 - 3 * 12;
    private static final double sX_START_CENTER = 0;
    private static final double sX_START_BOILER = 67;
    private static final double sX_START_LOADING = 80;
    
    protected SendableChooser<File> mAutonModeChooser;
    protected SendableChooser<StartingPositions> mPositionChooser;
    protected ITable mAutoModeTable;

    protected CommandParser mCommandParser;
    
    protected IPositioner mPositioner;
    
    public enum StartingPositions
    {
        RedLeft("Red Left", -sX_START_LOADING, -sY_START, 0), 
        RedMiddle("Red Middle", sX_START_CENTER, -sY_START, 0), 
        RedRight("Red Right", sX_START_BOILER, -sY_START, 0), 
        BlueLeft("Blue Left", sX_START_BOILER, sY_START, 180), 
        BlueMiddle("Blue Middle", sX_START_CENTER, sY_START, 180), 
        BlueRight("Blue Right", -sX_START_LOADING, sY_START, 180), 
        Origin("Origin", 0, 0, 0);

        public final String mDisplayName;
        public final double mX;
        public final double mY;
        public final double mAngle;
        


        private StartingPositions(String aDisplayName, double aX, double aY, double aAngle)
        {
            mDisplayName = aDisplayName;
            mX = aX;
            mY = aY;
            mAngle = aAngle;
        }

        @Override
        public String toString()
        {
            return mDisplayName;
        }
    }
    
    public AutonomousFactory(Snobot2017 aSnobot, IDriverJoystick aDriverJoystick)
    {
        mPositionChooser = new SendableChooser<StartingPositions>();
        mCommandParser = new CommandParser(aSnobot, mPositionChooser);
        mAutoModeTable = NetworkTable.getTable(SmartDashBoardNames.sAUTON_TABLE_NAME);
        
        mPositioner = aSnobot.getPositioner();

        SnobotAutonCrawler autonCrawler = new SnobotAutonCrawler(Properties2017.sAUTON_FILE_FILTER.getValue());
        autonCrawler.loadAutonFiles(Properties2017.sAUTON_DIRECTORY.getValue() + "/");

        mAutonModeChooser = autonCrawler.createSendableChooser(Properties2017.sAUTON_DEFAULT_FILE.getValue());

        SmartDashboard.putData(SmartDashBoardNames.sAUTON_CHOOSER, mAutonModeChooser);
        

        for (StartingPositions pos : StartingPositions.values())
        {
            mPositionChooser.addObject(pos.toString(), pos);
        }
        
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
        // mAutonModeChooser.getTable().addTableListener(buildAutonListener);
        // mPositionChooser.getTable().addTableListener(setPositionListener);
    }

    private void setPosition()
    {
        StartingPositions startingPosition = mPositionChooser.getSelected();

        if (startingPosition != null)
        {
            mPositioner.setPosition(startingPosition.mX, startingPosition.mY, startingPosition.mAngle);
        }
    }
}
