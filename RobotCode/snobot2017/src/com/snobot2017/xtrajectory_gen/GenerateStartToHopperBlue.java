package com.snobot2017.xtrajectory_gen;

import com.team254.lib.trajectory.gen.WaypointSequence;
import com.team254.lib.trajectory.gen.WaypointSequence.Waypoint;

public class GenerateStartToHopperBlue extends BasePathGenerator
{

    private void getBlueLeftToHopperThree(String directory, double kWheelbaseWidth)
    {
        final String path_name = "StartToHopper/BlueLeftToHopperThree";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(BLUE_LEFT_START);
        p.addWaypoint(createHackPoint(BLUE_LEFT_START, -10, -.0001));
        p.addWaypoint(EAST_HOPPER_THREE);

        generate(FAST_HOPPER_SPEED_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getBlueLeftToHopperTwo(String directory, double kWheelbaseWidth)
    {
        final String path_name = "StartToHopper/BlueLeftToHopperTwo";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(BLUE_LEFT_START);
        p.addWaypoint(new Waypoint(100, 150, 179));
        p.addWaypoint(EAST_HOPPER_TWO);

        generate(FAST_HOPPER_SPEED_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getBlueMiddleToHopperThree(String directory, double kWheelbaseWidth)
    {
        final String path_name = "StartToHopper/BlueMiddleToHopperThree";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(BLUE_CENTER_START);
        p.addWaypoint(new Waypoint(100, 240, -181));
        p.addWaypoint(EAST_HOPPER_THREE);

        generate(FAST_HOPPER_SPEED_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getBlueMiddleToHopperFour(String directory, double kWheelbaseWidth)
    {
        final String path_name = "StartToHopper/BlueMiddleToHopperFour";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(BLUE_CENTER_START);
        p.addWaypoint(new Waypoint(-80.01, 240.82, -135));
        p.addWaypoint(WEST_HOPPER_FOUR);

        generate(FAST_HOPPER_SPEED_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getBlueRightToHopperFour(String directory, double kWheelbaseWidth)
    {
        final String path_name = "StartToHopper/BlueRightToHopperFour";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(BLUE_RIGHT_START);
        p.addWaypoint(new Waypoint(-112.12, 185.87, -179));
        p.addWaypoint(WEST_HOPPER_FOUR);

        generate(FAST_HOPPER_SPEED_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getBlueRightToHopperFive(String directory, double kWheelbaseWidth)
    {
        final String path_name = "StartToHopper/BlueRightToHopperFive";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(BLUE_RIGHT_START);
        p.addWaypoint(new Waypoint(-112, -90, -179));
        p.addWaypoint(WEST_HOPPER_FIVE);

        generate(FAST_HOPPER_SPEED_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    public void generatePaths(String aDirectory, double aWheelbaseWidth)
    {
        getBlueLeftToHopperThree(aDirectory, aWheelbaseWidth);
        getBlueLeftToHopperTwo(aDirectory, aWheelbaseWidth);

        getBlueMiddleToHopperThree(aDirectory, aWheelbaseWidth);
        getBlueMiddleToHopperFour(aDirectory, aWheelbaseWidth);

        getBlueRightToHopperFour(aDirectory, aWheelbaseWidth);
        getBlueRightToHopperFive(aDirectory, aWheelbaseWidth);
    }

}
