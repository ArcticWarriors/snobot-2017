package com.snobot2017;

import com.snobot.lib.PropertyManager.BooleanProperty;
import com.snobot.lib.PropertyManager.DoubleProperty;
import com.snobot.lib.PropertyManager.IntegerProperty;
import com.snobot.lib.PropertyManager.StringProperty;

import edu.wpi.first.wpilibj.RobotBase;

public class Properties2017
{
    // **************************************************************
    // Configuration Constants
    // **************************************************************

    // Vision
    public static final StringProperty sADB_LOCATION;
    public static final IntegerProperty sVISION_COMMS_PORT = new IntegerProperty("VisionCommsPort", 8254);
    public static final BooleanProperty sENABLE_VISION = new BooleanProperty("EnableVision", true);

    // Logger
    public static final IntegerProperty sLOG_COUNT = new IntegerProperty("LogCount", 25);
    public static final StringProperty sLOG_FILE_PATH;

    // AutoLogger
    public static final IntegerProperty sAUTO_LOG_COUNT = new IntegerProperty("AutoLogCount", 25);
    public static final StringProperty sAUTO_LOG_FILE_PATH;
    public static final StringProperty sAUTO_LOG_RUN_PATH = new StringProperty("autologs/" + Properties2017.class.getCanonicalName() + "/");

    // Drivetrain
    public static final DoubleProperty sLEFT_ENCODER_DIST_PER_PULSE = new DoubleProperty("DriveEncoderLeftDPP", -0.00564998);
    public static final DoubleProperty sRIGHT_ENCODER_DIST_PER_PULSE = new DoubleProperty("DriveEncoderRightDPP", 0.00564998);

    // Autonomous
    public static final StringProperty sAUTON_DIRECTORY;
    public static final StringProperty sAUTON_PATH_DIRECTORY;
 
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
    
    static
    {
        String logPath;
        String adbLocation;
        String resourcesDir;

        if (RobotBase.isSimulation())
        {
            logPath = "logs/" + Properties2017.class.getCanonicalName() + "/";
            adbLocation = System.getProperty("user.home") + "/AppData/Local/Android/sdk/platform-tools/adb.exe";
            resourcesDir = "../../RobotCode/snobot2017/resources/";

            System.out.println("Using simulation constants");
        }
        else
        {
            logPath = "/u/logs/";
            adbLocation = "/tmp/adb";
            resourcesDir = "/home/lvuser/2016Resources/";

            System.out.println("Using tactical constants");
        }

        sLOG_FILE_PATH = new StringProperty("LogFilePath", logPath);
        sADB_LOCATION = new StringProperty("AdbLocation", adbLocation);

        String autoLogPath;

        if (RobotBase.isSimulation())
        {
            autoLogPath = "autologs/" + Properties2017.class.getCanonicalName() + "/";
            resourcesDir = "../../RobotCode/snobot2017/resources/";

            System.out.println("Using simulation constants");
        }
        else
        {
            autoLogPath = "/u/autologs/";
            resourcesDir = "/home/lvuser/2017Resources/";

            System.out.println("Using tactical constants");
        }

        sAUTO_LOG_FILE_PATH = new StringProperty("AutoLogFilePath", autoLogPath);

        sAUTON_DIRECTORY = new StringProperty("AutonDir", resourcesDir + "autonomous/");
        sAUTON_PATH_DIRECTORY = new StringProperty("AutonDirPaths", resourcesDir + "traj");
    }
}
