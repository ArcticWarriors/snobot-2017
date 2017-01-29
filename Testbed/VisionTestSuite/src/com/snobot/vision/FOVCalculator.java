package com.snobot.vision;

public class FOVCalculator
{

    public static void main(String[] args)
    {
        int tapeWidth = 2;
        int tapeHeight = 5;
        int fovWidthPixels = 640;
        int fovHeightPixels = 480;

        // The last two numbers are obtained experimentally from taking pictures
        // with the camera at known distances

        // Width
        System.out.println("Calculating width FOV");
        {
            // 2ft
            System.out.println(getFovAngle(fovWidthPixels, tapeWidth, 24, 42));
            System.out.println(getFovAngle(fovWidthPixels, tapeWidth, 24, 46));

            // 3ft
            System.out.println(getFovAngle(fovWidthPixels, tapeWidth, 36, 29));
            System.out.println(getFovAngle(fovWidthPixels, tapeWidth, 36, 29));
        }

        // Height
        System.out.println("Calculating height FOV");
        {
            // 2ft
            System.out.println(getFovAngle(fovHeightPixels, tapeHeight, 24, 101));
            System.out.println(getFovAngle(fovHeightPixels, tapeHeight, 24, 102));

            // 3ft
            System.out.println(getFovAngle(fovHeightPixels, tapeHeight, 36, 71));
            System.out.println(getFovAngle(fovHeightPixels, tapeHeight, 36, 71));
        }
    }

    public static double getFovAngle(double aFieldOfView, double aTargetSize, double aDistance, double aTargetPixel)
    {
        double fraction = (aTargetSize * aFieldOfView) / (2 * aTargetPixel * aDistance);
        double angleRad = Math.atan(fraction);

        return Math.toDegrees(angleRad);
    }
}
