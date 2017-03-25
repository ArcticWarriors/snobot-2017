package com.snobot2017;

import com.snobot.lib.PropertyManager.BooleanProperty;
import com.snobot.lib.PropertyManager.DoubleProperty;
import com.snobot.lib.PropertyManager.IntegerProperty;
import com.snobot.lib.PropertyManager.StringProperty;

import edu.wpi.first.wpilibj.RobotBase;

public class Properties2017
{
    public static final boolean sKILL_OLD_ADBS = false;

    // **************************************************************
    // Configuration Constants
    // **************************************************************

    // Vision
    public static final StringProperty sADB_LOCATION;
    public static final IntegerProperty sVISION_COMMS_PORT = new IntegerProperty("VisionCommsPort", 8254);
    public static final BooleanProperty sENABLE_VISION = new BooleanProperty("EnableVision", true);
    public static final DoubleProperty sCAMERA_LENS_DIST_FROM_CENTER = new DoubleProperty("CameraLensOfset", 12); // inches
    public static final DoubleProperty sVISION_TOO_CLOSE_DISTANCE = new DoubleProperty("VisionTooCLose", 10);
    public static final DoubleProperty sVISION_TOO_FAR_DISTANCE = new DoubleProperty("VisionTooFar", 60);
    
    // Logger
    public static final IntegerProperty sLOG_COUNT = new IntegerProperty("LogCount", 25);
    public static final StringProperty sLOG_FILE_PATH;

    // AutoLogger
    public static final StringProperty sAUTO_LOG_FILE_PATH;
    public static final StringProperty sREPLAY_PATH;
    
    // Snobot Actor
    public static final DoubleProperty sDRIVE_TO_POSITION_DISTANCE_KP = new DoubleProperty("SnobotActorDriveToPositionDistanceKp", .1);
    public static final DoubleProperty sDRIVE_TO_POSITION_ANGLE_KP = new DoubleProperty("SnobotActorDriveToPositionAngleKp", 0.2);
    public static final DoubleProperty sSNOBOT_FUDGE_FACTOR = new DoubleProperty("ActorFudgeFactor", 12);

    // Drivetrain
    public static final DoubleProperty sLEFT_ENCODER_DIST_PER_PULSE = new DoubleProperty("DriveEncoderLeftDPP", -0.00564998);
    public static final DoubleProperty sRIGHT_ENCODER_DIST_PER_PULSE = new DoubleProperty("DriveEncoderRightDPP", 0.00564998);

    // Autonomous
    public static final StringProperty sAUTON_DIRECTORY;
    public static final StringProperty sAUTON_PATH_DIRECTORY;
    public static final StringProperty sAUTON_FILE_FILTER = new StringProperty("AutonFileFilter", "");
    public static final DoubleProperty sTRAJECTORY_OVERRIDE_PERCENTAGE = new DoubleProperty("TrajectoryOverridePercentage", .7);

    // Light Manager
    public static final IntegerProperty sFLASH_LIGHT_LOOPS = new IntegerProperty("LightFlashLoops", 2);

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
    public static final DoubleProperty sSPLINE_TURN_FACTOR = new DoubleProperty("SplineTurnFactor", 0.1);

    // Climbing
    public static final DoubleProperty sROBOT_CATCHING_ROPE_SPEED = new DoubleProperty("RopeCatchingSpeed", 0.5);
    public static final DoubleProperty sROBOT_CLIMBING_ROPE_SPEED = new DoubleProperty("RopeClimbingSpeed", 1.0);

    // Fuel Pooper
    public static final DoubleProperty sSPCHINCTER_RIGHT_OPEN = new DoubleProperty("SphincterRightOpen", 1.0);
    public static final DoubleProperty sSPCHINCTER_LEFT_OPEN = new DoubleProperty("SphincterLeftOpen", 1.0);
    public static final DoubleProperty sSPCHINCTER_RIGHT_CLOSED = new DoubleProperty("SphincterRightClosed", 0.0);
    public static final DoubleProperty sSPCHINCTER_LEFT_CLOSED = new DoubleProperty("SphincterLeftClosed", 0.0);
    public static final DoubleProperty sDRIVE_PATH_WITH_GYRO_KP = new DoubleProperty("DrivePathWithGyroKP", 0.01);
    public static final DoubleProperty sSIDE_AUTO_TURN_SPEED = new DoubleProperty("SideAutoTurnSpeed", 0.4);

    static
    {
        String logPath;
        String adbLocation;
        String resourcesDir;
        String replayPath;
        String autoLogPath;

        if (RobotBase.isSimulation())
        {
            logPath = "logs/" + Properties2017.class.getCanonicalName() + "/";
            adbLocation = System.getProperty("user.home") + "/AppData/Local/Android/sdk/platform-tools/adb.exe";
            resourcesDir = "../../RobotCode/snobot2017/resources/";
            autoLogPath = "autologs/" + Properties2017.class.getCanonicalName() + "/";
            replayPath = resourcesDir + "replays/";

            System.out.println("Using simulation constants");
        }
        else
        {
            logPath = "/u/logs/";
            adbLocation = "/tmp/adb";
            resourcesDir = "/home/lvuser/2017Resources/";
            autoLogPath = "/u/autologs/";
            replayPath = resourcesDir + "replays/";

            System.out.println("Using tactical constants");
        }

        sLOG_FILE_PATH = new StringProperty("LogFilePath", logPath);
        sREPLAY_PATH = new StringProperty("ReplayPath", replayPath);
        sADB_LOCATION = new StringProperty("AdbLocation", adbLocation);

        sAUTO_LOG_FILE_PATH = new StringProperty("AutoLogFilePath", autoLogPath);

        sAUTON_DIRECTORY = new StringProperty("AutonDir", resourcesDir + "autonomous/");
        sAUTON_PATH_DIRECTORY = new StringProperty("AutonDirPaths", resourcesDir + "traj");
    }
}
