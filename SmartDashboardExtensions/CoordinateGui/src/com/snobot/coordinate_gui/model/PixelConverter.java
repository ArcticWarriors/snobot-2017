package com.snobot.coordinate_gui.model;


public class PixelConverter
{
    protected double mXCenterFeet;
    protected double mYCenterFeet;
    protected double mScaleFactor;

    protected int mXCenterPixels;

    public PixelConverter(double aFieldCenterX, double aFieldCenterY)
    {
        mXCenterFeet = aFieldCenterX;
        mYCenterFeet = aFieldCenterY;
    }

    public int convertFeetToPixels(double aFeet)
    {
        return (int) (aFeet * mScaleFactor);
    }

    public int convertXFeetToPixels(double aFeet)
    {
        return mXCenterPixels - convertFeetToPixels(mXCenterFeet - aFeet);
    }

    public int convertYFeetToPixels(double aFeet)
    {
        return convertFeetToPixels(mYCenterFeet - aFeet);
    }

    public double convertPixelsToFeet(int aPixels)
    {
        return aPixels / mScaleFactor;
    }

    public double convertXPixelsToFeet(int aX)
    {
        return mXCenterFeet - convertPixelsToFeet(mXCenterPixels - aX);
    }

    public double convertYPixelsToFeet(int aY)
    {
        return mYCenterFeet - convertPixelsToFeet(aY);
    }

    public void updateScaleFactor(int widthPixels, int heightPixels, double widthFeet, double heightFeet)
    {
        double horizontalScaleFactor = (widthPixels / widthFeet);
        double verticalScaleFactor = (heightPixels / heightFeet);

        double minScaleFactor = Math.min(horizontalScaleFactor, verticalScaleFactor);

        mScaleFactor = minScaleFactor;
        mXCenterPixels = (int) (widthFeet * mScaleFactor);
    }
}
