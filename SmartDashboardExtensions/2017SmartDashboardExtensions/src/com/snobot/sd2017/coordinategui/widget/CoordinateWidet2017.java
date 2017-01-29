package com.snobot.sd2017.coordinategui.widget;

import java.awt.BorderLayout;

import com.snobot.coordinate_gui.model.Coordinate;
import com.snobot.coordinate_gui.ui.renderProps.CoordinateLayerRenderProps;
import com.snobot.coordinate_gui.ui.renderProps.RobotLayerRenderProps;
import com.snobot.sd.util.AutoUpdateWidget;
import com.snobot2017.SmartDashBoardNames;

import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.robot.Robot;
import edu.wpi.first.wpilibj.tables.ITable;

public class CoordinateWidet2017 extends AutoUpdateWidget
{
    public static final String NAME = "2017 Coordinate Widget";
    
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
        ITable robotTable = Robot.getTable();

        if (robotTable != null && mCoordinateGui != null)
        {
            double x = Robot.getTable().getNumber(SmartDashBoardNames.sX_POSITION, 0);
            double y = Robot.getTable().getNumber(SmartDashBoardNames.sY_POSITION, 0);
            double angle = Robot.getTable().getNumber(SmartDashBoardNames.sORIENTATION, 0);

            Coordinate coord = new Coordinate(x, y, angle);
            // System.out.println(coord);
            mCoordinateGui.addCoordinate(coord);
        }
    }
}
