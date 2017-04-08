
<<<<<<< HEAD
<<<<<<< HEAD
#include "Robot.h"
#include "Talon.h"

#include "Joystick/SnobotDriverJoystick.h"
#include "Joystick/SnobotOperatorJoystick.h"
#include "Climber/SnobotClimber.h"
#include "Gearboss/SnobotGearBoss.h"
#include "Drivetrain/SnobotDrivetrain.h"
#include "Drivetrain/SnobotCanDrivetrain.h"
#include "Positioner/SnobotPositioner.h"
#include "SnobotLib/Logger/SnobotLogger.h"

// WpiLib
#include "AnalogGyro.h"

void Robot::RobotInit()
{
    std::shared_ptr<ILogger> logger(new SnobotLogger);

    ////////////////////////////////////////////////////////////
    // Initialize joysticks
    ////////////////////////////////////////////////////////////
    std::shared_ptr<Joystick> driverXbox(new Joystick(0));
    std::shared_ptr<Joystick> operatorXbox(new Joystick(1));

    std::shared_ptr<IOperatorJoystick> operatorJoystick(new SnobotOperatorJoystick(operatorXbox));
    std::shared_ptr<IDriverJoystick> driverJoystick(new SnobotDriverJoystick(driverXbox, logger));

    ////////////////////////////////////////////////////////////
    // Initialize subsystems
    ////////////////////////////////////////////////////////////

    bool useCan = true;


    // Drive Train
    if (useCan)
    {
//        std::shared_ptr<CANTalon> driveLeftMotorA(new CANTalon(0));
//        std::shared_ptr<CANTalon> driveLeftMotorB(new CANTalon(1));
//        driveLeftMotorA->SetFeedbackDevice(CANTalon::FeedbackDevice::QuadEncoder);
//        driveLeftMotorB->SetTalonControlMode(CANTalon::TalonControlMode::kFollowerMode);
//        driveLeftMotorB->Set(driveLeftMotorA->GetDeviceID());
//
//        std::shared_ptr<CANTalon> driveRightMotorA(new CANTalon(2));
//        std::shared_ptr<CANTalon> driveRightMotorB(new CANTalon(3));
//        driveRightMotorA->SetFeedbackDevice(CANTalon::FeedbackDevice::QuadEncoder);
//        driveRightMotorB->SetTalonControlMode(CANTalon::TalonControlMode::kFollowerMode);
//        driveRightMotorB->Set(driveRightMotorA->GetDeviceID());
//
//        mDrivetrain = std::shared_ptr<IDrivetrain>(new SnobotCanDrivetrain(driveLeftMotorA, driveRightMotorB, driverJoystick));
    }
    else
    {
        std::shared_ptr<SpeedController> driveLeftMotor(new Talon(0));
        std::shared_ptr<SpeedController> driveRightMotor(new Talon(1));
        std::shared_ptr<Encoder> driveLeftEncoder(new Encoder(0, 1));
        std::shared_ptr<Encoder> driveRightEncoder(new Encoder(2, 3));
        mDrivetrain = std::shared_ptr<IDrivetrain>(new SnobotDrivetrain(driveLeftMotor, driveRightMotor, driverJoystick, driveLeftEncoder, driveRightEncoder));
    }
    addModule(mDrivetrain);

    // Gearboss
    std::shared_ptr<DoubleSolenoid> gearSolenoid(new DoubleSolenoid(0, 1));
    mGearBoss = std::shared_ptr<SnobotGearBoss>(new SnobotGearBoss(gearSolenoid, operatorJoystick, logger));
    addModule(mGearBoss);

    // Climber
    std::shared_ptr<SpeedController> climberMotor(new Talon(2));
    mClimber = std::shared_ptr<SnobotClimber>(new SnobotClimber(climberMotor, operatorJoystick, logger));
    addModule(mClimber);

    // Positioner
    std::shared_ptr<Gyro> gyro(new AnalogGyro(0));
    mPositioner = std::shared_ptr<IPositioner>(new SnobotPositioner(mDrivetrain, gyro, logger));
    addModule(mPositioner);
}


=======

//#include <iostream>
//#include <memory>
//#include <string>
//
//#include <IterativeRobot.h>
//#include <LiveWindow/LiveWindow.h>
//#include <SmartDashboard/SendableChooser.h>
//#include <SmartDashboard/SmartDashboard.h>

