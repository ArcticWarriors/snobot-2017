package com.snobot2017.xtrajectory_gen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.team254.lib.trajectory.Path;
import com.team254.lib.trajectory.gen.PathGenerator;
import com.team254.lib.trajectory.gen.TrajectoryGenerator;
import com.team254.lib.trajectory.gen.WaypointSequence;
import com.team254.lib.trajectory.io.TextFileSerializer;

public class BasePathGenerator
{
    protected static final double ROBOT_LENGTH = 3 * 12;

    protected static final double CENTER_X = 0;
    protected static final double FIELD_LENGTH_Y = 336;
    protected static final double BOILER_LINE_OFFSET = 43.02;
    protected static final double LOADING_X_OFFSET = -79.53;
    protected static final double NON_CENTER_GEAR_X_OFFSET = 48;
    protected static final double CENTER_GEAR_Y = FIELD_LENGTH_Y - 7 * 12 + ROBOT_LENGTH;
    protected static final double NON_CENTER_CENTER_GEAR_Y = 197.5 + ROBOT_LENGTH;

    protected static final double HOPPER_ONE_Y = -200.7 + ROBOT_LENGTH;
    protected static final double HOPPER_TWO_Y = -163.11 + ROBOT_LENGTH;
    protected static final double HOPPER_THREE_Y = 163.11 + ROBOT_LENGTH;
    protected static final double HOPPER_FOUR_Y = -200.7 + ROBOT_LENGTH;
    protected static final double HOPPER_FIVE_Y = -200.7 + ROBOT_LENGTH;

    protected static final double LEFT_HOPPERS_X = -13.5 * 12;
    protected static final double RIGHT_HOPPERS_X = 13.5 * 12;

    protected static final TrajectoryGenerator.Config RED_SCORE_GEAR_CONFIG = new TrajectoryGenerator.Config();
    protected static final TrajectoryGenerator.Config BLUE_SCORE_GEAR_CONFIG = new TrajectoryGenerator.Config();

    static
    {
        RED_SCORE_GEAR_CONFIG.dt = .02;
        RED_SCORE_GEAR_CONFIG.max_acc = 120;
        RED_SCORE_GEAR_CONFIG.max_jerk = 480;
        RED_SCORE_GEAR_CONFIG.max_vel = 50;

        BLUE_SCORE_GEAR_CONFIG.dt = .02;
        BLUE_SCORE_GEAR_CONFIG.max_acc = 120;
        BLUE_SCORE_GEAR_CONFIG.max_jerk = 480;
        BLUE_SCORE_GEAR_CONFIG.max_vel = 50;

    }

    protected String joinPath(String path1, String path2)
    {
        File file1 = new File(path1);
        File file2 = new File(file1, path2);
        return file2.getPath();
    }

    protected boolean writeFile(String path, String data)
    {
        try
        {
            File file = new File(path);

            // if file doesnt exists, then create it
            if (!file.exists())
            {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
        }
        catch (IOException e)
        {
            return false;
        }

        return true;
    }

    protected void generate(TrajectoryGenerator.Config config, WaypointSequence p, String directory, String path_name, double kWheelbaseWidth)
    {
        Path path = PathGenerator.makePath(p, config, kWheelbaseWidth, path_name);

        // Outputs to the directory supplied as the first argument.
        TextFileSerializer js = new TextFileSerializer();
        String serialized = js.serialize(path);
        // System.out.print(serialized);
        String fullpath = new File(joinPath(directory, path_name + ".csv")).getAbsolutePath();
        if (!writeFile(fullpath, serialized))
        {
            System.err.println(fullpath + " could not be written!!!!");
            System.exit(1);
        }
        else
        {
            System.out.println("Wrote " + fullpath);
        }
    }
}
