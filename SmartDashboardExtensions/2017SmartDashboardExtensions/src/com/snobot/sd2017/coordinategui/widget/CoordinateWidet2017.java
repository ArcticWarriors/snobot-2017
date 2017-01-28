package com.snobot.sd2017.coordinategui.widget;

import java.awt.BorderLayout;

import com.snobot.coordinate_gui.model.Coordinate;
import com.snobot.coordinate_gui.ui.renderProps.CoordinateLayerRenderProps;
import com.snobot.coordinate_gui.ui.renderProps.RobotLayerRenderProps;
import com.snobot.sd.util.AutoUpdateWidget;
import com.snobot2017.SmartDashBoardNames;

import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.robot.Robot;

public class CoordinateWidet2017 extends AutoUpdateWidget
{
    public static final String NAME = "2016 Coordinate Widget";
    
    private CoordinateGui2017 mCoordinateGui;

    public CoordinateWidet2017()
    {
        this(false);
    }

    public CoordinateWidet2017(boolean aDebug)
    {
        super(aDebug, 10);

        CoordinateLayerRenderProps coordinateLayerRenderProps = new CoordinateLayerRenderProps();
        RobotLayerRenderProps robotLayerRenderProps = new RobotLayerRenderProps();

        mCoordinateGui = new CoordinateGui2017(coordinateLayerRenderProps, robotLayerRenderProps);

        setLayout(new BorderLayout());
        add(mCoordinateGui.getComponent(), BorderLayout.CENTER);

        setSize(100, 100);
    }

    @Override
    public void init()
    {
        revalidate();
        repaint();
    }

    @Override
    public void propertyChanged(Property property)
    {

    }

    @Override
    protected void poll() throws Exception
    {
        double x = Robot.getTable().getNumber(SmartDashBoardNames.sX_POSITION, 0);
        double y = Robot.getTable().getNumber(SmartDashBoardNames.sY_POSITION,0);
        double angle = Robot.getTable().getNumber(SmartDashBoardNames.sORIENTATION, 0);
        mCoordinateGui.addCoordinate(new Coordinate(x, y, angle));
    }
}
