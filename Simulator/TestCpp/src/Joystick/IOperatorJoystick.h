/*
 * IOperatorJoystick.hpp
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#ifndef SRC_IOPERATORJOYSTICK_HPP_
#define SRC_IOPERATORJOYSTICK_HPP_

#include "SnobotLib/Modules/IJoystick.h"

class IOperatorJoystick: public IJoystick
{
public:
    enum GearBossPositions
    {
        NONE, UP, DOWN
    };

    /**
     * Is the catch rope button pressed?
     *
     * @return true if pressed
     */
    virtual bool isCatchRope() = 0;

    /**
     * Is the climb button pressed?
     *
     * @return true if pressed
     */
    virtual bool isClimb() = 0;

    /**
     * Is the green light toggle on?
     *
     * @return whether the green lights should be on
     */
    virtual bool greenLightOn() = 0;

    /**
     * Should the green light be on?
     *
     * @return True if the light should be on
     */
    virtual bool blueLightOn() = 0;

    /**
     * Returns UP if up button is pressed, DOWN, if down button is pressed, and
     * NONE, if neither buttons are pressed
     *
     * @return UP to move Gear Boss up, DOWN to move Gear Boss down, and NONE to
     *         do nothing
     */
    virtual GearBossPositions moveGearBossToPosition() = 0;

    /**
     * Indicates the joystick should be rumbling
     *
     * @param aRumble
     *            True to rumble
     */
    virtual void setShouldRumble(bool aRumble) = 0;
};



#endif /* SRC_IOPERATORJOYSTICK_HPP_ */
