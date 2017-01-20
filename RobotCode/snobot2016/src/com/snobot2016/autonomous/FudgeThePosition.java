package com.snobot2016.autonomous;

import com.snobot2016.positioner.IPositioner;

import edu.wpi.first.wpilibj.command.Command;

/**
 * @author Andrew Command to take (X,Y) positions and replace the once in the
 *         positioner with them.
 */
public class FudgeThePosition extends Command
{
    private IPositioner mPositioner;
    private double mXPosition;
    private double mYPosition;

    /**
     * Creates a new FudgeThePosition command.
     * 
     * @param aPositioner
     *            The Robot's positioner.
     * @param aXPosition
     *            The desired new X position
     * @param aYPosition
     *            The desired new Y position
     */
    public FudgeThePosition(IPositioner aPositioner, double aXPosition, double aYPosition)
    {
        mPositioner = aPositioner;
        mXPosition = aXPosition;
        mYPosition = aYPosition;
    }

    /**
     * Sets the position based on the args
     */
    @Override
    protected void initialize()
    {
        mPositioner.setPosition(mXPosition, mYPosition, 0);
    }

    @Override
    protected void execute()
    {

    }

    @Override
    protected boolean isFinished()
    {
        return true;
    }

    @Override
    protected void end()
    {

    }

    @Override
    protected void interrupted()
    {

    }

}
