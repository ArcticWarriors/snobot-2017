package com.snobot2017.vision;


public class SavedRobotState
{
    /**
     * The X Location of the robot at the given time, in inches
     */
    public double mRobotX;

    /**
     * The Y location of the robot at the given time, in inches
     */
    public double mRobotY;

    /**
     * The angle of the robot at the given time, in degrees
     */
    public double mAngle;

    @Override
    public String toString()
    {
        return "RobotState [mRobotX=" + mRobotX + ", mRobotY=" + mRobotY + ", mAbsoluteAngle=" + mAngle + "]";
    }

}