package com.snobot2017.autonomous.commands.position_based;

import com.snobot2017.ISnobot2017;
import com.snobot2017.Properties2017;
import com.snobot2017.autonomous.AutonomousFactory.StartingPositions;
import com.snobot2017.autonomous.trajectory.TrajectoryPathCommand;
import com.team254.lib.trajectory.Path;
import com.team254.lib.trajectory.io.TextFileDeserializer;

public class TrajectoryStartToHopperFactory
{
    public static TrajectoryPathCommand createCommand(ISnobot2017 aSnobot, StartingPositions aStartPosition, boolean aDoClose)
    {
        String fileName = null;

        switch (aStartPosition)
        {
        case RedLeft:
            if (aDoClose)
            {
                fileName = "StartToHopper/RedLeftToHopperFive.csv";
            }
            else
            {
                fileName = "StartToHopper/RedLeftToHopperFour.csv";
            }
            break;
        case RedMiddle:
            if (aDoClose)
            {
                fileName = "StartToHopper/RedMiddleToHopperOne.csv";
            }
            else
            {
                fileName = "StartToHopper/RedMiddleToHopperFive.csv";
            }
            break;
        case RedRight:
            if (aDoClose)
            {
                fileName = "StartToHopper/RedRightToHopperOne.csv";
            }
            else
            {
                fileName = "StartToHopper/RedRightToHopperTwo.csv";
            }
            break;
        case BlueRight:
            if (aDoClose)
            {
                fileName = "StartToHopper/BlueRightToHopperFour.csv";
            }
            else
            {
                fileName = "StartToHopper/BlueRightToHopperFive.csv";
            }
            break;
        case BlueMiddle:
            if (aDoClose)
            {
                fileName = "StartToHopper/BlueMiddleToHopperThree.csv";
            }
            else
            {
                fileName = "StartToHopper/BlueMiddleToHopperFour.csv";
            }
            break;
        case BlueLeft:
            if (aDoClose)
            {
                fileName = "StartToHopper/BlueLeftToHopperThree.csv";
            }
            else
            {
                fileName = "StartToHopper/BlueLeftToHopperTwo.csv";
            }
            break;
        default:
            break;
        }

        if (fileName != null)
        {
            String pathFile = Properties2017.sAUTON_PATH_DIRECTORY.getValue() + "/" + fileName.trim();
            TextFileDeserializer deserializer = new TextFileDeserializer();
            Path p = deserializer.deserializeFromFile(pathFile);

            return new TrajectoryPathCommand(aSnobot.getDriveTrain(), aSnobot.getPositioner(), p);
        }
        
        return null;
    }

}
