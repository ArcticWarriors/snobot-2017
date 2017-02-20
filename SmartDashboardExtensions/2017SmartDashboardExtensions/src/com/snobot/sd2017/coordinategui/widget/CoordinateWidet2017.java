package com.snobot.sd2017.coordinategui.widget;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.snobot.coordinate_gui.model.Coordinate;
import com.snobot.coordinate_gui.ui.renderProps.CoordinateLayerRenderProps;
import com.snobot.coordinate_gui.ui.renderProps.RobotLayerRenderProps;
import com.snobot.sd.spline_plotter.IdealSplineSerializer;
import com.snobot.sd.util.AutoUpdateWidget;
import com.snobot.sd2017.coordinategui.widget.RayLayer.Ray;
import com.snobot2017.SmartDashBoardNames;

import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.smartdashboard.robot.Robot;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

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
        super(aDebug, 20);

        CoordinateLayerRenderProps trajectoryLayerRenderProps = new CoordinateLayerRenderProps();
        CoordinateLayerRenderProps coordinateLayerRenderProps = new CoordinateLayerRenderProps();
        RobotLayerRenderProps robotLayerRenderProps = new RobotLayerRenderProps();

        trajectoryLayerRenderProps.setFadeOverTime(false);
        trajectoryLayerRenderProps.setPointSize(5);
        trajectoryLayerRenderProps.setPointMemory(-1);
        trajectoryLayerRenderProps.setPointColor(Color.red);

        mCoordinateGui = new CoordinateGui2017(trajectoryLayerRenderProps, coordinateLayerRenderProps, robotLayerRenderProps);

        setLayout(new BorderLayout());
        add(mCoordinateGui.getComponent(), BorderLayout.CENTER);

        setSize(100, 100);

        initializeTrajectoryListener();
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

    private void initializeTrajectoryListener()
    {

        ITable mTable = NetworkTable.getTable(SmartDashBoardNames.sSPLINE_NAMESPACE);
        ITableListener idealSplineListener = new ITableListener()
        {

            @Override
            public void valueChanged(ITable arg0, String arg1, Object arg2, boolean arg3)
            {
                mCoordinateGui.setPath(IdealSplineSerializer.deserializePath(arg2.toString()));
            }
        };
        mTable.addTableListener(SmartDashBoardNames.sSPLINE_IDEAL_POINTS, idealSplineListener, true);
    }

    @Override
    protected void poll() throws Exception
    {
        ITable robotTable = Robot.getTable();

        if (robotTable != null && mCoordinateGui != null)
        {
            double x = Robot.getTable().getNumber(SmartDashBoardNames.sX_POSITION, 0) / 12;
            double y = Robot.getTable().getNumber(SmartDashBoardNames.sY_POSITION, 0) / 12;
            double angle = Robot.getTable().getNumber(SmartDashBoardNames.sORIENTATION, 0);

            Coordinate coord = new Coordinate(x, y, angle);
            mCoordinateGui.addCoordinate(coord);

            List<Ray> rays = parseRays();
            mCoordinateGui.setRays(rays);
        }
    }

    @SuppressWarnings("unchecked")
    private List<Ray> parseRays()
    {
        List<Ray> rays = new ArrayList<>();

        String targetJson = Robot.getTable().getString(SmartDashBoardNames.sVISION_TARGETS, "");
        if (targetJson.equals(""))
        {
            return rays;
        }


        Yaml yaml = new Yaml();
        Map<String, Object> targetMessage = (Map<String, Object>) yaml.load(targetJson);
        List<Map<String, Object>> targets = (List<Map<String, Object>>) targetMessage.get("targets");

        double robotX = Double.parseDouble(targetMessage.get("robot_x").toString());
        double robotY = Double.parseDouble(targetMessage.get("robot_y").toString());

        for (Map<String, Object> targetInfo : targets)
        {
            double targetX = Double.parseDouble(targetInfo.get("x").toString());
            double targetY = Double.parseDouble(targetInfo.get("y").toString());

            Ray ray = new Ray();
            ray.mXStart = robotX / 12;
            ray.mYStart = robotY / 12;
            ray.mXEnd = targetX / 12;
            ray.mYEnd = targetY / 12;
            ray.mAmbiguous = Boolean.parseBoolean(targetInfo.get("ambiguous").toString());

            rays.add(ray);
        }

        return rays;

    }
}
