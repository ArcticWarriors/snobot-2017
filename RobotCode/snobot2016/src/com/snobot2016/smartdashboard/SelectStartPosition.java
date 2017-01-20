package com.snobot2016.smartdashboard;

import com.snobot2016.Properties2016;
import com.snobot2016.positioner.IPositioner;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITableListener;

/**
 * @author Andrew/Alec
 */
public class SelectStartPosition
{
    /**
     * The sendable chooser the class returns.
     */
    private SendableChooser mPickPoint;
    
    /**
     * The positioner.
     */
    private IPositioner mPositioner;

    /**
     * This is the enum that becomes the start positions chooser. The numbers
     * are the X, Y, and Orientations for each position.
     */
    public enum StartPositions
    {
        FIRST_POSITION(-135.75, 30 - Properties2016.sAUTON_SETUP_OVERHANG.getValue(), 0),
        SECOND_POSITION(-82.875, 30 - Properties2016.sAUTON_SETUP_OVERHANG.getValue(), 0), 
        THIRD_POSITION(-30.0, 30 - Properties2016.sAUTON_SETUP_OVERHANG.getValue(), 0), 
        FOURTH_POSITION(22.875, 30 - Properties2016.sAUTON_SETUP_OVERHANG.getValue(), 0), 
        FIFTH_POSITION(75.75, 30 - Properties2016.sAUTON_SETUP_OVERHANG.getValue(), 0), 
        SPY_POSITION(-150.5, 306, 90), 
        ZERO_ZERO_ZERO_POSITION(0, 0, 0);

        public final double mX;
        public final double mY;
        public final double mOrientation;

        StartPositions(double aX, double aY, double aAngle)
        {
            mX = aX;
            mY = aY;
            mOrientation = aAngle;
            System.out.println(mX);
        }

    }

    /**
     * Constructor: news up and adds objects to the sendable chooser.
     */
    public SelectStartPosition(IPositioner aPositioner)
    {
        mPickPoint = new SendableChooser();
        mPickPoint.addDefault("Position 1", StartPositions.FIRST_POSITION);
        mPickPoint.addObject("Position 2", StartPositions.SECOND_POSITION);
        mPickPoint.addObject("Position 3", StartPositions.THIRD_POSITION);
        mPickPoint.addObject("Position 4", StartPositions.FOURTH_POSITION);
        mPickPoint.addObject("Position 5", StartPositions.FIFTH_POSITION);
        mPickPoint.addObject("Spy Bot", StartPositions.SPY_POSITION);
        mPickPoint.addObject("TEST (0, 0, 0)", StartPositions.ZERO_ZERO_ZERO_POSITION);

        mPositioner = aPositioner;
    }

    /**
     * Puts the chooser on the smart dashboard.
     */
    public void putOnDash()
    {
        SmartDashboard.putData("Select Start Position: ", mPickPoint);
    }

    /**
     * Returns the selected position.
     */
    public StartPositions getSelected()
    {
        return (StartPositions) mPickPoint.getSelected();
    }

    /**
     * Gives the selected position data to the positioner.
     */
    public void setStartPosition()
    {
        StartPositions selected = getSelected();
        mPositioner.setPosition(selected.mX, selected.mY, selected.mOrientation);
    }

    public void addChangeListener(ITableListener aListener)
    {
        mPickPoint.getTable().addTableListener(aListener);
    }
}
