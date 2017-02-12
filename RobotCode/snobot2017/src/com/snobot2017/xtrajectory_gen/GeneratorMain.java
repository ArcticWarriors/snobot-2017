package com.snobot2017.xtrajectory_gen;

import java.io.File;

public class GeneratorMain
{

    public static void main(String[] args)
    {
        String directory = "../snobot2017/resources/traj/";
        File f = new File(directory);
        System.out.println(f.getAbsolutePath());

        if (args.length >= 1)
        {
            directory = args[0];
        }

        final double kWheelbaseWidth = 25.5 / 12;

        new GenerateStartToGearPaths().generatePaths(directory, kWheelbaseWidth);
        new GenerateStartToHopperBlue().generatePaths(directory, kWheelbaseWidth);
        new GenerateStartToHopperRed().generatePaths(directory, kWheelbaseWidth);
        new GenerateGearToHopperPaths().generatePaths(directory, kWheelbaseWidth);
        new GenerateTestTrajectoryPaths().generatePaths(directory, kWheelbaseWidth);
    }
}
