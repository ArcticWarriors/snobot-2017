package com.snobot.vision;

public class HslThreshold
{
    public int hue, sat, lum;

    public HslThreshold()
    {

    }

    public HslThreshold(int hue, int sat, int lum)
    {
        this.hue = hue;
        this.sat = sat;
        this.lum = lum;
    }

    @Override
    public String toString()
    {
        return "HslThreshold [hue=" + hue + ", sat=" + sat + ", lum=" + lum + "]";
    }

}