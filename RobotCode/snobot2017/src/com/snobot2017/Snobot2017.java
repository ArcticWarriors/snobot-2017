package com.snobot2017;

import java.text.SimpleDateFormat;

import com.snobot.xlib.ASnobot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;

public class Snobot2017 extends ASnobot
{


    public Snobot2017()
    {
        super(new SimpleDateFormat("yyyyMMdd_hhmmssSSS"), Properties2017.sLOG_COUNT.getValue(), Properties2017.sLOG_FILE_PATH.getValue());

        // Drivetrain
        Encoder rightDriveEncoder;
        Encoder leftDriveEncoder;
        SpeedController rightDriveMotor;
        SpeedController leftDriveMotor;
    }
}
