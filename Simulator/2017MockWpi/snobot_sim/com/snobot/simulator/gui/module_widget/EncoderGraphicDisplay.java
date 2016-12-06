package com.snobot.simulator.gui.module_widget;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.snobot.simulator.module_wrapper.EncoderWrapper;

public class EncoderGraphicDisplay<T> extends BaseWidgetDisplay<T, EncoderWrapper>
{
    private class EncoderWrapperDisplay extends JPanel
    {

        private JTextField mRawField;
        private JTextField mDistanceField;

        public EncoderWrapperDisplay()
        {
            mRawField = new JTextField(6);
            mDistanceField = new JTextField(6);
            add(mRawField);
            add(mDistanceField);
        }

        public void updateDisplay(EncoderWrapper aValue)
        {
            if(aValue.isHookedUp())
            {
                DecimalFormat df = new DecimalFormat("#.###");
                mRawField.setText("" + aValue.getRaw());
                mDistanceField.setText(df.format(aValue.getDistance()));
            }
            else
            {
                mRawField.setText("Not Hooked Up");
                mDistanceField.setText("Not Hooked Up");
            }
        }
    }

    public EncoderGraphicDisplay(Map<T, EncoderWrapper> map, String aString)
    {
        super(map);
        setBorder(new TitledBorder(aString));
    }

    @Override
    public void update(Map<T, EncoderWrapper> aMap)
    {
        for (Entry<T, EncoderWrapper> pair : aMap.entrySet())
        {
            ((EncoderWrapperDisplay) mWidgetMap.get(pair.getKey())).updateDisplay(pair.getValue());
        }
    }

    @Override
    protected EncoderWrapperDisplay createWidget(Entry<T, EncoderWrapper> pair)
    {
        return new EncoderWrapperDisplay();
    }
}
