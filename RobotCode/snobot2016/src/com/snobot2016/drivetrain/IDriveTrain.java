package com.snobot2016.drivetrain;

import com.snobot.xlib.ISubsystem;

/**
 * 
 * @author Team 174
 *  
 *  Interface for the Snobot drive train
 */
public interface IDriveTrain extends ISubsystem
{
    /**
     * 
     * @param left
     *             The left Speed
     * @param right
     *             The Right Speed
     */
    void setLeftRightSpeed(double left, double right);

    /**
     * 
     * @return The distance that the right motor has traveled
     */
    double getRightEncoderDistance();
    
    /**
     * 
     * @return The distance that the left motor has traveled
     */
    double getLeftEncoderDistance();
    
    /**
     * resets the encoders
     */
    void resetEncoders();
}
