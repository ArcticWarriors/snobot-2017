package com.snobot.simulator;

import java.util.HashMap;
import java.util.Map;

import com.snobot.simulator.module_wrapper.AnalogWrapper;
import com.snobot.simulator.module_wrapper.CompressorWrapper;
import com.snobot.simulator.module_wrapper.DigitalSourceWrapper;
import com.snobot.simulator.module_wrapper.EncoderWrapper;
import com.snobot.simulator.module_wrapper.RelayWrapper;
import com.snobot.simulator.module_wrapper.SolenoidWrapper;
import com.snobot.simulator.module_wrapper.SpeedControllerWrapper;

public class SensorActuatorRegistry
{
    public static class EncoderPair
    {
        public int portA;
        public int portB;

        public EncoderPair(int aPortA, int aPortB)
        {
            portA = aPortA;
            portB = aPortB;
        }

        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result + portA;
            result = prime * result + portB;
            return result;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            EncoderPair other = (EncoderPair) obj;
            if (portA != other.portA)
                return false;
            if (portB != other.portB)
                return false;
            return true;
        }

    }

    private static SensorActuatorRegistry mInstance = new SensorActuatorRegistry();

    private Map<Integer, SpeedControllerWrapper> mSpeedControllerMap = new HashMap<Integer, SpeedControllerWrapper>();
    private Map<Integer, SpeedControllerWrapper> mCanSpeedControllerMap = new HashMap<Integer, SpeedControllerWrapper>();
    private Map<Integer, SolenoidWrapper> mSolenoidMap = new HashMap<Integer, SolenoidWrapper>();
    private Map<Integer, RelayWrapper> mRelayMap = new HashMap<Integer, RelayWrapper>();
    private Map<Integer, DigitalSourceWrapper> mDigitalInputMap = new HashMap<Integer, DigitalSourceWrapper>();
    private Map<Integer, AnalogWrapper> mAnalogMap = new HashMap<Integer, AnalogWrapper>();
    private Map<Integer, EncoderWrapper> mCanEncoderMap = new HashMap<Integer, EncoderWrapper>();
    private Map<EncoderPair, EncoderWrapper> mEncoderMap = new HashMap<EncoderPair, EncoderWrapper>();
    private CompressorWrapper mCompressor = new CompressorWrapper();

    private SensorActuatorRegistry()
    {

    }

    public static SensorActuatorRegistry get()
    {
        return mInstance;
    }

    public <ItemType> boolean registerItem(ItemType aItem, int aPort, Map<Integer, ItemType> aMap, String aMessage)
    {
        if (aMap.containsKey(aPort) || aPort < 0)
        {
            return false;
        }
        aMap.put(aPort, aItem);

        return true;
    }

    public boolean register(AnalogWrapper aActuator, int aPort)
    {
        return registerItem(aActuator, aPort, mAnalogMap, "Analog");
    }

    public boolean register(SpeedControllerWrapper aActuator, int aPort, boolean aIsCan)
    {
        if (aIsCan)
        {
            return registerItem(aActuator, aPort, mCanSpeedControllerMap, "CAN Speed Controller");
        }
        else
        {
            return registerItem(aActuator, aPort, mSpeedControllerMap, "Speed Controller");
        }
    }

    public boolean register(DigitalSourceWrapper aSensor, int aPort)
    {
        return registerItem(aSensor, aPort, mDigitalInputMap, "Digital IO");
    }

    public boolean register(SolenoidWrapper aActuator, int aPort)
    {
        return registerItem(aActuator, aPort, mSolenoidMap, "Solenoid");
    }

    public boolean register(RelayWrapper aActuator, int aPort)
    {
        return registerItem(aActuator, aPort, mRelayMap, "Relay");
    }

    public boolean register(EncoderWrapper aEncoder, EncoderPair aPorts)
    {
        if (mEncoderMap.containsKey(aPorts))
        {
            return false;
        }
        mEncoderMap.put(aPorts, aEncoder);

        return true;
    }

    public boolean register(EncoderWrapper aEncoder, int aPort)
    {
        if (mCanEncoderMap.containsKey(aPort))
        {
            return false;
        }
        mCanEncoderMap.put(aPort, aEncoder);

        return true;
    }

    public Map<Integer, SpeedControllerWrapper> getSpeedControllers()
    {
        return mSpeedControllerMap;
    }

    public Map<Integer, SpeedControllerWrapper> getCanSpeedControllers()
    {
        return mCanSpeedControllerMap;
    }

    public Map<Integer, SolenoidWrapper> getSolenoids()
    {
        return mSolenoidMap;
    }

    public Map<Integer, DigitalSourceWrapper> getDigitalSources()
    {
        return mDigitalInputMap;
    }

    public Map<Integer, RelayWrapper> getRelays()
    {
        return mRelayMap;
    }

    public Map<Integer, AnalogWrapper> getAnalog()
    {
        return mAnalogMap;
    }

    public Map<Integer, EncoderWrapper> getCanEncoders()
    {
        return mCanEncoderMap;
    }

    public Map<EncoderPair, EncoderWrapper> getEncoders()
    {
        return mEncoderMap;
    }

    public EncoderWrapper getEncoder(int aPortA, int aPortB)
    {
        return mEncoderMap.get(new EncoderPair(aPortA, aPortB));
    }

    public CompressorWrapper getCompressor()
    {
        return mCompressor;
    }
}