=======
>>>>>>> 1a6d3d7... C++ Simulator works!
#include "Robot.h"
#include "Talon.h"

#include "Joystick/SnobotDriverJoystick.h"
#include "Joystick/SnobotOperatorJoystick.h"
#include "Climber/SnobotClimber.h"
#include "Gearboss/SnobotGearBoss.h"
#include "Drivetrain/SnobotDrivetrain.h"
#include "Drivetrain/SnobotCanDrivetrain.h"
#include "Positioner/SnobotPositioner.h"
#include "SnobotLib/Logger/SnobotLogger.h"

// WpiLib
#include "AnalogGyro.h"

void Robot::RobotInit()
{
    std::shared_ptr<ILogger> logger(new SnobotLogger);

    ////////////////////////////////////////////////////////////
    // Initialize joysticks
    ////////////////////////////////////////////////////////////
    std::shared_ptr<Joystick> driverXbox(new Joystick(0));
    std::shared_ptr<Joystick> operatorXbox(new Joystick(1));

    std::shared_ptr<IOperatorJoystick> operatorJoystick(new SnobotOperatorJoystick(operatorXbox));
    std::shared_ptr<IDriverJoystick> driverJoystick(new SnobotDriverJoystick(driverXbox, logger));

    ////////////////////////////////////////////////////////////
    // Initialize subsystems
    ////////////////////////////////////////////////////////////

    bool useCan = true;


    // Drive Train
    if (useCan)
    {
        std::shared_ptr<CANTalon> driveLeftMotorA(new CANTalon(0));
        std::shared_ptr<CANTalon> driveLeftMotorB(new CANTalon(1));
        driveLeftMotorA->SetFeedbackDevice(CANTalon::FeedbackDevice::QuadEncoder);
        driveLeftMotorB->SetTalonControlMode(CANTalon::TalonControlMode::kFollowerMode);
        driveLeftMotorB->Set(driveLeftMotorA->GetDeviceID());

        std::shared_ptr<CANTalon> driveRightMotorA(new CANTalon(2));
        std::shared_ptr<CANTalon> driveRightMotorB(new CANTalon(3));
        driveRightMotorA->SetFeedbackDevice(CANTalon::FeedbackDevice::QuadEncoder);
        driveRightMotorB->SetTalonControlMode(CANTalon::TalonControlMode::kFollowerMode);
        driveRightMotorB->Set(driveRightMotorA->GetDeviceID());

        mDrivetrain = std::shared_ptr<IDrivetrain>(new SnobotCanDrivetrain(driveLeftMotorA, driveRightMotorB, driverJoystick));
    }
    else
    {
        std::shared_ptr<SpeedController> driveLeftMotor(new Talon(0));
        std::shared_ptr<SpeedController> driveRightMotor(new Talon(1));
        std::shared_ptr<Encoder> driveLeftEncoder(new Encoder(0, 1));
        std::shared_ptr<Encoder> driveRightEncoder(new Encoder(2, 3));
        mDrivetrain = std::shared_ptr<IDrivetrain>(new SnobotDrivetrain(driveLeftMotor, driveRightMotor, driverJoystick, driveLeftEncoder, driveRightEncoder));
    }
    addModule(mDrivetrain);

    // Gearboss
    std::shared_ptr<DoubleSolenoid> gearSolenoid(new DoubleSolenoid(0, 1));
    mGearBoss = std::shared_ptr<SnobotGearBoss>(new SnobotGearBoss(gearSolenoid, operatorJoystick, logger));
    addModule(mGearBoss);

    // Climber
    std::shared_ptr<SpeedController> climberMotor(new Talon(2));
    mClimber = std::shared_ptr<SnobotClimber>(new SnobotClimber(climberMotor, operatorJoystick, logger));
    addModule(mClimber);

    // Positioner
    std::shared_ptr<Gyro> gyro(new AnalogGyro(0));
    mPositioner = std::shared_ptr<IPositioner>(new SnobotPositioner(mDrivetrain, gyro, logger));
    addModule(mPositioner);
}


<<<<<<< HEAD
}
>>>>>>> b5fb176... Adding a CPP project to test the simulator with
=======
>>>>>>> 9fafcd1... Adding a port of the robot code in CPP

START_ROBOT_CLASS(Robot)
