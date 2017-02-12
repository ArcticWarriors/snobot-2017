package com.snobot2017.xtrajectory_gen;

import com.team254.lib.trajectory.gen.WaypointSequence;
import com.team254.lib.trajectory.gen.WaypointSequence.Waypoint;

public class GenerateGearToHopperPaths extends BasePathGenerator
{
    private void getRedLeftScoreGearGetHopper(String directory, double kWheelbaseWidth)
    {
        final String path_name = "RedLeftScoreGearGetHopper";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(new Waypoint(-NON_CENTER_GEAR_X_OFFSET, -NON_CENTER_CENTER_GEAR_Y, 45));
        p.addWaypoint(new Waypoint(-130, -NON_CENTER_CENTER_GEAR_Y, 130));
        p.addWaypoint(new Waypoint(WEST_HOPPERS_X, HOPPER_FIVE_Y, 90));

        generate(GEAR_SPEED_BACKWARDS_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private void getRedRightScoreGearGetHopper(String directory, double kWheelbaseWidth)
    {
        final String path_name = "RedRightScoreGearGetHopper";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(RED_RIGHT_GEAR);
        p.addWaypoint(negateAngle(EAST_HOPPER_ONE));

        generate(GEAR_SPEED_BACKWARDS_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private void getBlueRightScoreGearGetHopper(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueRightScoreGearGetHopper";


        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(BLUE_RIGHT_GEAR);
        p.addWaypoint(new Waypoint(-130, NON_CENTER_CENTER_GEAR_Y, 50));
        p.addWaypoint(negateAngle(WEST_HOPPER_FOUR));

        generate(GEAR_SPEED_BACKWARDS_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private void getBlueLeftScoreGearGetHopper(String directory, double kWheelbaseWidth)
    {
        final String path_name = "BlueLeftScoreGearGetHopper";


        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(BLUE_LEFT_GEAR);
        p.addWaypoint(negateAngle(EAST_HOPPER_THREE));

        generate(GEAR_SPEED_BACKWARDS_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }


    public void generatePaths(String aDirectory, double aWheelbaseWidth)
    {
        getRedLeftScoreGearGetHopper(aDirectory, aWheelbaseWidth);
        getRedRightScoreGearGetHopper(aDirectory, aWheelbaseWidth);
        // getBlueRightScoreGearGetHopper(aDirectory, aWheelbaseWidth);
        // getBlueLeftScoreGearGetHopper(aDirectory, aWheelbaseWidth);
    }

}
