package com.snobot2017.joystick;

import com.snobot.lib.Utilities;
import com.snobot.lib.logging.ILogger;
import com.snobot.lib.ui.LatchedButton;
import com.snobot.lib.ui.XboxButtonMap;
import com.snobot2017.SmartDashBoardNames;
import com.snobot2017.autonomous.AutonomousFactory;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SnobotDriveXbaxJoystick implements IDriverJoystick, IAutoLoggerJoystick
{
	private static final double sJOYSTICK_DEADBAND = .032;
    private Joystick mJoystick;
    private double mLeftSpeed;
    private double mRightSpeed;
    private ILogger mLogger;
    private LatchedButton mDPadUp;
    private LatchedButton mDPadDown;
    private AutonomousFactory mAutonFactory;

    public SnobotDriveXbaxJoystick(Joystick aJoystick, ILogger aLogger, AutonomousFactory aAutonFactory)
    {

        mJoystick = aJoystick;
        mLogger = aLogger;
        mAutonFactory = aAutonFactory;
        mDPadUp = new LatchedButton();
        mDPadDown = new LatchedButton();
    }

    @Override
    public void initializeLogHeaders()
    {
        mLogger.addHeader("XbaxLeftJoystickSpeed");
        mLogger.addHeader("XbaxRightJoystickSpeed");
    }

    @Override
    public void update()
    {

    	double leftJoystick = -Utilities.stopInDeadband(mJoystick.getRawAxis(XboxButtonMap.LEFT_Y_AXIS), sJOYSTICK_DEADBAND);
        double rightJoystick = -Utilities.stopInDeadband(mJoystick.getRawAxis(XboxButtonMap.RIGHT_Y_AXIS), sJOYSTICK_DEADBAND);
        

        double leftNeg = Math.signum(leftJoystick);
        double rightNeg = Math.signum(rightJoystick);
        

//        mRightSpeed = rightJoystick * rightJoystick * rightNeg;
//        mLeftSpeed = leftJoystick * leftJoystick * leftNeg;

        mRightSpeed = rightJoystick;
        mLeftSpeed = leftJoystick;

    }

    @Override
    public void updateSmartDashboard()
    {
        SmartDashboard.putNumber(SmartDashBoardNames.sLEFT_XBAX_JOYSTICK_SPEED, mLeftSpeed);
        SmartDashboard.putNumber(SmartDashBoardNames.sRIGHT_XBAX_JOYSTICK_SPEED, mRightSpeed);
//        SmartDashboard.putNumber(SmartDashBoardNames.sAUTON_NUM, mAutonFactory.autonMode());
    }

    @Override
    public void updateLog()
    {
        mLogger.updateLogger(mLeftSpeed);
        mLogger.updateLogger(mRightSpeed);
    }

    @Override
    public double getRightSpeed()
    {

        return mRightSpeed;
    }

    @Override
    public double getLeftSpeed()
    {

        return mLeftSpeed;
    }

    @Override
    public double getArcadePower()
    {
        return 0;
    }

    @Override
    public double getArcadeTurn()
    {
        return 0;
    }

    @Override
    public boolean isArcadeMode()
    {
        return false;
    }

    @Override
    public boolean isRecording()
    {
        return false;
    }

}
