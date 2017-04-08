/*
 * SnobotDrivetrain.h
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#ifndef SRC_DRIVETRAIN_SNOBOTDRIVETRAIN_H_
#define SRC_DRIVETRAIN_SNOBOTDRIVETRAIN_H_

#include "Drivetrain/ASnobotDrivetrain.h"

// WpiLIb
#include "SpeedController.h"
#include "RobotDrive.h"
#include "Encoder.h"

class SnobotDrivetrain: public ASnobotDrivetrain
{
public:
    SnobotDrivetrain(
            const std::shared_ptr<SpeedController>& aLeftMotor,
            const std::shared_ptr<SpeedController>& aRightMotor, const std::shared_ptr<IDriverJoystick>& aJoystick,
            const std::shared_ptr<Encoder>& aLeftDriveEncoder, const std::shared_ptr<Encoder>& aRightDriveEncoder);
    virtual ~SnobotDrivetrain();

    /**
     * Gathering and storing current sensor information. Ex. Motor Speed.
     */
    virtual void update() override;

    /**
     * Sets the encoders to zero
     */
    virtual void resetEncoders() override;

protected:

    std::shared_ptr<Encoder> mLeftDriveEncoder;
    std::shared_ptr<Encoder> mRightDriveEncoder;
};

#endif /* SRC_DRIVETRAIN_SNOBOTDRIVETRAIN_H_ */
