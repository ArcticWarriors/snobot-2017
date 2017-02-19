package FuelPooper;

import com.snobot.lib.Logger;
import com.snobot2017.Properties2017;
import com.snobot2017.SmartDashBoardNames;
import com.snobot2017.joystick.IOperatorJoystick;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FuelPooper implements IFuelPooper
{
    private Servo mSphincter;
    private Logger mLogger;
    private IOperatorJoystick mJoystick;
    private double mSphincterPosition;

    public FuelPooper(Servo aSphincter, Logger aLogger)
    {
        mSphincter = aSphincter;
        mLogger = aLogger;
    }

    @Override
    public void init()
    {
        mLogger.addHeader("SphincterPosition");
    }

    @Override
    public void update()
    {
        mSphincterPosition = mSphincter.getPosition();
    }

    @Override
    public void control()
    {
        // I Am The Senate
        if (mJoystick.isPooperOpen())
        {
            mSphincter.set(1);
        }
        else
        {
            mSphincter.set(0);
        }
    }

    @Override
    public void rereadPreferences()
    {
        // Nothing
    }

    @Override
    public void updateSmartDashboard()
    {
        SmartDashboard.putNumber(SmartDashBoardNames.sSPHINCTER_POSITION, mSphincterPosition);
    }

    @Override
    public void updateLog()
    {
        mLogger.updateLogger(mSphincter.getPosition());
    }

    @Override
    public void stop()
    {
        mSphincter.set(0);
    }

    @Override
    public void openSphincter()
    {
        double speed = Properties2017.sSPCHINCTER_OPEN.getValue();
        mSphincter.set(speed);
        mLogger.updateLogger(speed);
    }

    @Override
    public void closeSphincter()
    {
        double speed = Properties2017.sSPCHINCTER_CLOSE.getValue();
        mSphincter.set(speed);
        mLogger.updateLogger(speed);
    }

}
