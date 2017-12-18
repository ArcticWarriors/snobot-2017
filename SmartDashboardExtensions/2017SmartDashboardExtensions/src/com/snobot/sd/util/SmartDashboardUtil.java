package com.snobot.sd.util;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

public class SmartDashboardUtil
{
    private static final NetworkTable sTABLE = NetworkTableInstance.getDefault().getTable("SmartDashboard");

    public static NetworkTable getTable()
    {
        return sTABLE;
    }
}
