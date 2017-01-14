package com.snobot2016;

import com.snobot.xlib.PropertyManager.BooleanProperty;
import com.snobot.xlib.PropertyManager.DoubleProperty;
import com.snobot.xlib.PropertyManager.IntegerProperty;
import com.snobot.xlib.PropertyManager.StringProperty;

import edu.wpi.first.wpilibj.RobotBase;

public class Properties2016
{
    public static final boolean sIS_REAL_ROBOT = true;
    public static final boolean sUSE_SPI_GYRO = true;
    public static final boolean sUSE_IMU_POSITIONER = true;

    // **************************************************************
    // Port Mappings
    // **************************************************************

    // Joysticks
    public static final int sDRIVER_JOYSTICK_PORT = 0;
    public static final int sOPERATOR_JOYSTICK_PORT = 1;
    public static final int sDRIVER_JOYSTICK_PORT2 = 2;

    // Speed Controller
    public static final int sDRIVER_LEFT_MOTOR_PORT = 5;
    public static final int sDRIVER_RIGHT_MOTOR_PORT = 6;
    public static final int sSCALE_TILT_MOTOR_PORT = 3;
    public static final int sSCALE_MOVE_MOTOR_PORT = 4;
    public static final int sHARVESTER_ROLLER_MOTOR_PORT = 2;
    public static final int sHARVESTER_PIVOT_MOTOR_PORT = 1;

    // CAN
    public static final int sDRIVE_CAN_LEFT_A_PORT = 2;
    public static final int sDRIVE_CAN_LEFT_B_PORT = 1;
    public static final int sDRIVE_CAN_RIGHT_A_PORT = 3;
    public static final int sDRIVE_CAN_RIGHT_B_PORT = 4;

    // Digital
    public static final int sLEFT_DRIVE_ENCODER_PORT_A = 0;
    public static final int sLEFT_DRIVE_ENCODER_PORT_B = 1;
    public static final int sRIGHT_DRIVE_ENCODER_PORT_A = 2;
    public static final int sRIGHT_DRIVE_ENCODER_PORT_B = 3;
    // public static final int sULTRASONIC_PORT = 4;

    // Analog
    public static final int sGYRO_SENSOR_PORT = 0; // Probably not used...
    public static final int sHARVESTER_POT_PORT = 3;
    public static final int sSCALE_POT_PORT = 1;
    public static final int sEXTENSION_POT_PORT = 2;

    // Relay
    public static final int sLIGHT_RELAY = 1;

    // **************************************************************
    // Configuration Contants
    // **************************************************************

    // Camera
    public static final StringProperty sCAMERA_HOST_IP = new StringProperty("CameraHostIP", "10.1.74.11");
    public static final BooleanProperty sENABLE_CAMERA;

    // Autonomous
    public static final StringProperty sAUTON_DIRECTORY;
    public static final StringProperty sAUTON_DEFENSE_DIRECTORY;
    public static final StringProperty sAUTON_POST_DEFENSE_DIRECTORY;
    public static final StringProperty sAUTON_PATH_DIRECTORY;
    public static final DoubleProperty sAUTON_SETUP_OVERHANG = new DoubleProperty("AutonSetupOverhang", 2);

    // Autonomous modes
    public static final String sSTUPID_DRIVE_STRAIGHT = "StupidDriveStraight";
    public static final String sDRIVE_STRAIGHT_A_DISTANCE = "DriveStraightADistance";
    public static final String sSTUPID_TURN = "StupidTurn";
    public static final String sTURN_WITH_DEGREES = "TurnWithDegrees";
    public static final String sGO_TO_XY = "GoToXY";
    public static final String sRAISE_HARVESTER = "RaiseHarvester";
    public static final String sLOWER_HARVESTER = "LowerHarvester";
    public static final String sROLLER_INTAKE = "RollerIntake";
    public static final String sROLLER_OUTTAKE = "RollerOuttake";
    public static final String sTILT_LOWER_SCALER = "TiltLowerScaler";
    public static final String sTILT_RAISE_SCALER = "TiltRaiseScaler";
    public static final String sDRIVE_STRAIGHT_PATH = "DriveStraightPath";
    public static final String sDRIVE_TURN_PATH = "DriveTurnPath";
    public static final String sDRIVE_TRAJECTORY = "Trajectory";
    public static final String sSMART_HARVESTOR = "SmartHarvester";
    public static final String sFUDGE_THE_POSITION = "FudgeThePosition";
    public static final String sGO_TO_XY_PATH = "GoToXYPath";
    public static final String sGO_TO_LOW_GOAL = "GoToLowGoal";
    public static final String sSMART_HARVESTER = "SmartHarvester";
    public static final String sSUPER_SMART_HARVESTER = "SuperSmartHarvester";
    public static final String sSMART_SCALER = "SmartScaler";
    public static final String sCROSS_DEFENSE = "CrossDefense";
    public static final String sGO_TO_LOW_GOAL_TRAJ = "GoToLowGoalTrajectory";

    // Logger
    public static final IntegerProperty sLOG_COUNT = new IntegerProperty("LogCount", 25);
    public static final StringProperty sLOG_FILE_PATH;

