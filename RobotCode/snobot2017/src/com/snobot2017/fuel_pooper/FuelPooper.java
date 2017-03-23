package com.snobot2017.fuel_pooper;

import com.snobot.lib.logging.ILogger;
import com.snobot2017.Properties2017;
import com.snobot2017.SmartDashBoardNames;
import com.snobot2017.joystick.IOperatorJoystick;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FuelPooper implements IFuelPooper
{
    private IOperatorJoystick mJoystick;
    private DoubleSolenoid mSphincterRight;
    private DoubleSolenoid mSphincterLeft;
    private ILogger mLogger;

    private boolean mIsShincterOpen;

    public FuelPooper(IOperatorJoystick aJoystick, DoubleSolenoid aRightSphincter, DoubleSolenoid aLeftSphincter, ILogger aLogger)
    {
        mSphincterRight = aRightSphincter;
        mSphincterLeft = aLeftSphincter;
        mLogger = aLogger;
        mJoystick = aJoystick;

        mIsShincterOpen = false;
    }

    @Override
    public void initializeLogHeaders()
    {
        mLogger.addHeader("SphincterIsOpen");
    }

    @Override
    public void update()
    {

    }

    @Override
    public void control()
    {
        // I Am The Senate
        if (mJoystick.isPooperOpen())
        {
            openSphincter();
        }
        else
        {
            closeSphincter();
        }
    }

    @Override
    public void updateSmartDashboard()
    {
        SmartDashboard.putBoolean(SmartDashBoardNames.sSPCHINCTER_OPEN, mIsShincterOpen);
    }

    @Override
    public void updateLog()
    {
        mLogger.updateLogger(mIsShincterOpen);
    }

    @Override
    public void stop()
    {
        closeSphincter();
    }

    @Override
    public void openSphincter()
    {
        mIsShincterOpen = true;
        mSphincterRight.set(DoubleSolenoid.Value.kReverse);
        mSphincterLeft.set(DoubleSolenoid.Value.kReverse);
    }

    @Override
    public void closeSphincter()
    {
        mIsShincterOpen = false;
        mSphincterRight.set(DoubleSolenoid.Value.kForward);
        mSphincterLeft.set(DoubleSolenoid.Value.kForward);
    }

}
