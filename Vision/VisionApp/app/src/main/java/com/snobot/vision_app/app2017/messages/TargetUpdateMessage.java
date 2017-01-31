package com.snobot.vision_app.app2017.messages;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
        private double mAngle;
        private double mDistance;

        public TargetInfo(){
            this(0, 0);
        }

        public TargetInfo(double aAngle, double aDistance) {
            mAngle = aAngle;
            mDistance = aDistance;
        }
    }

    private JSONObject mJson;

    public TargetUpdateMessage(List<TargetInfo> aTargets, int aTimestamp) throws JSONException {
        mJson = new JSONObject();

        JSONArray targetJson = new JSONArray();

        for(TargetInfo targetInfo : aTargets)
        {
            JSONObject targetInfoJson = new JSONObject();
            targetInfoJson.put("angle", targetInfo.mAngle);
            targetInfoJson.put("distance", targetInfo.mDistance);
            targetJson.put(targetInfoJson);
        }

        mJson.put("timestamp", aTimestamp);
        mJson.put("targets", targetJson);
    }


    public JSONObject getJson()
    {
        return mJson;
    }

}
