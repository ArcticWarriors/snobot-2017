
package com.snobot2017;

public class PortMappings2017
{
    public static final boolean sUSE_CAN_DRIVETRAIN = false;

    // Joysticks
    public static final int sDRIVER_JOYSTICK_PORT = 0;

    // CAN
    public static final int sDRIVE_CAN_LEFT_A_PORT = 3;
    public static final int sDRIVE_CAN_LEFT_B_PORT = 4;
    public static final int sDRIVE_CAN_RIGHT_A_PORT = 1;
    public static final int sDRIVE_CAN_RIGHT_B_PORT = 2;

    // PWM
    public static final int sDRIVE_PWM_LEFT_A_PORT = 1;
    public static final int sDRIVE_PWM_RIGHT_A_PORT = 0;
    public static final int sCLIMB_PWM_PORT = 5;

    // Digital
    public static final int sLEFT_DRIVE_ENCODER_PORT_A = 6;
    public static final int sLEFT_DRIVE_ENCODER_PORT_B = 7;
    public static final int sRIGHT_DRIVE_ENCODER_PORT_A = 8;
    public static final int sRIGHT_DRIVE_ENCODER_PORT_B = 9;

    // Solenoid
    public static final int sGEARBOSS_SOLENOID_CHANNEL_A = 0;
    public static final int sGEARBOSS_SOLENOID_CHANNEL_B = 1;

    // Relays
    public static final int sRELAY_GREEN_LED = 0;
    public static final int sRELAY_BLUE_LED = 1;

    //////////////////////////////
    // Vision
    //////////////////////////////
    /**
     * The port that the app sends data (heartbeats, etc) to
     */
     public static final int sADB_BIND_PORT = 8254;

    /**
     * The port in the app that the MJPEG server binds to
     */
    public static final int sAPP_MJPEG_PORT = 5800;

    /**
     * This forwards data coming from the apps MJPEG server to this port
     */
    public static final int sAPP_MJPEG_FORWARDED_PORT = 12000;
}
