package com.snobot2017.autonomous;

import java.io.IOException;
import java.util.List;

import com.snobot.lib.autonomous.ACommandParser;
import com.snobot2017.SmartDashBoardNames;
import com.snobot2017.Snobot2017;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * Creates commands from a file path and adds them to a CommandGroup.
 * 
 * @author Alec/Andrew
 *
 */
public class CommandParser extends ACommandParser
{
    protected Snobot2017 mSnobot;

    /**
     * Creates a CommandParser object.
     * 
     * @param aSnobot
     *            The robot using the CommandParser.
     * @param aStartPosition
     */
    public CommandParser(Snobot2017 aSnobot)
    {
        super(NetworkTable.getTable(SmartDashBoardNames.sAUTON_TABLE_NAME), SmartDashBoardNames.sROBOT_COMMAND_TEXT,
                SmartDashBoardNames.sSUCCESFULLY_PARSED_AUTON, " ", "#");

        mSnobot = aSnobot;
    }

    /**
     * Takes a list of Strings and creates a Command.
     * 
     * @param args
     *            The command's name and parameters.
     */
    @Override
    protected Command parseCommand(List<String> args)
    {
        String commandName = args.get(0);
        Command newCommand = null;
        try
        {
            switch (commandName)
            {
            case AutonomousCommandNames.sPARALLEL_COMMAND:
            {
                newCommand = parseParallelCommand(args);
                break;
            }
            case AutonomousCommandNames.sWAIT_COMMAND:
            {
                newCommand = parseWaitCommand(args);
                break;
            }
            case AutonomousCommandNames.sSTUPID_DRIVE_STRAIGHT_COMMAND:
            {
                newCommand = parseStupidDriveStraightCommand(args);
                break;
            }
            case AutonomousCommandNames.sSCORE_GEAR_COMMAND:
            {
                newCommand = parseScoreGearCommand(args);
                break;
            }
            case AutonomousCommandNames.sAUTON_COPY:
            {
                newCommand = parseAutonCopyCommand();
            }
            default:
                addError("Received unexpected command name '" + commandName + "'");
            }
        }
        catch (IndexOutOfBoundsException e)
        {
            addError("You have not specified enough aguments for the command: " + commandName + ". " + e.getMessage());
        }
        catch (Exception e)
        {
            addError("Failed to parse the command: " + commandName + ". " + e.getMessage());
            e.printStackTrace();
        }
        return newCommand;
    }

    private Command parseScoreGearCommand(List<String> args)
    {
        // TODO Auto-generated method stub
        return null;
    }

    private Command parseMoveGearLowCommand(List<String> args)
    {
        // TODO Auto-generated method stub
        return null;
    }

    private Command parseStupidDriveStraightCommand(List<String> args)
    {
        double time = Double.parseDouble(args.get(1));
        double speed = Double.parseDouble(args.get(2));
        return new StupidDriveStraight(mSnobot.getDriveTrain(), time, speed);
    }

    private Command parseAutonCopyCommand() throws IOException
    {
        return new AutonCopy(mSnobot.getDriveTrain());
    }

    protected Command parseWaitCommand(List<String> args)
    {
        double time = Double.parseDouble(args.get(1));
        return new WaitCommand(time);
    }
}
