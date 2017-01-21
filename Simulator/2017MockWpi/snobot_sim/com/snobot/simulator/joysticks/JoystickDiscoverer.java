package com.snobot.simulator.joysticks;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.snobot.simulator.joysticks.joystick_specializations.GenericGamepadJoystick;
import com.snobot.simulator.joysticks.joystick_specializations.KeyboardJoystick;
import com.snobot.simulator.joysticks.joystick_specializations.Ps4Joystick;
import com.snobot.simulator.joysticks.joystick_specializations.XboxJoystick;

import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;
import net.java.games.input.ControllerEnvironment;

public class JoystickDiscoverer
{
    private static final List<Type> sTYPES_TO_IGNORE = Arrays.asList(Type.UNKNOWN, Type.MOUSE);

    public static final Map<Class<? extends IMockJoystick>, String> sAVAILABLE_SPECIALIZATIONS;

    static
    {
        sAVAILABLE_SPECIALIZATIONS = new HashMap<>();
        sAVAILABLE_SPECIALIZATIONS.put(Ps4Joystick.class, "PS4");
        sAVAILABLE_SPECIALIZATIONS.put(GenericGamepadJoystick.class, "Generic Gamepad");
        sAVAILABLE_SPECIALIZATIONS.put(XboxJoystick.class, "XBOX");
        sAVAILABLE_SPECIALIZATIONS.put(KeyboardJoystick.class, "Keyboard");
    }

    private JoystickDiscoverer()
    {

    }

    public static Map<String, ControllerConfiguration> rediscoverJoysticks()
    {
        Controller[] availableControllers = ControllerEnvironment.getDefaultEnvironment().getControllers();

        Map<String, ControllerConfiguration> controllerConfig = new LinkedHashMap<>();
        Map<String, Integer> deviceCounter = new LinkedHashMap<>();

        controllerConfig.clear();
        for (Controller controller : availableControllers)
        {
            if (sTYPES_TO_IGNORE.contains(controller.getType()))
            {
                continue;
            }

            Class<? extends IMockJoystick> specializationClass = getDefaultSpecialization(controller);

            int deviceCtr = deviceCounter.getOrDefault(controller.getName(), 0);
            deviceCounter.put(controller.getName(), ++deviceCtr);

            String deviceName = controller.getName() + " " + deviceCtr;

            ControllerConfiguration configuration = new ControllerConfiguration(controller, specializationClass);
            controllerConfig.put(deviceName, configuration);
        }

        return sort(controllerConfig);
    }

    // http://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values-java
    private static Map<String, ControllerConfiguration> sort(Map<String, ControllerConfiguration> aMap)
    {
        List<Map.Entry<String, ControllerConfiguration>> list = new LinkedList<Map.Entry<String, ControllerConfiguration>>(aMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, ControllerConfiguration>>()
        {
            public int compare(Map.Entry<String, ControllerConfiguration> o1, Map.Entry<String, ControllerConfiguration> o2)
            {
                // Hacky sort, we just want the keyboard to bubble down
                Class<? extends IMockJoystick> clazz1 = o1.getValue().mSpecialization;
                Class<? extends IMockJoystick> clazz2 = o2.getValue().mSpecialization;

                int clazz1Value = KeyboardJoystick.class.equals(clazz1) ? -1 : 1;
                int clazz2Value = KeyboardJoystick.class.equals(clazz2) ? -1 : 1;

                return clazz2Value - clazz1Value;
            }
        });

        Map<String, ControllerConfiguration> result = new LinkedHashMap<String, ControllerConfiguration>();
        for (Map.Entry<String, ControllerConfiguration> entry : list)
        {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    private static Class<? extends IMockJoystick> getDefaultSpecialization(Controller aController)
    {
        Class<? extends IMockJoystick> output = null;

        Type type = aController.getType();
        String name = aController.getName();

        if (type == Type.KEYBOARD)
        {
            output = KeyboardJoystick.class;
        }
        else if (type == Type.GAMEPAD)
        {
            switch (name)
            {
            default:
                System.err.println("Unknown joystick name " + name);
                output = GenericGamepadJoystick.class;
            }
        }
        else if (type == Type.STICK)
        {
            switch (name)
            {
            case "Wireless Controller":
                output = Ps4Joystick.class;
                break;
            default:
                System.err.println("Unknown joystick name " + name);
                output = GenericGamepadJoystick.class;
            }
        }
        else
        {
            System.err.println("Unknown joystick type " + type);
        }

        return output;
    }
}
