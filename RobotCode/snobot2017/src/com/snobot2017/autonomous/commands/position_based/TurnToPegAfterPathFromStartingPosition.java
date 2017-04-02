package com.snobot2017.autonomous.commands.position_based;

import com.snobot2017.SnobotActor.ISnobotActor;
import com.snobot2017.autonomous.AutonomousFactory.StartingPositions;
import com.snobot2017.autonomous.commands.TurnWithDegrees;
import com.snobot2017.positioner.IPositioner;

/**
 * Turns a fixed number of degrees based on the starting position. To be used in
 * conjunction with DriveStraightPathWithGyroFromStartingPosition so that you
 * can go to a peg from a starting position.
 * 
 * @author Team174
 *
 */
public class TurnToPegAfterPathFromStartingPosition extends TurnWithDegrees
{
    private IPositioner mPositioner;

    /**
     * Constructor
     * 
     * @param aSpeed
     *            The speed to drive
     * @param aTurnAngle
     * @param aDriveTrain
     * @param aPositioner
     */
    public TurnToPegAfterPathFromStartingPosition(double aSpeed, StartingPositions aStartPosition, IPositioner aPositioner, ISnobotActor aSnobotActor, double aRedLeft,
            double aRedRight, double aRedMiddle, double aBlueLeft, double aBlueRight, double aBlueMiddle)
    {
        super(aSpeed, getTurnAngle(aStartPosition, aRedLeft, aRedRight, aRedMiddle, aBlueLeft, aBlueRight, aBlueMiddle), aSnobotActor);
    }

    private static double getTurnAngle(StartingPositions aStartPosition,
            double aRedLeft, double aRedRight, double aRedMiddle, 
            double aBlueLeft, double aBlueRight, double aBlueMiddle)
    {
        double turnAngle = 0;
        
        switch (aStartPosition)
        {
        case RedLeft:
            turnAngle = aRedLeft;
            break;
        case RedRight:
            turnAngle = aRedRight;
            break;
        case BlueRight:
            turnAngle = aBlueRight;
            break;
        case BlueLeft:
            turnAngle = aBlueLeft;
            break;
        case RedMiddle:
            turnAngle = aRedMiddle;
            break;
        case BlueMiddle:
            turnAngle = aBlueMiddle;
            break;
        case Origin:
        default:
            turnAngle = 0;
            break;
        }
        
        return turnAngle;
    }

    @Override
    protected void execute()
    {
        super.execute();

        if(mFinished)
        {
            mPositioner.setPosition(0, 0, 0);
        }
    }
}