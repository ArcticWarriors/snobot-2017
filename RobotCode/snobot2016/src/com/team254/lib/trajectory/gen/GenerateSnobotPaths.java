package com.team254.lib.trajectory.gen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.team254.lib.trajectory.Path;
import com.team254.lib.trajectory.gen.WaypointSequence.Waypoint;

public class GenerateSnobotPaths
{
    private static final double sSPOT_1_X = -135;
    private static final double sSPOT_2_X = -82.875;
    private static final double sSPOT_3_X = -30;
    private static final double sSPOT_4_X = 22.875;
    private static final double sSPOT_5_X = 75.75;

    private static final double sSPOT_SPYBOT_X = -150.5;
    private static final double sSPOT_SPYBOT_Y = 306;
    
    /**
     * Staring Y position, when your back wheels are kissing the courtyard side
     * of a defense
     */
    private static final double sCROSSED_DEFENSE_Y = 140;

    /** The Y-Coordinate of the point to drive to where the turn should initiate */
    private static final double sTURNING_Y = 260;
    
    /** The X-Coordinate of the point to drive to where the turn should initiate */
    private static final double sTURNING_X = 75;

    // /** The Y-Coordinate of the low goal */
    // private static final double sGOAL_Y = 305;
    //
    // /** The X-Coordinate of the low goal */
    // private static final double sGOAL_X = 50;

    /** The Y-Coordinate of the low goal */
    private static final double sLEFT_GOAL_Y = 305;

    /** The X-Coordinate of the low goal */
    private static final double sLEFT_GOAL_X = -30;

    /** The Y-Coordinate of the low goal */
    private static final double sRIGHT_GOAL_Y = 305;

    /** The X-Coordinate of the low goal */
    private static final double sRIGHT_GOAL_X = 50;

    /** The angle to attack the low goal from */
    private static final double sGOAL_ANGLE = 60;

    /** The wheel base of the robot. Helps the generator plan turns */
    private static final double sWHEEL_BASE = 25.5 / 12;

    /** The TrajectoryConfig for driving to the low goal */
    private TrajectoryGenerator.Config mLowGoalConfig;

    public GenerateSnobotPaths()
    {
        mLowGoalConfig = new TrajectoryGenerator.Config();
        mLowGoalConfig.dt = .02;
        mLowGoalConfig.max_acc = 120;
        mLowGoalConfig.max_jerk = 480;
        mLowGoalConfig.max_vel = 45;
    }

    public void generatePath(String aDirectory)
    {
        File f = new File(aDirectory);
        System.out.println("Dumping trajectories to '" + f.getAbsolutePath() + "'");

        genLowBarToLowGoal(aDirectory);
        genPos1ToLowGoal(aDirectory);
        genPos2ToLowGoal(aDirectory);
        genPos3ToLowGoal(aDirectory);
        genPos4ToLowGoal(aDirectory);
        genPos5ToLowGoal(aDirectory);
        genSpybotToLowGoal(aDirectory);
        genTestPath(aDirectory);
    }

    public String joinPaths(String path1, String path2)
    {
        File file1 = new File(path1);
        File file2 = new File(file1, path2);
        return file2.getPath();
    }

    private boolean writeFile(String path, String data)
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

    private void generate(TrajectoryGenerator.Config aConfig, WaypointSequence aPoints, String aDirectory, String aOutFile, double aWheelbase)
    {
        // WaypointSequence waypoints,
        // TrajectoryGenerator.Config config, double wheelbase_width,
        // String name

        Path path = PathGenerator.makePath(aPoints, aConfig, aWheelbase, aOutFile);

        // Outputs to the directory supplied as the first argument.
        TextFileSerializer js = new TextFileSerializer();
        String serialized = js.serialize(path);
        // System.out.print(serialized);
        String fullpath = joinPaths(aDirectory, aOutFile + ".csv");
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

    private void genLowBarToLowGoal(String aDirectory)
    {
        final String path_name = "LowBarToLowGoal";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(sSPOT_1_X, 30, 0));
        p.addWaypoint(new Waypoint(sSPOT_1_X, 200, 0));
        p.addWaypoint(new Waypoint(sLEFT_GOAL_X, sLEFT_GOAL_Y, sGOAL_ANGLE));

        generate(mLowGoalConfig, p, aDirectory, path_name, sWHEEL_BASE);
    }

