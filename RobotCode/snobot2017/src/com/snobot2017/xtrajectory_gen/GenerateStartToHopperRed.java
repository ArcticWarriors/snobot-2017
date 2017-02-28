package com.snobot2017.xtrajectory_gen;

import com.team254.lib.trajectory.gen.WaypointSequence;
import com.team254.lib.trajectory.gen.WaypointSequence.Waypoint;

public class GenerateStartToHopperRed extends BasePathGenerator
{

    private void getRedRightToHopperOne(String directory, double kWheelbaseWidth)
    {
        final String path_name = "StartToHopper/RedRightToHopperOne";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(RED_RIGHT_START);
        p.addWaypoint(createHackPoint(RED_RIGHT_START, 10, .0001));
        p.addWaypoint(EAST_HOPPER_ONE);

        generate(FAST_HOPPER_SPEED_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getRedRightToHopperTwo(String directory, double kWheelbaseWidth)
    {
        final String path_name = "StartToHopper/RedRightToHopperTwo";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(RED_RIGHT_START);
        p.addWaypoint(new Waypoint(100, -150, 1));
        p.addWaypoint(EAST_HOPPER_TWO);

        generate(FAST_HOPPER_SPEED_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getRedMiddleToHopperOne(String directory, double kWheelbaseWidth)
    {
        final String path_name = "StartToHopper/RedMiddleToHopperOne";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(RED_CENTER_START);
        p.addWaypoint(new Waypoint(100, -240, 1));
        p.addWaypoint(EAST_HOPPER_ONE);

        generate(FAST_HOPPER_SPEED_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getRedMiddleToHopperFive(String directory, double kWheelbaseWidth)
    {
        final String path_name = "StartToHopper/RedMiddleToHopperFive";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(RED_CENTER_START);
        p.addWaypoint(new Waypoint(-112.12, -185.87, -1));
        p.addWaypoint(WEST_HOPPER_FIVE);

        generate(FAST_HOPPER_SPEED_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getRedLeftToHopperFour(String directory, double kWheelbaseWidth)
    {
        final String path_name = "StartToHopper/RedLeftToHopperFour";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(RED_LEFT_START);
        p.addWaypoint(new Waypoint(-112, 90, -1));
        p.addWaypoint(WEST_HOPPER_FOUR);

        generate(FAST_HOPPER_SPEED_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getRedLeftToHopperFive(String directory, double kWheelbaseWidth)
    {
        final String path_name = "StartToHopper/RedLeftToHopperFive";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(RED_LEFT_START);
        p.addWaypoint(new Waypoint(-112.12, -185.87, -1));
        p.addWaypoint(WEST_HOPPER_FIVE);

        generate(FAST_HOPPER_SPEED_CONFIG, p, directory, path_name, kWheelbaseWidth);
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
