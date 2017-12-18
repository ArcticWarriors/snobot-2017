package com.snobot.sd2017.path_plotter;

import java.awt.BorderLayout;
import java.util.StringTokenizer;

import com.snobot.sd.path_plotter.PathPlotterPanel;
import com.snobot.sd.util.AutoUpdateWidget;
import com.snobot2017.SmartDashBoardNames;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableValue;
import edu.wpi.first.networktables.TableEntryListener;
import edu.wpi.first.smartdashboard.properties.Property;

public class PlotPlannerWidget extends AutoUpdateWidget
{
    public static final String NAME = "2017 PathPlanning";
    private PathPlotterPanel mPanel;
    private NetworkTable mTable;
    private int mLastIndex;

    private String mTableNamespace;
    private String mSDIdealPathName;
    private String mSDRealPathname;

    public PlotPlannerWidget()
    {
        super(false, 10);
        setLayout(new BorderLayout());
        mPanel = new PathPlotterPanel();
        add(mPanel);

        mLastIndex = 0;

        mTableNamespace = SmartDashBoardNames.sPATH_NAMESPACE;
        mSDIdealPathName = SmartDashBoardNames.sPATH_IDEAL_POINTS;
        mSDRealPathname = SmartDashBoardNames.sPATH_POINT;

        mTable = NetworkTableInstance.getDefault().getTable(mTableNamespace);

        addPathListener();
    }

    private void addPathListener()
    {

        TableEntryListener plannedPathListener = new TableEntryListener()
        {

            @Override
            public void valueChanged(NetworkTable arg0, String arg1, NetworkTableEntry arg2, NetworkTableValue arg3, int arg4)
            {
                mPanel.setPath(IdealPlotSerializer.deserializePath(arg2.toString()));
                mLastIndex = 0;
                revalidate();
                repaint();
            }
        };
        mTable.addEntryListener(mSDIdealPathName, plannedPathListener, 0xFF);

        TableEntryListener realPointListener = new TableEntryListener()
        {

            @Override
            public void valueChanged(NetworkTable arg0, String arg1, NetworkTableEntry arg2, NetworkTableValue arg3, int arg4)
            {
                String point_info = mTable.getEntry(mSDRealPathname).getString("");

                StringTokenizer tokenizer = new StringTokenizer(point_info, ",");

                if (tokenizer.hasMoreElements())
                {
                    int index = Integer.parseInt(tokenizer.nextElement().toString());

                    if (index == 0 || index < mLastIndex)
                    {
                        mPanel.clearActuals();
                    }

                    if (index > mLastIndex)
                    {
                        mPanel.setPoint(index, IdealPlotSerializer.deserializePathPoint(tokenizer));
                    }

                    mLastIndex = index;
                }

                revalidate();
                repaint();
            }
        };
        mTable.addEntryListener(mSDRealPathname, realPointListener, 0xFF);
    }

    @Override
    public void propertyChanged(Property arg0)
    {
    }

    @Override
    public void init()
    {

    }

    @Override
    protected void poll() throws Exception
    {

    }

}
