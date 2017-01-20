package com.snobot2017.drivetrain;

import com.snobot.lib.ISubsystem;

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

}
