/*
 * SmartDashboardNames.h
 *
 *  Created on: May 19, 2017
 *      Author: preiniger
 */

#ifndef SRC_SMARTDASHBOARDNAMES_H_
#define SRC_SMARTDASHBOARDNAMES_H_

#include <string>

namespace SmartDashboardNames
{
	

// Auton Widget stuff
extern const std::string sROBOT_COMMAND_TEXT;
extern const std::string sSUCCESFULLY_PARSED_AUTON;
extern const std::string sAUTON_TABLE_NAME;
extern const std::string sAUTON_CHOOSER;
extern const std::string sAUTON_FILENAME;
extern const std::string sSAVE_AUTON;
extern const std::string sAUTON_NUM;

// Climbing
extern const std::string sROBOT_ROPE_MOTOR_SPEED;

// GearBoss
extern const std::string sGEAR_BOSS_SOLENOID;

// FlightSticks Joystick
extern const std::string sRIGHT_JOYSTICK_SPEED;
extern const std::string sLEFT_JOYSTICK_SPEED;

// Xbax Joystick
extern const std::string sLEFT_XBAX_JOYSTICK_SPEED;
extern const std::string sRIGHT_XBAX_JOYSTICK_SPEED;

// Operator Joystick
extern const std::string sOPERATOR_JOYSTICK_IS_CLIMBING;
extern const std::string sOPERATOR_JOYSTICK_IS_CATCHING;
extern const std::string sOPERATOR_JOYSTICK_GREEN_LIGHT_BTN;
extern const std::string sOPERATOR_JOYSTICK_BLUE_LIGHT_BTN;

// Drivetrain
extern const std::string sLEFT_DRIVE_ENCODER_DISTANCE;
extern const std::string sRIGHT_DRIVE_ENCODER_DISTANCE;
extern const std::string sLEFT_DRIVE_ENCODER_RAW;
extern const std::string sRIGHT_DRIVE_ENCODER_RAW;
extern const std::string sLEFT_DRIVE_MOTOR_SPEED;
extern const std::string sRIGHT_DRIVE_MOTOR_SPEED;

// Positioner
extern const std::string sX_POSITION;
extern const std::string sY_POSITION;
extern const std::string sORIENTATION;
extern const std::string sSPEED;

// Camera
extern const std::string sVISION_APP_CONNECTED;
extern const std::string sVISION_TARGETS;

// Path Plotting
extern const std::string sPATH_NAMESPACE;
extern const std::string sPATH_IDEAL_POINTS;
extern const std::string sPATH_POINT;
extern const std::string sPOSITION_CHOOSER;

// Spline Plotting
extern const std::string sSPLINE_NAMESPACE;
extern const std::string sSPLINE_IDEAL_POINTS;
extern const std::string sSPLINE_REAL_POINT;

// Snobot Actor names
extern const std::string sSNOBOT_ACTION;
extern const std::string sIN_ACTION;
extern const std::string sSNOBOT_ACTION_NAME;
}

#endif /* SRC_SMARTDASHBOARDNAMES_H_ */
