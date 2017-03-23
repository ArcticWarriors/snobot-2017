package com.snobot2017.xtrajectory_gen;

import com.team254.lib.trajectory.gen.WaypointSequence;
import com.team254.lib.trajectory.gen.WaypointSequence.Waypoint;

public class GenerateGearToHopperPaths extends BasePathGenerator
{
    private void getRedLeftScoreGearGetHopperFour(String directory, double kWheelbaseWidth)
    {
        final String path_name = "GearToHopper/RedLeftScoreGearGetHopperFour";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(new Waypoint(-NON_CENTER_GEAR_X_OFFSET, -NON_CENTER_CENTER_GEAR_Y, 45));
        p.addWaypoint(new Waypoint(-130, -NON_CENTER_CENTER_GEAR_Y, 134));
        p.addWaypoint(new Waypoint(-140, -NON_CENTER_CENTER_GEAR_Y + 90, 180));
        p.addWaypoint(new Waypoint(-130, 0, 179));
        p.addWaypoint(new Waypoint(WEST_HOPPERS_X, HOPPER_FOUR_Y, 90));

        generate(GEAR_SPEED_BACKWARDS_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getRedLeftScoreGearGetHopperFive(String directory, double kWheelbaseWidth)
    {
        final String path_name = "GearToHopper/RedLeftScoreGearGetHopperFive";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(new Waypoint(-NON_CENTER_GEAR_X_OFFSET, -NON_CENTER_CENTER_GEAR_Y, 45));
        p.addWaypoint(new Waypoint(-130, -NON_CENTER_CENTER_GEAR_Y, 130));
        p.addWaypoint(new Waypoint(WEST_HOPPERS_X, HOPPER_FIVE_Y, 90));

        generate(GEAR_SPEED_BACKWARDS_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private void getRedRightScoreGearGetHopperOne(String directory, double kWheelbaseWidth)
    {
        final String path_name = "GearToHopper/RedRightScoreGearGetHopperOne";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(RED_RIGHT_GEAR);
        p.addWaypoint(negateAngle(EAST_HOPPER_ONE));

        generate(GEAR_SPEED_BACKWARDS_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private void getRedRightScoreGearGetHopperTwo(String directory, double kWheelbaseWidth)
    {
        final String path_name = "GearToHopper/RedRightScoreGearGetHopperTwo";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(RED_RIGHT_GEAR);
        p.addWaypoint(new Waypoint(130, -NON_CENTER_CENTER_GEAR_Y, -140));
        p.addWaypoint(new Waypoint(140, -NON_CENTER_CENTER_GEAR_Y + 90, -200));
        p.addWaypoint(new Waypoint(130, -30, 181));
        p.addWaypoint(negateAngle(EAST_HOPPER_TWO));

        generate(GEAR_SPEED_BACKWARDS_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getBlueRightScoreGearGetHopperFour(String directory, double kWheelbaseWidth)
    {
        final String path_name = "GearToHopper/BlueRightScoreGearGetHopperFour";


        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(BLUE_RIGHT_GEAR);
        p.addWaypoint(new Waypoint(-130, NON_CENTER_CENTER_GEAR_Y, 50));
        p.addWaypoint(negateAngle(WEST_HOPPER_FOUR));

        generate(GEAR_SPEED_BACKWARDS_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getBlueRightScoreGearGetHopperFive(String directory, double kWheelbaseWidth)
    {
        final String path_name = "GearToHopper/BlueRightScoreGearGetHopperFive";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(BLUE_RIGHT_GEAR);
        p.addWaypoint(new Waypoint(-130, NON_CENTER_CENTER_GEAR_Y, 45));
        p.addWaypoint(new Waypoint(-140, NON_CENTER_CENTER_GEAR_Y - 90, 0));
        p.addWaypoint(new Waypoint(-130, 0, 1));
        p.addWaypoint(negateAngle(WEST_HOPPER_FIVE));

        generate(GEAR_SPEED_BACKWARDS_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }

    private void getBlueLeftScoreGearGetHopperTwo(String directory, double kWheelbaseWidth)
    {
        final String path_name = "GearToHopper/BlueLeftScoreGearGetHopperTwo";

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(BLUE_LEFT_GEAR);
        p.addWaypoint(new Waypoint(130, NON_CENTER_CENTER_GEAR_Y, -35));
        p.addWaypoint(new Waypoint(140, NON_CENTER_CENTER_GEAR_Y - 90, 20));
        p.addWaypoint(new Waypoint(140, 30, -1));
        p.addWaypoint(negateAngle(EAST_HOPPER_TWO));

        generate(GEAR_SPEED_BACKWARDS_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }
    
    private void getBlueLeftScoreGearGetHopperThree(String directory, double kWheelbaseWidth)
    {
        final String path_name = "GearToHopper/BlueLeftScoreGearGetHopperThree";


        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(BLUE_LEFT_GEAR);
        p.addWaypoint(negateAngle(EAST_HOPPER_THREE));

        generate(GEAR_SPEED_BACKWARDS_CONFIG, p, directory, path_name, kWheelbaseWidth);
    }


    public void generatePaths(String aDirectory, double aWheelbaseWidth)
    {
        getRedLeftScoreGearGetHopperFour(aDirectory, aWheelbaseWidth);
        getRedLeftScoreGearGetHopperFive(aDirectory, aWheelbaseWidth);

        getRedRightScoreGearGetHopperOne(aDirectory, aWheelbaseWidth);
        getRedRightScoreGearGetHopperTwo(aDirectory, aWheelbaseWidth);

        getBlueRightScoreGearGetHopperFour(aDirectory, aWheelbaseWidth);
        getBlueRightScoreGearGetHopperFive(aDirectory, aWheelbaseWidth);

        getBlueLeftScoreGearGetHopperTwo(aDirectory, aWheelbaseWidth);
        getBlueLeftScoreGearGetHopperThree(aDirectory, aWheelbaseWidth);
    }

}
