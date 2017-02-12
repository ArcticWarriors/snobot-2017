package com.snobot2017.xtrajectory_gen;

import com.team254.lib.trajectory.gen.WaypointSequence;

public class GenerateStartToGearPaths extends BasePathGenerator
{

    private void getScoreGearBlueLeft(String aDirectory, double aWheelbaseWidth)
    {
        final String path_name = "BlueLeftScoreGear";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(BLUE_LEFT_START);
        p.addWaypoint(BLUE_LEFT_GEAR);

        generate(BLUE_SCORE_GEAR_CONFIG, p, aDirectory, path_name, aWheelbaseWidth);
    }
    
    private void getScoreGearBlueCenter(String aDirectory, double aWheelbaseWidth)
    {
        final String path_name = "BlueMiddleScoreGear";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(BLUE_CENTER_START);
        p.addWaypoint(BLUE_CENTER_GEAR);

        generate(BLUE_SCORE_GEAR_CONFIG, p, aDirectory, path_name, aWheelbaseWidth);
    }
    
    private void getScoreGearBlueRight(String aDirectory, double aWheelbaseWidth)
    {
        final String path_name = "BlueRightScoreGear";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(BLUE_RIGHT_START);
        p.addWaypoint(BLUE_RIGHT_GEAR);

        generate(BLUE_SCORE_GEAR_CONFIG, p, aDirectory, path_name, aWheelbaseWidth);
    }

    private void getScoreGearRedLeft(String aDirectory, double aWheelbaseWidth)
    {
        final String path_name = "RedLeftScoreGear";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(RED_LEFT_START);
        p.addWaypoint(RED_LEFT_GEAR);

        generate(RED_SCORE_GEAR_CONFIG, p, aDirectory, path_name, aWheelbaseWidth);
    }

    private void getScoreGearRedMiddle(String aDirectory, double aWheelbaseWidth)
    {
        final String path_name = "RedMiddleScoreGear";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(RED_CENTER_START);
        p.addWaypoint(RED_CENTER_GEAR);

        generate(RED_SCORE_GEAR_CONFIG, p, aDirectory, path_name, aWheelbaseWidth);
    }

    private void getScoreGearRedRight(String aDirectory, double aWheelbaseWidth)
    {
        final String path_name = "RedRightScoreGear";

        WaypointSequence p = new WaypointSequence(10000);
        p.addWaypoint(RED_RIGHT_START);
        p.addWaypoint(RED_RIGHT_GEAR);

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
