package com.snobot2016.scaling;

/**
 * Author Jeffrey/Michael
 * interface for scaling class
 * 
 */

import com.snobot.xlib.ISubsystem;
import com.snobot.xlib.PropertyManager.DoubleProperty;
import com.snobot2016.Properties2016;

public interface IScaling extends ISubsystem
{
    /**
     * Public Enumeration that contains the 4 positions for the Scale Tilt
     * 
     * @author ag.goelayush
     *
     */
    public enum ScaleAngles
    {
        Ground(Properties2016.sSCALE_GROUND_ANGLE), 
        MoveForIntake(Properties2016.sGET_OUT_OF_THE_WAY_OF_INTAKE), 
        Vertical(Properties2016.sVERTICAL), 
        Hook(Properties2016.sHOOK_ANGLE);

        private DoubleProperty mAngle;

        ScaleAngles(DoubleProperty aAngle)
        {
            mAngle = aAngle;
        }

        public double getDesiredAngle()
        {
            return mAngle.getValue();
        }
    }

    /**
     * Raises the extension ladder
     */
    void raiseScaleExtensionLadder();

    /**
     * Lowers down the extension ladder
     */
    void lowerScaleExtensionLadder();

    /**
     * Lowers the Scaling Mechanism, down to the ground level
     */
    void lowerScaleTiltMechanism();

    /**
     * Raises the Scaling Mechanism, up against the wall
     */
    void raiseScaleTiltMechanism();

    /**
     * The distance scaler has extended, in percentage, calculated by using the
     * Scaling Potentiometer
     * 
     * @return % of the scale extended
     */
    double percentageScaled();

    /**
     * This function, for scale tilt, goes to the goal angle from the current
     * angle. The motor slows down as it approaches the goal angle.
     * 
     * @param goal
     *            Goal Angle
     * @return True if the distance between current angle and goal angle is less
     *         than 5, else false
     */
    boolean goToPosition(ScaleAngles goal);

    /**
     * Safety check to see if the current position of the scale tilt is already
     * at its maximum angle if raising the scale
     * 
     * @return True if Current Angle of the scale is less than the Highest Angle
     *         based on potentiometer
     */
    boolean safeToRaise();

    /**
     * Safety check to see if the current position of the scale tilt is already
     * at its lowest angle if lowering the scale
     * 
     * @return True if Current Angle of the scale is greater than the lowest
     *         angle based on potentiometer
     */
    boolean safeToLower();
}
