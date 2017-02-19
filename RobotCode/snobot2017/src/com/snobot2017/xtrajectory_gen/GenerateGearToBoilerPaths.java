package com.snobot2017.xtrajectory_gen;

import com.team254.lib.trajectory.gen.WaypointSequence;
import com.team254.lib.trajectory.gen.WaypointSequence.Waypoint;

public class GenerateGearToBoilerPaths extends BasePathGenerator
{
    private void getRedRightGearToBoiler(String directory, double kWheelbaseWidth)
    {
        final String path_name = "RedRightGearToBoiler";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(new Waypoint(RED_RIGHT_GEAR));
        p.addWaypoint(new Waypoint(150, -300, -45));
        p.addWaypoint(new Waypoint(RED_BOILER));

        generate(GEAR_SPEED_BACKWARDS_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getBlueLeftGearToBoiler(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueLeftGearToBoiler";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(new Waypoint(BLUE_LEFT_GEAR));
        p.addWaypoint(new Waypoint(BLUE_BOILER));

        generate(GEAR_SPEED_BACKWARDS_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    public void generatePaths(String aDirectory, double aWheelbaseWidth)
    {
        getRedRightGearToBoiler(aDirectory, aWheelbaseWidth);
        getBlueLeftGearToBoiler(aDirectory, aWheelbaseWidth);
    }

}