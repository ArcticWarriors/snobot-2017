/*
 * SnobotGearBoss.h
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#ifndef SRC_GEARBOSS_SNOBOTGEARBOSS_H_
#define SRC_GEARBOSS_SNOBOTGEARBOSS_H_

#include "Gearboss/IGearBoss.h"
#include "Joystick/IOperatorJoystick.h"
#include "SnobotLib/Logger/ILogger.h"

// WpiLib
#include "DoubleSolenoid.h"

class SnobotGearBoss: public IGearBoss
{
public:
    SnobotGearBoss(std::shared_ptr<DoubleSolenoid> aGearSolenoid,
            std::shared_ptr<IOperatorJoystick> aOperatorJoystick,
            std::shared_ptr<ILogger> aLogger);
    virtual ~SnobotGearBoss();
    

    /**
     * Moves gear to its high position. This is to get the gear onto the peg.
     */
    virtual void moveGearHigh();

    /**
     * This is to get the Gear Boss down and away from the gear. This is the
     * second part of the process of putting the gear on the peg.
     */
    virtual void moveGearLow();

    /**
     * This returns the position of the gear bucket.
     * 
     * @return true for gear high and false for gear low
     */
    virtual bool isGearUp();

    ////////////////////////////////////////////
    // Subsystem Overrides
    ////////////////////////////////////////////

    /**
     * Gathering and storing current sensor information. Ex. Motor Speed.
     */
    virtual void update() override;

    /**
     * Setting sensor and device states.
     */
    virtual void control() override;

    /**
     * Stops all sensors and motors
     */
    virtual void stop() override;

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
    std::shared_ptr<DoubleSolenoid> mGearSolenoid;
    std::shared_ptr<IOperatorJoystick> mOperatorJoystick;
    std::shared_ptr<ILogger> mLogger;

    bool mGearIsUp;

    static const DoubleSolenoid::Value sGEAR_DOWN_VALUE;
    static const DoubleSolenoid::Value sGEAR_UP_VALUE;
};

#endif /* SRC_GEARBOSS_SNOBOTGEARBOSS_H_ */
