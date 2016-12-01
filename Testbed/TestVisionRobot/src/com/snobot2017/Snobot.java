package com.snobot2017;

import java.text.SimpleDateFormat;

import com.snobot.xlib.ASnobot;
import com.snobot2017.drivetrain.IDriveTrain;
import com.snobot2017.drivetrain.SnobotDriveTrainWithCan;
import com.snobot2017.joystick.SnobotDriveXboxJoystick;
import com.snobot2017.positioner.IPositioner;
import com.snobot2017.positioner.SimplePositioner;
import com.snobot2017.vision.VisionAdbServer;
import com.snobot2017.vision.VisionManager;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Snobot extends ASnobot
{
    // Modules
    private IDriveTrain mDrivetrain;
    private IPositioner mPositioner;

    // Vision
    private VisionAdbServer mVisionServer;
    private VisionManager mVisionManager;

    public Snobot()
    {
        super(new SimpleDateFormat("yyyyMMdd_hhmmssSSS"), Properties2017.sLOG_COUNT.getValue(), Properties2017.sLOG_FILE_PATH.getValue());

        // Raw Joysticks
        Joystick rawDriverJoystick = new Joystick(PortMappings2017.sDRIVER_JOYSTICK_PORT);

        // Our Joysticks
        SnobotDriveXboxJoystick driverXbox = new SnobotDriveXboxJoystick(rawDriverJoystick);
        mSubsystems.add(driverXbox);

        // Drive train
        CANTalon leftMotorA = new CANTalon(PortMappings2017.sDRIVE_CAN_LEFT_A_PORT);
        CANTalon leftMotorB = new CANTalon(PortMappings2017.sDRIVE_CAN_LEFT_B_PORT);
        CANTalon rightMotorA = new CANTalon(PortMappings2017.sDRIVE_CAN_RIGHT_A_PORT);
        CANTalon rightMotorB = new CANTalon(PortMappings2017.sDRIVE_CAN_RIGHT_B_PORT);
        mDrivetrain = new SnobotDriveTrainWithCan(leftMotorA, leftMotorB, rightMotorA, rightMotorB, driverXbox, mLogger);
        mSubsystems.add(mDrivetrain);

        // Positioner
        Gyro gyro = new ADXRS450_Gyro();
        mPositioner = new SimplePositioner(gyro, mDrivetrain, mLogger);
        mSubsystems.add(mPositioner);

        // Vision
        mVisionServer = new VisionAdbServer(Properties2017.sVISION_COMMS_PORT.getValue());
        mVisionManager = new VisionManager(driverXbox, mVisionServer);
        mSubsystems.add(mVisionManager);
    }

    @Override
    public void robotInit()
    {
        init();
    }

    public IDriveTrain getDriveTrain()
    {
        return this.mDrivetrain;
    }
}
