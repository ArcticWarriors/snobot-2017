package com.snobot.simulator.joysticks;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

        Map<String, ControllerConfiguration> controllerConfig = new HashMap<>();
        Map<String, Integer> deviceCounter = new LinkedHashMap<>();

        controllerConfig.clear();
        for (Controller controller : availableControllers)
        {
            if (sTYPES_TO_IGNORE.contains(controller.getType()))
            {
                continue;
            }

            Class<? extends IMockJoystick> specializationClass = controller.getType() == Type.KEYBOARD ? KeyboardJoystick.class
                    : GenericGamepadJoystick.class;

            int deviceCtr = deviceCounter.getOrDefault(controller.getName(), 0);
            deviceCounter.put(controller.getName(), ++deviceCtr);

            String deviceName = controller.getName() + " " + deviceCtr;

            ControllerConfiguration configuration = new ControllerConfiguration(controller, specializationClass);
            controllerConfig.put(deviceName, configuration);
        }

        return controllerConfig;
    }
}
