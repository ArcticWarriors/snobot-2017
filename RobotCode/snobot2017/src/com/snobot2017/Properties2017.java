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

    // Drivetrain
    public static final DoubleProperty sLEFT_ENCODER_DIST_PER_PULSE = new DoubleProperty("DriveEncoderLeftDPP", -0.00564998);
    public static final DoubleProperty sRIGHT_ENCODER_DIST_PER_PULSE = new DoubleProperty("DriveEncoderRightDPP", 0.00564998);

    // Autonomous
    public static final StringProperty sAUTON_DIRECTORY;

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
            resourcesDir = "/home/lvuser/2016Resources/";

            System.out.println("Using tactical constants");
        }

        sAUTO_LOG_FILE_PATH = new StringProperty("AutoLogFilePath", autoLogPath);

        sAUTON_DIRECTORY = new StringProperty("AutonDir", resourcesDir + "autonomous/");
    }
}
