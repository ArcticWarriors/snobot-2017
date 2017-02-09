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
    
    private static void getRedLeftToHopperFive(String directory, double kWheelbaseWidth)
    {
        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        final String path_name = "RedLeftToHopperFive";

        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 50;

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(-79.52, -324.81, 0));
        p.addWaypoint(new Waypoint(-79.88, -356.97, 0));
        p.addWaypoint(new Waypoint(-100.12, -185.87, -45));
        p.addWaypoint(new Waypoint(-162.23, -119.16, -45)); 
        

        generate(config, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getBlueLeftToHopperThree(String directory, double kWheelbaseWidth)
    {
        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        final String path_name = "BlueLeftToHopperThree";

        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 50;

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(43.02, 334.81, 0));
        p.addWaypoint(new Waypoint(102.25, 270.82, -45));
        p.addWaypoint(new Waypoint(163.11, 203.79, -45)); 

        generate(config, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getBlueLeftToHopperTwo(String directory, double kWheelbaseWidth)
    {
        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        final String path_name = "BlueLeftToHopperTwo";

        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 50;

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(43.02, 334.81, 0));
        p.addWaypoint(new Waypoint(60.27, 270.82, -45));
        p.addWaypoint(new Waypoint(163.11, 0, -45)); 

        generate(config, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getBlueMiddleToHopperThree(String directory, double kWheelbaseWidth)
    {
        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        final String path_name = "BlueMiddleToHopperThree";

        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 50;

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, 334.81, 0));
        p.addWaypoint(new Waypoint(60.27, 270.82, -45));
        p.addWaypoint(new Waypoint(163.11, 203.79, -45)); 

        generate(config, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getBlueMiddleToHopperTwo(String directory, double kWheelbaseWidth)
    {
        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        final String path_name = "BlueMiddleToHopperTwo";

        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 50;

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, 334.81, 0));
        p.addWaypoint(new Waypoint(60.27, 270.82, -45));
        p.addWaypoint(new Waypoint(163.11, 0, -45)); 

        generate(config, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getBlueMiddleToHopperFour(String directory, double kWheelbaseWidth)
    {
        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        final String path_name = "BlueMiddleToHopperFour";

        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 50;

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, 334.81, 0));
        p.addWaypoint(new Waypoint(-80.01, 240.82, 45));
        p.addWaypoint(new Waypoint(-163.11, 117.58, 45)); 

        generate(config, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getBlueLeftToHopperFour(String directory, double kWheelbaseWidth)
    {
        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        final String path_name = "BlueLeftToHopperFour";

        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 50;

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(-79.52, 334.81, 0));
        p.addWaypoint(new Waypoint(-80.01, 240.82, 45));
        p.addWaypoint(new Waypoint(-163.11, 117.58, 45)); 

        generate(config, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getBlueMiddleScoreGear(String directory, double kWheelbaseWidth)
    {
        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        final String path_name = "BlueMiddleScoreGear";

        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 50;

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, 334.81, 0));
        p.addWaypoint(new Waypoint(0, 217, 0));

        generate(config, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getBlueLeftScoreGear(String directory, double kWheelbaseWidth)
    {
        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        final String path_name = "BlueLeftScoreGear";

        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 50;

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(-79.52, 334.81, 0));
        p.addWaypoint(new Waypoint(-34, 197.5, 0));

        generate(config, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getBlueRightScoreGear(String directory, double kWheelbaseWidth)
    {
        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        final String path_name = "BlueRightScoreGear";

        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 50;

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(43.02, 334.81, 0));
        p.addWaypoint(new Waypoint(34, 197.5, 0));

        generate(config, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getRedRightScoreGear(String directory, double kWheelbaseWidth)
    {
        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        final String path_name = "RedRightScoreGear";

        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 50;

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(43.02, -334.81, 0));
        p.addWaypoint(new Waypoint(34, -197.5, 0));

        generate(config, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getRedMiddleScoreGear(String directory, double kWheelbaseWidth)
    {
        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        final String path_name = "RedMiddleScoreGear";

        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 50;

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, -334.81, 0));
        p.addWaypoint(new Waypoint(0, -217, 0));

        generate(config, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getRedLeftScoreGear(String directory, double kWheelbaseWidth)
    {
        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        final String path_name = "RedLeftScoreGear";

        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 50;

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(-79.53, -334.81, 0));
        p.addWaypoint(new Waypoint(-34, -217, 0));

        generate(config, p, directory, path_name, kWheelbaseWidth);
    }
    
    //////////////////////////////////////////////////////////////
    // Test Trajectories
    //////////////////////////////////////////////////////////////
    private static void genTestStraightSlow(String directory, double kWheelbaseWidth)
    {
        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        final String path_name = "test/TestStraightSlow";

        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 36;

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, 0, 0));
        p.addWaypoint(new Waypoint(0, 96, 0));

        generate(config, p, directory, path_name, kWheelbaseWidth);
    }

    private static void genTestStraightFast(String directory, double kWheelbaseWidth)
    {
        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        final String path_name = "test/TestStraightFast";

        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 84;

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, 0, 0));
        p.addWaypoint(new Waypoint(0, 96, 0));

        generate(config, p, directory, path_name, kWheelbaseWidth);
    }

    private static void genTestMoveRightSlow(String directory, double kWheelbaseWidth)
    {
        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        final String path_name = "test/TestMoveRightSlow";

        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 36;

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, 0, 0));
        p.addWaypoint(new Waypoint(48, 96, 0));

        generate(config, p, directory, path_name, kWheelbaseWidth);
    }

    private static void genTestMoveLeftSlow(String directory, double kWheelbaseWidth)
    {
        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        final String path_name = "test/TestMoveLeftSlow";

        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 36;

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, 0, 0));
        p.addWaypoint(new Waypoint(-48, 96, 0));

        generate(config, p, directory, path_name, kWheelbaseWidth);
    }

    private static void genTestMoveRightFast(String directory, double kWheelbaseWidth)
    {
        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        final String path_name = "test/TestMoveRightFast";

        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 84;

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, 0, 0));
        p.addWaypoint(new Waypoint(48, 96, 0));

        generate(config, p, directory, path_name, kWheelbaseWidth);
    }

    private static void genTestMoveLeftFast(String directory, double kWheelbaseWidth)
    {
        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        final String path_name = "test/TestMoveLeftFast";

        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 84;

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, 0, 0));
        p.addWaypoint(new Waypoint(-48, 96, 0));

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

        genTestStraightSlow(directory, kWheelbaseWidth);
        genTestStraightFast(directory, kWheelbaseWidth);
        genTestMoveRightSlow(directory, kWheelbaseWidth);
        genTestMoveLeftSlow(directory, kWheelbaseWidth);
        genTestMoveRightFast(directory, kWheelbaseWidth);
        genTestMoveLeftFast(directory, kWheelbaseWidth);

        getRedRightToHopperOne(directory, kWheelbaseWidth);
        getRedRightToHopperTwo(directory, kWheelbaseWidth);
        getRedMiddleToHopperOne(directory, kWheelbaseWidth);
        getRedMiddleToHopperTwo(directory, kWheelbaseWidth);
        getRedMiddleToHopperFive(directory, kWheelbaseWidth);
        getRedLeftToHopperFive(directory, kWheelbaseWidth);
        getBlueLeftToHopperThree(directory, kWheelbaseWidth);
        getBlueLeftToHopperTwo(directory, kWheelbaseWidth);
        getBlueMiddleToHopperThree(directory, kWheelbaseWidth);
        getBlueMiddleToHopperTwo(directory, kWheelbaseWidth);
        getBlueMiddleToHopperFour(directory, kWheelbaseWidth);
        getBlueLeftToHopperFour(directory, kWheelbaseWidth);
        getBlueMiddleScoreGear(directory, kWheelbaseWidth);
        getBlueLeftScoreGear(directory, kWheelbaseWidth);
        getBlueRightScoreGear(directory, kWheelbaseWidth);
        getRedRightScoreGear(directory, kWheelbaseWidth);
        getRedMiddleScoreGear(directory, kWheelbaseWidth);
        getRedLeftScoreGear(directory, kWheelbaseWidth);
    }
}
