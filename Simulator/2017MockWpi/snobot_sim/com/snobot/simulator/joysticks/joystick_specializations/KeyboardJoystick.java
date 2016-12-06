package com.snobot.simulator.joysticks.joystick_specializations;

import java.util.Arrays;
import java.util.List;

import com.snobot.simulator.joysticks.BaseJoystick;

import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;
import net.java.games.input.ControllerEnvironment;

public class KeyboardJoystick extends BaseJoystick
{

    private final static List<Type> acceptableTypes = Arrays.asList(Type.KEYBOARD);

    public KeyboardJoystick()
    {
        super(acceptableTypes.toString());

        ControllerEnvironment ce = ControllerEnvironment.getDefaultEnvironment();

        Controller[] cs = ce.getControllers();
        for (int i = 0; i < cs.length; i++)
        {
            if (acceptableTypes.contains(cs[i].getType()))
            {
                mController = cs[i];
            }
        }

        if (mController != null)
        {

			mAxis.add(Identifier.Key.W);
			mAxis.add(Identifier.Key.A);
			mAxis.add(Identifier.Key.S);
			mAxis.add(Identifier.Key.D);
			mAxis.add(Identifier.Key.I);
			mAxis.add(Identifier.Key.J);
			mAxis.add(Identifier.Key.K);
			mAxis.add(Identifier.Key.L);
			  
			
			mButtons.add(Identifier.Key._0);
			mButtons.add(Identifier.Key._1);
			mButtons.add(Identifier.Key._2);
			mButtons.add(Identifier.Key._3);
			mButtons.add(Identifier.Key._4);
			mButtons.add(Identifier.Key._5);
			mButtons.add(Identifier.Key._6);
			mButtons.add(Identifier.Key._7);
			mButtons.add(Identifier.Key._8);
			mButtons.add(Identifier.Key._9);
        }

        mAxisValues = new short[mAxis.size()];
        mPovValues = new short[0];
    }
}
