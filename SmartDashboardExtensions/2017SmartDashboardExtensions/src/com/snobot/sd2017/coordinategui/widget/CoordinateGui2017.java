package com.snobot.sd2017.coordinategui.widget;

import java.util.List;

import com.snobot.coordinate_gui.BaseCoordinateGui;
import com.snobot.coordinate_gui.ui.layers.CoordinateLayer;
import com.snobot.coordinate_gui.ui.layers.FieldImageLayer;
import com.snobot.coordinate_gui.ui.layers.RobotLayer;
import com.snobot.coordinate_gui.ui.renderProps.CoordinateLayerRenderProps;
import com.snobot.coordinate_gui.ui.renderProps.RobotLayerRenderProps;
import com.snobot.sd2017.coordinategui.widget.RayLayer.Ray;

public class CoordinateGui2017 extends BaseCoordinateGui
{
    private static final String FIELD_IMAGE_PATH = "/com/snobot/sd2017/coordinategui/widget/2017Field.png";

    private static final double FIELD_WIDTH = 27;
    private static final double FIELD_HEIGHT = 54;

    private static final double CENTER_POINT_X = 13.5;
    private static final double CENTER_POINT_Y = 27;

    private static final double ROBOT_WIDTH = 36 / 12.0;
    private static final double ROBOT_HEIGHT = 44 / 12.0;

    protected FieldImageLayer mFieldLayer;
    protected CoordinateLayer mCoordinateLayer;
    protected RayLayer mRayLayer;

    protected RobotLayer mRobotLayer;

    public CoordinateGui2017(
            CoordinateLayerRenderProps aCoordinateLayerRenderProps, 
            RobotLayerRenderProps aRobotLayerRenderProps)
    {
        super(CENTER_POINT_X, CENTER_POINT_Y);

        mFieldLayer = new FieldImageLayer(FIELD_IMAGE_PATH, mConverter, FIELD_WIDTH, FIELD_HEIGHT);
        mRobotLayer = new RobotLayer(mCoordinateDataProvider, aRobotLayerRenderProps, mConverter, ROBOT_WIDTH,
                ROBOT_HEIGHT);
        mCoordinateLayer = new CoordinateLayer(mCoordinateDataProvider, aCoordinateLayerRenderProps, mConverter);
        mRayLayer = new RayLayer(mConverter);

        mLayerManager.addLayer(mFieldLayer);
        mLayerManager.addLayer(mRobotLayer);
        mLayerManager.addLayer(mCoordinateLayer);
        mLayerManager.addLayer(mRayLayer);
    }

    public void setRays(List<Ray> aRays)
    {
        mRayLayer.setRays(aRays);
    }

}
