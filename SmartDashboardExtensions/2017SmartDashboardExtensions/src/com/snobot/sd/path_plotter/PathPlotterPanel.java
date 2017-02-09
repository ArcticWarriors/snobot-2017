package com.snobot.sd.path_plotter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * Panel used to plot the ideal and real paths that the robot uses for motion
 * profiling
 * 
 * @author PJ
 *
 */
public class PathPlotterPanel extends JPanel
{
    private XYSeriesCollection mCollection;
    private XYSeries mIdealPosition;
    private XYSeries mIdealVelocity;
    private XYSeries mRealPosition;
    private XYSeries mRealVelocity;

    private JPanel m_chartPanel;

    public PathPlotterPanel()
    {
        setLayout(new BorderLayout());
        mIdealPosition = new XYSeries("Ideal Position");
        mIdealVelocity = new XYSeries("Ideal Velocity");
        mRealVelocity = new XYSeries("Real Velocity");
        mRealPosition = new XYSeries("Real Position");

        mCollection = new XYSeriesCollection();
        mCollection.addSeries(mIdealPosition);
        mCollection.addSeries(mIdealVelocity);
        mCollection.addSeries(mRealVelocity);
        mCollection.addSeries(mRealPosition);

        final JFreeChart chart = ChartFactory.createXYLineChart("Motion Profile", "Time (sec)",
                "Data",
                mCollection,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);
        chart.setBackgroundPaint(Color.white);

        m_chartPanel = new ChartPanel(chart);
        m_chartPanel.setPreferredSize(new Dimension(400, 300));
        m_chartPanel.setBackground(getBackground());

        add(m_chartPanel, BorderLayout.CENTER);
    }

    public void setPath(List<PathSetpoint> path_points)
    {
        mIdealPosition.clear();
        mIdealVelocity.clear();
        clearActuals();

        for (int i = 0; i < path_points.size(); ++i)
        {
            mIdealPosition.add(i, path_points.get(i).mPosition);
            mIdealVelocity.add(i, path_points.get(i).mVelocity);
        }
    }

    public void clearActuals()
    {
        mRealPosition.clear();
        mRealVelocity.clear();
    }

    public void setPoint(int index, PathSetpoint aPoint)
    {
        mRealPosition.add(index, aPoint.mPosition);
        mRealVelocity.add(index, aPoint.mVelocity);
    }

}
