package com.snobot.simulator;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.snobot.DcMotorModelConfig;
import com.snobot.simulator.module_wrapper.EncoderWrapperJni;
import com.snobot.simulator.module_wrapper.RelayWrapperJni;
import com.snobot.simulator.module_wrapper.SolenoidWrapperJni;
import com.snobot.simulator.module_wrapper.SpeedControllerWrapperJni;
import com.snobot.simulator.motor_sim.motors.MakeTransmission;

@SuppressWarnings("unchecked")
public class SimulatorConfigReader
{

    public void loadConfig(String aConfigFile)
    {
        if (aConfigFile == null)
        {
            System.out.println("*******************************************");
            System.out.println("Config file not set, won't hook anything up");
            System.out.println("*******************************************");
            return;
        }

        try
        {
            File file = new File(aConfigFile);
            System.out.println("Loading " + file.getAbsolutePath());
            Yaml yaml = new Yaml();
            parseConfig(yaml.load(new FileReader(file)));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    protected void parseConfig(Object aConfig)
    {
        Map<String, Object> config = (Map<String, Object>) aConfig;

        if (config.containsKey("speed_controllers"))
        {
            parseSpeedControllers((List<Map<String, Object>>) config.get("speed_controllers"));
        }

        if (config.containsKey("quad_encoders"))
        {
            parseEncoders((List<Map<String, Object>>) config.get("quad_encoders"));
        }

        if (config.containsKey("relays"))
        {
            parseRelays((List<Map<String, Object>>) config.get("relays"));
        }

        if (config.containsKey("solenoids"))
        {
            parseSolenoids((List<Map<String, Object>>) config.get("solenoids"));
        }

        if (config.containsKey("tank_drives"))
        {
            parseTankDriveConfig((List<Map<String, Object>>) config.get("tank_drives"));
        }

    }

    protected void parseSolenoids(List<Map<String, Object>> aSolenoids)
    {
        for (Map<String, Object> solenoidConfig : aSolenoids)
        {
            int handle = getIntHandle(solenoidConfig.get("handle"));
            if (solenoidConfig.containsKey("name"))
            {
                SolenoidWrapperJni.setName(handle, solenoidConfig.get("name").toString());
            }
        }
    }

    protected void parseRelays(List<Map<String, Object>> aRelays)
    {
        for (Map<String, Object> relayConfig : aRelays)
        {
            int handle = getIntHandle(relayConfig.get("handle"));
            if (relayConfig.containsKey("name"))
            {
                RelayWrapperJni.setName(handle, relayConfig.get("name").toString());
            }
        }
    }

    protected void parseSpeedControllers(List<Map<String, Object>> aSpeedControllers)
    {
        for (Map<String, Object> scConfig : aSpeedControllers)
        {
            int handle = getIntHandle(scConfig.get("handle"));
            if (scConfig.containsKey("name"))
            {
                SpeedControllerWrapperJni.setName(handle, scConfig.get("name").toString());
            }

            if (scConfig.containsKey("motor_sim"))
            {
                parseMotorSimConfig(handle, (Map<String, Object>) scConfig.get("motor_sim"));
            }
        }
    }

    protected void parseEncoders(List<Map<String, Object>> aEncoders)
    {
        for (Map<String, Object> encConfig : aEncoders)
        {
<<<<<<< HEAD
<<<<<<< HEAD
            int handle = getEncoderHandle(encConfig, "handle_a", "handle_b", "single_handle");
=======
            int handle = -1;

            if (encConfig.containsKey("handle_a") && encConfig.containsKey("handle_b"))
            {
                int handleA = getIntHandle(encConfig.get("handle_a"));
                int handleB = getIntHandle(encConfig.get("handle_b"));

                handle = EncoderWrapperJni.getHandle(handleA, handleB);
            }
            else
            {
                throw new RuntimeException("Could not load encoder config, will cause the program to crash");
            }
>>>>>>> 9fafcd1... Adding a port of the robot code in CPP
=======
            int handle = getEncoderHandle(encConfig, "handle_a", "handle_b", "single_handle");
>>>>>>> 408e130... Adding more CAN simulation

            if (encConfig.containsKey("name"))
            {
                EncoderWrapperJni.setName(handle, encConfig.get("name").toString());
            }

            if (encConfig.containsKey("speed_controller_handle"))
            {
                int speedControllerHandle = getIntHandle(encConfig.get("speed_controller_handle"));
                SimulationConnectorJni.connectEncoderAndSpeedController(handle, speedControllerHandle);
            }
        }
    }

    protected int getEncoderHandle(Map<String, Object> aConfig, String aDoubleHandleAKey, String aDoubleHandleBKey, String aSingleHandleKey)
    {
        int handle = -1;

        if (aConfig.containsKey(aDoubleHandleAKey) && aConfig.containsKey(aDoubleHandleBKey))
        {
            int handleA = getIntHandle(aConfig.get(aDoubleHandleAKey));
            int handleB = getIntHandle(aConfig.get(aDoubleHandleBKey));

            handle = EncoderWrapperJni.getHandle(handleA, handleB);
        }
        else if (aConfig.containsKey(aSingleHandleKey))
        {
            handle = getIntHandle(aConfig.get(aSingleHandleKey));
        }
        else
        {
            throw new RuntimeException("Could not load encoder config, will cause the program to crash");
        }

        return handle;
    }

    protected void parseMotorSimConfig(int aScHandle, Map<String, Object> motorSimConfig)
    {
        String type = motorSimConfig.get("type").toString();
        switch (type)
        {
        case "Simple":
            loadMotorSimSimple(aScHandle, motorSimConfig);
            break;
        case "StaticLoad":
            loadMotorSimStaticLoad(aScHandle, motorSimConfig);
            break;
        case "GravityLoad":
            loadMotorSimGravityLoad(aScHandle, motorSimConfig);
            break;
        case "RotationalLoad":
            loadMotorSimRotationalLoad(aScHandle, motorSimConfig);
            break;
        default:
            System.err.println("Unknown motor sim type: " + type);
        }
    }

    protected void parseTankDriveConfig(List<Map<String, Object>> aTankDriveConfig)
    {
        for (Map<String, Object> tankDriveConfig : aTankDriveConfig)
        {
            int scHandle = getIntHandle(tankDriveConfig.get("gyro_handle"));
            double turnKp = ((Number) tankDriveConfig.get("turn_kp")).doubleValue();

<<<<<<< HEAD
<<<<<<< HEAD
            int leftEncHandle = getEncoderHandle(tankDriveConfig, "left_enc_handle_a", "left_enc_handle_b", "left_single_handle");
            int rightEncHandle = getEncoderHandle(tankDriveConfig, "right_enc_handle_a", "right_enc_handle_b", "right_single_handle");
=======
            int leftEncHandle = EncoderWrapperJni.getHandle(leftEncHandleA, leftEncHandleB);
            int rightEncHandle = EncoderWrapperJni.getHandle(rightEncHandleA, rightEncHandleB);
>>>>>>> 9fafcd1... Adding a port of the robot code in CPP
=======
            int leftEncHandle = getEncoderHandle(tankDriveConfig, "left_enc_handle_a", "left_enc_handle_b", "left_single_handle");
            int rightEncHandle = getEncoderHandle(tankDriveConfig, "right_enc_handle_a", "right_enc_handle_b", "right_single_handle");
>>>>>>> 408e130... Adding more CAN simulation

            SimulationConnectorJni.connectTankDriveSimulator(leftEncHandle, rightEncHandle, scHandle, turnKp);
        }
    }

    protected void loadMotorSimSimple(int aScHandle, Map<String, Object> motorSimConfig)
    {
        double maxSpeed = ((Number) motorSimConfig.get("max_speed")).doubleValue();
        SimulationConnectorJni.setSpeedControllerModel_Simple(aScHandle, maxSpeed);
    }

    protected void loadMotorSimStaticLoad(int aScHandle, Map<String, Object> motorSimConfig)
    {
        double load = ((Number) motorSimConfig.get("load")).doubleValue();
        DcMotorModelConfig motorConfig = createDcMotorModel((Map<String, Object>) motorSimConfig.get("motor_model"));

        if (motorSimConfig.containsKey("conversion_factor"))
        {
            double conversionFactor = ((Number) motorSimConfig.get("conversion_factor")).doubleValue();
            SimulationConnectorJni.setSpeedControllerModel_Static(aScHandle, motorConfig, load, conversionFactor);
        }
        else
        {
            SimulationConnectorJni.setSpeedControllerModel_Static(aScHandle, motorConfig, load);
        }
    }

    protected void loadMotorSimGravityLoad(int aScHandle, Map<String, Object> motorSimConfig)
    {
        double load = ((Number) motorSimConfig.get("load")).doubleValue();
        DcMotorModelConfig motorConfig = createDcMotorModel((Map<String, Object>) motorSimConfig.get("motor_model"));

        SimulationConnectorJni.setSpeedControllerModel_Gravitational(aScHandle, motorConfig, load);
    }

    protected void loadMotorSimRotationalLoad(int aScHandle, Map<String, Object> motorSimConfig)
    {
        double armCenterOfMass = ((Number) motorSimConfig.get("arm_center_of_mass")).doubleValue();
        double armMass = ((Number) motorSimConfig.get("arm_mass")).doubleValue();
        DcMotorModelConfig motorConfig = createDcMotorModel((Map<String, Object>) motorSimConfig.get("motor_model"));

        SimulationConnectorJni.setSpeedControllerModel_Rotational(aScHandle, motorConfig, armCenterOfMass, armMass);
    }

    protected DcMotorModelConfig createDcMotorModel(Map<String, Object> modelConfig)
    {
        DcMotorModelConfig output = null;

        if (modelConfig.containsKey("motor_factory_func"))
        {
            String motorFactoryFunc = modelConfig.get("motor_factory_func").toString();

            int lastDot = motorFactoryFunc.lastIndexOf(".");
            String className = motorFactoryFunc.substring(0, lastDot);
            String methodName = motorFactoryFunc.substring(lastDot + 1);

            try
            {
                Class<?> myClass = Class.forName(className);
                Method myMethod = myClass.getDeclaredMethod(methodName);
                output = (DcMotorModelConfig) myMethod.invoke(null);
            }
            catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalArgumentException | IllegalAccessException
                    | InvocationTargetException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            System.err.println("Only reflection creation is supported");
        }

        if (modelConfig.containsKey("transmission"))
        {
            Map<String, Object> transmissionConfig = (Map<String, Object>) modelConfig.get("transmission");
            int numMotors = (Integer) transmissionConfig.get("num_motors");
            double reduction = ((Number) transmissionConfig.get("gear_reduction")).doubleValue();
            double efficiency = ((Number) transmissionConfig.get("efficiency")).doubleValue();

            output = MakeTransmission.makeTransmission(output, numMotors, reduction, efficiency);
        }

        if (modelConfig.containsKey("inverted"))
        {
            output.setInverted((Boolean) modelConfig.get("inverted"));
        }

        return output;
        // return new DcMotorModelConfig(
        // output.NOMINAL_VOLTAGE,
        // output.FREE_SPEED_RPM,
        // output.FREE_CURRENT,
        // output.STALL_TORQUE,
        // output.STALL_CURRENT,
        // output.mMotorInertia,
        // output.mHasBrake,
        // output.mInverted);
    }

    protected int getIntHandle(Object aHandleObject)
    {
        int output = 0;

        try
        {
            output = (Integer) aHandleObject;
        }
        catch (ClassCastException ex)
        {
            String fullName = aHandleObject.toString();

            int lastDot = fullName.lastIndexOf(".");
            String className = fullName.substring(0, lastDot);
            String fieldName = fullName.substring(lastDot + 1);

            try
            {
                Class<?> myClass = Class.forName(className);
                Field myField = myClass.getDeclaredField(fieldName);
                output = myField.getInt(null);
            }
            catch (ClassNotFoundException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e)
            {
                e.printStackTrace();
            }

        }

        return output;
    }
}
