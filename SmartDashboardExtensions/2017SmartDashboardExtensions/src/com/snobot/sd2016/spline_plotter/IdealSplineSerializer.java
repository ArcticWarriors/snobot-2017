package com.snobot.sd2016.spline_plotter;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Serializes a 2D cubic spline into a string, to be used by the SmartDashboard
 * 
 * @author PJ
 *
 */
public class IdealSplineSerializer
{

    /**
     * Constructor, private because the static functions should be used
     */
    private IdealSplineSerializer()
    {

    }

    /**
     * De-Serializes a list of spline segments from the given string
     * 
     * @param aString
     *            The string to de-serialize
     * 
     * @return The path to drive
     */
    public static List<SplineSegment> deserializePath(String aString)
    {
        List<SplineSegment> points = new ArrayList<SplineSegment>();
        StringTokenizer tokenizer = new StringTokenizer(aString, ",");

        while (tokenizer.hasMoreElements())
        {
            SplineSegment segment = deserializePathPoint(tokenizer);
            points.add(segment);
        }

        return points;
    }

    /**
     * Serializes a path of spline points into a string
     * 
     * @param aPoints
     *            The list of points
     * 
     * @return The serialized string
     */
    public static String serializePath(List<SplineSegment> aPoints)
    {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < aPoints.size(); ++i)
        {
            output.append(serializePathPoint(aPoints.get(i)));
        }

        return output.toString();
    }

    /**
     * Serializes a single spline point
     * 
     * @param aPoint
     *            The piont to serialize
     * 
     * @return The serialized point
     */
    public static String serializePathPoint(SplineSegment aPoint)
    {
        return ""
                + aPoint.mLeftSidePosition + ", "
                + aPoint.mLeftSideVelocity + ", "
                + aPoint.mRightSidePosition + ", "
                + aPoint.mRightSideVelocity + ", "
                + aPoint.mRobotHeading + ","
                + aPoint.mAverageX + ","
                + aPoint.mAverageY + ",";
    }

    /**
     * De-serializes a spline point from the given string
     * 
     * @param tokenizer
     *            The tokenizer containing the point to parse
     * 
     * @return The de-serialized point
     */
    public static SplineSegment deserializePathPoint(StringTokenizer tokenizer)
    {
        SplineSegment point = new SplineSegment();

        try
        {
            point.mLeftSidePosition = Double.parseDouble(tokenizer.nextToken());
            point.mLeftSideVelocity = Double.parseDouble(tokenizer.nextToken());
            point.mRightSidePosition = Double.parseDouble(tokenizer.nextToken());
            point.mRightSideVelocity = Double.parseDouble(tokenizer.nextToken());
            point.mRobotHeading = Double.parseDouble(tokenizer.nextToken());
            point.mAverageX = Double.parseDouble(tokenizer.nextToken());
            point.mAverageY = Double.parseDouble(tokenizer.nextToken());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return point;
    }
}
