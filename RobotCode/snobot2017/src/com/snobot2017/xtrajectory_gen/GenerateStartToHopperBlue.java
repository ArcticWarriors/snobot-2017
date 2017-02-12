package com.snobot2017.xtrajectory_gen;

import com.team254.lib.trajectory.gen.WaypointSequence;
import com.team254.lib.trajectory.gen.WaypointSequence.Waypoint;

public class GenerateStartToHopperBlue extends BasePathGenerator
{

    private void getBlueLeftToHopperThree(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueLeftToHopperThree";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(BLUE_LEFT_START);
        p.addWaypoint(createNegativeHackPoint(BLUE_LEFT_START));
        p.addWaypoint(EAST_HOPPER_THREE);

        generate(GEAR_SPEED_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getBlueLeftToHopperTwo(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueLeftToHopperTwo";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(BLUE_LEFT_START);
        p.addWaypoint(new Waypoint(100, 150, 179));
        p.addWaypoint(EAST_HOPPER_TWO);

        generate(GEAR_SPEED_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getBlueMiddleToHopperThree(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueMiddleToHopperThree";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(BLUE_CENTER_START);
        p.addWaypoint(new Waypoint(100, 240, -181));
        p.addWaypoint(EAST_HOPPER_THREE);

        generate(GEAR_SPEED_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getBlueMiddleToHopperFour(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueMiddleToHopperFour";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(BLUE_CENTER_START);
        p.addWaypoint(new Waypoint(-80.01, 240.82, -135));
        p.addWaypoint(WEST_HOPPER_FOUR);

        generate(GEAR_SPEED_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getBlueRightToHopperFour(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueRightToHopperFour";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(BLUE_RIGHT_START);
        p.addWaypoint(new Waypoint(-112.12, 185.87, -179));
        p.addWaypoint(WEST_HOPPER_FOUR);

        generate(GEAR_SPEED_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getBlueRightToHopperFive(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueRightToHopperFive";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(BLUE_RIGHT_START);
        p.addWaypoint(new Waypoint(-112, -90, -179));
        p.addWaypoint(WEST_HOPPER_FIVE);

        generate(GEAR_SPEED_CONFIG, p, directory, path_name, kWheelbaseWidth);
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
