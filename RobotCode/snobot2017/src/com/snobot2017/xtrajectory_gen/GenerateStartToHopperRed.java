package com.snobot2017.xtrajectory_gen;

import com.team254.lib.trajectory.gen.WaypointSequence;
import com.team254.lib.trajectory.gen.WaypointSequence.Waypoint;

public class GenerateStartToHopperRed extends BasePathGenerator
{

    private void getRedRightToHopperOne(String directory, double kWheelbaseWidth)
    {
        final String path_name = "RedRightToHopperOne";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(BOILER_LINE_OFFSET, -FIELD_LENGTH_Y, 0));
        p.addWaypoint(new Waypoint(RIGHT_HOPPERS_X, HOPPER_ONE_Y, 89));

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getRedRightToHopperTwo(String directory, double kWheelbaseWidth)
    {
        final String path_name = "RedRightToHopperTwo";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(BOILER_LINE_OFFSET, FIELD_LENGTH_Y, 0));
        p.addWaypoint(new Waypoint(105.3, -205.99, 0));
        p.addWaypoint(new Waypoint(160.23, -10, 45));
        p.addWaypoint(new Waypoint(RIGHT_HOPPERS_X, HOPPER_TWO_Y, 89));

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getRedMiddleToHopperOne(String directory, double kWheelbaseWidth)
    {
        final String path_name = "RedMiddleToHoppeOne";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, FIELD_LENGTH_Y, 0));
        p.addWaypoint(new Waypoint(102.23, -262.26, 45));
        p.addWaypoint(new Waypoint(RIGHT_HOPPERS_X, HOPPER_ONE_Y, 45));

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getRedMiddleToHopperTwo(String directory, double kWheelbaseWidth)
    {
        final String path_name = "RedMiddleToHopperTwo";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, FIELD_LENGTH_Y, 0));
        p.addWaypoint(new Waypoint(105.3, -205.99, 0));
        p.addWaypoint(new Waypoint(160.23, -10, 45));
        p.addWaypoint(new Waypoint(RIGHT_HOPPERS_X, HOPPER_TWO_Y, 45));

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getRedMiddleToHopperFive(String directory, double kWheelbaseWidth)
    {
        final String path_name = "RedMiddleToHopperFive";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, FIELD_LENGTH_Y, 0));
        p.addWaypoint(new Waypoint(-112.12, -185.87, 0));
        p.addWaypoint(new Waypoint(LEFT_HOPPERS_X, HOPPER_FIVE_Y, -45));

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getRedLeftToHopperFive(String directory, double kWheelbaseWidth)
    {
        final String path_name = "RedLeftToHopperFive";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(LOADING_X_OFFSET, -FIELD_LENGTH_Y, 0));
        // p.addWaypoint(new Waypoint(-100.12, -185.87, -45));
        p.addWaypoint(new Waypoint(LEFT_HOPPERS_X, HOPPER_FIVE_Y, -45));

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    public void generatePaths(String aDirectory, double aWheelbaseWidth)
    {
        getRedRightToHopperOne(aDirectory, aWheelbaseWidth);
        getRedRightToHopperTwo(aDirectory, aWheelbaseWidth);

        getRedMiddleToHopperOne(aDirectory, aWheelbaseWidth);
        getRedMiddleToHopperTwo(aDirectory, aWheelbaseWidth);
        getRedMiddleToHopperFive(aDirectory, aWheelbaseWidth);

        getRedLeftToHopperFive(aDirectory, aWheelbaseWidth);
    }

}
