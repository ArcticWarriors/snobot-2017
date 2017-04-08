package com.snobot2017.autonomous;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.snobot.lib.autonomous.ACommandParser;
import com.snobot.lib.motion_profile.ISetpointIterator;
import com.snobot.lib.motion_profile.PathConfig;
import com.snobot.lib.motion_profile.PathGenerator;
import com.snobot.lib.motion_profile.PathSetpoint;
import com.snobot.lib.motion_profile.StaticSetpointIterator;
import com.snobot2017.ISnobot2017;
import com.snobot2017.Properties2017;
import com.snobot2017.SmartDashBoardNames;
import com.snobot2017.Snobot2017;
import com.snobot2017.autonomous.AutonomousFactory.StartingPositions;
import com.snobot2017.autonomous.commands.DriveStraightADistance;
import com.snobot2017.autonomous.commands.DriveToPegUsingVision;
import com.snobot2017.autonomous.commands.GoToPositionInSteps;
import com.snobot2017.autonomous.commands.GoToPositionSmoothly;
import com.snobot2017.autonomous.commands.LowerGear;
import com.snobot2017.autonomous.commands.RaiseGear;
import com.snobot2017.autonomous.commands.Replay;
import com.snobot2017.autonomous.commands.StupidDriveStraight;
import com.snobot2017.autonomous.commands.StupidTurn;
import com.snobot2017.autonomous.commands.TurnToAngle;
import com.snobot2017.autonomous.commands.position_based.DriveStraightPathWithGryoBasedOnStartingPosition;
import com.snobot2017.autonomous.commands.position_based.TrajectoryGearToFuelFactory;
import com.snobot2017.autonomous.commands.position_based.TrajectoryGearToHopperFactory;
import com.snobot2017.autonomous.commands.position_based.TrajectoryStartToGearFactory;
import com.snobot2017.autonomous.commands.position_based.TrajectoryStartToHopperFactory;
import com.snobot2017.autonomous.commands.position_based.TurnToAngleBasedOnStartingPosition;
import com.snobot2017.autonomous.path.DriveStraightPath;
import com.snobot2017.autonomous.path.DriveStraightPathWithGyro;
import com.snobot2017.autonomous.path.DriveTurnPath;
import com.snobot2017.autonomous.trajectory.TrajectoryPathCommand;
import com.team254.lib.trajectory.Path;
import com.team254.lib.trajectory.io.TextFileDeserializer;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.tables.ITable;

/**
 * Creates commands from a file path and adds them to a CommandGroup.
 * 
 * @author Alec/Andrew
 *
 */
public class CommandParser extends ACommandParser
{
    private static final double sEXPECTED_DT = .02;
    protected ISnobot2017 mSnobot;
    protected SendableChooser<StartingPositions> mPositionChooser;
    protected ITable mAutonTable;

