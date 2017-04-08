/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2016-2017. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.hal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
<<<<<<< HEAD
<<<<<<< HEAD
import java.io.OutputStream;
import java.util.Random;
=======
>>>>>>> 8da7889... Fixing bugs
=======
import java.io.OutputStream;
import java.util.Random;
>>>>>>> 9fafcd1... Adding a port of the robot code in CPP

import edu.wpi.first.wpilibj.networktables.NetworkTablesJNI;

//
// base class for all JNI wrappers
//
public class JNIWrapper
{
<<<<<<< HEAD
<<<<<<< HEAD
    static boolean libraryLoaded = false;
<<<<<<< HEAD

    private static void loadLibrary(String aLibraryName) throws IOException
=======
    private static boolean libraryLoaded = false;
=======
>>>>>>> 6761f30... Making the simulator able to run CPP projects 
=======
    private static boolean libraryLoaded = false;
>>>>>>> 9fafcd1... Adding a port of the robot code in CPP
    
    private static void createAndLoadTempLibrary(File aTempDir, String aResourceName) throws IOException
    {
        String fileName = aResourceName.substring(aResourceName.lastIndexOf("/") + 1);
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        String basename = fileName.substring(0, fileName.lastIndexOf("."));

        InputStream is = NetworkTablesJNI.class.getResourceAsStream(aResourceName);
        if (is != null)
        {
            File jniLibrary = new File(aTempDir, fileName);

            // flag for delete on exit
            jniLibrary.deleteOnExit();
            OutputStream os = new FileOutputStream(jniLibrary);

            byte[] buffer = new byte[1024];
            int readBytes;
            try
            {
                while ((readBytes = is.read(buffer)) != -1)
                {
                    os.write(buffer, 0, readBytes);
                }
            }
            finally
            {
                os.close();
                is.close();
            }

            System.out.println("Created temporary library at " + jniLibrary.getAbsolutePath() + " from resource " + aResourceName);
            System.load(jniLibrary.getAbsolutePath());
        }
    }

    private static void loadLibrary(File aTempDir, String aLibraryname)
<<<<<<< HEAD
>>>>>>> 0485e69... Merge conflict fix
=======
>>>>>>> 9fafcd1... Adding a port of the robot code in CPP
    {
        String osName = System.getProperty("os.name");

        String resname;
        if (osName.startsWith("Windows"))
        {
            resname = "/Windows/" + System.getProperty("os.arch") + "/";
            resname += "snobotSimHal.dll";
        }
        else
        {
<<<<<<< HEAD
            resname = "/" + osName + "/" + System.getProperty("os.arch") + "/";
            if (osName.startsWith("Windows"))
            {
                resname += "snobotSimHal.dylib";
            }
            else
            {
                resname += "snobotSimHal.so";
            }
        }

        InputStream is = NetworkTablesJNI.class.getResourceAsStream(resname);
        if (is != null)
        {
            File jniLibrary;
            // create temporary file
            if (osName.startsWith("Windows"))
            {
                jniLibrary = File.createTempFile(aLibraryName, ".dll");
            }
            else if (osName.startsWith("Mac"))
            {
                jniLibrary = File.createTempFile(aLibraryName, ".dylib");
            }
            else
            {
                jniLibrary = File.createTempFile(aLibraryName, ".so");
            }

            // flag for delete on exit
            jniLibrary.deleteOnExit();
            OutputStream os = new FileOutputStream(jniLibrary);

            byte[] buffer = new byte[1024];
            int readBytes;
            try
            {
                while ((readBytes = is.read(buffer)) != -1)
                {
                    os.write(buffer, 0, readBytes);
                }
            }
            finally
            {
                os.close();
                is.close();
            }
            System.load(jniLibrary.getAbsolutePath());
            System.out.println("Loaded " + jniLibrary);
=======
            resname = "/" + osname + "/" + System.getProperty("os.arch") + "/";
        }

        if (osname.startsWith("Windows"))
        {
            resname += aLibraryname + ".dll";
        }
        else if (osname.startsWith("Mac"))
        {
            resname += aLibraryname + ".dylib";
        }
        else
        {
            resname += "lib" + aLibraryname + ".so";
        }

        try
        {
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> 408e130... Adding more CAN simulation
            if(aTempDir == null)
            {
                File f = new File("../2017MockWpi/native_wpi_libs" + resname);
                System.out.println(f.getAbsolutePath());
                System.load(f.getAbsolutePath());
            }
            else
            {
                createAndLoadTempLibrary(aTempDir, resname);
            }
<<<<<<< HEAD
=======
            createAndLoadTempLibrary(aTempDir, resname);
>>>>>>> 9fafcd1... Adding a port of the robot code in CPP
=======
>>>>>>> 408e130... Adding more CAN simulation
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException("Could not load " + resname);
>>>>>>> 0485e69... Merge conflict fix
        }
    }

    static
    {
        if (!libraryLoaded)
        {
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
            try
            {
                loadLibrary("wpilibJavaJNI");
                // loadLibrary("HALAthena");
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
                System.exit(1);
                }

=======
=======
>>>>>>> 9fafcd1... Adding a port of the robot code in CPP
            long rando = new Random().nextLong();
            File tempDir = new File("temp/" + rando + "/");
            tempDir.mkdirs();
            tempDir.deleteOnExit();
<<<<<<< HEAD
=======
=======
>>>>>>> 408e130... Adding more CAN simulation
            boolean copyLibraries = false;
            File tempDir = null;

            if (copyLibraries)
            {
                long rando = new Random().nextLong();
                tempDir = new File("temp/" + rando + "/");
                tempDir.mkdirs();
                tempDir.deleteOnExit();
            }
<<<<<<< HEAD
>>>>>>> 3a4a39b... Adding more CAN simulation
=======
>>>>>>> 9fafcd1... Adding a port of the robot code in CPP
=======
>>>>>>> 408e130... Adding more CAN simulation

            loadLibrary(tempDir, "snobotSimHal");
            loadLibrary(tempDir, "HALAthena");
            loadLibrary(tempDir, "wpilibJavaJNI");
            loadLibrary(tempDir, "ntcore");
            loadLibrary(tempDir, "wpiutil");
            loadLibrary(tempDir, "wpilibc");
<<<<<<< HEAD
<<<<<<< HEAD
            loadLibrary(tempDir, "ctreOverride");
>>>>>>> 0485e69... Merge conflict fix
=======
//            loadLibrary("libwpiutil");
            loadLibrary("snobotSimHal");
            loadLibrary("HALAthena");
            loadLibrary("wpilibJavaJNI");
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> 8da7889... Fixing bugs
=======
            loadLibrary("wpilibc");
>>>>>>> 6761f30... Making the simulator able to run CPP projects 
=======
            // loadLibrary("wpiutil");
            // loadLibrary("wpilibc");
>>>>>>> 48caa8b... Updating to match libraries
=======
            loadLibrary("snobotSimHal");
            loadLibrary("HALAthena");
            loadLibrary("wpilibJavaJNI");
            loadLibrary("ntcore");
            loadLibrary("wpiutil");
            loadLibrary("wpilibc");
>>>>>>> 1a6d3d7... C++ Simulator works!
=======
>>>>>>> 9fafcd1... Adding a port of the robot code in CPP
=======
            loadLibrary(tempDir, "ctreOverride");
>>>>>>> 2006634... CANTalon sim
            libraryLoaded = true;
        }
    }

    public static int getPortWithModule(byte module, byte channel)
    {
        return channel;
    }

    public static int getPort(byte channel)
    {
        return channel;
    }
}
