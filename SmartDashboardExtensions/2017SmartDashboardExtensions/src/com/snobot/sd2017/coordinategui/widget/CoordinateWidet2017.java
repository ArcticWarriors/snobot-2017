package com.snobot.sd2017.coordinategui.widget;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.snobot.coordinate_gui.model.Coordinate;
import com.snobot.coordinate_gui.steamworks.CameraRayLayer.Ray;
import com.snobot.coordinate_gui.steamworks.CoordinateGui2017;
import com.snobot.coordinate_gui.ui.renderProps.CoordinateLayerRenderProps;
import com.snobot.coordinate_gui.ui.renderProps.RobotLayerRenderProps;
import com.snobot.sd.spline_plotter.SplineSegment;
import com.snobot.sd.util.AutoUpdateWidget;
import com.snobot.sd.util.SmartDashboardUtil;
import com.snobot.sd2017.spline_plotter.IdealSplineSerializer;
import com.snobot2017.SmartDashBoardNames;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableValue;
import edu.wpi.first.networktables.TableEntryListener;
import edu.wpi.first.smartdashboard.properties.Property;

public class CoordinateWidet2017 extends AutoUpdateWidget
{
    public static final String NAME = "2017 Coordinate Widget";

    private NetworkTable mSplineTable;
    
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

        mSplineTable = NetworkTableInstance.getDefault().getTable(SmartDashBoardNames.sSPLINE_NAMESPACE);

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
        TableEntryListener idealSplineListener = new TableEntryListener()
        {

            @Override
            public void valueChanged(NetworkTable arg0, String arg1, NetworkTableEntry arg2, NetworkTableValue arg3, int arg4)
            {
                List<Coordinate> coordinates = new ArrayList<>();
                for (SplineSegment splineSegment : IdealSplineSerializer.deserializePath(arg2.toString()))
                {
                    coordinates.add(new Coordinate(splineSegment.mAverageX / 12.0, splineSegment.mAverageY / 12.0, splineSegment.mRobotHeading));
                }
                mCoordinateGui.setPath(coordinates);
            }
        };
        mSplineTable.addEntryListener(SmartDashBoardNames.sSPLINE_IDEAL_POINTS, idealSplineListener, 0xFF);
    }

    @Override
    protected void poll() throws Exception
    {
        if (mCoordinateGui != null)
        {
            double x = SmartDashboardUtil.getTable().getEntry(SmartDashBoardNames.sX_POSITION).getDouble(0) / 12;
            double y = SmartDashboardUtil.getTable().getEntry(SmartDashBoardNames.sY_POSITION).getDouble(0) / 12;
            double angle = SmartDashboardUtil.getTable().getEntry(SmartDashBoardNames.sORIENTATION).getDouble(0);

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

        String targetJson = SmartDashboardUtil.getTable().getEntry(SmartDashBoardNames.sVISION_TARGETS).getString("");
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
