package com.snobot.vision_app.app2017.messages.out;

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
        private boolean mAmbiguous;

        public TargetInfo(){
            this(0, 0, false);
        }

        public TargetInfo(double aAngle, double aDistance, boolean aAmbiguous) {
            mAngle = aAngle;
            mDistance = aDistance;
            mAmbiguous = aAmbiguous;
        }
    }

    private JSONObject mJson;

    public TargetUpdateMessage(List<TargetInfo> aTargets, double aLatencySec) throws JSONException {
        mJson = new JSONObject();

        JSONArray targetJson = new JSONArray();

        for(TargetInfo targetInfo : aTargets)
        {
            JSONObject targetInfoJson = new JSONObject();
            targetInfoJson.put("angle", targetInfo.mAngle);
            targetInfoJson.put("distance", targetInfo.mDistance);
            targetInfoJson.put("ambiguous", targetInfo.mAmbiguous);
            targetJson.put(targetInfoJson);
        }

        mJson.put("camera_latency_sec", aLatencySec);
        mJson.put("targets", targetJson);
        mJson.put("type", "target_update");
    }


    public JSONObject getJson()
    {
        return mJson;
    }

}
