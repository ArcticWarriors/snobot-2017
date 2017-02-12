package com.snobot2017.xtrajectory_gen;

import com.team254.lib.trajectory.gen.TrajectoryGenerator;
import com.team254.lib.trajectory.gen.WaypointSequence;
import com.team254.lib.trajectory.gen.WaypointSequence.Waypoint;

public class GenerateGearToHopperPaths extends BasePathGenerator
{
    private void getRedLeftScoreGearGetHopper(String directory, double kWheelbaseWidth)
    {
        final String path_name = "RedLeftScoreGearGetHopper";

        TrajectoryGenerator.Config xxxx = new TrajectoryGenerator.Config();
        xxxx.dt = .02;
        xxxx.max_acc = 120;
        xxxx.max_jerk = 480;
        xxxx.max_vel = 50;
        // xxxx.angle_offset = -0;

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(-NON_CENTER_GEAR_X_OFFSET, -NON_CENTER_CENTER_GEAR_Y, 45));
        p.addWaypoint(new Waypoint(-100, -NON_CENTER_CENTER_GEAR_Y, 90));
        // p.addWaypoint(GEAR_TO_WEST_HOPPER_FIVE);

        generate(xxxx, p, directory, path_name, kWheelbaseWidth);
    }
    
    private void getRedRightScoreGearGetHopper(String directory, double kWheelbaseWidth)
    {
        final String path_name = "RedRightScoreGearGetHopper";


        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(RED_RIGHT_GEAR);
        p.addWaypoint(EAST_HOPPER_ONE);

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private void getBlueRightScoreGearGetHopper(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueRightScoreGearGetHopper";


        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(BLUE_RIGHT_GEAR);
        p.addWaypoint(WEST_HOPPER_FOUR);

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private void getBlueLeftScoreGearGetHopper(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueLeftScoreGearGetHopper";


        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(BLUE_LEFT_GEAR);
        p.addWaypoint(EAST_HOPPER_THREE);

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }


    public void generatePaths(String aDirectory, double aWheelbaseWidth)
    {
        getRedLeftScoreGearGetHopper(aDirectory, aWheelbaseWidth);
        getRedRightScoreGearGetHopper(aDirectory, aWheelbaseWidth);
        getBlueRightScoreGearGetHopper(aDirectory, aWheelbaseWidth);
        getBlueLeftScoreGearGetHopper(aDirectory, aWheelbaseWidth);
    }

}
