package com.snobot.simulator.joysticks;

import java.util.List;

import edu.wpi.first.wpilibj.hal.HAL;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Controller;

public class BaseJoystick implements IMockJoystick
{

    protected final String mName;
    protected final List<Identifier> mAxis;
    protected final List<Identifier> mButtons;
    protected final List<Identifier> mPOV;
    protected final Controller mController;

    protected short[] mAxisValues;
    protected short[] mPovValues;

    public BaseJoystick(String aName, Controller aController, List<Identifier> aAxisList, List<Identifier> aButtonList, List<Identifier> aPOV)
    {
        mController = aController;
        mAxis = aAxisList;
        mButtons = aButtonList;
        mPOV = aPOV;
        mName = aName;

        mAxisValues = new short[mAxis.size()];
        mPovValues = new short[mPOV.size()];
    }

    public Controller getController()
    {
        return mController;
    }

    @Override
    public int getAxisCount()
    {
        return mAxis.size();
    }

    @Override
    public int getButtonCount()
    {
        return mButtons.size();
    }

    @Override
    public void setRumble(short s)
    {
        // TODO implement rumble...
    }

    @Override
    public short[] getAxisValues()
    {

        if (mController != null)
        {
            mController.poll();

            for (int i = 0; i < mAxis.size(); ++i)
            {
                Component c = mController.getComponent(mAxis.get(i));
                if (c != null)
                {
                    mAxisValues[i] = (short) (c.getPollData() * 127);
                }
            }
        }
        else
        {
            System.err.println("Controller is null.  The simulator could not setup a controller of type [" + mName + "]");
        }

        return mAxisValues;
    }

    @Override
    public int getButtonMask()
    {

        int output = 0;

        if (mController != null)
        {
            mController.poll();

            for (int i = 0; i < mButtons.size(); ++i)
            {
                Component component = mController.getComponent(mButtons.get(i));
                if (component != null)
                {
                    int pressed = component.getPollData() == 0 ? 0 : 1;
                    output += (pressed << i);
                }
            }
        }
        else
        {
            System.err.println("Controller is null.  The simulator could not setup a controller of type [" + mName + "]");
        }

        return output;
    }

    @Override
    public short[] getPovValues()
    {
        short[] output = new short[HAL.kMaxJoystickPOVs];

        int i;
        for (i = 0; i < mPOV.size(); ++i)
        {
            Identifier id = mPOV.get(i);
            Component component = mController.getComponent(id);
            if (component != null)
            {
                double value = component.getPollData();
                if (value == 0)
                {
                    output[i] = -1;
                }
                else
                {
                    output[i] = (short) ((value - .25) * 360);
                }
            }
        }

        for (; i < output.length; ++i)
        {
            output[i] = -1;
        }

        return output;
    }

    public String getName()
    {
        return mName;
    }

    @Override
    public String toString()
    {
        return getName();
    }

    @Override
    public boolean getRawButton(int aIndex)
    {
        int mask = getButtonMask();
        return (mask & (1 << aIndex)) != 0;
    }

    @Override
    public double getRawAxis(int aIndex)
    {
        double output = 0;

        Component c = mController.getComponent(mAxis.get(aIndex));
        if (c != null)
        {
            output = c.getPollData();
        }

        return output;
    }
}
