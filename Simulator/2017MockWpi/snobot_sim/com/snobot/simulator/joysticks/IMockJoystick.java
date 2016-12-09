package com.snobot.simulator.joysticks;

public interface IMockJoystick
{

    int getAxisCount();

    int getButtonCount();

    void setRumble(short s);

    short[] getAxisValues();

    short[] getPovValues();

    int getButtonMask();

    boolean getRawButton(int aIndex);

    double getRawAxis(int aIndex);

    String getName();

}
