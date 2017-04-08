/*
 * SnobotClimber.h
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#ifndef SRC_CLIMBER_SNOBOTCLIMBER_H_
#define SRC_CLIMBER_SNOBOTCLIMBER_H_

#include "Climber/IClimber.h"
#include "Joystick/IOperatorJoystick.h"
#include "SnobotLib/Logger/ILogger.h"
#include <memory>

// WpiLib
#include "SpeedController.h"

class SnobotClimber: public IClimber
{
public:
    SnobotClimber(const std::shared_ptr<SpeedController>& aClimbingMotor, const std::shared_ptr<IOperatorJoystick>& aJoystick, const std::shared_ptr<ILogger>& aLogger);
    virtual ~SnobotClimber();

    /**
     * Catch the Rope
     */
    virtual void catchRope() override;

    /**
     * Climb the Rope
     */
    virtual void climbRope() override;

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

    void controlRotation();

    std::shared_ptr<SpeedController> mClimbingMotor;
    std::shared_ptr<IOperatorJoystick> mJoystick;
    std::shared_ptr<ILogger> mLogger;
    double mMotorSpeed;
};

#endif /* SRC_CLIMBER_SNOBOTCLIMBER_H_ */
