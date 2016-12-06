package com.snobot.simulator.gui;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.gui.module_widget.AnalogOutputDisplay;
import com.snobot.simulator.gui.module_widget.DigitalSourceGraphicDisplay;
import com.snobot.simulator.gui.module_widget.EncoderGraphicDisplay;
import com.snobot.simulator.gui.module_widget.RelayGraphicDisplay;
import com.snobot.simulator.gui.module_widget.SolenoidGraphicDisplay;
import com.snobot.simulator.gui.module_widget.SpeedControllerGraphicDisplay;

public class GraphicalSensorDisplayPanel extends JPanel
{
    public GraphicalSensorDisplayPanel()
    {
        create();
    }

    private SpeedControllerGraphicDisplay mSpeedControllerPanel;
    private SpeedControllerGraphicDisplay mCanSpeedControllerPanel;
    private SolenoidGraphicDisplay mSolenoidPanel;
    private DigitalSourceGraphicDisplay mDigitalSourcePanel;
    private RelayGraphicDisplay mRelayPanel;
    private AnalogOutputDisplay mAnalogPanel;
    private EncoderGraphicDisplay mEncoderPanel;
    private EncoderGraphicDisplay mCanEncoderPanel;

    public void create()
    {
        SensorActuatorRegistry reg = SensorActuatorRegistry.get();

        mSpeedControllerPanel = new SpeedControllerGraphicDisplay(reg.getSpeedControllers(), "Speed Controllers");
        mCanSpeedControllerPanel = new SpeedControllerGraphicDisplay(reg.getCanSpeedControllers(), "CAN Speed Controllers");
        mSolenoidPanel = new SolenoidGraphicDisplay(reg.getSolenoids());
        mDigitalSourcePanel = new DigitalSourceGraphicDisplay(reg.getDigitalSources());
        mRelayPanel = new RelayGraphicDisplay(reg.getRelays());
        mAnalogPanel = new AnalogOutputDisplay(reg.getAnalog());
        mEncoderPanel = new EncoderGraphicDisplay(reg.getEncoders(), "Encoders (Digital Input)");
        mCanEncoderPanel = new EncoderGraphicDisplay(reg.getCanEncoders(), "Encoders (CAN)");
        System.out.println(reg.getCanEncoders());

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        if (!mSpeedControllerPanel.isEmpty())
        {
            add(mSpeedControllerPanel);
        }
        if (!mCanSpeedControllerPanel.isEmpty())
        {
            add(mCanSpeedControllerPanel);
        }
        if (!mSolenoidPanel.isEmpty())
        {
            add(mSolenoidPanel);
        }
        if (!mDigitalSourcePanel.isEmpty())
        {
            add(mDigitalSourcePanel);
        }
        if (!mRelayPanel.isEmpty())
        {
            add(mRelayPanel);
        }
        if (!mAnalogPanel.isEmpty())
        {
            add(mAnalogPanel);
        }
        if (!mEncoderPanel.isEmpty())
        {
            add(mEncoderPanel);
        }
        if (!mCanEncoderPanel.isEmpty())
        {
            add(mCanEncoderPanel);
        }
    }

    public void update()
    {
        SensorActuatorRegistry reg = SensorActuatorRegistry.get();

        mSpeedControllerPanel.update(reg.getSpeedControllers());
        mCanSpeedControllerPanel.update(reg.getCanSpeedControllers());
        mSolenoidPanel.update(reg.getSolenoids());
        mDigitalSourcePanel.update(reg.getDigitalSources());
        mRelayPanel.update(reg.getRelays());
        mAnalogPanel.update(reg.getAnalog());
        mEncoderPanel.update(reg.getEncoders());
        mCanEncoderPanel.update(reg.getCanEncoders());
    }
}
