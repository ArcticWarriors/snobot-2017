package com.snobot2017.vision.messages;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class TargetUpdateMessage
{
    /**
     * Information on a single vision target
     * 
     * @author PJ
     *
     */
    public static class TargetInfo
    {
        /**
         * Angle to the target, in degrees
         */
        private double mAngle;

        /**
         * Distance to the target, as a ray. That is, the hypotenuse of the
         * triangle
         */
        private double mDistance;

        /**
         * Indicates the the target location is reported with a very low
         * confidence
         */
        private boolean mAbmigious;

        public TargetInfo()
        {
            this(0, 0);
        }

        public TargetInfo(double aAngle, double aDistance)
        {
            this(aAngle, aDistance, true);
        }

        public TargetInfo(double aAngle, double aDistance, boolean aAmbigious)
        {
            mAngle = aAngle;
            mDistance = aDistance;
            mAbmigious = aAmbigious;
        }

        public TargetInfo(JSONObject aJson)
        {
            mAngle = Double.parseDouble(aJson.get("angle").toString());
            mDistance = Double.parseDouble(aJson.get("distance").toString());
            mAbmigious = Boolean.parseBoolean(aJson.get("ambiguous").toString());
        }

        /**
         * Gets the angle to the target, in degrees
         * 
         * @return The angle
         */
        public double getAngle()
        {
            return mAngle;
        }

        /**
         * Gets the distance of the ray to the target
         * 
         * @return The distance in inches
         */
        public double getDistance()
        {
            return mDistance;
        }

        public boolean isAmbigious()
        {
            return mAbmigious;
        }

    }
    
    private List<TargetInfo> mTargets;
    private double mTimestamp;

    public TargetUpdateMessage()
    {
        mTargets = new ArrayList<>();
    }

    public TargetUpdateMessage(double aTimestamp, List<TargetInfo> aTargets)
    {
        mTimestamp = aTimestamp;
        mTargets = aTargets;
    }

    public TargetUpdateMessage(JSONObject aJson, double aCurrentTime)
    {
        mTargets = new ArrayList<>();

        double latency_sec = Double.parseDouble(aJson.get("camera_latency_sec").toString());
        mTimestamp = aCurrentTime - latency_sec;

        JSONArray targets = (JSONArray) aJson.get("targets");

        for (Object targetObj : targets)
        {
            JSONObject targetJson = (JSONObject) targetObj;

            mTargets.add(new TargetInfo(targetJson));
        }
    }

    /**
     * Gets the timestamp of when the image was captured
     * 
     * @return The timestamp in seconds
     */
    public double getTimestamp()
    {
        return mTimestamp;
    }

    /**
     * Gets the valid targets detected by the app
     * 
     * @return The valid targets
     */
    public List<TargetInfo> getTargets()
    {
        return mTargets;
    }

}
