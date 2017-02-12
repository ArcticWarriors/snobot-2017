package com.snobot2017.xtrajectory_gen;

import com.team254.lib.trajectory.gen.WaypointSequence;
import com.team254.lib.trajectory.gen.WaypointSequence.Waypoint;

public class GenerateStartToHopperRed extends BasePathGenerator
{

    private void getRedRightToHopperOne(String directory, double kWheelbaseWidth)
    {
        final String path_name = "RedRightToHopperOne";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(RED_RIGHT_START);
        p.addWaypoint(createPositiveHackPoint(RED_RIGHT_START));
        p.addWaypoint(EAST_HOPPER_ONE);

        generate(GEAR_SPEED_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getRedRightToHopperTwo(String directory, double kWheelbaseWidth)
    {
        final String path_name = "RedRightToHopperTwo";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(RED_RIGHT_START);
        p.addWaypoint(new Waypoint(100, -150, 1));
        p.addWaypoint(EAST_HOPPER_TWO);

        generate(GEAR_SPEED_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getRedMiddleToHopperOne(String directory, double kWheelbaseWidth)
    {
        final String path_name = "RedMiddleToHopperOne";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(RED_CENTER_START);
        p.addWaypoint(new Waypoint(100, -240, 1));
        p.addWaypoint(EAST_HOPPER_ONE);

        generate(GEAR_SPEED_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getRedMiddleToHopperFive(String directory, double kWheelbaseWidth)
    {
        final String path_name = "RedMiddleToHopperFive";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(RED_CENTER_START);
        p.addWaypoint(new Waypoint(-112.12, -185.87, -1));
        p.addWaypoint(WEST_HOPPER_FIVE);

        generate(GEAR_SPEED_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getRedLeftToHopperFour(String directory, double kWheelbaseWidth)
    {
        final String path_name = "RedLeftToHopperFour";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(RED_LEFT_START);
        p.addWaypoint(new Waypoint(-112, 90, -1));
        p.addWaypoint(WEST_HOPPER_FOUR);

        generate(GEAR_SPEED_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getRedLeftToHopperFive(String directory, double kWheelbaseWidth)
    {
        final String path_name = "RedLeftToHopperFive";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(RED_LEFT_START);
        p.addWaypoint(createNegativeHackPoint(RED_LEFT_START));
        p.addWaypoint(WEST_HOPPER_FIVE);

        generate(GEAR_SPEED_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    public void generatePaths(String aDirectory, double aWheelbaseWidth)
    {
        getRedRightToHopperOne(aDirectory, aWheelbaseWidth);
        getRedRightToHopperTwo(aDirectory, aWheelbaseWidth);

        getRedMiddleToHopperOne(aDirectory, aWheelbaseWidth);
         getRedMiddleToHopperFive(aDirectory, aWheelbaseWidth);

        getRedLeftToHopperFour(aDirectory, aWheelbaseWidth);
        getRedLeftToHopperFive(aDirectory, aWheelbaseWidth);
    }

}
