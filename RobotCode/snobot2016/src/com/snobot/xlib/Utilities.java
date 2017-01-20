package com.snobot.xlib;

/**
 * This class holds a bunch of static methods and variables needed for
 * mathematics
 */
public class Utilities
{

    private Utilities()
    {
    }

    public static double getDifferenceInAngleRadians(double from, double to)
    {
        return boundAngleNegPiToPiRadians(to - from);
    }

    public static double getDifferenceInAngleDegrees(double from, double to)
    {
        return boundAngleNeg180to180Degrees(to - from);
    }

    public static double boundAngle0to360Degrees(double angle)
    {
        return wrap(angle, 0, 360);
    }

    public static double boundAngleNeg180to180Degrees(double angle)
    {

        return wrap(angle, -180, 180);
    }

    public static double boundAngle0to2PiRadians(double angle)
    {
        return wrap(angle, 0, 2 * Math.PI);
    }

    public static double boundAngleNegPiToPiRadians(double angle)
    {
        return wrap(angle, -Math.PI, Math.PI);
    }

    public static double wrap(double value, double min, double max)
    {
        double diff = max - min;
        while (value >= max)
        {
            value -= diff;
        }

        while (value < min)
        {
            value += diff;
        }

        return value;
    }

	public static double stopInDeadband(double aInValue, double aDeadband) {
		if(aInValue <= aDeadband && aInValue >= -aDeadband)
		{
			return 0;
		}
		return aInValue;
	}
}
