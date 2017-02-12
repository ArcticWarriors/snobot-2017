package com.snobot2017.xtrajectory_gen;

import com.team254.lib.trajectory.gen.WaypointSequence;
import com.team254.lib.trajectory.gen.WaypointSequence.Waypoint;

public class GenerateStartToHopperBlue extends BasePathGenerator
{

    private void getBlueLeftToHopperThree(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueLeftToHopperThree";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(BOILER_LINE_OFFSET, FIELD_LENGTH_Y, 0));
        p.addWaypoint(new Waypoint(102.25, 270.82, -45));
        p.addWaypoint(new Waypoint(163.11, 203.79, -45));

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getBlueLeftToHopperTwo(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueLeftToHopperTwo";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(BOILER_LINE_OFFSET, FIELD_LENGTH_Y, 0));
        p.addWaypoint(new Waypoint(60.27, 270.82, -45));
        p.addWaypoint(new Waypoint(163.11, 0, -45));

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getBlueMiddleToHopperThree(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueMiddleToHopperThree";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, FIELD_LENGTH_Y, 0));
        p.addWaypoint(new Waypoint(60.27, 270.82, -45));
        p.addWaypoint(new Waypoint(163.11, 203.79, -45));

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getBlueMiddleToHopperTwo(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueMiddleToHopperTwo";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, FIELD_LENGTH_Y, 0));
        p.addWaypoint(new Waypoint(60.27, 270.82, -45));
        p.addWaypoint(new Waypoint(163.11, 0, -45));

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getBlueMiddleToHopperFour(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueMiddleToHopperFour";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, FIELD_LENGTH_Y, 0));
        p.addWaypoint(new Waypoint(-80.01, 240.82, 45));
        p.addWaypoint(new Waypoint(-163.11, 117.58, 45));

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getBlueLeftToHopperFour(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueLeftToHopperFour";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(-79.52, FIELD_LENGTH_Y, 0));
        p.addWaypoint(new Waypoint(-80.01, 240.82, 45));
        p.addWaypoint(new Waypoint(-163.11, 117.58, 45));

        generate(RED_SCORE_GEAR_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    public void generatePaths(String aDirectory, double aWheelbaseWidth)
    {
        getBlueLeftToHopperThree(aDirectory, aWheelbaseWidth);
        getBlueLeftToHopperTwo(aDirectory, aWheelbaseWidth);

        getBlueMiddleToHopperThree(aDirectory, aWheelbaseWidth);
        getBlueMiddleToHopperTwo(aDirectory, aWheelbaseWidth);
        getBlueMiddleToHopperFour(aDirectory, aWheelbaseWidth);

        getBlueLeftToHopperFour(aDirectory, aWheelbaseWidth);
    }

}
