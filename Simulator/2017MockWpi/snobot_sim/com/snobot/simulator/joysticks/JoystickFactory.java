package com.snobot.simulator.joysticks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.snobot.simulator.joysticks.joystick_specializations.NullJoystick;

public class JoystickFactory
{

    private static final JoystickFactory sInstance = new JoystickFactory();
	private static final String sJOYSTICK_CONFIG_FILE = "user_config/joystick_config.properties";
    private static final int sNUMBER_OF_STICKS = 6;
    private static final String sKEY = "Joystick_";

    private Map<Integer, IMockJoystick> mJoystickMap;

    public static JoystickFactory get()
    {
        return sInstance;
    }

    private JoystickFactory()
    {
        mJoystickMap = new HashMap<Integer, IMockJoystick>();

        String errorMessage = "";
        try
        {
            InputStream input_stream = new FileInputStream(sJOYSTICK_CONFIG_FILE);
            Properties properties = new Properties();
            properties.load(input_stream);
            input_stream.close();

            loadSticks(properties);

        }
        catch (ClassNotFoundException e)
        {
            errorMessage = "  ClassNotFoundException: " + e.getMessage();
        }
        catch (InstantiationException e)
        {
            errorMessage = "  InstantiationException: " + e.getMessage();
        }
        catch (IllegalAccessException e)
        {
            errorMessage = "  IllegalAccessException: " + e.getMessage();
        }
        catch (IOException e)
        {
            errorMessage = "  IOException: " + e.getMessage();
        }

        if (!errorMessage.isEmpty())
        {
            System.err.println("Could not read joystick properties file... will use default joystick");
            System.err.println(errorMessage);

            updateToDefaultMap();
            writeJoystickFile();
        }
    }

    private void loadSticks(Properties properties) throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        ClassLoader classLoader = JoystickFactory.class.getClassLoader();

        for (Entry<Object, Object> i : properties.entrySet())
        {
            int number = Integer.parseInt(i.getKey().toString().substring(sKEY.length()));

            Class<?> aClass = classLoader.loadClass(i.getValue().toString());

            if (IMockJoystick.class.isAssignableFrom(aClass))
            {
                IMockJoystick joystick = (IMockJoystick) aClass.newInstance();
                mJoystickMap.put(number, joystick);
            }
        }
    }

    private void writeJoystickFile()
    {
        try
        {
            String comment = "Acceptable Types:\n  ";

            Properties p = new Properties();
            for (int i = 0; i < sNUMBER_OF_STICKS; ++i)
            {
                p.put(sKEY + i, mJoystickMap.get(i).getClass().getName());
            }

            FileOutputStream stream = new FileOutputStream(sJOYSTICK_CONFIG_FILE);

            p.store(stream, comment);
            System.out.println("Wrote joystick config file to " + new File(sJOYSTICK_CONFIG_FILE).getAbsolutePath());
            stream.close();
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }
    }

    private void updateToDefaultMap()
    {

        for (int i = 0; i < sNUMBER_OF_STICKS; ++i)
        {
            if (!mJoystickMap.containsKey(i))
            {
                mJoystickMap.put(i, new NullJoystick());
            }
        }
    }

    public IMockJoystick get(int aJoystickIndex)
    {
        return mJoystickMap.get(aJoystickIndex);
    }

    public void setJoysticks(List<IMockJoystick> joysticks)
    {

        mJoystickMap.clear();
        for (int i = 0; i < joysticks.size(); ++i)
        {
            mJoystickMap.put(i, joysticks.get(i));
        }
        updateToDefaultMap();
        writeJoystickFile();
    }

    public Map<Integer, IMockJoystick> getJoysticks()
    {
        return mJoystickMap;
    }

}
