/*
 * ASnobotDrivetrain.h
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#ifndef SRC_DRIVETRAIN_ASNOBOTDRIVETRAIN_H_
#define SRC_DRIVETRAIN_ASNOBOTDRIVETRAIN_H_

#include "Drivetrain/IDrivetrain.h"
#include "Joystick/IDriverJoystick.h"
#include "SnobotLib/Logger/ILogger.h"

// WpiLIb
#include "SpeedController.h"
#include "RobotDrive.h"

class ASnobotDrivetrain: public IDrivetrain
{
public:
    ASnobotDrivetrain(
            const std::shared_ptr<SpeedController>& aLeftMotor,
            const std::shared_ptr<SpeedController>& aRightMotor, const std::shared_ptr<IDriverJoystick>& aJoystick);
    virtual ~ASnobotDrivetrain();

    /**
     * Request right encoder distance
     *
     * @return distance in inches
     */
    virtual double getRightDistance();

    /**
     * Request left encoder distance
     *
     * @return distance in inches
     */
    virtual double getLeftDistance();

    /**
     * Set left and right drive mode speed
     *
     * @param aLeftSpeed
     *            Set motor speed between -1 and 1
     * @param aRightSpeed
     *            Set motor speed between -1 and 1
     */
    virtual void setLeftRightSpeed(double aLeftSpeed, double aRightSpeed);

    virtual double getLeftMotorSpeed();
    virtual double getRightMotorSpeed();

    ////////////////////////////////////////////
    // Subsystem Overrides
    ////////////////////////////////////////////

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

    std::shared_ptr<SpeedController> mLeftMotor;
    std::shared_ptr<SpeedController> mRightMotor;
    std::shared_ptr<IDriverJoystick> mDriverJoystick;

    std::shared_ptr<ILogger> mLogger;

    RobotDrive mRobotDrive;

    double mLeftMotorSpeed;
    double mRightMotorSpeed;

    double mRightMotorDistance;
    double mLeftMotorDistance;

    int mRightEncoderRaw;
    int mLeftEncoderRaw;

};

#endif /* SRC_DRIVETRAIN_ASNOBOTDRIVETRAIN_H_ */
