package com.snobot2017.autonomous.commands.position_based;

import com.snobot.lib.motion_profile.ISetpointIterator;
import com.snobot.lib.motion_profile.PathConfig;
import com.snobot.lib.motion_profile.StaticSetpointIterator;
import com.snobot2017.autonomous.AutonomousFactory.StartingPositions;
import com.snobot2017.autonomous.path.DriveStraightPathWithGyro;
import com.snobot2017.drivetrain.IDriveTrain;
import com.snobot2017.positioner.IPositioner;

/**
 * uses the motion profile to follow a straight path
 * 
 * @author Andrew
 *
 */
public class DriveStraightPathWithGryoBasedOnStartingPosition extends DriveStraightPathWithGyro
{
    /**
     * 
     * @param aDriveTrain
     *            The robot's drive train
     * @param aPositioner
     *            The robot's positioner
     * @param aExpectedDt
     * @param aSetpointIterator
     *            A setpoint iterator for the path follower
     */
    public DriveStraightPathWithGryoBasedOnStartingPosition(IDriveTrain aDriveTrain, IPositioner aPositioner, StartingPositions aStartPosition,
            double aMaxVelocity, double aMaxAccelleration, double aExpectedDt, 
            double aRedLeft, double aRedRight, double aRedMiddle, 
            double aBlueLeft, double aBlueRight, double aBlueMiddle)
    {

        super(aDriveTrain, aPositioner, createSetpointIterator(aStartPosition, aMaxVelocity, aMaxAccelleration, aExpectedDt, aRedLeft, aRedRight,
                aRedMiddle, aBlueLeft, aBlueRight, aBlueMiddle));
    }
    
    private static final ISetpointIterator createSetpointIterator(
            StartingPositions aStartPosition,
            double aMaxVelocity, double aMaxAccelleration, double aExpectedDt, 
            double aRedLeft, double aRedRight, double aRedMiddle, 
            double aBlueLeft, double aBlueRight, double aBlueMiddle)
    {

        double endpoint = 0;

        switch (aStartPosition)
        {
        case RedLeft:
            endpoint = aRedLeft;
            break;
        case RedRight:
            endpoint = aRedRight;
            break;
        case BlueRight:
            endpoint = aBlueRight;
            break;
        case BlueLeft:
            endpoint = aBlueLeft;
            break;
        case RedMiddle:
            endpoint = aRedMiddle;
            break;
        case BlueMiddle:
            endpoint = aBlueMiddle;
            break;
        // Intended fall through - do nothing in these unexpected cases
        case Origin:
        default:
            break;
        }

        PathConfig dudePathConfig = new PathConfig(endpoint, aMaxVelocity, aMaxAccelleration, aExpectedDt);
        ISetpointIterator dudeSetpointIterator = new StaticSetpointIterator(dudePathConfig);

        return dudeSetpointIterator;
    }
}
