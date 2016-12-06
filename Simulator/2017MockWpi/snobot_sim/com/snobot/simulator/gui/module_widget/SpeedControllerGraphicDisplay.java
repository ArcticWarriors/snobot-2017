package com.snobot.simulator.gui.module_widget;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.snobot.simulator.gui.Util;
import com.snobot.simulator.module_wrapper.SpeedControllerWrapper;

public class SpeedControllerGraphicDisplay extends BaseWidgetDisplay<Integer, SpeedControllerWrapper>
{
    private class MotorDisplay extends JPanel
    {
        private static final int sDOT_SIZE = 30;

        private double mMotorSpeed;

        public MotorDisplay()
        {
            setPreferredSize(new Dimension(sDOT_SIZE, sDOT_SIZE));
        }

        public void updateDisplay(double aValue)
        {
            mMotorSpeed = aValue;
            repaint();
        }

        @Override
        public void paint(Graphics g)
        {
            g.clearRect(0, 0, getWidth(), getHeight());
            g.setColor(Util.getMotorColor(mMotorSpeed));
            g.fillOval(0, 0, sDOT_SIZE, sDOT_SIZE);
        }
    }

    public SpeedControllerGraphicDisplay(Map<Integer, SpeedControllerWrapper> aMap, String aName)
    {
        super(aMap);
        setBorder(new TitledBorder(aName));
    }

    @Override
    public void update(Map<Integer, SpeedControllerWrapper> aMap)
    {
        for (Entry<Integer, SpeedControllerWrapper> pair : aMap.entrySet())
        {
            ((MotorDisplay) mWidgetMap.get(pair.getKey())).updateDisplay(pair.getValue().get());
        }
    }

    @Override
    protected MotorDisplay createWidget(Entry<Integer, SpeedControllerWrapper> pair)
    {
        return new MotorDisplay();
    }
}