    // Drive path
    public static final DoubleProperty sDRIVE_PATH_KP = new DoubleProperty("DrivePathKP", 0.001);
    public static final DoubleProperty sDRIVE_PATH_KD = new DoubleProperty("DrivePathKD", 0);
    public static final DoubleProperty sDRIVE_PATH_KV = new DoubleProperty("DrivePathKVel", 0.0063);
    public static final DoubleProperty sDRIVE_PATH_KA = new DoubleProperty("DrivePathKAccel", 0.002);

    // Turn Path
    public static final DoubleProperty sTURN_PATH_KP = new DoubleProperty("TurnPathKP", 0.005);
    public static final DoubleProperty sTURN_PATH_KD = new DoubleProperty("TurnPathKD", 0);
    public static final DoubleProperty sTURN_PATH_KV = new DoubleProperty("TurnPathKVel", 0.0053);
    public static final DoubleProperty sTURN_PATH_KA = new DoubleProperty("TurnPathKAccel", 0);

    // Trajectory Driving
    public static final DoubleProperty sSPLINE_TURN_FACTOR = new DoubleProperty("SplineTurnFactor", 0.001);

    // Scaler
    public static final DoubleProperty sSCALE_HIGH_ANGLE = new DoubleProperty("ScaleTiltMaxAngle", 110);
    public static final DoubleProperty sSCALE_LOW_ANGLE = new DoubleProperty("ScaleTiltMinAngle", 0);
    public static final DoubleProperty sSCALE_HIGH_VOLTAGE = new DoubleProperty("ScalePotMaxVoltage", 3.0);
    public static final DoubleProperty sSCALE_LOW_VOLTAGE = new DoubleProperty("ScalePotMinVoltage", 1.0);

    public static final DoubleProperty sSCALE_GROUND_ANGLE = new DoubleProperty("ScaleAngleGroundPos", 0);
    public static final DoubleProperty sGET_OUT_OF_THE_WAY_OF_INTAKE = new DoubleProperty("ScaleAngleMoveforIntakePos", 30);
    public static final DoubleProperty sVERTICAL = new DoubleProperty("ScaleAngleVerticalPos", 90);
    public static final DoubleProperty sHOOK_ANGLE = new DoubleProperty("ScaleAngleHookPos", 110);
    public static final DoubleProperty sK_P_SCALE_TILT_ANGLE = new DoubleProperty("ScaleAngleKP", 0.005);

    public static final DoubleProperty sMIN_SCALE_EXTENSION_POT_VOLTAGE = new DoubleProperty("ScaleExtensionPotMinVoltage", 0.78);
    public static final DoubleProperty sMAX_SCALE_EXTENSION_POT_VOLTAGE = new DoubleProperty("ScaleExtensionPotMaxVoltage", 2.19);

    public static final DoubleProperty sLOW_GOAL_Y = new DoubleProperty("LowGoalY", 305);
    public static final DoubleProperty sLOW_GOAL_X = new DoubleProperty("LowGoalX", 70);
    // public static final DoubleProperty sGOAL_SCALE_HEIGHT_IN_INCHES = new
    // DoubleProperty("ScaleGoalSonicHeighInches", 35);
    // Harvester
    public static final DoubleProperty sMIN_HARVESTER_POT_VOLTAGE = new DoubleProperty("HarvesterPotMinVoltage", 1);
    public static final DoubleProperty sMAX_HARVESTER_POT_VOLTAGE = new DoubleProperty("HarvesterPotMaxVoltage", 4);
    public static final DoubleProperty sHARVESTER_POT_KP = new DoubleProperty("HarvestPotKP", .005);

    public static final DoubleProperty sLEFT_ENCODER_DIST_PER_PULSE = new DoubleProperty("DriveEncoderLeftDPP", -0.00564998);
    public static final DoubleProperty sRIGHT_ENCODER_DIST_PER_PULSE = new DoubleProperty("DriveEncoderRightDPP", 0.00564998);

    static
    {
        String logPath;
        String resourcesDir;
        boolean enableCamera;

        if (RobotBase.isSimulation())
        {
            logPath = "logs/";
            resourcesDir = "../../RobotCode/snobot2016/resources/";
            enableCamera = false;

            System.out.println("Using simulation constants");
        }
        else
        {
            logPath = "/u/logs/";
            resourcesDir = "/home/lvuser/2016Resources/";
            enableCamera = true;

            System.out.println("Using tactical constants");

        }

        sENABLE_CAMERA = new BooleanProperty("CameraEnable", enableCamera);
        sLOG_FILE_PATH = new StringProperty("LogFilePath", logPath);

        sAUTON_PATH_DIRECTORY = new StringProperty("AutonDirPaths", resourcesDir + "traj");
        sAUTON_DIRECTORY = new StringProperty("AutonDir", resourcesDir + "Autonomous/");
        sAUTON_DEFENSE_DIRECTORY = new StringProperty("AutonDirDefenses", sAUTON_DIRECTORY.getValue() + "RealAutonomousModes/DefenseAutons");
        sAUTON_POST_DEFENSE_DIRECTORY = new StringProperty("AutonDirThingsToDo", sAUTON_DIRECTORY.getValue()
                + "RealAutonomousModes/AutonomousThingsToDo");
    }

}
