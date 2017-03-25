package com.snobot.vision_app.app2017.messages.out;

import org.json.JSONException;
import org.json.JSONObject;

public class HeartbeatMessage
{
    private JSONObject mJson;

    public HeartbeatMessage() throws JSONException {
        mJson = new JSONObject();
        mJson.put("type", "heartbeat");
    }

    public JSONObject getJson()
    {
        return mJson;
    }
}
