package com.snobot2017.xtrajectory_gen;

import com.team254.lib.trajectory.gen.WaypointSequence;
import com.team254.lib.trajectory.gen.WaypointSequence.Waypoint;

public class GenerateStartToGearPaths extends BasePathGenerator
{
    private void getScoreGearBlueCenter(String aDirectory, double aWheelbaseWidth)
    {
        final String path_name = "BlueMiddleScoreGear";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, FIELD_LENGTH_Y, 180));
        p.addWaypoint(new Waypoint(0, CENTER_GEAR_Y, 180));

        generate(BLUE_SCORE_GEAR_CONFIG, p, aDirectory, path_name, aWheelbaseWidth);
    }
    
    private void getScoreGearBlueLeft(String aDirectory, double aWheelbaseWidth)
    {
        final String path_name = "BlueLeftScoreGear";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(BOILER_LINE_OFFSET, FIELD_LENGTH_Y, -180));
        p.addWaypoint(new Waypoint(NON_CENTER_GEAR_X_OFFSET, NON_CENTER_CENTER_GEAR_Y, -180));

        generate(BLUE_SCORE_GEAR_CONFIG, p, aDirectory, path_name, aWheelbaseWidth);
    }
    
    private void getScoreGearBlueRight(String aDirectory, double aWheelbaseWidth)
    {
        final String path_name = "BlueRightScoreGear";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(-79.52, FIELD_LENGTH_Y, 0));
        p.addWaypoint(new Waypoint(-34, 197.5, 0));

        generate(RED_SCORE_GEAR_CONFIG, p, aDirectory, path_name, aWheelbaseWidth);
    }

    private void getScoreGearRedRight(String aDirectory, double aWheelbaseWidth)
    {
        final String path_name = "RedRightScoreGear";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(BOILER_LINE_OFFSET, -FIELD_LENGTH_Y, 0));
        p.addWaypoint(new Waypoint(NON_CENTER_GEAR_X_OFFSET, -NON_CENTER_CENTER_GEAR_Y, -45));

        generate(RED_SCORE_GEAR_CONFIG, p, aDirectory, path_name, aWheelbaseWidth);
    }

    private void getScoreGearRedMiddle(String aDirectory, double aWheelbaseWidth)
    {
        final String path_name = "RedMiddleScoreGear";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(0, -FIELD_LENGTH_Y, 0));
        p.addWaypoint(new Waypoint(0, -CENTER_GEAR_Y, 0));

        generate(RED_SCORE_GEAR_CONFIG, p, aDirectory, path_name, aWheelbaseWidth);
    }

    private void getScoreGearRedLeft(String aDirectory, double aWheelbaseWidth)
    {
        final String path_name = "RedLeftScoreGear";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(new Waypoint(LOADING_X_OFFSET, -FIELD_LENGTH_Y, 0));
        p.addWaypoint(new Waypoint(-NON_CENTER_GEAR_X_OFFSET, -NON_CENTER_CENTER_GEAR_Y, 45));

        generate(RED_SCORE_GEAR_CONFIG, p, aDirectory, path_name, aWheelbaseWidth);
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
