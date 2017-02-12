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
	private static final double ROBOT_LENGTH = 3 * 12;
	
    private static final double CENTER_X = 0;
    private static final double FIELD_LENGTH_Y = 336;
    private static final double BOILER_LINE_OFFSET = 43.02;
    private static final double LOADING_X_OFFSET = -79.53;
    private static final double NON_CENTER_GEAR_X_OFFSET = 48;
    private static final double CENTER_GEAR_Y = FIELD_LENGTH_Y - 7 * 12 + ROBOT_LENGTH;
    private static final double NON_CENTER_CENTER_GEAR_Y = 197.5 + ROBOT_LENGTH;

    private static final double HOPPER_ONE_Y = -200.7 + ROBOT_LENGTH;
    private static final double HOPPER_TWO_Y = -163.11 + ROBOT_LENGTH;
    private static final double HOPPER_THREE_Y = 163.11 + ROBOT_LENGTH;
    private static final double HOPPER_FOUR_Y = -200.7 + ROBOT_LENGTH;
    private static final double HOPPER_FIVE_Y = -200.7 + ROBOT_LENGTH;

    private static final double LEFT_HOPPERS_X = -13.5 * 12;
    private static final double RIGHT_HOPPERS_X = 13.5 * 12;

    private static final TrajectoryGenerator.Config RED_SCORE_GEAR_CONFIG = new TrajectoryGenerator.Config();
    private static final TrajectoryGenerator.Config BLUE_SCORE_GEAR_CONFIG = new TrajectoryGenerator.Config();
    
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
        final String path_name = "RedRightToHopperOne";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(BOILER_LINE_OFFSET, -FIELD_LENGTH_Y, 0));
        p.addWaypoint(new Waypoint(RIGHT_HOPPERS_X, HOPPER_ONE_Y, 89));

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getRedRightToHopperTwo(String directory, double kWheelbaseWidth)
    {
        final String path_name = "RedRightToHopperTwo";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(BOILER_LINE_OFFSET, FIELD_LENGTH_Y, 0));
        p.addWaypoint(new Waypoint(105.3, -205.99, 0));
        p.addWaypoint(new Waypoint(160.23, -10, 45)); 
        p.addWaypoint(new Waypoint(RIGHT_HOPPERS_X, HOPPER_TWO_Y, 89));

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getRedMiddleToHopperOne(String directory, double kWheelbaseWidth)
    {
        final String path_name = "RedMiddleToHoppeOne";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, FIELD_LENGTH_Y, 0));
        p.addWaypoint(new Waypoint(102.23, -262.26, 45));
        p.addWaypoint(new Waypoint(RIGHT_HOPPERS_X, HOPPER_ONE_Y, 45));
        
        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getRedMiddleToHopperTwo(String directory, double kWheelbaseWidth)
    {
        final String path_name = "RedMiddleToHopperTwo";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, FIELD_LENGTH_Y, 0));
        p.addWaypoint(new Waypoint(105.3, -205.99, 0));
        p.addWaypoint(new Waypoint(160.23, -10, 45)); 
        p.addWaypoint(new Waypoint(RIGHT_HOPPERS_X, HOPPER_TWO_Y, 45));

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getRedMiddleToHopperFive(String directory, double kWheelbaseWidth)
    {
        final String path_name = "RedMiddleToHopperFive";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, FIELD_LENGTH_Y, 0));
        p.addWaypoint(new Waypoint(-112.12, -185.87, 0));
        p.addWaypoint(new Waypoint(LEFT_HOPPERS_X, HOPPER_FIVE_Y, -45)); 
        

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getRedLeftToHopperFive(String directory, double kWheelbaseWidth)
    {
        final String path_name = "RedLeftToHopperFive";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(LOADING_X_OFFSET, -FIELD_LENGTH_Y, 0));
