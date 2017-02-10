package com.snobot2017.SnobotActor;

import com.snobot.lib.ISubsystem;

public interface ISnobotActor extends ISubsystem
{
    boolean turnToAngle(double aAngle, double aSpeed);
    boolean driveDistance();

    void setGoal(double aDesiredDistance, double aGoalSpeed);
}
