package com.snobot2017.autonomous.commands.position_based;

import com.snobot2017.ISnobot2017;
import com.snobot2017.Properties2017;
import com.snobot2017.autonomous.AutonomousFactory.StartingPositions;
import com.snobot2017.autonomous.trajectory.TrajectoryPathCommand;
import com.team254.lib.trajectory.Path;
import com.team254.lib.trajectory.io.TextFileDeserializer;

public class TrajectoryGearToFuelFactory
{
    public static TrajectoryPathCommand createCommand(ISnobot2017 aSnobot, StartingPositions aStartPosition)
    {
        String boilFilename = null;

        switch (aStartPosition)
        {
        case RedRight:
            boilFilename = "GearToBoiler/RedRightGearToBoiler.csv";
            break;
        case RedMiddle:
            boilFilename = "GearToBoiler/RedMiddleGearToBoiler.csv";
            break;
        case BlueMiddle:
            boilFilename = "GearToBoiler/BlueMiddleGearToBoiler.csv";
            break;
        case BlueLeft:
            boilFilename = "GearToBoiler/BlueLeftGearToBoiler.csv";
            break;

        case RedLeft:
        case BlueRight:
        default:
            break;
        }

        if (boilFilename != null)
        {
            String pathFile = Properties2017.sAUTON_PATH_DIRECTORY.getValue() + "/" + boilFilename.trim();
            TextFileDeserializer deserializer = new TextFileDeserializer();
            Path p = deserializer.deserializeFromFile(pathFile);

            return new TrajectoryPathCommand(aSnobot.getDriveTrain(), aSnobot.getPositioner(), p);
        }

        return null;
    }

}
