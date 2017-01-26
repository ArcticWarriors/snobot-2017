package com.snobot.coordinate_gui.ui.layers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.Iterator;

import com.snobot.coordinate_gui.model.Coordinate;
import com.snobot.coordinate_gui.model.DataProvider;
import com.snobot.coordinate_gui.model.PixelConverter;
import com.snobot.coordinate_gui.ui.renderProps.CoordinateLayerRenderProps;

public class CoordinateLayer implements ILayer
{
    protected final DataProvider<Coordinate> mDataProvider;
    protected final PixelConverter mPixelConverter;
    protected final CoordinateLayerRenderProps mRenderProperties;

    public CoordinateLayer(DataProvider<Coordinate> aDataProvider, CoordinateLayerRenderProps aRenderProps, PixelConverter aPixelConverter)
    {
        mDataProvider = aDataProvider;
        mRenderProperties = aRenderProps;
        mPixelConverter = aPixelConverter;
    }

    @Override
    public void render(Graphics2D aGraphics)
    {
        renderCoordinates(aGraphics, mDataProvider, mRenderProperties);
    }

    protected void renderCoordinates(Graphics2D aGraphics, DataProvider<Coordinate> dataProvider, CoordinateLayerRenderProps renderProperties)
    {
        Iterator<Coordinate> rev_iterator = dataProvider.getReverseIterator();
        int coordinateCtr = 0;

        while (rev_iterator.hasNext())
        {
            int pointMemory = renderProperties.getPointMemory();
            if (pointMemory != -1 && coordinateCtr >= pointMemory)
            {
                break;
            }

            Coordinate coord = rev_iterator.next();

            float opacity = 1.0f - ((float) coordinateCtr / pointMemory);
            opacity = Math.min(1, opacity);
            opacity = Math.max(0, opacity);
            Color defaultColor = renderProperties.getPointColor();
            Color color;

            if (renderProperties.isFadeOverTime())
            {
                color = new Color(defaultColor.getRed() / 255.0f, defaultColor.getGreen() / 255.0f, defaultColor.getBlue() / 255.0f, opacity);
            }
            else
            {
                color = defaultColor;
            }

            paintCoordinate(aGraphics, coord, color, renderProperties.getPointSize());
            ++coordinateCtr;
        }
    }

    private void paintCoordinate(Graphics2D aGraphics, Coordinate aCoordinate, Color aColor, int aSize)
    {
        if (aCoordinate != null)
        {
            int x = mPixelConverter.convertXFeetToPixels(aCoordinate.x);
            int y = mPixelConverter.convertYFeetToPixels(aCoordinate.y);

            aGraphics.setColor(aColor);
            aGraphics.fillOval(x - aSize / 2, y - aSize / 2, aSize, aSize);
        }
    }

    @Override
    public Dimension getPreferredSize()
    {
        return null;
    }
}
