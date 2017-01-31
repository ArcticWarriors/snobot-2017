package com.snobot.sd2017.coordinategui.widget;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.snobot.coordinate_gui.model.PixelConverter;
import com.snobot.coordinate_gui.ui.layers.ILayer;

public class RayLayer implements ILayer
{
    public static class Ray
    {
        public double mXStart;
        public double mYStart;
        public double mXEnd;
        public double mYEnd;

        @Override
        public String toString()
        {
            return "Ray [mXStart=" + mXStart + ", mYStart=" + mYStart + ", mXEnd=" + mXEnd + ", mYEnd=" + mYEnd + "]";
        }
    }

    protected PixelConverter mPixelConverter;
    private List<Ray> mRays;
    private Color mRayColor = Color.green;

    public RayLayer(PixelConverter aConverter)
    {
        mPixelConverter = aConverter;
        mRays = new ArrayList<>();
    }

    @Override
    public void render(Graphics2D aGraphics)
    {
        for (Ray ray : mRays)
        {
            int xStart = mPixelConverter.convertXFeetToPixels(ray.mXStart);
            int yStart = mPixelConverter.convertYFeetToPixels(ray.mYStart);
            int xEnd = mPixelConverter.convertXFeetToPixels(ray.mXEnd);
            int yEnd = mPixelConverter.convertYFeetToPixels(ray.mYEnd);

            aGraphics.setColor(mRayColor);
            aGraphics.drawLine(xStart, yStart, xEnd, yEnd);
        }
    }

    @Override
    public Dimension getPreferredSize()
    {
        return null;
    }

    public void setRays(List<Ray> aRays)
    {
        mRays = aRays;
    }

}
