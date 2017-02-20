package com.snobot.vision_app.app2017.java_algorithm.common;

import org.opencv.core.MatOfPoint;

public class TapeLocation
{
    private MatOfPoint mContour;
    private double mAngle;
    private double mDistanceFromHoriz;
    private double mDistanceFromVert;
    private double mAspectRatio;

    public TapeLocation(MatOfPoint aContour, double aAngle, double aDistanceFromHoriz, double aDistanceFromVert, double aAspectRatio)
    {
        mContour = aContour;
        mAngle = aAngle;
        mDistanceFromHoriz = aDistanceFromHoriz;
        mDistanceFromVert = aDistanceFromVert;
        mAspectRatio = aAspectRatio;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(mAngle);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(mAspectRatio);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((mContour == null) ? 0 : mContour.hashCode());
        temp = Double.doubleToLongBits(mDistanceFromHoriz);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(mDistanceFromVert);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TapeLocation other = (TapeLocation) obj;
        if (Double.doubleToLongBits(mAngle) != Double.doubleToLongBits(other.mAngle))
            return false;
        if (Double.doubleToLongBits(mAspectRatio) != Double.doubleToLongBits(other.mAspectRatio))
            return false;
        if (mContour == null)
        {
            if (other.mContour != null)
                return false;
        }
        else if (!mContour.equals(other.mContour))
            return false;
        if (Double.doubleToLongBits(mDistanceFromHoriz) != Double.doubleToLongBits(other.mDistanceFromHoriz))
            return false;
        if (Double.doubleToLongBits(mDistanceFromVert) != Double.doubleToLongBits(other.mDistanceFromVert))
            return false;
        return true;
    }

    @Override
    public String toString()
    {
        return "TapeLocation [mAngle=" + mAngle + ", mDistanceFromHoriz=" + mDistanceFromHoriz + ", mDistanceFromVert="
                + mDistanceFromVert + ", mAspectRatio=" + mAspectRatio + "]";
    }

    public MatOfPoint getContour() {
        return mContour;
    }

    public double getAngle() {
        return mAngle;
    }

    public double getAspectRatio() {
        return mAspectRatio;
    }

    public double getPreferredDistance() {
        return mDistanceFromVert;
    }
}