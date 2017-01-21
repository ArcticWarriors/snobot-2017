package com.snobot.simulator.gui.module_widget;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.snobot.simulator.module_wrapper.DigitalSourceWrapper;

public class DigitalSourceGraphicDisplay extends BaseWidgetDisplay<Integer, DigitalSourceWrapper>
{
    private class DigitalSourceWrapperDisplay extends JPanel
    {
        private static final int sDOT_SIZE = 30;

        private boolean mState;

        public DigitalSourceWrapperDisplay()
        {
            setPreferredSize(new Dimension(sDOT_SIZE, sDOT_SIZE));
        }

        public void updateDisplay(boolean aValue)
        {
            mState = aValue;
            repaint();
        }

        @Override
        public void paint(Graphics g)
        {
            g.clearRect(0, 0, getWidth(), getHeight());
            g.setColor(mState ? Color.green : Color.red);
            g.fillOval(0, 0, sDOT_SIZE, sDOT_SIZE);
        }
    }

    public DigitalSourceGraphicDisplay(Map<Integer, DigitalSourceWrapper> aMap)
    {
        super(aMap);
        setBorder(new TitledBorder("Digital IO"));
    }

    @Override
    public void update(Map<Integer, DigitalSourceWrapper> aMap)
    {
        for (Entry<Integer, DigitalSourceWrapper> pair : aMap.entrySet())
        {
            if (!pair.getValue().isEncoder())
            {
                ((DigitalSourceWrapperDisplay) mWidgetMap.get(pair.getKey())).updateDisplay(pair.getValue().get());
            }
        }
    }

    @Override
    protected DigitalSourceWrapperDisplay createWidget(Entry<Integer, DigitalSourceWrapper> pair)
    {
        if (pair.getValue().isEncoder())
        {
            return null;
        }
        return new DigitalSourceWrapperDisplay();
    }
}
