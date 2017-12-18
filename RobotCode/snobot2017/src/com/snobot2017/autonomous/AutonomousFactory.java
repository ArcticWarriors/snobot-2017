package com.snobot2017.autonomous;

import java.io.File;

import com.snobot.lib.autonomous.ObservableSendableChooser;
import com.snobot.lib.autonomous.SnobotAutonCrawler;
import com.snobot2017.Properties2017;
import com.snobot2017.SmartDashBoardNames;
import com.snobot2017.Snobot2017;
import com.snobot2017.joystick.IDriverJoystick;
import com.snobot2017.positioner.IPositioner;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableValue;
import edu.wpi.first.networktables.TableEntryListener;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonomousFactory
{
    private static final double sY_START = 336 - 3 * 12;
    
    protected ObservableSendableChooser<File> mAutonModeChooser;
    protected ObservableSendableChooser<StartingPositions> mPositionChooser;
    protected NetworkTable mAutoModeTable;

    protected CommandParser mCommandParser;
    
    protected IPositioner mPositioner;
    
    public enum StartingPositions
    {
        RedLeft("Red Left",       -Properties2017.sLOADING_X_OFFSET, -sY_START, 0), 
        RedMiddle("Red Middle",   0,                                 -sY_START, 0), 
        RedRight("Red Right",     Properties2017.sBOILER_X_OFFSET,   -sY_START, 0), 
        BlueLeft("Blue Left",     Properties2017.sBOILER_X_OFFSET,   sY_START, 180), 
        BlueMiddle("Blue Middle", 0,                                 sY_START, 180), 
        BlueRight("Blue Right",   -Properties2017.sLOADING_X_OFFSET, sY_START, 180), 
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
        mPositionChooser = new ObservableSendableChooser<StartingPositions>();
        mCommandParser = new CommandParser(aSnobot, mPositionChooser);
        mAutoModeTable = NetworkTableInstance.getDefault().getTable(SmartDashBoardNames.sAUTON_TABLE_NAME);
        
        mPositioner = aSnobot.getPositioner();

        mAutonModeChooser = new SnobotAutonCrawler(Properties2017.sAUTON_FILE_FILTER.getValue())
                .loadAutonFiles(Properties2017.sAUTON_DIRECTORY.getValue() + "/", Properties2017.sAUTON_DEFAULT_FILE.getValue());

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
        TableEntryListener buildAutonListener = new TableEntryListener()
        {

            @Override
            public void valueChanged(NetworkTable table, String key, NetworkTableEntry entry, NetworkTableValue value, int flags)
            {
                createAutonMode();
            }
        };

        TableEntryListener setPositionListener = new TableEntryListener()
        {
            @Override
            public void valueChanged(NetworkTable table, String key, NetworkTableEntry entry, NetworkTableValue value, int flags)
            {
                setPosition();
            }
        };

        TableEntryListener saveListener = new TableEntryListener()
        {

            @Override
            public void valueChanged(NetworkTable table, String key, NetworkTableEntry entry, NetworkTableValue value, int flags)
            {
                if (mAutoModeTable.getEntry(SmartDashBoardNames.sSAVE_AUTON).getBoolean(false))
                {
                    mCommandParser.saveAutonMode();
                }
            }
        };
        
        mAutoModeTable.addEntryListener(SmartDashBoardNames.sSAVE_AUTON, saveListener, 0xFF);

        mPositionChooser.addSelectionChangedListener(setPositionListener);
        mAutonModeChooser.addSelectionChangedListener(buildAutonListener);
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
