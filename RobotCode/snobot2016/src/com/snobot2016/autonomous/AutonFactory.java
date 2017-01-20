package com.snobot2016.autonomous;

import com.snobot2016.SmartDashBoardNames;
import com.snobot2016.Snobot;
import com.snobot2016.positioner.IPositioner;
import com.snobot2016.smartdashboard.DefenseInFront;
import com.snobot2016.smartdashboard.SelectAutonomous;
import com.snobot2016.smartdashboard.SelectStartPosition;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class AutonFactory
{
    private DefenseInFront mDefenseInFront;
    private SelectStartPosition mSelectStartPosition;
    private SelectAutonomous mSelectAutonomous;

    private ITable mDefenseTable;
    private ITable mPostDefenseTable;

    private CommandParser mDefenseCommandParser;
    private CommandParser mPostDefenseCommandParser;

    public AutonFactory(IPositioner aPositioner, Snobot aSnobot)
    {
        mSelectStartPosition = new SelectStartPosition(aPositioner);
        mDefenseInFront = new DefenseInFront();
        mSelectAutonomous = new SelectAutonomous();


        mDefenseTable = NetworkTable.getTable(SmartDashBoardNames.sDEFENSE_AUTON_TABLE);
        mPostDefenseTable = NetworkTable.getTable(SmartDashBoardNames.sPOST_DEFENSE_AUTON_TABLE);

        mDefenseCommandParser = new CommandParser(aSnobot, mDefenseTable, mSelectStartPosition, "Defense");
        mPostDefenseCommandParser = new CommandParser(aSnobot, mPostDefenseTable, "PostDefense", mSelectStartPosition, mDefenseCommandParser, mDefenseInFront);

        this.putOnDash();
        addListeners();
    }

    private void addListeners()
    {
        // Whenever the SendableChooser is changed (different button selected),
        // this will call build auton, forcing the position to update, and the
        // command parsers to re-send the autonomous mode
        ITableListener selectionChangeListener = new ITableListener()
        {

            @Override
            public void valueChanged(ITable arg0, String arg1, Object arg2, boolean arg3)
            {
                buildAnAuton();
            }
        };

        ITableListener saveDefenseModeListener = new ITableListener()
        {

            @Override
            public void valueChanged(ITable arg0, String arg1, Object arg2, boolean arg3)
            {
                if (mDefenseTable.getBoolean(SmartDashBoardNames.sSAVE_AUTON, false))
                {
                    mDefenseCommandParser.saveAutonMode();
                }
            }
        };

        ITableListener savePostDefenseModeListener = new ITableListener()
        {

            @Override
            public void valueChanged(ITable arg0, String arg1, Object arg2, boolean arg3)
            {
                if (mPostDefenseTable.getBoolean(SmartDashBoardNames.sSAVE_AUTON, false))
                {
                    mPostDefenseCommandParser.saveAutonMode();
                }
            }
        };

        mDefenseInFront.addChangeListener(selectionChangeListener);
        mSelectAutonomous.addChangeListener(selectionChangeListener);
        mSelectStartPosition.addChangeListener(selectionChangeListener);

        mDefenseTable.addTableListener(SmartDashBoardNames.sSAVE_AUTON, saveDefenseModeListener, true);
        mPostDefenseTable.addTableListener(SmartDashBoardNames.sSAVE_AUTON, savePostDefenseModeListener, true);
    }

    public void putOnDash()
    {
        mSelectAutonomous.putOnDash();
        mSelectStartPosition.putOnDash();
        mDefenseInFront.putOnDash();
    }

    public CommandGroup buildAnAuton()
    {
        CommandGroup cobbledCommandGroup = new CommandGroup();

        try
        {
            mSelectStartPosition.setStartPosition();

            mDefenseCommandParser.readFile(mDefenseInFront.getDefensePath()); // Forces a re-read, publish to dashboard

            cobbledCommandGroup.addSequential(mPostDefenseCommandParser.readFile(mSelectAutonomous.getSelected()));
        }
        catch (Exception e)
        {
            System.err.println("Could not read auton files");
            e.printStackTrace();
        }
        return cobbledCommandGroup;
    }
}
