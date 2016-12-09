/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2016. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.hal;

import java.nio.ByteBuffer;

import com.snobot.simulator.RobotStateSingleton;
import com.snobot.simulator.SensorActuatorRegistry;
import com.snobot.simulator.joysticks.IMockJoystick;
import com.snobot.simulator.joysticks.JoystickFactory;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.hal.FRCNetComm.tResourceType;

/**
 * JNI Wrapper for HAL<br>
 * .
 */
@SuppressWarnings(
{ "AbbreviationAsWordInName", "MethodName" })
public class HAL extends JNIWrapper
{
    public static void waitForDSData()
    {
        Timer.delay(sWaitTime);
        sMatchTime += sCYCLE_TIME;

        RobotStateSingleton.get().updateLoopListeners();
    }

    public static int initialize(int mode)
    {
        return 1;
    }

    public static void observeUserProgramStarting()
    {
        synchronized (sPROGRAM_STARTED_LOCK)
        {
            System.out.println("Robot Initialized");
            System.out.println("*************************************************************");
            System.out.println("*                    Starting Robot Code                    *");
            System.out.println("*************************************************************");
            System.out.println("\n\n");
            sPROGRAM_STARTED_LOCK.notify();
        }
    }

    public static void observeUserProgramDisabled()
    {

    }

    public static void observeUserProgramAutonomous()
    {

    }

    public static void observeUserProgramTeleop()
    {

    }

    public static void observeUserProgramTest()
    {

    }

    public static void report(int resource, int instanceNumber)
    {
        report(resource, instanceNumber, 0, "");
    }

    public static void report(int resource, int instanceNumber, int context)
    {
        report(resource, instanceNumber, context, "");
    }

    /**
     * Report the usage of a resource of interest. <br>
     *
     * <p>
     * Original signature:
     * <code>uint32_t report(tResourceType, uint8_t, uint8_t, const
     * char*)</code>
     *
     * @param resource
     *            one of the values in the tResourceType above (max value 51).
     *            <br>
     * @param instanceNumber
     *            an index that identifies the resource instance. <br>
     * @param context
     *            an optional additional context number for some cases (such as
     *            module number). Set to 0 to omit. <br>
     * @param feature
     *            a string to be included describing features in use on a
     *            specific resource. Setting the same resource more than once
     *            allows you to change the feature string.
     */
    public static int report(int resource, int instanceNumber, int context, String feature)
    {
        if (resource == tResourceType.kResourceType_Solenoid)
        {
            SensorActuatorRegistry.get().getSolenoids().get(instanceNumber).setIsReal(true);
        }
        else if (resource == tResourceType.kResourceType_Joystick)
        {
            JoystickFactory.get().registerJoystickCreated(instanceNumber);
        }
        return 0;
    }

    private static native int nativeGetControlWord();

    @SuppressWarnings("JavadocMethod")
    public static void getControlWord(ControlWord controlWord)
    {
        sROBOT_STATE.updateControlWord(controlWord);
    }

    private static native int nativeGetAllianceStation();

    @SuppressWarnings("JavadocMethod")
    public static AllianceStationID getAllianceStation()
    {
        switch (nativeGetAllianceStation())
        {
        case 0:
            return AllianceStationID.Red1;
        case 1:
            return AllianceStationID.Red2;
        case 2:
            return AllianceStationID.Red3;
        case 3:
            return AllianceStationID.Blue1;
        case 4:
            return AllianceStationID.Blue2;
        case 5:
            return AllianceStationID.Blue3;
        default:
            return null;
        }
    }

    public static int kMaxJoystickAxes = 12;
    public static int kMaxJoystickPOVs = 12;

    public static short getJoystickAxes(byte joystickNum, float[] axesArray)
    {
        IMockJoystick joystick = sJOYSTICK_FACTORY.get(joystickNum);
        short[] joystickValue = joystick.getAxisValues();

        for (int i = 0; i < joystickValue.length; ++i)
        {
            axesArray[i] = joystickValue[i] * 127;
        }

        return (short) joystickValue.length;
    }

    public static short getJoystickPOVs(byte joystickNum, short[] povsArray)
    {
        IMockJoystick joystick = sJOYSTICK_FACTORY.get(joystickNum);
        short[] joystickValue = joystick.getPovValues();

        for (int i = 0; i < joystickValue.length; ++i)
        {
            povsArray[i] = joystickValue[i];
        }

        return (short) joystickValue.length;
    }

    public static int getJoystickButtons(byte joystickNum, ByteBuffer count)
    {
        int num_buttons = sJOYSTICK_FACTORY.get(joystickNum).getButtonCount();
        int masked_values = sJOYSTICK_FACTORY.get(joystickNum).getButtonMask();

        count.clear();
        count.put((byte) num_buttons);
        count.position(0);

        return masked_values;
    }

    public static int setJoystickOutputs(byte joystickNum, int outputs, short leftRumble, short rightRumble)
    {
        sJOYSTICK_FACTORY.get(joystickNum).setRumble(leftRumble);
        return 0;
    }

    public static int getJoystickIsXbox(byte joystickNum)
    {
        return 0;
    }

    public static int getJoystickType(byte joystickNum)
    {
        return 0;
    }

    public static String getJoystickName(byte joystickNum)
    {
        return "Joystick " + joystickNum;
    }

    public static int getJoystickAxisType(byte joystickNum, byte axis)
    {
        return 0;
    }

    public static double getMatchTime()
    {
        return sMatchTime;
    }

    public static boolean getSystemActive()
    {
        return false;
    }

    public static boolean getBrownedOut()
    {
        return false;
    }

    public static int setErrorData(String error)
    {
        return 0;
    }

    public static int sendError(boolean isError, int errorCode, boolean isLVCode, String details, String location, String callStack, boolean printMsg)
    {
        System.err.println("HAL::" + details);
        return 0;
    }

    // **************************************************
    // Our stuff
    // **************************************************
    private static final RobotStateSingleton sROBOT_STATE = RobotStateSingleton.get();
    private static final JoystickFactory sJOYSTICK_FACTORY = JoystickFactory.get();
    private static final Object sPROGRAM_STARTED_LOCK = new Object();

    private static final double sCYCLE_TIME = .02; // The period that the main
                                                   // loop should be run at

    private static double sWaitTime = .02; // The time to sleep. You can run
                                           // simulations faster/slower by
                                           // changing this. For example,
                                           // making the wait time 1 second,
                                           // means one 20ms cycle will happen
                                           // each second, 50x slower than
                                           // normal. Or, you could make it
                                           // .002, which would make the code
                                           // execute at 10x speed

    private static double sMatchTime = 0;

    public static void setWaitTime(double aTime)
    {
        sWaitTime = aTime;
    }

    public static double getCycleTime()
    {
        return sCYCLE_TIME;
    }

    public static void waitForProgramStart()
    {
        synchronized (sPROGRAM_STARTED_LOCK)
        {
            try
            {
                sPROGRAM_STARTED_LOCK.wait();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

    }
}
