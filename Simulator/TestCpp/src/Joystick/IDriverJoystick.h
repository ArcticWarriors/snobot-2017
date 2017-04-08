/*
 * IDriverJoystick.h
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#ifndef SRC_JOYSTICK_IDRIVERJOYSTICK_H_
#define SRC_JOYSTICK_IDRIVERJOYSTICK_H_


#include "SnobotLib/Modules/IJoystick.h"

class IDriverJoystick: public IJoystick
{
public:

    virtual ~IDriverJoystick()
    {

    }

    /**
     *
     * @return Returns the right speed
     */
    virtual double getRightSpeed() = 0;

    /**
     *
     * @return Returns the left speed
     */
    virtual double getLeftSpeed() = 0;
};



#endif /* SRC_JOYSTICK_IDRIVERJOYSTICK_H_ */
