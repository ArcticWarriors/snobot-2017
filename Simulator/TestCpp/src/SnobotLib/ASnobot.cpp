/*
 * ASnobot.cpp
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#include <SnobotLib/ASnobot.h>

ASnobot::ASnobot()
{

}

ASnobot::~ASnobot()
{

}


void ASnobot::AutonomousInit()
{

}

void ASnobot::AutonomousPeriodic()
{
    update();
    updateSmartDashboard();
    updateLog();
}

void ASnobot::TeleopInit()
{

}

void ASnobot::TeleopPeriodic()
{
    update();
    control();
    updateSmartDashboard();
    updateLog();
}

void ASnobot::TestPeriodic()
{

}

void ASnobot::update()
{
    for (auto iSubsystem : mUpdateableModules)
    {
        iSubsystem->update();

    }
}

void ASnobot::control()
{
    for (auto iSubsystem : mControllableModules)
    {
        iSubsystem->control();
    }
}

void ASnobot::updateSmartDashboard()
{
    for (auto iSubsystem : mSmartDashboardModules)
    {
        iSubsystem->updateSmartDashboard();
    }
}

void ASnobot::updateLog()
{

}
