package com.snobot2017.drivetrain;

import com.snobot.lib.modules.ISubsystem;
import com.snobot2017.SnobotActor.ISnobotActor;

public interface IDriveTrain extends ISubsystem
{
    /**
     * Request right encoder distance
     * 
     * @return distance in inches
     */
    double getRightDistance();

    /**
     * Request left encoder distance
     * 
     * @return distance in inches
     */
    double getLeftDistance();

    /**
     * Set left and right drive mode speed
     * 
     * @param aLeftSpeed
     *            Set motor speed between -1 and 1
     * @param aRightSpeed
     *            Set motor speed between -1 and 1
     */
    void setLeftRightSpeed(double aLeftSpeed, double aRightSpeed);

    double getLeftMotorSpeed();
    double getRightMotorSpeed();

    /**
     * Sets the encoders to zero
     */
    void resetEncoders();

    /**
     * Setting the SnobotActor in the drive train so that the drive train knows
     * what it is because we can't do it in the constructor.
     * 
     * @param aSnobotActor
     *            The actor
     */
    void setSnobotActor(ISnobotActor aSnobotActor);
}
