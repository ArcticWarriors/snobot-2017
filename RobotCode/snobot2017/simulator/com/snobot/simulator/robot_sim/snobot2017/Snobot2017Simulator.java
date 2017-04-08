package com.snobot.simulator.robot_sim.snobot2017;

import java.util.Map;

import com.snobot.simulator.ASimulator;
import com.snobot.simulator.config.SimulatorConfigReader;
import com.snobot2017.PortMappings2017;

public class Snobot2017Simulator extends ASimulator
{
    private class Snobot2017ConfigReader extends SimulatorConfigReader
    {
        public boolean loadConfig(String aConfigFile)
        {
            if (aConfigFile != null)
            {
                System.out.println("Ignoring provided config file, doing custom thing...");
            }

            String configFile = "simulator_config/snobot2017_NoCan.yml";
            if (PortMappings2017.sUSE_CAN_DRIVETRAIN)
            {
                configFile = "simulator_config/snobot2017_WithCan.yml";
            }

            return super.loadConfig(configFile);
        }

        @Override
        protected void parseConfig(Object aConfig)
        {
            super.parseConfig(aConfig);

            @SuppressWarnings("unchecked")
            Map<String, Object> config = (Map<String, Object>) aConfig;

            boolean simulateCamera = true;

            if (config.containsKey("simulate_camera"))
            {
                simulateCamera = (Boolean) config.get("simulate_camera");
            }

            if (simulateCamera)
            {
                CameraSimulator cameraSimulator = new CameraSimulator();
                // mSimulatorComponenets.add(cameraSimulator);
            }
        }
    }

    @Override
    protected void createSimulatorComponents(String aConfigFile)
    {
        new Snobot2017ConfigReader().loadConfig(aConfigFile);
    }

}
