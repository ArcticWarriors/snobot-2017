/*
 * SnobotPositioner.cpp
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#include "Positioner/SnobotPositioner.h"
#include "SmartDashboardNames.h"
#include <math.h>

// WpiLib
#include "SmartDashboard/SmartDashboard.h"

<<<<<<< HEAD
<<<<<<< HEAD

SnobotPositioner::SnobotPositioner(const std::shared_ptr<IDrivetrain>& aDriveTrain, const std::shared_ptr<Gyro>& aGyro, const std::shared_ptr<ILogger>& aLogger) :
        mDriveTrain(aDriveTrain),
		mLogger(aLogger),
		mGyro(aGyro),
        mXPosition(0),
		mYPosition(0),
		mOrientation(0),
		mTotalDistance(0),
        mLastDistance(0),
		mLastTime(0),
		mSpeed(0),
		mStartAngle(0)
=======
SnobotPositioner::SnobotPositioner(const std::shared_ptr<IDrivetrain>& aDriveTrain, const std::shared_ptr<Gyro>& aGyro, const std::shared_ptr<ILogger>& aLogger) :
        mDriveTrain(aDriveTrain), mGyro(aGyro), mLogger(aLogger),
        mXPosition(0), mYPosition(0), mOrientation(0), mTotalDistance(0),
        mLastDistance(0), mLastTime(0), mSpeed(0), mStartAngle(0)
>>>>>>> 9fafcd1... Adding a port of the robot code in CPP
=======

SnobotPositioner::SnobotPositioner(const std::shared_ptr<IDrivetrain>& aDriveTrain, const std::shared_ptr<Gyro>& aGyro, const std::shared_ptr<ILogger>& aLogger) :
        mDriveTrain(aDriveTrain),
		mLogger(aLogger),
		mGyro(aGyro),
        mXPosition(0),
		mYPosition(0),
		mOrientation(0),
		mTotalDistance(0),
        mLastDistance(0),
		mLastTime(0),
		mSpeed(0),
		mStartAngle(0)
>>>>>>> 9bd993d... Linux Simulator
{

}

SnobotPositioner::~SnobotPositioner()
{

}


void SnobotPositioner::initializeLogHeaders()
{
    mTimer.Start();

    mLogger->addHeader("X-coordinate");
    mLogger->addHeader("Y-coordinate");
    mLogger->addHeader("Orientation");
    mLogger->addHeader("Speed");
}

void SnobotPositioner::update()
{
//    // Orientation
    mOrientation = (mGyro->GetAngle() + mStartAngle);
    double orientationRadians = mOrientation * 3.14159265358979323846 / 180;

    // ChangeInDistance and X/Y
    mTotalDistance = (mDriveTrain->getLeftDistance() + mDriveTrain->getRightDistance()) / 2;
    double deltaDistance = mTotalDistance - mLastDistance;
    mXPosition += deltaDistance * sin(orientationRadians);
    mYPosition += deltaDistance * cos(orientationRadians);
    //System.out.println("Positioner " + mTotalDistance + " " + mDriveTrain.getRightDistance() + " " + mDriveTrain.getLeftDistance());

    // Update
    mSpeed = (deltaDistance) / (mTimer.Get() - mLastTime);
    mLastTime = mTimer.Get();
    mLastDistance = mTotalDistance;
}

double SnobotPositioner::getXPosition()
{
    return mXPosition;
}

double SnobotPositioner::getYPosition()
{
    return mYPosition;
}

double SnobotPositioner::getOrientationDegrees()
{
    return mOrientation;
}

void SnobotPositioner::setPosition(double aX, double aY, double aAngle)
{
    mDriveTrain->resetEncoders();
    mXPosition = aX;
    mYPosition = aY;
    mStartAngle = aAngle;
    mGyro->Reset();
    mDriveTrain->resetEncoders();

    mTotalDistance = 0;
    mLastDistance = 0;
    mLastTime = mTimer.Get();
}

void SnobotPositioner::control()
{
    // Nothing
}

void SnobotPositioner::updateSmartDashboard()
{
    SmartDashboard::PutNumber(SmartDashboardNames::sX_POSITION, mXPosition);
    SmartDashboard::PutNumber(SmartDashboardNames::sY_POSITION, mYPosition);
    SmartDashboard::PutNumber(SmartDashboardNames::sORIENTATION, (mOrientation));
    SmartDashboard::PutNumber(SmartDashboardNames::sSPEED, mSpeed);
}

void SnobotPositioner::updateLog()
{
    mLogger->updateLogger(mXPosition);
    mLogger->updateLogger(mYPosition);
    mLogger->updateLogger(mOrientation);
    mLogger->updateLogger(mSpeed);
}

void SnobotPositioner::stop()
{
    // Nothing
}