//        p.addWaypoint(new Waypoint(-100.12, -185.87, -45));
        p.addWaypoint(new Waypoint(LEFT_HOPPERS_X, HOPPER_FIVE_Y, -45)); 
        

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getBlueLeftToHopperThree(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueLeftToHopperThree";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(BOILER_LINE_OFFSET, FIELD_LENGTH_Y, 0));
        p.addWaypoint(new Waypoint(102.25, 270.82, -45));
        p.addWaypoint(new Waypoint(163.11, 203.79, -45)); 

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getBlueLeftToHopperTwo(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueLeftToHopperTwo";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(BOILER_LINE_OFFSET, FIELD_LENGTH_Y, 0));
        p.addWaypoint(new Waypoint(60.27, 270.82, -45));
        p.addWaypoint(new Waypoint(163.11, 0, -45)); 

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getBlueMiddleToHopperThree(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueMiddleToHopperThree";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, FIELD_LENGTH_Y, 0));
        p.addWaypoint(new Waypoint(60.27, 270.82, -45));
        p.addWaypoint(new Waypoint(163.11, 203.79, -45)); 

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getBlueMiddleToHopperTwo(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueMiddleToHopperTwo";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, FIELD_LENGTH_Y, 0));
        p.addWaypoint(new Waypoint(60.27, 270.82, -45));
        p.addWaypoint(new Waypoint(163.11, 0, -45)); 

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getBlueMiddleToHopperFour(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueMiddleToHopperFour";


        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, FIELD_LENGTH_Y, 0));
        p.addWaypoint(new Waypoint(-80.01, 240.82, 45));
        p.addWaypoint(new Waypoint(-163.11, 117.58, 45)); 

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getBlueLeftToHopperFour(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueLeftToHopperFour";


        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(-79.52, FIELD_LENGTH_Y, 0));
        p.addWaypoint(new Waypoint(-80.01, 240.82, 45));
        p.addWaypoint(new Waypoint(-163.11, 117.58, 45)); 

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getBlueMiddleScoreGear(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueMiddleScoreGear";


        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, FIELD_LENGTH_Y, 180));
        p.addWaypoint(new Waypoint(0, CENTER_GEAR_Y, 180));

        generate(BLUE_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getBlueLeftScoreGear(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueLeftScoreGear";


        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(BOILER_LINE_OFFSET, FIELD_LENGTH_Y, -180));
        p.addWaypoint(new Waypoint(NON_CENTER_GEAR_X_OFFSET, NON_CENTER_CENTER_GEAR_Y, -180));

        generate(BLUE_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getBlueRightScoreGear(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueRightScoreGear";


        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(-79.52, FIELD_LENGTH_Y, 0));
        p.addWaypoint(new Waypoint(-34, 197.5, 0));

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getRedRightScoreGear(String directory, double kWheelbaseWidth)
    {
        final String path_name = "RedRightScoreGear";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(BOILER_LINE_OFFSET, -FIELD_LENGTH_Y, 0));
        p.addWaypoint(new Waypoint(NON_CENTER_GEAR_X_OFFSET, -NON_CENTER_CENTER_GEAR_Y, -45));

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getRedMiddleScoreGear(String directory, double kWheelbaseWidth)
    {
        final String path_name = "RedMiddleScoreGear";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, -FIELD_LENGTH_Y, 0));
        p.addWaypoint(new Waypoint(0, -CENTER_GEAR_Y, 0));

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getRedLeftScoreGear(String directory, double kWheelbaseWidth)
    {
        final String path_name = "RedLeftScoreGear";


        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(LOADING_X_OFFSET, -FIELD_LENGTH_Y, 0));
        p.addWaypoint(new Waypoint(-NON_CENTER_GEAR_X_OFFSET, -NON_CENTER_CENTER_GEAR_Y, 45));

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getRedLeftScoreGearGetHopper(String directory, double kWheelbaseWidth)
    {
        final String path_name = "RedLeftScoreGearGetHopper";


        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(-34, -197.5, 45));
        p.addWaypoint(new Waypoint(-163.11, -119.16, 45));

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getRedRightScoreGearGetHopper(String directory, double kWheelbaseWidth)
    {
        final String path_name = "RedRightScoreGearGetHopper";


        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(34, -197.5, -45));
        p.addWaypoint(new Waypoint(163.11, -205.71, -90));

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getBlueRightScoreGearGetHopper(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueRightScoreGearGetHopper";


        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(-34, 197.5, -45));
        p.addWaypoint(new Waypoint(-163.11, 117.58, -90));

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getBlueLeftScoreGearGetHopper(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueLeftScoreGearGetHopper";


        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(34, 197.5, -45));
        p.addWaypoint(new Waypoint(163.11, 203.71, -90));

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getBlueMiddleScoreGearGetHopper(String directory, double kWheelbaseWidth)
    {
        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        final String path_name = "BlueMiddleScoreGearGetHopper";

        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 50;

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, 217, -45));
        p.addWaypoint(new Waypoint(-163.11, 117.58, -90));

        generate(config, p, directory, path_name, kWheelbaseWidth);
    }
    
    private static void getRedMiddleScoreGearGetHopper(String directory, double kWheelbaseWidth)
    {
        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        final String path_name = "RedMiddleScoreGearGetHopper";

        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 50;

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, -217, 45));
        p.addWaypoint(new Waypoint(-5, -240, 45));
        p.addWaypoint(new Waypoint(-162.11, -205.58, 90));

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    //////////////////////////////////////////////////////////////
    // Test Trajectories
    //////////////////////////////////////////////////////////////
    private static void genTestStraightSlow(String directory, double kWheelbaseWidth)
    {
        final String path_name = "test/TestStraightSlow";

        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
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
        final String path_name = "test/TestStraightFast";

        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
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
        final String path_name = "test/TestMoveRightSlow";

        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 36;

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, 0, 0));
        p.addWaypoint(new Waypoint(NON_CENTER_GEAR_X_OFFSET, 96, 0));

        generate(config, p, directory, path_name, kWheelbaseWidth);
    }

    private static void genTestMoveLeftSlow(String directory, double kWheelbaseWidth)
    {
        final String path_name = "test/TestMoveLeftSlow";

        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 36;

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, 0, 0));
        p.addWaypoint(new Waypoint(-NON_CENTER_GEAR_X_OFFSET, 96, 0));

        generate(config, p, directory, path_name, kWheelbaseWidth);
    }

    private static void genTestMoveRightFast(String directory, double kWheelbaseWidth)
    {
        final String path_name = "test/TestMoveRightFast";

        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 84;

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, 0, 0));
        p.addWaypoint(new Waypoint(NON_CENTER_GEAR_X_OFFSET, 96, 0));

        generate(config, p, directory, path_name, kWheelbaseWidth);
    }

