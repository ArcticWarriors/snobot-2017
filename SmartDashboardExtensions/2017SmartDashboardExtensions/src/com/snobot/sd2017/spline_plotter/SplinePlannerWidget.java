package com.snobot.sd2017.spline_plotter;

import java.awt.BorderLayout;
import java.util.StringTokenizer;

import com.snobot.sd.spline_plotter.IdealSplineSerializer;
import com.snobot.sd.spline_plotter.SplinePlotterPanel;
import com.snobot.sd.spline_plotter.SplineSegment;
import com.snobot.sd.util.AutoUpdateWidget;
import com.snobot2017.SmartDashBoardNames;

import edu.wpi.first.smartdashboard.properties.Property;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

/**
 * Widget used to plot information on driving a cubic spline, pre-planned path
 * 
 * @author PJ
 *
 */
public class SplinePlannerWidget extends AutoUpdateWidget
{
    public static final String NAME = "2017 SplinePlanning";
    private SplinePlotterPanel mPanel;
    private int mLastIndex;

    private ITable mTable; // The network table used to send trajectory data
    private String mIdealSplineName; // The SD name used to convey the ideal spline points
    private String mRealSplineName;  // The SD name of the real points


    public SplinePlannerWidget()
    {
        super(false, 10);
        setLayout(new BorderLayout());
        mPanel = new SplinePlotterPanel();
        add(mPanel);

        mTable = NetworkTable.getTable(SmartDashBoardNames.sSPLINE_NAMESPACE);

        mLastIndex = 0;

        mIdealSplineName = SmartDashBoardNames.sSPLINE_IDEAL_POINTS;
        mRealSplineName = SmartDashBoardNames.sSPLINE_REAL_POINT;

        addPathListener();
    }

    private void addPathListener()
    {

        ITableListener idealSplineListener = new ITableListener()
        {

            @Override
            public void valueChanged(ITable arg0, String arg1, Object arg2, boolean arg3)
            {
                mPanel.setPath(IdealSplineSerializer.deserializePath(arg2.toString()));
                mLastIndex = 0;
                revalidate();
                repaint();
            }
        };
        mTable.addTableListener(mIdealSplineName, idealSplineListener, true);

        ITableListener realSplineListener = new ITableListener()
        {

            @Override
            public void valueChanged(ITable arg0, String arg1, Object arg2, boolean arg3)
            {
                String point_info = mTable.getString(mRealSplineName, "");

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
                        SplineSegment segment = IdealSplineSerializer.deserializePathPoint(tokenizer);
                        mPanel.setPoint(index, segment);
                    }

                    mLastIndex = index;
                }
            }
        };
        mTable.addTableListener(mRealSplineName, realSplineListener, true);
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
