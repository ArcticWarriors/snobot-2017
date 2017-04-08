/*
 * SmartDashboardNames.cpp
 *
 *  Created on: May 19, 2017
 *  Author: preiniger
 */

#include "SmartDashboardNames.h"

namespace SmartDashboardNames
{


// Auton Widget stuff
const std::string sROBOT_COMMAND_TEXT = "Robot Command text";
const std::string sSUCCESFULLY_PARSED_AUTON = "Parsed Command";
const std::string sAUTON_TABLE_NAME = "AutonTable";
const std::string sAUTON_CHOOSER = "Autonomous Selection";
const std::string sAUTON_FILENAME = "Auton Filename";
const std::string sSAVE_AUTON = "Save Auton";
const std::string sAUTON_NUM = "Auton Number";

// Climbing
const std::string sROBOT_ROPE_MOTOR_SPEED = "Rope Motor Speed";

// GearBoss
const std::string sGEAR_BOSS_SOLENOID = "Gear Boss Up";

// FlightSticks Joystick
const std::string sRIGHT_JOYSTICK_SPEED = "Right Joystick Speed";
const std::string sLEFT_JOYSTICK_SPEED = "Left Joystick Speed";

// Xbax Joystick
const std::string sLEFT_XBAX_JOYSTICK_SPEED = "Left Xbax Joystick Speed";
const std::string sRIGHT_XBAX_JOYSTICK_SPEED = "Right Xbax Joystick Speed";

// Operator Joystick
const std::string sOPERATOR_JOYSTICK_IS_CLIMBING = "OpJoy Climbing";
const std::string sOPERATOR_JOYSTICK_IS_CATCHING = "OpJoy Catching";
const std::string sOPERATOR_JOYSTICK_GREEN_LIGHT_BTN = "Green Light On";
const std::string sOPERATOR_JOYSTICK_BLUE_LIGHT_BTN = "Blue Light On";

// Drivetrain
const std::string sLEFT_DRIVE_ENCODER_DISTANCE = "Left Drive Motor Encoder";
const std::string sRIGHT_DRIVE_ENCODER_DISTANCE = "Right Drive Motor Encoder";
const std::string sLEFT_DRIVE_ENCODER_RAW = "Left Encoder Raw";
const std::string sRIGHT_DRIVE_ENCODER_RAW = "Right Encoder Raw";
const std::string sLEFT_DRIVE_MOTOR_SPEED = "Left Motor Speed";
const std::string sRIGHT_DRIVE_MOTOR_SPEED = "Right Motor Speed";

// Positioner
const std::string sX_POSITION = "X Position";
const std::string sY_POSITION = "Y Position";
const std::string sORIENTATION = "Orientation";
const std::string sSPEED = "Speed";

// Camera
const std::string sVISION_APP_CONNECTED = "App Connected";
const std::string sVISION_TARGETS = "Targets";

// Path Plotting
const std::string sPATH_NAMESPACE = "PathPlotting"; // Namespace that driving motion profiling paths will go into
const std::string sPATH_IDEAL_POINTS = "PlanedPath"; // The name for the planned path (the auto-generated one)
const std::string sPATH_POINT = "PathPoint"; // The name for the real point for a path motion profile
const std::string sPOSITION_CHOOSER = "Position Chooser"; // Chooses the starting position for the red and blue side

// Spline Plotting
const std::string sSPLINE_NAMESPACE = "SplinePlotting"; // Namespace that driving motion  profiling splines will go into
const std::string sSPLINE_IDEAL_POINTS = "PlanedSpline"; // The name for the planned splines (the auto-generated one)
const std::string sSPLINE_REAL_POINT = "SplinePoint"; // The name for the real point for a spline motion profile

// Snobot Actor names
const std::string sSNOBOT_ACTION = "SnobotAction"; // The name of the current snobotActor action
const std::string sIN_ACTION = "InAction";
const std::string sSNOBOT_ACTION_NAME = "SnobotActionName";

// Sphincter
const std::string sSPCHINCTER_OPEN = "SphincterOpen";
const std::string sSPHINCTER_DOWN = "SphincterClose";
}
