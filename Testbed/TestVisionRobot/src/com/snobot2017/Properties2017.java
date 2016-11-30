package com.snobot2017;

import com.snobot.xlib.PropertyManager.DoubleProperty;
import com.snobot.xlib.PropertyManager.IntegerProperty;
import com.snobot.xlib.PropertyManager.StringProperty;

import edu.wpi.first.wpilibj.RobotBase;

public class Properties2017
{
    // **************************************************************
    // Port Mappings
    // **************************************************************

    // Joysticks
    public static final int sDRIVER_JOYSTICK_PORT = 0;

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

    // **************************************************************
    // Configuration Contants
    // **************************************************************

    // Logger
    public static final IntegerProperty sLOG_COUNT = new IntegerProperty("LogCount", 25);
    public static final StringProperty sLOG_FILE_PATH;

    // Drivetrain
    public static final DoubleProperty sLEFT_ENCODER_DIST_PER_PULSE = new DoubleProperty("DriveEncoderLeftDPP", -0.00564998);
    public static final DoubleProperty sRIGHT_ENCODER_DIST_PER_PULSE = new DoubleProperty("DriveEncoderRightDPP", 0.00564998);

    static
    {
        String logPath;

        if (RobotBase.isSimulation())
        {
            logPath = "logs/";
            System.out.println("Using simulation constants");
        }
        else
        {
            logPath = "/u/logs/";
            System.out.println("Using tactical constants");

        }

        sLOG_FILE_PATH = new StringProperty("LogFilePath", logPath);
    }
}