//    private static void genTestMoveLeftFast(String directory, double kWheelbaseWidth)
//    {
//        final String path_name = "test/TestMoveLeftFast";
//
//        WaypointSequence p = new WaypointSequence(10000);
//        p.addWaypoint(new Waypoint(0, 0, 0));
//        p.addWaypoint(new Waypoint(0, 96, 0));
//
//        generate(config, p, directory, path_name, kWheelbaseWidth);
//    }

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

//        genTestStraightSlow(directory, kWheelbaseWidth);
//        genTestStraightFast(directory, kWheelbaseWidth);
//        genTestMoveRightSlow(directory, kWheelbaseWidth);
//        genTestMoveLeftSlow(directory, kWheelbaseWidth);
//        genTestMoveRightFast(directory, kWheelbaseWidth);
//        genTestMoveLeftFast(directory, kWheelbaseWidth);
//
//        getRedRightToHopperOne(directory, kWheelbaseWidth);
//        getRedRightToHopperTwo(directory, kWheelbaseWidth);
//        getRedMiddleToHopperOne(directory, kWheelbaseWidth);
//        getRedMiddleToHopperTwo(directory, kWheelbaseWidth);
//        getRedMiddleToHopperFive(directory, kWheelbaseWidth);
        getRedLeftToHopperFive(directory, kWheelbaseWidth);
//        getBlueLeftToHopperThree(directory, kWheelbaseWidth);
//        getBlueLeftToHopperTwo(directory, kWheelbaseWidth);
//        getBlueMiddleToHopperThree(directory, kWheelbaseWidth);
//        getBlueMiddleToHopperTwo(directory, kWheelbaseWidth);
//        getBlueMiddleToHopperFour(directory, kWheelbaseWidth);
//        getBlueLeftToHopperFour(directory, kWheelbaseWidth);
//        getBlueMiddleScoreGear(directory, kWheelbaseWidth);
//        getBlueLeftScoreGear(directory, kWheelbaseWidth);
//        getBlueRightScoreGear(directory, kWheelbaseWidth);
//        getRedRightScoreGear(directory, kWheelbaseWidth);
//        getRedMiddleScoreGear(directory, kWheelbaseWidth);
//        getRedLeftScoreGear(directory, kWheelbaseWidth);
//        getRedLeftScoreGearGetHopper( directory, kWheelbaseWidth);
//        getRedRightScoreGearGetHopper(directory, kWheelbaseWidth);
//        getBlueRightScoreGearGetHopper( directory, kWheelbaseWidth);
//        getBlueLeftScoreGearGetHopper(directory, kWheelbaseWidth);
        getRedLeftScoreGearGetHopper( directory, kWheelbaseWidth);
        getRedRightScoreGearGetHopper(directory, kWheelbaseWidth);
        getBlueRightScoreGearGetHopper( directory, kWheelbaseWidth);
        getBlueLeftScoreGearGetHopper(directory, kWheelbaseWidth);
        getBlueMiddleScoreGearGetHopper(directory, kWheelbaseWidth);
        getRedMiddleScoreGearGetHopper(directory, kWheelbaseWidth);
    }
}
