///*
// * SnobotCanDrivetrain.cpp
// *
// *  Created on: May 26, 2017
// *      Author: preiniger
// */
//
//#include "Drivetrain/SnobotCanDrivetrain.h"
//
//SnobotCanDrivetrain::SnobotCanDrivetrain(const std::shared_ptr<CANTalon>& aLeftMotor, const std::shared_ptr<CANTalon>& aRightMotor, const std::shared_ptr<IDriverJoystick>& aJoystick) :
//        ASnobotDrivetrain(aLeftMotor, aRightMotor, aJoystick)
//{
//
//}
//
//SnobotCanDrivetrain::~SnobotCanDrivetrain()
//{
//
//}
//
//void SnobotCanDrivetrain::update()
//{
//    std::shared_ptr<CANTalon> leftMotor = std::dynamic_pointer_cast<CANTalon>(mLeftMotor);
//    std::shared_ptr<CANTalon> rightMotor = std::dynamic_pointer_cast<CANTalon>(mRightMotor);
//
//    mLeftMotorDistance = leftMotor->GetEncPosition();
//    mRightMotorDistance = rightMotor->GetEncPosition();
//    mRightEncoderRaw = 0;
//    mLeftEncoderRaw = 0;
//}
//
//void SnobotCanDrivetrain::resetEncoders()
//{
//    std::shared_ptr<CANTalon> leftMotor = std::dynamic_pointer_cast<CANTalon>(mLeftMotor);
//    std::shared_ptr<CANTalon> rightMotor = std::dynamic_pointer_cast<CANTalon>(mRightMotor);
//
//    leftMotor->SetEncPosition(0);
//    rightMotor->SetEncPosition(0);
//}
