package com.snobot2017.xtrajectory_gen;

import com.team254.lib.trajectory.gen.WaypointSequence;
import com.team254.lib.trajectory.gen.WaypointSequence.Waypoint;

public class GenerateStartToHopperBlue extends BasePathGenerator
{

    private void getBlueLeftToHopperThree(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueLeftToHopperThree";

        Waypoint hackPoint = new Waypoint(BLUE_LEFT_START);
        hackPoint.x -= .01;
        hackPoint.y -= .01;
        hackPoint.theta -= .0001;

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(BLUE_LEFT_START);
        p.addWaypoint(hackPoint);
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
        p.addWaypoint(new Waypoint(60.27, 270.82, -45));
        p.addWaypoint(new Waypoint(163.11, 203.79, -45));

        generate(GEAR_SPEED_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getBlueMiddleToHopperTwo(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueMiddleToHopperTwo";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(BLUE_CENTER_START);
        p.addWaypoint(new Waypoint(60.27, 270.82, -45));
        p.addWaypoint(new Waypoint(163.11, 0, -45));

        generate(GEAR_SPEED_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getBlueMiddleToHopperFour(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueMiddleToHopperFour";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(BLUE_CENTER_START);
        p.addWaypoint(new Waypoint(-80.01, 240.82, 45));
        p.addWaypoint(new Waypoint(-163.11, 117.58, 45));

        generate(GEAR_SPEED_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getBlueRightToHopperFour(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueRightToHopperFour";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(BLUE_RIGHT_START);
        p.addWaypoint(new Waypoint(-112.12, 185.87, -180));
        p.addWaypoint(new Waypoint(WEST_HOPPERS_X, HOPPER_FOUR_Y, 91));

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
        // getBlueLeftToHopperThree(aDirectory, aWheelbaseWidth);
        // getBlueLeftToHopperTwo(aDirectory, aWheelbaseWidth);
        //
        // getBlueMiddleToHopperThree(aDirectory, aWheelbaseWidth);
        // getBlueMiddleToHopperTwo(aDirectory, aWheelbaseWidth);
        // getBlueMiddleToHopperFour(aDirectory, aWheelbaseWidth);
        //
        // getBlueRightToHopperFour(aDirectory, aWheelbaseWidth);
        // getBlueRightToHopperFive(aDirectory, aWheelbaseWidth);
    }

}
