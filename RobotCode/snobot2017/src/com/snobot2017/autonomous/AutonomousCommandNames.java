package com.snobot2017.autonomous;

public class AutonomousCommandNames
{

    // Generic
    public static final String sPARALLEL_COMMAND = "Parallel";
    public static final String sWAIT_COMMAND = "Wait";

    // Subsystems
    public static final String sPLACE_GEAR_COMMAND = "PlaceGear";
    public static final String sRAISE_GEAR_COMMAND = "RaiseGear";

    // Stupid
    public static final String sSTUPID_DRIVE_STRAIGHT_COMMAND = "StupidDriveStraight";
    public static final String sSTUPID_TURN_COMMAND = "StupidTurn";
    public static final String sREPLAY = "Replay";

    // Smarter
    public static final String sTURN_TO_ANGLE = "TurnToAngle";
    public static final String sDRIVE_STRAIGHT_A_DISTANCE = "DriveStraightADistance";
    public static final String sGO_TO_POSITION_SMOOTH_IN_STEPS = "GoToPositionInSteps";
    public static final String sGO_TO_POSITION_SMOOTHLY = "GoToPositionSmoothly";
    public static final String sDRIVE_TO_PEG_USING_VISION = "DriveToPegUsingVision";

    // Path + Trajectory
    public static final String sDRIVE_PATH_STRAIGHT = "DrivePathStraight";
    public static final String sDRIVE_PATH_TURN = "DrivePathTurn";
    public static final String sDRIVE_TRAJECTORY = "DriveTrajectory";
    public static final String sDRIVE_STRAIGHT_PATH_WITH_GYRO = "DriveStraightPathWithGyro";

    // StartingPosition based commands
    public static final String sTRAJ_START_TO_GEAR = "TrajStartToGear";
    public static final String sTRAJ_START_TO_HOPPER = "TrajStartToHopper";
    public static final String sTRAJ_GEAR_TO_HOPPER = "TrajGearToHopper";
    public static final String sTRAJ_GEAR_TO_FUEL = "TrajGearToFuel";
    public static final String sTURN_TO_PEG_AFTER_PATH_FROM_STARTING_POSITION = "TurnToPegAfterPathFromStartingPosition";
    public static final String sDRIVE_STRAIGHT_PATH_WITH_GYRO_FROM_STARTING_POSITION = "DriveStraightPathWithGyroFromStartingPosition";

}
