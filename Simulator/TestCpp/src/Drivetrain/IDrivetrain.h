/*
 * IDrivetrain.h
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#ifndef SRC_DRIVETRAIN_IDRIVETRAIN_H_
#define SRC_DRIVETRAIN_IDRIVETRAIN_H_

#include "SnobotLib/Modules/ISubsystem.h"

class IDrivetrain: public ISubsystem
{
public:
    virtual ~IDrivetrain()
    {

    }

    /**
     * Request right encoder distance
     * 
     * @return distance in inches
     */
    virtual double getRightDistance() = 0;

    /**
     * Request left encoder distance
     * 
     * @return distance in inches
     */
    virtual double getLeftDistance() = 0;

    /**
     * Set left and right drive mode speed
     * 
     * @param aLeftSpeed
     *            Set motor speed between -1 and 1
     * @param aRightSpeed
     *            Set motor speed between -1 and 1
     */
    virtual void setLeftRightSpeed(double aLeftSpeed, double aRightSpeed) = 0;

    virtual double getLeftMotorSpeed() = 0;
    virtual double getRightMotorSpeed() = 0;

    /**
     * Sets the encoders to zero
     */
    virtual void resetEncoders() = 0;
};




#endif /* SRC_DRIVETRAIN_IDRIVETRAIN_H_ */
