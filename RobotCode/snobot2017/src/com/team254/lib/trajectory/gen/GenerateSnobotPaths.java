package com.team254.lib.trajectory.gen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.team254.lib.trajectory.Path;
import com.team254.lib.trajectory.gen.WaypointSequence.Waypoint;

public class GenerateSnobotPaths
{
    private static double mYPosForDefenses = 140;

    public static String joinPath(String path1, String path2)
    {
        File file1 = new File(path1);
        File file2 = new File(file1, path2);
        return file2.getPath();
    }

    private static boolean writeFile(String path, String data)
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

    private static void generate(TrajectoryGenerator.Config config, WaypointSequence p, String directory, String path_name, double kWheelbaseWidth)
    {
        // WaypointSequence waypoints,
        // TrajectoryGenerator.Config config, double wheelbase_width,
        // String name

        Path path = PathGenerator.makePath(p, config, kWheelbaseWidth, path_name);

        // Outputs to the directory supplied as the first argument.
        TextFileSerializer js = new TextFileSerializer();
        String serialized = js.serialize(path);
        // System.out.print(serialized);
        String fullpath = joinPath(directory, path_name + ".csv");
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

    private static void genLowBarToLowGoal(String directory, double kWheelbaseWidth)
    {
        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        final String path_name = "LowBarToLowGoal";

        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 50;

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(-135, 30, 0));
        p.addWaypoint(new Waypoint(-135, 200, 0));
        p.addWaypoint(new Waypoint(-50, 305, 45));

        generate(config, p, directory, path_name, kWheelbaseWidth);
    }

    private static void genPos2ToLowGoal(String Directory, double kWheelbaseWidth)
    {
        TrajectoryGenerator.Config dudeConfig = new TrajectoryGenerator.Config();
        final String dudePathName = "Position2ToLowGoal";
        dudeConfig.dt = .02;
        dudeConfig.max_acc = 120;
        dudeConfig.max_jerk = 480;
        dudeConfig.max_vel = 50;

        WaypointSequence dudeP = new WaypointSequence(10000);
        dudeP.addWaypoint(new Waypoint(-82.875, mYPosForDefenses, 0));
        dudeP.addWaypoint(new Waypoint(-82.875, mYPosForDefenses + 200, 0));
        dudeP.addWaypoint(new Waypoint(-50, 305, 45));

        generate(dudeConfig, dudeP, Directory, dudePathName, kWheelbaseWidth);
    }

    private static void genPos3ToLowGoal(String Directory, double kWheelbaseWidth)
    {
        TrajectoryGenerator.Config dudeConfig = new TrajectoryGenerator.Config();
        final String dudePathName = "Position3ToLowGoal";
        dudeConfig.dt = .02;
        dudeConfig.max_acc = 120;
        dudeConfig.max_jerk = 480;
        dudeConfig.max_vel = 50;

        WaypointSequence dudeP = new WaypointSequence(10000);
        dudeP.addWaypoint(new Waypoint(-30, mYPosForDefenses, 0));
        dudeP.addWaypoint(new Waypoint(-30, mYPosForDefenses + 170, 0));
        dudeP.addWaypoint(new Waypoint(-50, 305, 45));

        generate(dudeConfig, dudeP, Directory, dudePathName, kWheelbaseWidth);
    }

    private static void genPos4ToLowGoal(String Directory, double kWheelbaseWidth)
    {
        TrajectoryGenerator.Config dudeConfig = new TrajectoryGenerator.Config();
        final String dudePathName = "Position4ToLowGoal";
        dudeConfig.dt = .02;
        dudeConfig.max_acc = 120;
        dudeConfig.max_jerk = 480;
        dudeConfig.max_vel = 50;

        WaypointSequence dudeP = new WaypointSequence(10000);
        dudeP.addWaypoint(new Waypoint(22.875, mYPosForDefenses, 0));
        dudeP.addWaypoint(new Waypoint(22.875, mYPosForDefenses + 170, 0));
        dudeP.addWaypoint(new Waypoint(50, 305, -45));

        generate(dudeConfig, dudeP, Directory, dudePathName, kWheelbaseWidth);
    }

    private static void genPos5ToLowGoal(String Directory, double kWheelbaseWidth)
    {
        TrajectoryGenerator.Config dudeConfig = new TrajectoryGenerator.Config();
        final String dudePathName = "Position5ToLowGoal";
        dudeConfig.dt = .02;
        dudeConfig.max_acc = 120;
        dudeConfig.max_jerk = 480;
        dudeConfig.max_vel = 50;

        WaypointSequence dudeP = new WaypointSequence(10000);
        dudeP.addWaypoint(new Waypoint(75.75, mYPosForDefenses, 0));
        dudeP.addWaypoint(new Waypoint(75.75, mYPosForDefenses + 170, 0));
        dudeP.addWaypoint(new Waypoint(50, 305, -45));

        generate(dudeConfig, dudeP, Directory, dudePathName, kWheelbaseWidth);
    }

    public static void main(String[] args)
    {
        String directory = "../../RobotCode/snobot2016/resources/traj/";
        File f = new File(directory);
        System.out.println(f.getAbsolutePath());

        if (args.length >= 1)
        {
            directory = args[0];
        }

        final double kWheelbaseWidth = 25.5 / 12;

        genLowBarToLowGoal(directory, kWheelbaseWidth);
        genPos2ToLowGoal(directory, kWheelbaseWidth);
        genPos3ToLowGoal(directory, kWheelbaseWidth);
        genPos4ToLowGoal(directory, kWheelbaseWidth);
        genPos5ToLowGoal(directory, kWheelbaseWidth);
    }
}
