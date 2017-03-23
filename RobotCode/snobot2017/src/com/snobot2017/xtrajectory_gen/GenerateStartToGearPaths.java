package com.snobot2017.xtrajectory_gen;

import com.team254.lib.trajectory.gen.WaypointSequence;
import com.team254.lib.trajectory.gen.WaypointSequence.Waypoint;

public class GenerateStartToGearPaths extends BasePathGenerator
{

    private void getScoreGearBlueLeft(String aDirectory, double aWheelbaseWidth)
    {
        final String path_name = "StartToGear/BlueLeftScoreGear";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(BLUE_LEFT_START);
        p.addWaypoint(new Waypoint(100, 270,-200));
        p.addWaypoint(BLUE_LEFT_GEAR);

        generate(GEAR_SPEED_CONFIG, p, aDirectory, path_name, aWheelbaseWidth);
    }
    
    private void getScoreGearBlueCenter(String aDirectory, double aWheelbaseWidth)
    {
        final String path_name = "StartToGear/BlueMiddleScoreGear";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(BLUE_CENTER_START);
        p.addWaypoint(BLUE_CENTER_GEAR);

        generate(GEAR_SPEED_CONFIG, p, aDirectory, path_name, aWheelbaseWidth);
    }
    
    private void getScoreGearBlueRight(String aDirectory, double aWheelbaseWidth)
    {
        final String path_name = "StartToGear/BlueRightScoreGear";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(BLUE_RIGHT_START);
        p.addWaypoint(new Waypoint(-100, 270,200));
        p.addWaypoint(BLUE_RIGHT_GEAR);

        generate(GEAR_SPEED_CONFIG, p, aDirectory, path_name, aWheelbaseWidth);
    }

    private void getScoreGearRedLeft(String aDirectory, double aWheelbaseWidth)
    {
        final String path_name = "StartToGear/RedLeftScoreGear";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(RED_LEFT_START);
        p.addWaypoint(new Waypoint(-100, -270,-20));
        p.addWaypoint(RED_LEFT_GEAR);

        generate(GEAR_SPEED_CONFIG, p, aDirectory, path_name, aWheelbaseWidth);
    }

    private void getScoreGearRedMiddle(String aDirectory, double aWheelbaseWidth)
    {
        final String path_name = "StartToGear/RedMiddleScoreGear";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(RED_CENTER_START);
        p.addWaypoint(RED_CENTER_GEAR);

        generate(GEAR_SPEED_CONFIG, p, aDirectory, path_name, aWheelbaseWidth);
    }

    private void getScoreGearRedRight(String aDirectory, double aWheelbaseWidth)
    {
        final String path_name = "StartToGear/RedRightScoreGear";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(RED_RIGHT_START);
        p.addWaypoint(new Waypoint(100, -270,20));
        p.addWaypoint(RED_RIGHT_GEAR);

        generate(GEAR_SPEED_CONFIG, p, aDirectory, path_name, aWheelbaseWidth);
    }

    public void generatePaths(String aDirectory, double aWheelbaseWidth)
    {
        getScoreGearBlueCenter(aDirectory, aWheelbaseWidth);
        getScoreGearBlueLeft(aDirectory, aWheelbaseWidth);
        getScoreGearBlueRight(aDirectory, aWheelbaseWidth);
        getScoreGearRedRight(aDirectory, aWheelbaseWidth);
        getScoreGearRedMiddle(aDirectory, aWheelbaseWidth);
        getScoreGearRedLeft(aDirectory, aWheelbaseWidth);
    }
}
