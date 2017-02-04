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

public class PlotterXY extends JPanel
{
    public static class XYPoint
    {
        public double mX;
        public double mY;
    }

    private XYSeriesCollection mCollection;
    private XYSeries mIdeal;
    private XYSeries mReal;

    private JPanel m_chartPanel;

    public PlotterXY(String chartTitle)
    {
        setLayout(new BorderLayout());
        mIdeal = new XYSeries("Ideal  Position");
        mReal = new XYSeries("Ideal  Velocity");

        mCollection = new XYSeriesCollection();
        mCollection.addSeries(mIdeal);
        mCollection.addSeries(mReal);

        final JFreeChart chart = ChartFactory.createXYLineChart(
                chartTitle, 
                "X (Inches)", 
                "Y (Inches)", 
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

    public void setPath(List<XYPoint> xyList)
    {
        mIdeal.clear();
        clearActuals();

        for (int i = 0; i < xyList.size(); ++i)
        {
            mIdeal.add(xyList.get(i).mX, xyList.get(i).mY);
        }
    }

    public void clearActuals()
    {
        mReal.clear();
    }

    public void setPoint(double aX, double aY)
    {
        mReal.add(aX, aY);
    }
}
