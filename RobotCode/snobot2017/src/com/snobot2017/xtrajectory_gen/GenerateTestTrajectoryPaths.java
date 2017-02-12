package com.snobot2017.xtrajectory_gen;

import com.team254.lib.trajectory.gen.TrajectoryGenerator;
import com.team254.lib.trajectory.gen.WaypointSequence;
import com.team254.lib.trajectory.gen.WaypointSequence.Waypoint;

public class GenerateTestTrajectoryPaths extends BasePathGenerator
{
    private void genTestStraightSlow(String aDirectory, double aWheelbaseWidth)
    {
        final String path_name = "test/TestStraightSlow";

        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 36;

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(new Waypoint(0, 0, 0));
        p.addWaypoint(new Waypoint(0, 96, 0));

        generate(config, p, aDirectory, path_name, aWheelbaseWidth);
    }

    private void genTestStraightFast(String aDirectory, double aWheelbaseWidth)
    {
        final String path_name = "test/TestStraightFast";

        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 84;

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(new Waypoint(0, 0, 0));
        p.addWaypoint(new Waypoint(0, 96, 0));

        generate(config, p, aDirectory, path_name, aWheelbaseWidth);
    }

    private void genTestMoveRightSlow(String aDirectory, double aWheelbaseWidth)
    {
        final String path_name = "test/TestMoveRightSlow";

        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 36;

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(new Waypoint(0, 0, 0));
        p.addWaypoint(new Waypoint(NON_CENTER_GEAR_X_OFFSET, 96, 0));

        generate(config, p, aDirectory, path_name, aWheelbaseWidth);
    }

    private void genTestMoveLeftSlow(String aDirectory, double aWheelbaseWidth)
    {
        final String path_name = "test/TestMoveLeftSlow";

        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 36;

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(new Waypoint(0, 0, 0));
        p.addWaypoint(new Waypoint(-NON_CENTER_GEAR_X_OFFSET, 96, 0));

        generate(config, p, aDirectory, path_name, aWheelbaseWidth);
    }

    private void genTestMoveRightFast(String aDirectory, double aWheelbaseWidth)
    {
        final String path_name = "test/TestMoveRightFast";

        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 84;

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(new Waypoint(0, 0, 0));
        p.addWaypoint(new Waypoint(NON_CENTER_GEAR_X_OFFSET, 96, 0));

        generate(config, p, aDirectory, path_name, aWheelbaseWidth);
    }

    private void genTestMoveLeftFast(String aDirectory, double aWheelbaseWidth)
    {
        final String path_name = "test/TestMoveLeftFast";

        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 84;

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(new Waypoint(0, 0, 0));
        p.addWaypoint(new Waypoint(0, 96, 0));

        generate(config, p, aDirectory, path_name, aWheelbaseWidth);
    }

    private void genTestDriveBackwards(String aDirectory, double aWheelbaseWidth)
    {
        final String path_name = "test/TestBackwards";

        TrajectoryGenerator.Config config = new TrajectoryGenerator.Config();
        config.dt = .02;
        config.max_acc = 120;
        config.max_jerk = 480;
        config.max_vel = 36;

        WaypointSequence p = new WaypointSequence();
        p.addWaypoint(new Waypoint(0, 0, 0));
        p.addWaypoint(new Waypoint(0, -96, 0));

        generate(config, p, aDirectory, path_name, aWheelbaseWidth);
    }

    public void generatePaths(String aDirectory, double aWheelbaseWidth)
    {
        // genTestStraightSlow(aDirectory, aWheelbaseWidth);
        // genTestStraightFast(aDirectory, aWheelbaseWidth);
        // genTestMoveRightSlow(aDirectory, aWheelbaseWidth);
        // genTestMoveRightFast(aDirectory, aWheelbaseWidth);
        // genTestMoveLeftSlow(aDirectory, aWheelbaseWidth);
        // genTestMoveLeftFast(aDirectory, aWheelbaseWidth);
        genTestDriveBackwards(aDirectory, aWheelbaseWidth);
    }
}
