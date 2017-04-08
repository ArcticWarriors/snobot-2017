/*
 * SnobotClimber.cpp
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#include "Climber/SnobotClimber.h"
#include "SmartDashboardNames.h"

// WpiLib
#include "SpeedController.h"
#include "SmartDashboard/SmartDashboard.h"

SnobotClimber::SnobotClimber(const std::shared_ptr<SpeedController>& aClimbingMotor, const std::shared_ptr<IOperatorJoystick>& aJoystick, const std::shared_ptr<ILogger>& aLogger) :
        mClimbingMotor(aClimbingMotor), mJoystick(aJoystick), mLogger(aLogger),
        mMotorSpeed(0)
{

}

SnobotClimber::~SnobotClimber()
{

}


void SnobotClimber::initializeLogHeaders()
{
    mLogger->addHeader("RotationSpeed");
}

void SnobotClimber::update()
{
    mMotorSpeed = mClimbingMotor->Get();
}

void SnobotClimber::control()
{
    controlRotation();
}

void SnobotClimber::controlRotation()
{
    if (mJoystick->isCatchRope())
    {
        catchRope();
    }
    else if (mJoystick->isClimb())
    {
        climbRope();
    }
    else
    {
        stop();
    }
}

void SnobotClimber::updateSmartDashboard()
{
    SmartDashboard::PutNumber(SmartDashboardNames::sROBOT_ROPE_MOTOR_SPEED, mMotorSpeed);
}

void SnobotClimber::updateLog()
{
    mLogger->updateLogger(mMotorSpeed);
}

void SnobotClimber::stop()
{
    mClimbingMotor->Set(0);
}

void SnobotClimber::catchRope()
{
    double speed = .5;
    mClimbingMotor->Set(speed);
    mLogger->updateLogger(speed);
}

void SnobotClimber::climbRope()
{
    double speed = 1;
    mClimbingMotor->Set(speed);
    mLogger->updateLogger(speed);
}
