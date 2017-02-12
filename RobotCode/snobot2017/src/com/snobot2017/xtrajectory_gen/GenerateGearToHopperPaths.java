package com.snobot2017.xtrajectory_gen;

import com.team254.lib.trajectory.gen.WaypointSequence;
import com.team254.lib.trajectory.gen.WaypointSequence.Waypoint;

public class GenerateGearToHopperPaths extends BasePathGenerator
{
    private void getRedLeftScoreGearGetHopper(String directory, double kWheelbaseWidth)
    {
        final String path_name = "RedLeftScoreGearGetHopper";


        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(-34, -197.5, 45));
        p.addWaypoint(new Waypoint(-163.11, -119.16, 45));

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private void getRedRightScoreGearGetHopper(String directory, double kWheelbaseWidth)
    {
        final String path_name = "RedRightScoreGearGetHopper";


        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(34, -197.5, -45));
        p.addWaypoint(new Waypoint(163.11, -205.71, -90));

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private void getBlueRightScoreGearGetHopper(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueRightScoreGearGetHopper";


        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(-34, 197.5, -45));
        p.addWaypoint(new Waypoint(-163.11, 203.71, -90));

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private void getBlueLeftScoreGearGetHopper(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueLeftScoreGearGetHopper";


        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(34, 197.5, -45));
        p.addWaypoint(new Waypoint(163.11, 203.71, -90));

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
