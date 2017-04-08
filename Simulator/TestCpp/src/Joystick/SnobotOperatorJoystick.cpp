/*
 * SnobotOperatorJoystick.cpp
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#include "Joystick/SnobotOperatorJoystick.h"

SnobotOperatorJoystick::SnobotOperatorJoystick(const std::shared_ptr<Joystick>& aJoystick) :
        mJoystick(aJoystick)
{

}

SnobotOperatorJoystick::~SnobotOperatorJoystick()
{

}

void SnobotOperatorJoystick::initializeLogHeaders()
{

}

void SnobotOperatorJoystick::update()
{

}

void SnobotOperatorJoystick::updateLog()
{

}

void SnobotOperatorJoystick::updateSmartDashboard()
{

}

bool SnobotOperatorJoystick::greenLightOn()
{
    return mJoystick->GetRawButton(8);
}

bool SnobotOperatorJoystick::blueLightOn()
{
    return mJoystick->GetRawButton(7);
}

IOperatorJoystick::GearBossPositions SnobotOperatorJoystick::moveGearBossToPosition()
{
    IOperatorJoystick::GearBossPositions output;

    if (mJoystick->GetRawButton(2))
    {
        output = GearBossPositions::UP;
    }
    else if (mJoystick->GetRawButton(1))
    {
        output = GearBossPositions::DOWN;
    }
    else
    {
        output = GearBossPositions::NONE;
    }

    return output;
}

bool SnobotOperatorJoystick::isCatchRope()
{
    return mJoystick->GetRawButton(5);
}

bool SnobotOperatorJoystick::isClimb()
{
    return mJoystick->GetRawButton(6);
}

void SnobotOperatorJoystick::setShouldRumble(bool aRumble)
{
//      System.out.println("RUmbling " + aRumble);
    if (aRumble)
    {
        mJoystick->SetRumble(Joystick::kLeftRumble, 1);
        mJoystick->SetRumble(Joystick::kRightRumble, 1);
    }
    else
    {
        mJoystick->SetRumble(Joystick::kLeftRumble, 0);
        mJoystick->SetRumble(Joystick::kRightRumble, 0);
    }
}
