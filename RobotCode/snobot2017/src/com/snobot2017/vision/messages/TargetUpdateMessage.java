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

        public TargetInfo()
        {
            mAngle = 0;
            mDistance = 0;
        }

        public TargetInfo(JSONObject aJson)
        {
            mAngle = Double.parseDouble(aJson.get("angle").toString());
            mDistance = Double.parseDouble(aJson.get("distance").toString());
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

    }
    
    private JSONObject mJson;
    private List<TargetInfo> mTargets;
    private double mTimestamp;

    public TargetUpdateMessage()
    {
        mTargets = new ArrayList<>();
    }

    public TargetUpdateMessage(JSONObject aJson)
    {
        mJson = aJson;
        mTargets = new ArrayList<>();

        mTimestamp = Double.parseDouble(aJson.get("timestamp").toString());

        JSONArray targets = (JSONArray) aJson.get("targets");

        for (Object targetObj : targets)
        {
            JSONObject targetJson = (JSONObject) targetObj;

            mTargets.add(new TargetInfo(targetJson));
        }
    }

    public String toJsonString()
    {
        return mJson.toJSONString();
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
