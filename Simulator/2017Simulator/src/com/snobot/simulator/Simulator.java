package com.snobot.simulator;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import javax.swing.SwingUtilities;

import com.snobot.simulator.gui.SimulatorFrame;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.hal.HAL;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class Simulator
{
    private static final String sUSER_CONFIG_DIR = "user_config/";
    private static final String sPROPERTIES_FILE = sUSER_CONFIG_DIR + "simulator_config.properties";

    private String mClassName; // The name of the class that should be instantiated
    private String mSimulatorClassName; // The name of the class that represents the simulator

    private RobotBase mRobot; // The robot code to run
    private ASimulator mSimulator; // The robot code to run

    public Simulator()
    {
        File config_dir = new File(sUSER_CONFIG_DIR);
        if (!Files.exists(config_dir.toPath()))
        {
            config_dir.mkdir();
        }
    }

    private void loadConfig(String aFile)
    {

        try
        {
            if (!Files.exists(Paths.get(aFile)))
            {
                System.err.println("Could not read properties file, will use defaults and will overwrite the file if it exists");
                Files.copy(Paths.get("_default_properties.properties"), Paths.get(aFile));
            }

            Properties p = new Properties();
            p.load(new FileInputStream(new File(aFile)));

            mClassName = p.getProperty("robot_class");
            mSimulatorClassName = p.getProperty("simulator_class");
            NetworkTable.setPersistentFilename(sUSER_CONFIG_DIR + mClassName + ".preferences.ini");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.err.println("Could not read properties file");
        }
    }

    private void createRobot() throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        System.out.println("*************************************************************");
        System.out.println("*                    Starting Robot Code                    *");
        System.out.println("*************************************************************");

        mRobot = (RobotBase) Class.forName(mClassName).newInstance();
    }

    public void startSimulation() throws InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        RobotBase.initializeHardwareConfiguration();
        loadConfig(sPROPERTIES_FILE);

        // Do all of the stuff that
        HAL.setWaitTime(.02);

        createSimulator();
        createRobot();

        Thread robotThread = new Thread(createRobotThread(), "RobotThread");
        Runnable guiThread = createGuiThread();

        robotThread.start();
        SwingUtilities.invokeLater(guiThread);
    }

    private void createSimulator()
    {
        try
        {
            if (mSimulatorClassName != null && !mSimulatorClassName.isEmpty())
            {
                mSimulator = (ASimulator) Class.forName(mSimulatorClassName).newInstance();
            }
            else
            {
                System.out.println("**********************************************************************************");
                System.out.println("WARNING: Simulator class name was not set up.  Will use default, simple simulation");
                System.out.println("**********************************************************************************");
            }

        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessError | IllegalAccessException e)
        {
            throw new RuntimeException("Could not find simulator class " + mSimulatorClassName);
        }
    }

    private Runnable createGuiThread()
    {
        return new Runnable()
        {

            @Override
            public void run()
            {

                // Even though we don't store it, it will still get
                // created and hook itself up
                try
                {
                    HAL.waitForProgramStart();

                    if (mSimulator != null)
                    {
                        mSimulator.createSimulatorComponents();
                        mSimulator.setRobot(mRobot);
                        System.out.println("Created simulator : " + mSimulatorClassName);

                        RobotStateSingleton.get().addLoopListener(new RobotStateSingleton.LoopListener()
                        {

                            @Override
                            public void looped()
                            {
                                mSimulator.update();
                            }
                        });
                    }

                    SimulatorFrame frame = new SimulatorFrame();
                    frame.pack();
                    frame.setVisible(true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
        };
    }

    private Runnable createRobotThread()
    {
        return new Runnable()
        {

            @Override
            public void run()
            {

                try
                {
                    mRobot.startCompetition();
                }
                catch (UnsatisfiedLinkError e)
                {
                    e.printStackTrace();
                    System.err.println("\n\n\n\n");
                    System.err.println("Unsatisfied link error.  This likely means that there is a native "
                            + "call in WpiLib or the NetworkTables libraries.  Please tell PJ so he can mock it out.\n\nError Message: " + e);

                    System.exit(-1);
                }
                catch (Exception e)
                {
                    System.out.println("Unexpected exception, shutting down simulator");
                    e.printStackTrace();
                    System.exit(-1);
                }
            }
        };
    }
}
