package com.team254.lib.trajectory.gen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.team254.lib.trajectory.Path;
import com.team254.lib.trajectory.gen.WaypointSequence.Waypoint;
import com.team254.lib.trajectory.io.TextFileSerializer;

public class GenerateSnobotPaths
{
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

    private static void getRedRightToHopperOne(String directory, double kWheelbaseWidth)
    {
        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        final String path_name = "RedRightToHopperOne";

        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 50;

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(42.9, -356.97, 0));
        p.addWaypoint(new Waypoint(102.23, -262.26, 45));
        p.addWaypoint(new Waypoint(163.13, -200.7, 45));

        generate(config, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getRedRightToHopperTwo(String directory, double kWheelbaseWidth)
    {
        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        final String path_name = "RedRightToHopperTwo";

        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 50;

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(42.9, -356.97, 0));
        p.addWaypoint(new Waypoint(105.3, -205.99, 0));
        p.addWaypoint(new Waypoint(160.23, -10, 45)); 
        p.addWaypoint(new Waypoint(163.13, 0, 45));

        generate(config, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getRedMiddleToHopperOne(String directory, double kWheelbaseWidth)
    {
        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        final String path_name = "RedMiddleToHoppeOne";

        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 50;

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, -356.97, 0));
        p.addWaypoint(new Waypoint(102.23, -262.26, 45));
        p.addWaypoint(new Waypoint(163.13, -200.7, 45));
        
        generate(config, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getRedMiddleToHopperTwo(String directory, double kWheelbaseWidth)
    {
        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        final String path_name = "RedMiddleToHopperTwo";

        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 50;

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, -356.97, 0));
        p.addWaypoint(new Waypoint(105.3, -205.99, 0));
        p.addWaypoint(new Waypoint(160.23, -10, 45)); 
        p.addWaypoint(new Waypoint(163.13, 0, 45));

        generate(config, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getRedMiddleToHopperFive(String directory, double kWheelbaseWidth)
    {
        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        final String path_name = "RedMiddleToHopperFive";

        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 50;

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, -356.97, 0));
        p.addWaypoint(new Waypoint(-112.12, -185.87, 0));
        p.addWaypoint(new Waypoint(-162.23, -119.16, -45)); 
        

        generate(config, p, directory, path_name, kWheelbaseWidth);
    }
    
    public static void main(String[] args)
    {
        String directory = "../snobot2017/resources/traj/";
        File f = new File(directory);
        System.out.println(f.getAbsolutePath());

        if (args.length >= 1)
        {
            directory = args[0];
        }

        final double kWheelbaseWidth = 25.5 / 12;

        getRedRightToHopperOne(directory, kWheelbaseWidth);
        getRedRightToHopperTwo(directory, kWheelbaseWidth);
        getRedMiddleToHopperOne(directory, kWheelbaseWidth);
        getRedMiddleToHopperTwo(directory, kWheelbaseWidth);
        getRedMiddleToHopperFive(directory, kWheelbaseWidth);
    }
}
