/*
 * ASnobotDrivetrain.cpp
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#include "Drivetrain/ASnobotDrivetrain.h"
#include "SmartDashboardNames.h"

// WpiLib
#include "SmartDashboard/SmartDashboard.h"

ASnobotDrivetrain::ASnobotDrivetrain(const std::shared_ptr<SpeedController>& aLeftMotor, const std::shared_ptr<SpeedController>& aRightMotor, const std::shared_ptr<IDriverJoystick>& aJoystick) :
<<<<<<< HEAD
<<<<<<< HEAD
        mLeftMotor(aLeftMotor),
        mRightMotor(aRightMotor), mDriverJoystick(aJoystick), mRobotDrive(aLeftMotor, aRightMotor),
        mLeftMotorSpeed(0), mRightMotorSpeed(0), mRightMotorDistance(0), mLeftMotorDistance(0), mRightEncoderRaw(0), mLeftEncoderRaw(0)
=======
        mLeftMotor(aLeftMotor), mRightMotor(aRightMotor), mDriverJoystick(aJoystick), mLeftMotorSpeed(0), mRightMotorSpeed(0), mRightMotorDistance(0), mLeftMotorDistance(0), mRightEncoderRaw(0), mLeftEncoderRaw(
                0), mRobotDrive(aLeftMotor, aRightMotor)
>>>>>>> 9fafcd1... Adding a port of the robot code in CPP
=======
        mLeftMotor(aLeftMotor),
<<<<<<< HEAD
		mRightMotor(aRightMotor),
		mDriverJoystick(aJoystick),
		mRobotDrive(aLeftMotor, aRightMotor),

		mLeftMotorSpeed(0),
		mRightMotorSpeed(0),
		mRightMotorDistance(0),
		mLeftMotorDistance(0),
		mRightEncoderRaw(0),
		mLeftEncoderRaw(0)
>>>>>>> 9bd993d... Linux Simulator
=======
        mRightMotor(aRightMotor), mDriverJoystick(aJoystick), mRobotDrive(aLeftMotor, aRightMotor),
        mLeftMotorSpeed(0), mRightMotorSpeed(0), mRightMotorDistance(0), mLeftMotorDistance(0), mRightEncoderRaw(0), mLeftEncoderRaw(0)
>>>>>>> 408e130... Adding more CAN simulation
{

}

ASnobotDrivetrain::~ASnobotDrivetrain()
{

}


void ASnobotDrivetrain::initializeLogHeaders()
{
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 408e130... Adding more CAN simulation
    mLogger->addHeader("LeftEncoderDistance");
    mLogger->addHeader("RightEncoderDistance");
    mLogger->addHeader("LeftMotorSpeed");
    mLogger->addHeader("RightMotorSpeed");
<<<<<<< HEAD
=======
//    mLogger.addHeader("LeftEncoderDistance");
//    mLogger.addHeader("RightEncoderDistance");
//    mLogger.addHeader("LeftMotorSpeed");
//    mLogger.addHeader("RightMotorSpeed");
>>>>>>> 9fafcd1... Adding a port of the robot code in CPP
=======
>>>>>>> 408e130... Adding more CAN simulation
}

void ASnobotDrivetrain::control()
{
    setLeftRightSpeed(mDriverJoystick->getLeftSpeed(), mDriverJoystick->getRightSpeed());
}

void ASnobotDrivetrain::updateSmartDashboard()
{
    SmartDashboard::PutNumber(SmartDashboardNames::sLEFT_DRIVE_ENCODER_RAW, mLeftEncoderRaw);
    SmartDashboard::PutNumber(SmartDashboardNames::sRIGHT_DRIVE_ENCODER_RAW, mRightEncoderRaw);
    SmartDashboard::PutNumber(SmartDashboardNames::sRIGHT_DRIVE_ENCODER_DISTANCE, mRightMotorDistance);
    SmartDashboard::PutNumber(SmartDashboardNames::sLEFT_DRIVE_ENCODER_DISTANCE, mLeftMotorDistance);
    SmartDashboard::PutNumber(SmartDashboardNames::sLEFT_DRIVE_MOTOR_SPEED, mLeftMotorSpeed);
    SmartDashboard::PutNumber(SmartDashboardNames::sRIGHT_DRIVE_MOTOR_SPEED, mRightMotorSpeed);
}

void ASnobotDrivetrain::updateLog()
{
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 408e130... Adding more CAN simulation
    mLogger->updateLogger(mLeftMotorDistance);
    mLogger->updateLogger(mRightMotorDistance);
    mLogger->updateLogger(mLeftMotorSpeed);
    mLogger->updateLogger(mRightMotorSpeed);
<<<<<<< HEAD
=======
//    mLogger.updateLogger(mLeftMotorDistance);
//    mLogger.updateLogger(mRightMotorDistance);
//    mLogger.updateLogger(mLeftMotorSpeed);
//    mLogger.updateLogger(mRightMotorSpeed);
>>>>>>> 9fafcd1... Adding a port of the robot code in CPP
=======
>>>>>>> 408e130... Adding more CAN simulation
}

double ASnobotDrivetrain::getRightDistance()
{
    return mRightMotorDistance;
}

double ASnobotDrivetrain::getLeftDistance()
{
    return mLeftMotorDistance;
}

void ASnobotDrivetrain::setLeftRightSpeed(double aLeftSpeed, double aRightSpeed)
{
    mLeftMotorSpeed = aLeftSpeed;
    mRightMotorSpeed = aRightSpeed;
    mRobotDrive.SetLeftRightMotorOutputs(mLeftMotorSpeed, mRightMotorSpeed);
}

double ASnobotDrivetrain::getLeftMotorSpeed()
{
    return mLeftMotorSpeed;
}

double ASnobotDrivetrain::getRightMotorSpeed()
{
    return mRightMotorSpeed;

}

void ASnobotDrivetrain::stop()
{
    setLeftRightSpeed(0, 0);
}