    /**
     * Creates a CommandParser object.
     * 
     * @param aSnobot
     *            The robot using the CommandParser.
     * @param aPositionChooser
     * @param aStartPosition
     */
    public CommandParser(Snobot2017 aSnobot, SendableChooser<StartingPositions> aPositionChooser)
    {
        super(" ", "#");
        mAutonTable = NetworkTable.getTable(SmartDashBoardNames.sAUTON_TABLE_NAME);
        mSnobot = aSnobot;
        mPositionChooser = aPositionChooser;

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
            // Generic
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

            // Subsystems
            case AutonomousCommandNames.sPLACE_GEAR_COMMAND:
            {
                newCommand = parsePlaceGearCommand(args);
                break;
            }
            case AutonomousCommandNames.sRAISE_GEAR_COMMAND:
            {
                newCommand = parseRaiseGearCommand(args);
                break;
            }

            // Stupid
            case AutonomousCommandNames.sSTUPID_DRIVE_STRAIGHT_COMMAND:
            {
                newCommand = parseStupidDriveStraightCommand(args);
                break;
            }
            case AutonomousCommandNames.sSTUPID_TURN_COMMAND:
            {
                newCommand = parseStupidTurnCommand(args);
                break;
            }
            case AutonomousCommandNames.sREPLAY:
            {
                newCommand = parseReplayCommand(args);
                break;
            }

            // Smarter
            case AutonomousCommandNames.sTURN_TO_ANGLE:
            {
                newCommand = parseTurnToAngle(args);
                break;
            }
            case AutonomousCommandNames.sDRIVE_STRAIGHT_A_DISTANCE:
            {
                newCommand = parseDriveStraightADistance(args);
                break;
            }
            case AutonomousCommandNames.sGO_TO_POSITION_SMOOTH_IN_STEPS:
            {
                newCommand = parseGoToPositionInStepsCommand(args);
                break;
            }
            case AutonomousCommandNames.sGO_TO_POSITION_SMOOTHLY:
            {
                newCommand = parseGoToPositionSmoothlyCommand(args);
                break;
            }
            case AutonomousCommandNames.sTURN_TO_PEG_AFTER_PATH_FROM_STARTING_POSITION:
            {
                newCommand = parseTurnToPegAfterPathFromStartingPosition(args);
                break;
            }
            case AutonomousCommandNames.sDRIVE_TO_PEG_USING_VISION:
            {
                newCommand = parseDriveToPegUsingVisionCommand(args);
                break;
            }

            // Path and Trajectory
            case AutonomousCommandNames.sDRIVE_PATH_STRAIGHT:
            {
                newCommand = createDrivePathCommand(args);
                break;
            }
            case AutonomousCommandNames.sDRIVE_PATH_TURN:
            {
                newCommand = createTurnPathCommand(args);
                break;
            }
            case AutonomousCommandNames.sDRIVE_TRAJECTORY:
            {
                newCommand = createTrajectoryCommand(args.get(1));
                break;
            }
            case AutonomousCommandNames.sDRIVE_STRAIGHT_PATH_WITH_GYRO:
            {
                newCommand = parseDriveStraightPathWithGyro(args);
                break;
            }

            // StartingPosition modes
            case AutonomousCommandNames.sTRAJ_START_TO_GEAR:
            {
                newCommand = createTrajStartToGearCommand(args);
                break;
            }
            case AutonomousCommandNames.sTRAJ_START_TO_HOPPER:
            {
                newCommand = createTrajStartToHopper(args);
                break;
            }
            case AutonomousCommandNames.sTRAJ_GEAR_TO_HOPPER:
            {
                newCommand = createTrajGearToHopper(args);
                break;
            }
            case AutonomousCommandNames.sTRAJ_GEAR_TO_FUEL:
            {
                newCommand = createTrajGearToFuel();
                break;
            }

            case AutonomousCommandNames.sDRIVE_STRAIGHT_PATH_WITH_GYRO_FROM_STARTING_POSITION:
            {
                newCommand = parseDriveStraightPathWithGyroFromStartingPosition(args);
                break;
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


    //////////////////////////////////////////
    // Subsystem Commands
    //////////////////////////////////////////
    private Command parsePlaceGearCommand(List<String> args)
    {
        double time = Double.parseDouble(args.get(1));
        return new LowerGear(mSnobot.getGearBoss(), time);
    }

    private Command parseRaiseGearCommand(List<String> args)
    {
        double time = Double.parseDouble(args.get(1));
        return new RaiseGear(mSnobot.getGearBoss(), time);
    }

    //////////////////////////////////////////
    // Stupid Commands
    //////////////////////////////////////////

    private Command parseStupidDriveStraightCommand(List<String> args)
    {
        double time = Double.parseDouble(args.get(1));
        double speed = Double.parseDouble(args.get(2));
        return new StupidDriveStraight(mSnobot.getDriveTrain(), time, speed);
    }

    private Command parseStupidTurnCommand(List<String> args)
    {
        double time = Double.parseDouble(args.get(1));
        double speed = Double.parseDouble(args.get(2));
        return new StupidTurn(mSnobot.getDriveTrain(), time, speed);
    }

    private Command parseReplayCommand(List<String> args) throws IOException
    {
        String autoPath = Properties2017.sREPLAY_PATH.getValue() + args.get(1);
        return new Replay(mSnobot.getDriveTrain(), autoPath);
    }

    //////////////////////////////////////////
    // Smarter
    //////////////////////////////////////////
    private Command parseTurnToAngle(List<String> args)
    {
        double speed = Double.parseDouble(args.get(1));
        double angle = Double.parseDouble(args.get(2));
        return new TurnToAngle(speed, angle, mSnobot.getSnobotActor());
    }

    private Command parseDriveStraightADistance(List<String> args)
    {
        double distance = Double.parseDouble(args.get(1));
        double speed = Double.parseDouble(args.get(2));
        return new DriveStraightADistance(distance, speed, mSnobot.getSnobotActor());
    }

    private Command parseGoToPositionInStepsCommand(List<String> args) throws IOException
    {
        double x = Double.parseDouble(args.get(1));
        double y = Double.parseDouble(args.get(2));
        double speed = .5;
        if (args.size() > 3)
        {
            speed = Double.parseDouble(args.get(3));
        }

        return new GoToPositionInSteps(x, y, speed, mSnobot.getSnobotActor());
    }

    private Command parseGoToPositionSmoothlyCommand(List<String> args)
    {
        double x = Double.parseDouble(args.get(1));
        double y = Double.parseDouble(args.get(2));

        return new GoToPositionSmoothly(x, y, mSnobot.getSnobotActor());
    }

    private Command parseDriveToPegUsingVisionCommand(List<String> args)
    {
        double timeout = 4;
        double deadband = 6;

        if (args.size() >= 2)
        {
            timeout = Double.parseDouble(args.get(1));
        }

        if (args.size() >= 3)
        {
            deadband = Double.parseDouble(args.get(2));
        }

        return new DriveToPegUsingVision(mSnobot.getVisionManager(), mSnobot.getSnobotActor(), deadband, timeout);
    }

    //////////////////////////////////////////
    // Path and Trajectory
    //////////////////////////////////////////
    private Command createDrivePathCommand(List<String> args)
    {
        PathConfig dudePathConfig = new PathConfig(Double.parseDouble(args.get(1)), // Endpoint
                Double.parseDouble(args.get(2)), // Max Velocity
                Double.parseDouble(args.get(3)), // Max Acceleration
                sEXPECTED_DT);

        PathGenerator dudePathGenerator = new PathGenerator();
        List<PathSetpoint> dudeList = dudePathGenerator.generate(dudePathConfig);
        ISetpointIterator dudeSetpointIterator = new StaticSetpointIterator(dudeList);

        return new DriveStraightPath(mSnobot.getDriveTrain(), mSnobot.getPositioner(), dudeSetpointIterator);
    }

    private Command createTurnPathCommand(List<String> args)
    {
        PathConfig dudePathConfig = new PathConfig(Double.parseDouble(args.get(1)), // Endpoint
                Double.parseDouble(args.get(2)), // Max Velocity
                Double.parseDouble(args.get(3)), // Max Acceleration
                sEXPECTED_DT);

        ISetpointIterator dudeSetpointIterator = new StaticSetpointIterator(dudePathConfig);

        return new DriveTurnPath(mSnobot.getDriveTrain(), mSnobot.getPositioner(), dudeSetpointIterator);
    }

    private Command createTrajectoryCommand(String aFile)
    {
        String pathFile = Properties2017.sAUTON_PATH_DIRECTORY.getValue() + "/" + aFile.trim();
        TextFileDeserializer deserializer = new TextFileDeserializer();
        Path p = deserializer.deserializeFromFile(pathFile);

        Command output = null;
        if (p == null)
        {
            addError("Could not read path file " + pathFile);
        }
        else
        {
            output = new TrajectoryPathCommand(mSnobot.getDriveTrain(), mSnobot.getPositioner(), p);
        }

        return output;
    }


    private Command parseDriveStraightPathWithGyro(List<String> args)
    {
        PathConfig dudePathConfig = new PathConfig(
                Double.parseDouble(args.get(1)), // Endpoint
                Double.parseDouble(args.get(2)), // Max Velocity
                Double.parseDouble(args.get(3)), // Max Acceleration
                sEXPECTED_DT);
        ISetpointIterator dudeSetpointIterator = new StaticSetpointIterator(dudePathConfig);
        return new DriveStraightPathWithGyro(mSnobot.getDriveTrain(), mSnobot.getPositioner(), dudeSetpointIterator);
    }

    /////////////////////////////////////////////////
    // StartingPosition based commands
    /////////////////////////////////////////////////
    private Command createTrajStartToGearCommand(List<String> args)
    {
        StartingPositions startPosition = getStartingPosition();
        if (startPosition == null)
        {
            addError("Invalid starting position");
            return null;
        }

        return TrajectoryStartToGearFactory.createCommand(mSnobot, startPosition);
    }

    private Command createTrajStartToHopper(List<String> args)
    {
        StartingPositions startPosition = getStartingPosition();
        if (startPosition == null)
        {
            addError("Invalid starting position");
            return null;
        }

        boolean doClose = true;
        if (args.size() > 1 && args.get(1).equals("Far"))
        {
            doClose = false;
        }

        return TrajectoryStartToHopperFactory.createCommand(mSnobot, startPosition, doClose);
    }

    private Command createTrajGearToHopper(List<String> args)
    {
        StartingPositions startPosition = getStartingPosition();
        if (startPosition == null)
        {
            addError("Invalid starting position");
            return null;
        }

        boolean doClose = true;
        if (args.size() > 1 && args.get(1).equals("Far"))
        {
            doClose = false;
        }

        return TrajectoryGearToHopperFactory.createCommand(mSnobot, startPosition, doClose);
    }

    private Command createTrajGearToFuel()
    {
        StartingPositions startPosition = getStartingPosition();
        if (startPosition == null)
        {
            addError("Invalid starting position");
            return null;
        }

        return TrajectoryGearToFuelFactory.createCommand(mSnobot, startPosition);
    }

    private Command parseTurnToPegAfterPathFromStartingPosition(List<String> args)
    {
        StartingPositions startPosition = getStartingPosition();
        double lTurnRedLeft = Double.parseDouble(args.get(1));
        double lTurnRedRight = Double.parseDouble(args.get(2));
        double lTurnRedMiddle = Double.parseDouble(args.get(3));
        double lTurnBlueLeft = Double.parseDouble(args.get(4));
        double lTurnBlueRight = Double.parseDouble(args.get(5));
        double lTurnBlueMiddle = Double.parseDouble(args.get(6));

        return new TurnToAngleBasedOnStartingPosition(Properties2017.sSIDE_AUTO_TURN_SPEED.getValue(), startPosition, mSnobot.getPositioner(),
                mSnobot.getSnobotActor(), lTurnRedLeft, lTurnRedRight, lTurnRedMiddle, lTurnBlueLeft, lTurnBlueRight, lTurnBlueMiddle);
    }

    private Command parseDriveStraightPathWithGyroFromStartingPosition(List<String> args)
    {
        StartingPositions startPosition = getStartingPosition();
        double lMaxVelocity = Double.parseDouble(args.get(1));
        double lMaxAcceleration = Double.parseDouble(args.get(2));
        double lRedLeft = Double.parseDouble(args.get(3));
        double lRedRight = Double.parseDouble(args.get(4));
        double lRedMiddle = Double.parseDouble(args.get(5));
        double lBlueLeft = Double.parseDouble(args.get(6));
        double lBlueRight = Double.parseDouble(args.get(7));
        double lBlueMiddle = Double.parseDouble(args.get(8));
        return new DriveStraightPathWithGryoBasedOnStartingPosition(mSnobot.getDriveTrain(), mSnobot.getPositioner(), startPosition, lMaxVelocity,
                lMaxAcceleration, sEXPECTED_DT, lRedLeft, lRedRight, lRedMiddle, lBlueLeft, lBlueRight, lBlueMiddle);
    }

    ////////////////////////////////////////
    // Maintenance
    ////////////////////////////////////////

    protected StartingPositions getStartingPosition()
    {
        return mPositionChooser.getSelected();
    }

    @Override
    public CommandGroup readFile(String aFilePath)
    {
        return super.readFile(aFilePath);
    }

    @Override
    protected void publishFileName(String aFileName)
    {
        if (aFileName == null)
        {
            aFileName = "NOT FOUND!";
        }

        mAutonTable.putString(SmartDashBoardNames.sAUTON_FILENAME, aFileName);
    }

    @Override
    protected void publishParsingResults(String aCommandString)
    {
        if (!mErrorText.isEmpty())
        {
            aCommandString += "\n\n# There was an error parsing the commands...\n#\n";
            aCommandString += mErrorText;
        }
        
        mAutonTable.putString(SmartDashBoardNames.sROBOT_COMMAND_TEXT, aCommandString);
        mAutonTable.putBoolean(SmartDashBoardNames.sSUCCESFULLY_PARSED_AUTON, mSuccess);
    }

    public void saveAutonMode()
    {
        String new_text = mAutonTable.getString(SmartDashBoardNames.sROBOT_COMMAND_TEXT, "");
        String filename = mAutonTable.getString(SmartDashBoardNames.sAUTON_FILENAME, "auton_file.txt");

        System.out.println("*****************************************");
        System.out.println("Saving auton mode to " + filename);
        System.out.println("*****************************************");

        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filename)));
            bw.write(new_text);
            bw.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        mAutonTable.putBoolean(SmartDashBoardNames.sSAVE_AUTON, false);
    }
}
