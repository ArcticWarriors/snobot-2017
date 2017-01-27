package com.snobot.sd.util;

import java.awt.Color;

/**
 *
 * @author PJ
 */
public class Util
{

    public static Color getMotorColor(double aSpeed)
    {
        if (Double.isNaN(aSpeed))
        {
            aSpeed = 0;
        }
        if (aSpeed > 1)
        {
            aSpeed = 1;
        }
        else if (aSpeed < -1)
        {
            aSpeed = -1;
        }

        float percent = (float) ((aSpeed + 1) / 2);
        float hue = percent * .33f;
        float saturation = 1f;
        float brightness = 1f;

        return Color.getHSBColor(hue, saturation, brightness);

    }

    /**
     * Modifies the color to make it more see through
     * 
     * @param aColor
     *            The color to modify
     * @param aAlpha
     *            The new alpha value (0-255, 0 being full see-through)
     * @return The modified color
     */
    public static Color alphaColor(Color aColor, int aAlpha)
    {
        return new Color(aColor.getRed(), aColor.getGreen(), aColor.getBlue(), aAlpha);
    }
}
