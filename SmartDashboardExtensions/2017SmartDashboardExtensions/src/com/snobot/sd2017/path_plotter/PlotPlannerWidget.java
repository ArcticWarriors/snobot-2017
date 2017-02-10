package com.snobot.sd2017.path_plotter;

import java.awt.BorderLayout;
import java.util.StringTokenizer;

import com.snobot.sd.path_plotter.IdealPlotSerializer;
import com.snobot.sd.path_plotter.PathPlotterPanel;
import com.snobot.sd.util.AutoUpdateWidget;
import com.snobot2017.SmartDashBoardNames;

import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

public class PlotPlannerWidget extends AutoUpdateWidget
{
    public static final String NAME = "2017 PathPlanning";
    private PathPlotterPanel mPanel;
    private ITable mTable;
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

        mTable = NetworkTable.getTable(mTableNamespace);

        addPathListener();
    }

    private void addPathListener()
    {

        ITableListener plannedPathListener = new ITableListener()
        {

            @Override
            public void valueChanged(ITable arg0, String arg1, Object arg2, boolean arg3)
            {
                mPanel.setPath(IdealPlotSerializer.deserializePath(arg2.toString()));
                mLastIndex = 0;
                revalidate();
                repaint();
            }
        };
        mTable.addTableListener(mSDIdealPathName, plannedPathListener, true);

        ITableListener realPointListener = new ITableListener()
        {

            @Override
            public void valueChanged(ITable arg0, String arg1, Object arg2, boolean arg3)
            {
                String point_info = mTable.getString(mSDRealPathname, "");

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
        mTable.addTableListener(mSDRealPathname, realPointListener, true);
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
