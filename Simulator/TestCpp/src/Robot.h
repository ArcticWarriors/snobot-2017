/*
 * Robot.h
 *
 *  Created on: May 10, 2017
 *      Author: PJ
 */

#ifndef SRC_ROBOT_H_
#define SRC_ROBOT_H_

<<<<<<< HEAD
<<<<<<< HEAD

#include "Drivetrain/IDrivetrain.h"
#include "Gearboss/IGearBoss.h"
#include "Climber/IClimber.h"
#include "Positioner/IPositioner.h"

#include "SnobotLib/ASnobot.h"

class Robot: public ASnobot
=======
#include <IterativeRobot.h>
#include "SpeedController.h"
#include "Joystick.h"

class Robot: public frc::IterativeRobot
>>>>>>> b5fb176... Adding a CPP project to test the simulator with
=======

#include "Drivetrain/IDrivetrain.h"
#include "Gearboss/IGearBoss.h"
#include "Climber/IClimber.h"
#include "Positioner/IPositioner.h"

#include "SnobotLib/ASnobot.h"

class Robot: public ASnobot
>>>>>>> 9fafcd1... Adding a port of the robot code in CPP
{
public:
    void RobotInit();

<<<<<<< HEAD
<<<<<<< HEAD
protected:

    std::shared_ptr<IDrivetrain> mDrivetrain;
    std::shared_ptr<IGearBoss> mGearBoss;
    std::shared_ptr<IClimber> mClimber;
    std::shared_ptr<IPositioner> mPositioner;
=======
    void AutonomousInit() override;

    void AutonomousPeriodic() override;

    void TeleopInit() override;

    void TeleopPeriodic() override;

    void TestPeriodic() override;
>>>>>>> b5fb176... Adding a CPP project to test the simulator with

=======
>>>>>>> 9fafcd1... Adding a port of the robot code in CPP
protected:

    std::shared_ptr<IDrivetrain> mDrivetrain;
    std::shared_ptr<IGearBoss> mGearBoss;
    std::shared_ptr<IClimber> mClimber;
    std::shared_ptr<IPositioner> mPositioner;

};

#endif /* SRC_ROBOT_H_ */
