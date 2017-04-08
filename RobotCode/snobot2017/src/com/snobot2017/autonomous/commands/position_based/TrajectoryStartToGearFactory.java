package com.snobot2017.autonomous.commands.position_based;

import com.snobot2017.ISnobot2017;
import com.snobot2017.Properties2017;
import com.snobot2017.autonomous.AutonomousFactory.StartingPositions;
import com.snobot2017.autonomous.trajectory.TrajectoryPathCommand;
import com.team254.lib.trajectory.Path;
import com.team254.lib.trajectory.io.TextFileDeserializer;

public class TrajectoryStartToGearFactory
{
    public static TrajectoryPathCommand createCommand(ISnobot2017 aSnobot, StartingPositions aStartPosition)
    {
        String scoreFilename = null;

        switch (aStartPosition)
        {
        case RedRight:
            scoreFilename = "StartToGear/RedRightScoreGear.csv";
            break;
        case RedMiddle:
            scoreFilename = "StartToGear/RedMiddleScoreGear.csv";
            break;
        case RedLeft:
            scoreFilename = "StartToGear/RedLeftScoreGear.csv";
            break;
        case BlueRight:
            scoreFilename = "StartToGear/BlueRightScoreGear.csv";
            break;
        case BlueMiddle:
            scoreFilename = "StartToGear/BlueMiddleScoreGear.csv";
            break;
        case BlueLeft:
            scoreFilename = "StartToGear/BlueLeftScoreGear.csv";
            break;
        default:
            break;
        }

        if (scoreFilename != null)
        {
            String pathFile = Properties2017.sAUTON_PATH_DIRECTORY.getValue() + "/" + scoreFilename.trim();
            TextFileDeserializer deserializer = new TextFileDeserializer();
            Path p = deserializer.deserializeFromFile(pathFile);

            return new TrajectoryPathCommand(aSnobot.getDriveTrain(), aSnobot.getPositioner(), p);
        }
        
        return null;
    }

}
