package com.snobot.sd2016.spline_plotter;

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
 * Plots the path of an individual side of the robot
 * 
 * @author PJ
 *
 */
public class PlotterWheel extends JPanel
{
    private XYSeriesCollection mCollection;
    private XYSeries mIdealPosition;
    private XYSeries mIdealVelocity;

    private XYSeries mRealPosition;
    private XYSeries mRealVelocity;

    private JPanel m_chartPanel;

    public PlotterWheel(String chartTitle)
    {
        setLayout(new BorderLayout());
        mIdealPosition = new XYSeries("Ideal  Position");
        mIdealVelocity = new XYSeries("Ideal  Velocity");

        mRealPosition = new XYSeries("Real  Position");
        mRealVelocity = new XYSeries("Real  Velocity");

        mCollection = new XYSeriesCollection();
        mCollection.addSeries(mIdealPosition);
        mCollection.addSeries(mIdealVelocity);
        mCollection.addSeries(mRealPosition);
        mCollection.addSeries(mRealVelocity);

        final JFreeChart chart = ChartFactory.createXYLineChart(
                chartTitle,
                "Time (sec)",
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

    public void setPath(List<Double> position, List<Double> velocity)
    {
        mIdealPosition.clear();
        mIdealVelocity.clear();
        clearActuals();

        for (int i = 0; i < position.size(); ++i)
        {
            mIdealPosition.add(i, position.get(i));
            mIdealVelocity.add(i, velocity.get(i));
        }
    }

    public void clearActuals()
    {
        mRealPosition.clear();
        mRealVelocity.clear();
    }

    public void setPoint(int index, double position, double velocity)
    {
        mRealPosition.add(index, position);
        mRealVelocity.add(index, velocity);
    }

}
