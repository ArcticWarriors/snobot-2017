package com.snobot.simulator.joysticks.joystick_specializations;

import java.util.ArrayList;

import com.snobot.simulator.joysticks.BaseJoystick;

import net.java.games.input.Component;
import net.java.games.input.Controller;

public class GenericGamepadJoystick extends BaseJoystick
{
    public GenericGamepadJoystick(Controller aController)
    {
        super("Generic Gamepad", aController, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        if (mController != null)
        {
            Component[] components = mController.getComponents();

            for (int j = 0; j < components.length; j++)
            {
                if (components[j].isAnalog())
                {
                    mAxis.add(components[j].getIdentifier());
                }
                else
                {
                    mButtons.add(components[j].getIdentifier());
                }
            }
            mAxisValues = new short[mAxis.size()];
            mPovValues = new short[mPOV.size()];
        }
    }
}
