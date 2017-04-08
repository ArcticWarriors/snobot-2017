/*
 * SnobotDriverJoystick.h
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#ifndef SRC_JOYSTICK_SNOBOTDRIVERJOYSTICK_H_
#define SRC_JOYSTICK_SNOBOTDRIVERJOYSTICK_H_

#include "Joystick/IDriverJoystick.h"
#include "SnobotLib/Logger/ILogger.h"

// WpiLib
#include "Joystick.h"

class SnobotDriverJoystick: public IDriverJoystick
{
public:
    SnobotDriverJoystick(const std::shared_ptr<Joystick>& aJoystick, const std::shared_ptr<ILogger>& aLogger);
    virtual ~SnobotDriverJoystick();

    /**
     *
     * @return Returns the right speed
     */
    virtual double getRightSpeed();

    /**
     *
     * @return Returns the left speed
     */
    virtual double getLeftSpeed();

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
    std::shared_ptr<ILogger> mLogger;
};

#endif /* SRC_JOYSTICK_SNOBOTDRIVERJOYSTICK_H_ */
