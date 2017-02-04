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
 * This panel plots the heading v. time
 * 
 * @author PJ
 *
 */
public class PlotterHeading extends JPanel
{
    private XYSeriesCollection mCollection;
    private XYSeries mIdealHeading;
    private XYSeries mRealHeading;

    private JPanel m_chartPanel;

    public PlotterHeading(String aChartTitle)
    {
        setLayout(new BorderLayout());
        mIdealHeading = new XYSeries("Ideal Heading");
        mRealHeading = new XYSeries("Real Heading");

        mCollection = new XYSeriesCollection();
        mCollection.addSeries(mIdealHeading);
        mCollection.addSeries(mRealHeading);

        final JFreeChart chart = ChartFactory.createXYLineChart(
                aChartTitle,
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

    public void setPath(List<Double> headings)
    {
        mIdealHeading.clear();
        clearActuals();

        for (int i = 0; i < headings.size(); ++i)
        {
            mIdealHeading.add(i, headings.get(i));
        }
    }

    public void clearActuals()
    {
        mRealHeading.clear();
    }

    public void setPoint(int index, double heading)
    {
        mRealHeading.add(index, heading);
    }
}
