package com.snobot.sd2016.spline_plotter;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

public class StandaloneMain
{

    public static void main(String[] args)
    {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final SplinePlotterPanel panel = new SplinePlotterPanel();

        final List<SplineSegment> path_points = new ArrayList<SplineSegment>();

        SplineSegment p;

        p = new SplineSegment();
        path_points.add(p);

        double angle = Math.PI / 2;
        double radius = 1.7;
        double angle_mult = .1;

        for (int i = 1; i < 10; ++i)
        {
            p = new SplineSegment();
            p.mLeftSideVelocity = 0 + i * .7;
            p.mLeftSidePosition = path_points.get(path_points.size() - 1).mLeftSidePosition + p.mLeftSideVelocity * .02;
            p.mRightSideVelocity = p.mLeftSideVelocity;
            p.mRightSidePosition = p.mLeftSidePosition;
            p.mRobotHeading = i;
            p.mAverageX = (radius * i) * Math.sin(angle_mult * i);
            p.mAverageY = (radius * i) * Math.cos(angle_mult * i);

            path_points.add(p);
        }
        for (int i = 0; i < 20; ++i)
        {
            p = new SplineSegment();
            p.mLeftSideVelocity = 7.0;
            p.mLeftSidePosition = path_points.get(path_points.size() - 1).mLeftSidePosition + p.mLeftSideVelocity * .02;
            p.mRightSideVelocity = p.mLeftSideVelocity;
            p.mRightSidePosition = p.mLeftSidePosition;
            p.mRobotHeading = i;
            p.mAverageX = (radius * i) * Math.sin(angle_mult * i);
            p.mAverageY = (radius * i) * Math.cos(angle_mult * i);
            path_points.add(p);
        }
        for (int i = 0; i < 10; ++i)
        {
            p = new SplineSegment();
            p.mLeftSideVelocity = 7 - i * .7;
            p.mLeftSidePosition = path_points.get(path_points.size() - 1).mLeftSidePosition + p.mLeftSideVelocity * .02;
            p.mRightSideVelocity = p.mLeftSideVelocity;
            p.mRightSidePosition = p.mLeftSidePosition;
            p.mRobotHeading = i;
            p.mAverageX = (radius * i) * Math.sin(angle_mult * i);
            p.mAverageY = (radius * i) * Math.cos(angle_mult * i);
            path_points.add(p);
        }

        p = new SplineSegment();
        p.mLeftSideVelocity = 0;
        p.mLeftSidePosition = path_points.get(path_points.size() - 1).mLeftSidePosition + p.mLeftSideVelocity * .02;
        p.mRightSideVelocity = p.mLeftSideVelocity;
        p.mRightSidePosition = p.mLeftSidePosition;
        p.mRobotHeading = 0;
        path_points.add(p);

        panel.setPath(path_points);

        frame.add(panel);

        frame.pack();
        frame.setVisible(true);

        frame.addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(ComponentEvent arg0)
            {
            }
        });

        Thread t = new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                List<SplineSegment> actuals = new ArrayList<SplineSegment>();

                for (int i = 0; i < path_points.size(); ++i)
                {
                    SplineSegment p = path_points.get(i);
                    p.mLeftSideVelocity *= .9;
                    p.mLeftSidePosition = 0;
                    p.mRightSideVelocity = p.mLeftSideVelocity * .5;
                    p.mRightSidePosition = 0;
                    p.mRobotHeading = 0;

                    if (i != 0)
                    {
                        p.mLeftSidePosition = actuals.get(i - 1).mLeftSidePosition + p.mLeftSideVelocity * .02;
                        p.mRightSidePosition = p.mLeftSidePosition * .5;
                        p.mRobotHeading = i * .8;
                        p.mAverageX = p.mAverageX * .8;
                        p.mAverageY = p.mAverageY * .8;
                    }

                    actuals.add(p);
                }

                for (int i = 0; i < actuals.size(); ++i)
                {
                    panel.setPoint(i, actuals.get(i));

                    try
                    {
                        Thread.sleep(500);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }
}
