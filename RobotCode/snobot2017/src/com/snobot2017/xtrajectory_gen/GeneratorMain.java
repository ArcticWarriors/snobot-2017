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

        final double kWheelbaseWidth = 24;

        new GenerateGearToBoilerPaths().generatePaths(directory, kWheelbaseWidth);
        // new GenerateStartToGearPaths().generatePaths(directory,
        // kWheelbaseWidth);
        // new GenerateStartToHopperBlue().generatePaths(directory,
        // kWheelbaseWidth);
        // new GenerateStartToHopperRed().generatePaths(directory,
        // kWheelbaseWidth);
        // new GenerateGearToHopperPaths().generatePaths(directory,
        // kWheelbaseWidth);
        // new GenerateTestTrajectoryPaths().generatePaths(directory,
        // kWheelbaseWidth);

        // new GenerateFuel2xPaths().generatePaths(directory, kWheelbaseWidth);
        
        
//        double leftAvg = (5289 + 5718 + 5337) / 3.0;
//        double rightAvg = (7293+7483+7332) / 3.0;
//        double left = 1/(leftAvg * (1/96.0) * (1/4.0));
//        double right = 1/(rightAvg * (1/96.0) * (1/4.0));
//        System.out.println(left + ", " + right);
    }
}
