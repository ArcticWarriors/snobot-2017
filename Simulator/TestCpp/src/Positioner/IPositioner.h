/*
 * IPositioner.h
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#ifndef SRC_POSITIONER_IPOSITIONER_H_
#define SRC_POSITIONER_IPOSITIONER_H_

#include "SnobotLib/Modules/ISubsystem.h"

class IPositioner: public ISubsystem
{
public:

    virtual ~IPositioner()
    {

    }

    /**
     * @return The robot's current X-position.
     */
    virtual double getXPosition() = 0;

    /**
     * @return The robot's current Y-position.
     */
    virtual double getYPosition() = 0;

    /**
     * @return The robot's current orientation in degrees.
     */
    virtual double getOrientationDegrees() = 0;

    /**
     * Sets the starting position of the robot
     *
     * @param aX
     *            The starting X position
     * @param aY
     *            The starting Y Position
     * @param aAngle
     *            The starting Angle (in degrees)
     */
    virtual void setPosition(double aX, double aY, double aAngle) = 0;
};



#endif /* SRC_POSITIONER_IPOSITIONER_H_ */
