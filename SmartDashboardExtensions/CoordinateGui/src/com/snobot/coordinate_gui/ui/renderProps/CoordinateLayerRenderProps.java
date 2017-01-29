package com.snobot.coordinate_gui.ui.renderProps;

import java.awt.Color;

public class CoordinateLayerRenderProps
{

    protected int mSize;
    protected int mPointMemory;
    protected Color mColor;
    protected boolean mFadeOverTime;

    public CoordinateLayerRenderProps()
    {
        mSize = 10;
        mPointMemory = 100;
        mColor = Color.green;
        mFadeOverTime = true;
    }

    public int getPointMemory()
    {
        return mPointMemory;
    }

    public Color getPointColor()
    {
        return mColor;
    }

    public boolean isFadeOverTime()
    {
        return mFadeOverTime;
    }

    public int getPointSize()
    {
        return mSize;
    }

    public void setPointSize(int mSize)
    {
        this.mSize = mSize;
    }

    public void setPointMemory(int mPointMemory)
    {
        this.mPointMemory = mPointMemory;
    }

    public void setPointColor(Color mColor)
    {
        this.mColor = mColor;
    }

    public void setFadeOverTime(boolean mFadeOverTime)
    {
        this.mFadeOverTime = mFadeOverTime;
    }

}
