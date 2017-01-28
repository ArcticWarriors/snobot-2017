package com.snobot.coordinate_gui.ui.layers;

import java.awt.Dimension;
import java.awt.Graphics2D;

import com.snobot.coordinate_gui.model.Coordinate;
import com.snobot.coordinate_gui.model.DataProvider;
import com.snobot.coordinate_gui.model.PixelConverter;
import com.snobot.coordinate_gui.ui.layers.ILayerManager.IFieldClickListener;
import com.snobot.coordinate_gui.ui.renderProps.CreatePointsLayerRenderProps;

public class CreatePointsLayer implements ILayer
{
    protected final PixelConverter mPixelConverter;
    protected final ILayerManager mLayerManager;
    protected final DataProvider<Coordinate> mTrajectoryConfigDataProvider;
    protected final DataProvider<Coordinate> mTrajectoryPreviewDataProvider;
    protected final CreatePointsLayerRenderProps mRenderProperties;
    protected final CoordinateLayer mConfigLayer;
    protected final CoordinateLayer mPreviewLayer;

    private Coordinate mActiveCoordinate;


    public CreatePointsLayer(
            ILayerManager aLayerManager, 
            DataProvider<Coordinate> aTrajConfigDataProvider,
            DataProvider<Coordinate> aTrajPreviewDataProvider, 
            CreatePointsLayerRenderProps aRenderProperties,
            PixelConverter aPixelConverter)
    {
        mConfigLayer = new CoordinateLayer(aTrajConfigDataProvider, aRenderProperties.getConfigRenderProperties(), aPixelConverter);
        mPreviewLayer = new CoordinateLayer(aTrajPreviewDataProvider, aRenderProperties.getPreviewRenderProperties(), aPixelConverter);

        mPixelConverter = aPixelConverter;
        mLayerManager = aLayerManager;
        mTrajectoryConfigDataProvider = aTrajConfigDataProvider;
        mTrajectoryPreviewDataProvider = aTrajPreviewDataProvider;
        mRenderProperties = aRenderProperties;

        mLayerManager.addFieldClickListener(new IFieldClickListener()
        {

            @Override
            public void onClicked(double aXFeet, double aYFeet)
            {
                addTrajectoryConfigPoints(createCoordinate(aXFeet, aYFeet));
            }

            @Override
            public void onDrag(double aXFeet, double aYFeet)
            {
                boolean shouldAdd = true;

                Coordinate lastCoord = mTrajectoryConfigDataProvider.getMostRecentData();
                if (lastCoord != null)
                {
                    double dx = lastCoord.x - aXFeet;
                    double dy = lastCoord.y - aYFeet;
                    double distance = Math.sqrt(dx * dx + dy * dy);
                    if (distance < mRenderProperties.getMinDragDistance())
                    {
                        shouldAdd = false;
                    }
                }

                if (shouldAdd)
                {
                    addTrajectoryConfigPoints(createCoordinate(aXFeet, aYFeet));
                    mLayerManager.render();
                }
            }

            @Override
            public void onHover(double aXFeet, double aYFeet)
            {
                mActiveCoordinate = createCoordinate(aXFeet, aYFeet);
                mLayerManager.render();
            }
        });

        mActiveCoordinate = null;
    }

    private void addTrajectoryConfigPoints(Coordinate aCoord)
    {
        // I have altered the preview, pray I do not alter it further
        mTrajectoryPreviewDataProvider.clear();
        mTrajectoryConfigDataProvider.addData(aCoord);
        mLayerManager.render();
    }

    private Coordinate createCoordinate(double aXFeet, double aYFeet)
    {
        Coordinate output;


        if (mRenderProperties.isSnapToGrid())
        {
            double snapped_x = getSnappedX(aXFeet);
            double snapped_y = getSnappedY(aYFeet);
            double angle = mRenderProperties.getAngle(mTrajectoryConfigDataProvider.getMostRecentData(), snapped_x, snapped_y);

            output = new Coordinate(snapped_x, snapped_y, angle);
        }
        else
        {
            double angle = mRenderProperties.getAngle(mTrajectoryConfigDataProvider.getMostRecentData(), aXFeet, aYFeet);
            output = new Coordinate(aXFeet, aYFeet, angle);
        }

        return output;
    }

    private double getSnappedX(double aX)
    {
        double gridFactor = mRenderProperties.getGridFactorX();
        return Math.round(aX / gridFactor) * gridFactor;
    }

    private double getSnappedY(double aY)
    {
        double gridFactor = mRenderProperties.getGridFactorY();
        return Math.round(aY / gridFactor) * gridFactor;
    }

    @Override
    public void render(Graphics2D aGraphics)
    {
        mPreviewLayer.render(aGraphics);
        mConfigLayer.render(aGraphics);

        if (mActiveCoordinate != null)
        {
            int x = mPixelConverter.convertXFeetToPixels(mActiveCoordinate.x);
            int y = mPixelConverter.convertYFeetToPixels(mActiveCoordinate.y);
            int size = (mRenderProperties.getActivePointSize());

            aGraphics.setColor(mRenderProperties.getActivePointColor());
            aGraphics.fillOval(x - size / 2, y - size / 2, size, size);
            
            String toDraw = String.format("%.02f, %.02f, %.02f", mActiveCoordinate.x, mActiveCoordinate.y, mActiveCoordinate.angle);

            aGraphics.drawString(toDraw, x + 5, y - 5);
        }

    }

    @Override
    public Dimension getPreferredSize()
    {
        return null;
    }

}
