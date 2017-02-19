package com.snobot.vision_app.app2017;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Pair;

/**
 * Created by PJ on 2/19/2017.
 */

public class VisionAlgorithmPreferences
{
    private static final int sDEFAULT_HUE_MIN = 27;
    private static final int sDEFAULT_HUE_MAX = 92;
    private static final int sDEFAULT_SAT_MIN = 44;
    private static final int sDEFAULT_SAT_MAX = 255;
    private static final int sDEFAULT_LAT_MIN = 172;
    private static final int sDEFAULT_LAT_MAX = 255;

    private Pair<Integer, Integer> mHueRange;
    private Pair<Integer, Integer> mSatRange;
    private Pair<Integer, Integer> mLumRange;

    private SharedPreferences mPreferences;

    public VisionAlgorithmPreferences(Context aContext)
    {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(aContext);

        mHueRange = new Pair<>(
                mPreferences.getInt("HueMin", sDEFAULT_HUE_MIN),
                mPreferences.getInt("HueMax", sDEFAULT_HUE_MAX));

        mSatRange = new Pair<>(
                mPreferences.getInt("SatMin", sDEFAULT_SAT_MIN),
                mPreferences.getInt("SatMax", sDEFAULT_SAT_MAX));

        mLumRange = new Pair<>(
                mPreferences.getInt("LumMin", sDEFAULT_LAT_MIN),
                mPreferences.getInt("LumMax", sDEFAULT_LAT_MAX));

        setHueThreshold(mHueRange);
        setSatThreshold(mSatRange);
        setLumThreshold(mLumRange);
    }

    public void setHueThreshold(Pair<Integer, Integer> aThreshold)
    {
        setHueThreshold(aThreshold.first, aThreshold.second);
    }

    public void setHueThreshold(int aMin, int aMax)
    {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt("HueMin", aMin);
        editor.putInt("HueMax", aMax);
        editor.apply();
    }

    public void setSatThreshold(Pair<Integer, Integer> aThreshold)
    {
        setSatThreshold(aThreshold.first, aThreshold.second);
    }

    public void setSatThreshold(int aMin, int aMax)
    {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt("SatMin", aMin);
        editor.putInt("SatMax", aMax);
        editor.apply();
    }

    public void setLumThreshold(Pair<Integer, Integer> aThreshold)
    {
        setLumThreshold(aThreshold.first, aThreshold.second);
    }

    public void setLumThreshold(int aMin, int aMax)
    {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt("LumMin", aMin);
        editor.putInt("LumMax", aMax);
        editor.apply();
    }

    public Pair<Integer, Integer> getHueThreshold() {
        return mHueRange;
    }

    public Pair<Integer, Integer> getSatThreshold() {
        return mSatRange;
    }

    public Pair<Integer, Integer> getLumThreshold() {
        return mLumRange;
    }


    public void restoreDefaults() {

        mHueRange = new Pair<>(
                mPreferences.getInt("HueMin", sDEFAULT_HUE_MIN),
                mPreferences.getInt("HueMax", sDEFAULT_HUE_MAX));

        mSatRange = new Pair<>(
                mPreferences.getInt("SatMin", sDEFAULT_SAT_MIN),
                mPreferences.getInt("SatMax", sDEFAULT_SAT_MAX));

        mLumRange = new Pair<>(
                mPreferences.getInt("LumMin", sDEFAULT_LAT_MIN),
                mPreferences.getInt("LumMax", sDEFAULT_LAT_MAX));

    }
}
