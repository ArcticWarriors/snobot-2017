package com.snobot2016.light;

import com.snobot.xlib.Logger;
/**
 * Author Jeffrey/Michael
 * 
 * Creates light to be used by camera
 * Uses a relay so the driver can manually over ride the light to turn it on or off
 */
import com.snobot2016.SmartDashBoardNames;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * 
 * @author jbnol_000
 *
 *Special class for the light with operator override
 *
 */
public class Light implements ILight
{
    private Relay mRelay;
    private Logger mLogger;
    private boolean mLightOn;

    /**
     * 
     * @param aRelay
     *              Allows the light to be turned on and off manually
     * @param aLogger
     *               Logs how many times the light was turned on and off
     *  
     */
    public Light(Relay aRelay, Logger aLogger)
    {
        mRelay = aRelay;
        mLogger = aLogger;
    }

    @Override
    public void init()
    {
        mLogger.addHeader("LightOnOff");
    }

    @Override
    public void update()
    {
        mLightOn = SmartDashboard.getBoolean(SmartDashBoardNames.sSNOBOT_LIGHT, true);

    }

    @Override
    public void control()
    {
         if (mLightOn)
        {
            mRelay.set(Value.kForward);
        }
        else
        {
            mRelay.set(Value.kOff);
        }
    }

    @Override
    public void rereadPreferences()
    {

    }

    @Override
    public void updateSmartDashboard()
    {
        SmartDashboard.putBoolean(SmartDashBoardNames.sSNOBOT_LIGHT, mLightOn);
    }

    @Override
    public void updateLog()
    {
        mLogger.updateLogger(mLightOn);
    }

    @Override
    public void stop()
    {

    }

    @Override
    public boolean isLightOn()
    {
        return mLightOn;
    }

}
