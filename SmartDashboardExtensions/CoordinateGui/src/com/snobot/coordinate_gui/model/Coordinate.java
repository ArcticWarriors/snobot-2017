package com.snobot.coordinate_gui.model;

/**
 * Represents a positioning coordinate in feet.
 * @author Rich
 */
public class Coordinate
{
    /** The x coordinate, in feet */
    public double x;

    /** The y coordinate, in feet */
    public double y;

    /** The angle of the coordinate, in degrees */
    public double angle;

    /**
     * Constructor.  Sets the (x,y,angle) position to all zeros
     */
    public Coordinate()
    {
        this(0.0, 0.0, 0.0);
    }

    /**
     * Copy constructor.  Copies the (x,y,angle) position from the given coordinate
     * @param c The coordinate to copy
     */
    public Coordinate(Coordinate c)
    {
        this(c.x, c.y, c.angle);
    }

    /**
     * Constructor.  Sets the (x,y,angle) to the given values
     * @param x The x coordinate
     * @param y The y coordinate
     * @param angle The angle
     */
    public Coordinate(double x, double y, double angle)
    {
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    @Override
    public String toString()
    {
        return "Coordinate{" + "x=" + x + ", y=" + y + ", angle=" + angle + '}';
    }

}
