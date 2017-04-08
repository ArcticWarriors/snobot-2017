/*
 * SnobotPositioner.h
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#ifndef SRC_POSITIONER_SNOBOTPOSITIONER_H_
#define SRC_POSITIONER_SNOBOTPOSITIONER_H_


#include "Positioner/IPositioner.h"
#include "Drivetrain/IDrivetrain.h"
#include "SnobotLib/Logger/ILogger.h"

// WpiLib
#include "interfaces/Gyro.h"
#include "Timer.h"


class SnobotPositioner: public IPositioner
{
public:
    SnobotPositioner(const std::shared_ptr<IDrivetrain>& aDriveTrain, const std::shared_ptr<Gyro>& aGyro, const std::shared_ptr<ILogger>& aLogger);
    virtual ~SnobotPositioner();
    /**
     * @return The robot's current X-position.
     */
    virtual double getXPosition() override;

    /**
     * @return The robot's current Y-position.
     */
    virtual double getYPosition() override;

    /**
     * @return The robot's current orientation in degrees.
     */
    virtual double getOrientationDegrees() override;

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
    virtual void setPosition(double aX, double aY, double aAngle) override;

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

    Timer mTimer;
    std::shared_ptr<IDrivetrain> mDriveTrain;
    std::shared_ptr<ILogger> mLogger;
    std::shared_ptr<Gyro> mGyro;

    double mXPosition;
    double mYPosition;
    double mOrientation;
    double mTotalDistance;

    double mLastDistance;
    double mLastTime;
    double mSpeed;
    double mStartAngle;
};

#endif /* SRC_POSITIONER_SNOBOTPOSITIONER_H_ */