    private void genPos1ToLowGoal(String aDirectory)
    {
        final String dudePathName = "Position1ToLowGoal";

        WaypointSequence dudeP = new WaypointSequence(10000);
        dudeP.addWaypoint(new Waypoint(sSPOT_1_X, sCROSSED_DEFENSE_Y, 0));
        dudeP.addWaypoint(new Waypoint(sSPOT_1_X, 200, 0));
        dudeP.addWaypoint(new Waypoint(sLEFT_GOAL_X, sLEFT_GOAL_Y, sGOAL_ANGLE));

        generate(mLowGoalConfig, dudeP, aDirectory, dudePathName, sWHEEL_BASE);
    }

    private void genPos2ToLowGoal(String aDirectory)
    {
        final String dudePathName = "Position2ToLowGoal";

        WaypointSequence dudeP = new WaypointSequence(10000);
        dudeP.addWaypoint(new Waypoint(sSPOT_2_X, sCROSSED_DEFENSE_Y, 0));
        dudeP.addWaypoint(new Waypoint(-sTURNING_X, sTURNING_Y, 0));
        dudeP.addWaypoint(new Waypoint(sLEFT_GOAL_X, sLEFT_GOAL_Y, sGOAL_ANGLE));

        generate(mLowGoalConfig, dudeP, aDirectory, dudePathName, sWHEEL_BASE);
    }

    private void genPos3ToLowGoal(String aDirectory)
    {
        final String dudePathName = "Position3ToLowGoal";

        WaypointSequence dudeP = new WaypointSequence(10000);
        dudeP.addWaypoint(new Waypoint(sSPOT_3_X, sCROSSED_DEFENSE_Y, 0));
        dudeP.addWaypoint(new Waypoint(-sTURNING_X, sTURNING_Y, 0));
        dudeP.addWaypoint(new Waypoint(sLEFT_GOAL_X, sLEFT_GOAL_Y, sGOAL_ANGLE));

        generate(mLowGoalConfig, dudeP, aDirectory, dudePathName, sWHEEL_BASE);
    }

    private void genPos4ToLowGoal(String aDirectory)
    {
        final String dudePathName = "Position4ToLowGoal";

        WaypointSequence dudeP = new WaypointSequence(10000);
        dudeP.addWaypoint(new Waypoint(sSPOT_4_X, sCROSSED_DEFENSE_Y, 0));
        dudeP.addWaypoint(new Waypoint(sTURNING_X, sTURNING_Y, 0));
        dudeP.addWaypoint(new Waypoint(sRIGHT_GOAL_X, sRIGHT_GOAL_Y, -sGOAL_ANGLE));

        generate(mLowGoalConfig, dudeP, aDirectory, dudePathName, sWHEEL_BASE);
    }

    private void genPos5ToLowGoal(String aDirectory)
    {
        final String dudePathName = "Position5ToLowGoal";

        WaypointSequence dudeP = new WaypointSequence(10000);
        dudeP.addWaypoint(new Waypoint(sSPOT_5_X, sCROSSED_DEFENSE_Y, 0));
        dudeP.addWaypoint(new Waypoint(sTURNING_X, sTURNING_Y, 0));
        dudeP.addWaypoint(new Waypoint(sRIGHT_GOAL_X, sRIGHT_GOAL_Y, -sGOAL_ANGLE));

        generate(mLowGoalConfig, dudeP, aDirectory, dudePathName, sWHEEL_BASE);
    }

    private void genSpybotToLowGoal(String aDirectory)
    {
        final String dudePathName = "SpybotToLowGoal";

        WaypointSequence dudeP = new WaypointSequence(10000);
        dudeP.addWaypoint(new Waypoint(sSPOT_SPYBOT_X, sSPOT_SPYBOT_Y, 90));
        dudeP.addWaypoint(new Waypoint(sLEFT_GOAL_X, sLEFT_GOAL_Y, sGOAL_ANGLE));

        generate(mLowGoalConfig, dudeP, aDirectory, dudePathName, sWHEEL_BASE);
    }

    private void genTestPath(String aDirectory)
    {
        final String dudePathName = "TestPath";

        WaypointSequence dudeP = new WaypointSequence(10000);
        dudeP.addWaypoint(new Waypoint(0, 0, 0));
        dudeP.addWaypoint(new Waypoint(-60, 156, -45));

        generate(mLowGoalConfig, dudeP, aDirectory, dudePathName, sWHEEL_BASE);
    }

    public static void main(String[] args)
    {
        String directory = "../../RobotCode/snobot2016/resources/traj/";

        if (args.length >= 1)
        {
            directory = args[0];
        }

        GenerateSnobotPaths generator = new GenerateSnobotPaths();
        generator.generatePath(directory);
    }
}
