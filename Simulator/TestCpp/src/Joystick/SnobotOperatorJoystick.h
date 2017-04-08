/*
 * SnobotOperatorJoystick.h
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#ifndef SRC_JOYSTICK_SNOBOTOPERATORJOYSTICK_H_
#define SRC_JOYSTICK_SNOBOTOPERATORJOYSTICK_H_

#include "Joystick/IOperatorJoystick.h"

// WpiLib
#include "Joystick.h"

class SnobotOperatorJoystick: public IOperatorJoystick
{
public:
    SnobotOperatorJoystick(const std::shared_ptr<Joystick>& aJoystick);
    virtual ~SnobotOperatorJoystick();

    /**
     * Is the catch rope button pressed?
     *
     * @return true if pressed
     */
    virtual bool isCatchRope();

    /**
     * Is the climb button pressed?
     *
     * @return true if pressed
     */
    virtual bool isClimb();

    /**
     * Is the green light toggle on?
     *
     * @return whether the green lights should be on
     */
    virtual bool greenLightOn();

    /**
     * Should the green light be on?
     *
     * @return True if the light should be on
     */
    virtual bool blueLightOn();

    /**
     * Returns UP if up button is pressed, DOWN, if down button is pressed, and
     * NONE, if neither buttons are pressed
     *
     * @return UP to move Gear Boss up, DOWN to move Gear Boss down, and NONE to
     *         do nothing
     */
    virtual GearBossPositions moveGearBossToPosition();

    /**
     * Indicates the joystick should be rumbling
     *
     * @param aRumble
     *            True to rumble
     */
    virtual void setShouldRumble(bool aRumble);

    ////////////////////////////////////////////
    // Joystick Overrides
    ////////////////////////////////////////////

    /**
     * Gathering and storing current sensor information. Ex. Motor Speed.
     */
    virtual void update() override;

    /**
     * Perform initialization.
     */
    virtual void initializeLogHeaders() override;

    /**
     * Updates the logger.
     */
    virtual void updateLog() override;

    /**
     * Updates information that is sent to SmartDashboard Takes Enum argument
     */
    virtual void updateSmartDashboard() override;

protected:

    std::shared_ptr<Joystick> mJoystick;

};

#endif /* SRC_JOYSTICK_SNOBOTOPERATORJOYSTICK_H_ */
