package com.snobot.simulator.gui.module_widget;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.snobot.simulator.module_wrapper.ASensorWrapper;

public abstract class BaseWidgetDisplay<KeyType, ItemType extends ASensorWrapper> extends JPanel
{

    protected Map<KeyType, Container> mWidgetMap = new HashMap<>();

    public BaseWidgetDisplay(Map<KeyType, ItemType> aMap)
    {
    	setLayout(new GridBagLayout());

    	int i = 0;
        for (Entry<KeyType, ItemType> pair : aMap.entrySet())
        {
        	GridBagConstraints gc = new GridBagConstraints();
        	gc.gridy = i;
        	
            Container panelPair = createWidget(pair);
            if (panelPair != null)
            {
                mWidgetMap.put(pair.getKey(), panelPair);

                gc.gridx = 0;
                add(new JLabel("" + pair.getValue().getName()), gc);

                gc.gridx = 1;
                add(panelPair, gc);

                ++i;
            }
        }
    }

    protected abstract Container createWidget(Entry<KeyType, ItemType> pair);

    public abstract void update(Map<KeyType, ItemType> aMap);

    public boolean isEmpty()
    {
        return mWidgetMap.isEmpty();
    }
}
