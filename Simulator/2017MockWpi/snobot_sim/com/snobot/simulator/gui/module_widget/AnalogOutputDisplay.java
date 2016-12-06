package com.snobot.simulator.gui.module_widget;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.snobot.simulator.gui.Util;
import com.snobot.simulator.module_wrapper.AnalogWrapper;

public class AnalogOutputDisplay extends BaseWidgetDisplay<Integer, AnalogWrapper>
{

    private class AnalogDisplay extends JPanel
    {
        private static final int sDOT_SIZE = 30;

        private double mMotorSpeed;

        public AnalogDisplay()
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
            g.setColor(Util.colorGetShadedColor(mMotorSpeed, 5, 0));
            g.fillOval(0, 0, sDOT_SIZE, sDOT_SIZE);
        }
    }

    public AnalogOutputDisplay(Map<Integer, AnalogWrapper> aMap)
    {
        super(aMap);
        setBorder(new TitledBorder("Analog"));
    }

    @Override
    public void update(Map<Integer, AnalogWrapper> aMap)
    {
        for (Entry<Integer, AnalogWrapper> pair : aMap.entrySet())
        {
            ((AnalogDisplay) mWidgetMap.get(pair.getKey())).updateDisplay(pair.getValue().getVoltage());
        }
    }

    @Override
    protected AnalogDisplay createWidget(Entry<Integer, AnalogWrapper> pair)
    {
        return new AnalogDisplay();
    }
}
