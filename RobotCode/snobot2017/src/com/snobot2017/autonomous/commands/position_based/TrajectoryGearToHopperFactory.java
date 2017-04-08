package com.snobot2017.autonomous.commands.position_based;

import com.snobot2017.ISnobot2017;
import com.snobot2017.Properties2017;
import com.snobot2017.autonomous.AutonomousFactory.StartingPositions;
import com.snobot2017.autonomous.trajectory.TrajectoryPathCommand;
import com.team254.lib.trajectory.Path;
import com.team254.lib.trajectory.io.TextFileDeserializer;

public class TrajectoryGearToHopperFactory
{
    public static TrajectoryPathCommand createCommand(ISnobot2017 aSnobot, StartingPositions aStartPosition, boolean aDoClose)
    {
        String hopperFilename = null;

        switch (aStartPosition)
        {
        case RedLeft:

            if (aDoClose)
            {
                hopperFilename = "GearToHopper/RedLeftScoreGearGetHopperFive.csv";
            }
            else
            {
                hopperFilename = "GearToHopper/RedLeftScoreGearGetHopperFour.csv";
            }

            break;
        case RedRight:

            if (aDoClose)
            {
                hopperFilename = "GearToHopper/RedRightScoreGearGetHopperOne.csv";
            }
            else
            {
                hopperFilename = "GearToHopper/RedRightScoreGearGetHopperTwo.csv";
            }
            break;
        case BlueRight:

            if (aDoClose)
            {
                hopperFilename = "GearToHopper/BlueRightScoreGearGetHopperFour.csv";
            }
            else
            {
                hopperFilename = "GearToHopper/BlueRightScoreGearGetHopperFive.csv";
            }
            break;
        case BlueLeft:

            if (aDoClose)
            {
                hopperFilename = "GearToHopper/BlueLeftScoreGearGetHopperThree.csv";
            }
            else
            {
                hopperFilename = "GearToHopper/BlueLeftScoreGearGetHoppertwo.csv";
            }
            break;

        // Intentional fall through, nothing to do
        case Origin:
        default:
            break;
        }


        if (hopperFilename != null)
        {
            String pathFile = Properties2017.sAUTON_PATH_DIRECTORY.getValue() + "/" + hopperFilename.trim();
            TextFileDeserializer deserializer = new TextFileDeserializer();
            Path p = deserializer.deserializeFromFile(pathFile);

            return new TrajectoryPathCommand(aSnobot.getDriveTrain(), aSnobot.getPositioner(), p);
        }

        return null;
    }

}
