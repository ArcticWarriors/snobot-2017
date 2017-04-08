/*
 * SnobotDrivetrain.cpp
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#include "Drivetrain/SnobotDrivetrain.h"

SnobotDrivetrain::SnobotDrivetrain(
        const std::shared_ptr<SpeedController>& aLeftMotor,
        const std::shared_ptr<SpeedController>& aRightMotor, const std::shared_ptr<IDriverJoystick>& aJoystick,
        const std::shared_ptr<Encoder>& aLeftDriveEncoder, const std::shared_ptr<Encoder>& aRightDriveEncoder) :
        ASnobotDrivetrain(aLeftMotor, aRightMotor, aJoystick), mLeftDriveEncoder(aLeftDriveEncoder), mRightDriveEncoder(aRightDriveEncoder)

{

}

SnobotDrivetrain::~SnobotDrivetrain()
{

}

void SnobotDrivetrain::update()
{
    mLeftDriveEncoder->SetDistancePerPulse(1);
    mRightDriveEncoder->SetDistancePerPulse(1);

    mLeftMotorDistance = mLeftDriveEncoder->GetDistance();
    mRightMotorDistance = mRightDriveEncoder->GetDistance();
    mRightEncoderRaw = mRightDriveEncoder->GetRaw();
    mLeftEncoderRaw = mLeftDriveEncoder->GetRaw();
}

void SnobotDrivetrain::resetEncoders()
{
    mLeftDriveEncoder->Reset();
    mRightDriveEncoder->Reset();
}
