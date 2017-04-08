/*
 * SnobotDriverJoystick.cpp
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#include "Joystick/SnobotDriverJoystick.h"
#include "SmartDashboardNames.h"

// WpiLib
#include "SmartDashboard/SmartDashboard.h"

SnobotDriverJoystick::SnobotDriverJoystick(const std::shared_ptr<Joystick>& aJoystick, const std::shared_ptr<ILogger>& aLogger) :
        mJoystick(aJoystick), mLogger(aLogger)
{

}

SnobotDriverJoystick::~SnobotDriverJoystick()
{

}


void SnobotDriverJoystick::initializeLogHeaders()
{
    mLogger->addHeader("XbaxLeftJoystickSpeed");
    mLogger->addHeader("XbaxRightJoystickSpeed");
}

void SnobotDriverJoystick::update()
{

}

void SnobotDriverJoystick::updateSmartDashboard()
{
    SmartDashboard::PutNumber(SmartDashboardNames::sLEFT_XBAX_JOYSTICK_SPEED, getLeftSpeed());
    SmartDashboard::PutNumber(SmartDashboardNames::sRIGHT_XBAX_JOYSTICK_SPEED, getRightSpeed());
//        SmartDashboard.putNumber(SmartDashBoardNames.sAUTON_NUM, mAutonFactory.autonMode());
}

void SnobotDriverJoystick::updateLog()
{
    mLogger->updateLogger(getLeftSpeed());
    mLogger->updateLogger(getRightSpeed());
}

double SnobotDriverJoystick::getRightSpeed()
{
    return mJoystick->GetRawAxis(5);
}

double SnobotDriverJoystick::getLeftSpeed()
{
    return mJoystick->GetRawAxis(1);
}
