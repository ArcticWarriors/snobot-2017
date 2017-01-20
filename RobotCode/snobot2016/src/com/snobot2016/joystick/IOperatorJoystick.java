package com.snobot2016.joystick;

import com.snobot.xlib.ISubsystem;

/**
 * 
 * @author jbnol_000
 *
 * Interface for the Operator Joystick
 *
 */
public interface IOperatorJoystick extends ISubsystem

{
    
    ///////////////////////////////////////////////
    // Emergency Overrides (for when sensors break)
    ///////////////////////////////////////////////
    
    /**
     * Emergency override for scale tilt
     * 
     * @return The override value for the scaling tilt motor
     */
    double getScaleTiltOverrideSpeed();

    /**
     * Emergency override for the harvester tilt motor
     * 
     * @return Speed for the harvester tilt motor
     */
    double getHarvestorTiltOverrideSpeed();

    ///////////////////////////////////////////////
    // Scaling
    ///////////////////////////////////////////////

    /**
     * Returns the speed the scaling climb motor should be using (no safety)
     * 
     * @return The climbimg speed
     */
    double getScaleMoveSpeed();

    /**
     * Returns if the "Move scaler to the ground" button is pressed. This should
     * use safety features
     * 
     * @return True if the scaler should move to the ground
     */
    boolean isScaleGoToGroundPressed();

    /**
     * Returns if the "Move scaler to the 90 deg" button is pressed. This should
     * use safety features
     * 
     * @return True if the scaler should move to the vertical position
     */
    boolean isScaleGoToVerticalPressed();

    /**
     * Returns if the "Get the scaler out of the way of the harvester" button is
     * pressed. This should use safety features
     * 
     * @return True if the scaler should move out of the way
     */
    boolean isScaleMoveForIntakePressed();

    /**
     * Returns if the "Move scaler so we can hook onto the bar" button is
     * pressed. This should use safety features
     * 
     * @return True if the scaler should move to the hook position
     */
    boolean isScaleGoToHookPositionPressed();

    /**
     * Indicates that the user wants to use the auto-hang feature. Should use
     * safety features
     * 
     * @return True if the auto-hang feature is enabled
     */
    boolean isFinalCountDown();

    ///////////////////////////////////////////////
    // Harvester
    ///////////////////////////////////////////////

    /**
     * Returns the speed at which the harvester intake motor should be running
     * 
     * @return The desired motor speed
     */
    double getHarvesterIntakeSpeed();

    /**
     * Returns if the "Move scaler to safety" button is pressed. Should use
     * safety features
     * 
     * @return True if the harvestor should move to the safe (90 deg) position
     */
    boolean moveHarvesterToUpPosition();

    /**
     * Returns if the "Move scaler to pick up a ball" button is pressed. Should
     * use safety features
     * 
     * @return True if the harvester should move to the down position
     */
    boolean moveHarvesterToDownPosition();
}
