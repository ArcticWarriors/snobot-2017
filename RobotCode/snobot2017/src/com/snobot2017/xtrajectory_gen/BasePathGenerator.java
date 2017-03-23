package com.snobot2017.xtrajectory_gen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.team254.lib.trajectory.Path;
import com.team254.lib.trajectory.gen.PathGenerator;
import com.team254.lib.trajectory.gen.TrajectoryGenerator;
import com.team254.lib.trajectory.gen.WaypointSequence;
import com.team254.lib.trajectory.gen.WaypointSequence.Waypoint;
import com.team254.lib.trajectory.io.TextFileSerializer;

public class BasePathGenerator
{

    protected static final double ROBOT_LENGTH = 20;
    protected static final double ROBOT_WIDTH = 2 * 12;

    // Raw Staring locations
    protected static final double START_Y = 336 - ROBOT_LENGTH;
    protected static final double START_X_CENTER = 0;
    protected static final double START_X_BOILER = 43 + ROBOT_WIDTH;
    protected static final double START_X_LOADING = 79;

    // Gear Locations
    protected static final double NON_CENTER_GEAR_X_OFFSET = 48;
    
    protected static final double CENTER_GEAR_Y = START_Y - 8 * 12;
    protected static final double NON_CENTER_CENTER_GEAR_Y = 197.5;

    // Raw hopper locations
    protected static final double WEST_HOPPERS_X = -13.5 * 12;
    protected static final double EAST_HOPPERS_X = 13.5 * 12;

    protected static final double HOPPER_ONE_Y = -200;
    protected static final double HOPPER_TWO_Y = 0;
    protected static final double HOPPER_THREE_Y = 200;
    protected static final double HOPPER_FOUR_Y = 120;
    protected static final double HOPPER_FIVE_Y = -120;

    // Starting locations
    protected static final Waypoint BLUE_LEFT_START = new Waypoint(START_X_BOILER, START_Y, -180);
    protected static final Waypoint BLUE_CENTER_START = new Waypoint(0, START_Y, -180);
    protected static final Waypoint BLUE_RIGHT_START = new Waypoint(-START_X_LOADING, START_Y, -180);
    protected static final Waypoint RED_LEFT_START = new Waypoint(-START_X_LOADING, -START_Y, 0);
    protected static final Waypoint RED_CENTER_START = new Waypoint(0, -START_Y, 0);
    protected static final Waypoint RED_RIGHT_START = new Waypoint(START_X_BOILER, -START_Y, 0);

    // Gear Locations
    protected static final Waypoint BLUE_LEFT_GEAR = new Waypoint(NON_CENTER_GEAR_X_OFFSET, NON_CENTER_CENTER_GEAR_Y, -120);
    protected static final Waypoint BLUE_CENTER_GEAR = new Waypoint(0, CENTER_GEAR_Y, -180);
    protected static final Waypoint BLUE_RIGHT_GEAR = new Waypoint(-NON_CENTER_GEAR_X_OFFSET, NON_CENTER_CENTER_GEAR_Y, 120);
    protected static final Waypoint RED_LEFT_GEAR = new Waypoint(-NON_CENTER_GEAR_X_OFFSET, -NON_CENTER_CENTER_GEAR_Y, 60);
    protected static final Waypoint RED_CENTER_GEAR = new Waypoint(0, -CENTER_GEAR_Y, 0);
    protected static final Waypoint RED_RIGHT_GEAR = new Waypoint(NON_CENTER_GEAR_X_OFFSET, -NON_CENTER_CENTER_GEAR_Y, -60);

    // Hoper Locations
    protected static final Waypoint EAST_HOPPER_ONE = new Waypoint(EAST_HOPPERS_X, HOPPER_ONE_Y, 90);
    protected static final Waypoint EAST_HOPPER_TWO = new Waypoint(EAST_HOPPERS_X, HOPPER_TWO_Y, 90);
    protected static final Waypoint EAST_HOPPER_THREE = new Waypoint(EAST_HOPPERS_X, HOPPER_THREE_Y, 90);
    protected static final Waypoint WEST_HOPPER_FOUR = new Waypoint(WEST_HOPPERS_X, HOPPER_FOUR_Y, -90);
    protected static final Waypoint WEST_HOPPER_FIVE = new Waypoint(WEST_HOPPERS_X, HOPPER_FIVE_Y, -90);

    protected static final TrajectoryGenerator.Config GEAR_SPEED_CONFIG = new TrajectoryGenerator.Config();
    protected static final TrajectoryGenerator.Config GEAR_SPEED_BACKWARDS_CONFIG = new TrajectoryGenerator.Config();
    protected static final TrajectoryGenerator.Config FAST_HOPPER_SPEED_CONFIG = new TrajectoryGenerator.Config();

    // Boiler Locations
    protected static final Waypoint RED_BOILER = new Waypoint(163.13, -334.81, -45);
    protected static final Waypoint BLUE_BOILER = new Waypoint(163.13, 334.81, -135);

    static
    {
        GEAR_SPEED_CONFIG.dt = .02;
        GEAR_SPEED_CONFIG.max_acc = 80;
        GEAR_SPEED_CONFIG.max_jerk = 480;
        GEAR_SPEED_CONFIG.max_vel = 20;
        GEAR_SPEED_CONFIG.isBackwards = false;

        GEAR_SPEED_BACKWARDS_CONFIG.dt = .02;
        GEAR_SPEED_BACKWARDS_CONFIG.max_acc = 80;
        GEAR_SPEED_BACKWARDS_CONFIG.max_jerk = 480;
        GEAR_SPEED_BACKWARDS_CONFIG.max_vel = 36;
        GEAR_SPEED_BACKWARDS_CONFIG.isBackwards = true;

        FAST_HOPPER_SPEED_CONFIG.dt = .02;
        FAST_HOPPER_SPEED_CONFIG.max_acc = 80;
        FAST_HOPPER_SPEED_CONFIG.max_jerk = 480;
        FAST_HOPPER_SPEED_CONFIG.max_vel = 72;
        FAST_HOPPER_SPEED_CONFIG.isBackwards = false;

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
        if (config.isBackwards)
        {
            System.out.println("MAking backwards");
            path.makeBackwards();
        }

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

    protected Waypoint createHackPoint(Waypoint toCopy, double aDy, double aDTheta)
    {
        Waypoint hackPoint = new Waypoint(toCopy);
        hackPoint.x += aDy;
        hackPoint.theta += aDTheta;

        return hackPoint;
    }

    protected Waypoint modifyPoint(Waypoint toCopy, double aDx, double aDy, double aDTheta)
    {
        Waypoint hackPoint = new Waypoint(toCopy);
        hackPoint.x += aDy; // switched on purpose
        hackPoint.y += aDx;
        hackPoint.theta += Math.toRadians(aDTheta);

        return hackPoint;
    }

    protected Waypoint negateAngle(Waypoint toCopy)
    {
        Waypoint hackPoint = new Waypoint(toCopy);
        hackPoint.theta -= Math.toRadians(180);

        return hackPoint;
    }
}
