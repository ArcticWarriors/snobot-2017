package com.snobot2017.autonomous;

public class AutonomousCommandNames
{

    // Generic
    public static final String sPARALLEL_COMMAND = "Parallel";
    public static final String sWAIT_COMMAND = "Wait";

    // Subsystems
    public static final String sPLACE_GEAR_COMMAND = "PlaceGear";

    // Stupid
    public static final String sSTUPID_DRIVE_STRAIGHT_COMMAND = "StupidDriveStraight";
    public static final String sAUTON_COPY = "AutoCopy";
    public static final String sREPLAY = "Replay";

    // Smarter
    public static final String sTURN_WITH_DEGREES = "TurnWithDegrees";
    public static final String sDRIVE_STRAIGHT_A_DISTANCE = "DriveStraightADistance";
    public static final String sGO_TO_POSITION_SMOOTH_IN_STEPS = "GoToPositionInSteps";

    // Path + Trajectory
    public static final String sDRIVE_PATH_STRAIGHT = "DrivePathStraight";
    public static final String sDRIVE_PATH_TURN = "DrivePathTurn";
    public static final String sDRIVE_TRAJECTORY = "DriveTrajectory";
    public static final String sGO_TO_POSITION_SMOOTHLY = "GoToPositionSmoothly";

    // Combined modes
    public static final String sDUMP_HOPPER = "DumpHopper";

    public static final String sSCORE_GEAR = "ScoreGear";
    public static final String sSCORE_GEAR_DUMP_HOPPER = "ScoreGearDumpHopper";
    public static final String sSCORE_GEAR_SCORE_FUEL = "ScoreGearScoreFuel";

    public static final String sSCORE_GEAR_WITH_CAM = "ScoreGearWithCamera";
    public static final String sSCORE_GEAR_WITH_CAM_DUMP_HOPPER = "ScoreGearWithCameraDumpHopper";
    public static final String sSCORE_GEAR_WITH_CAM_SCORE_FUEL = "ScoreGearWithCameraScoreFuel";
}
