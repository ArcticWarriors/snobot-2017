package com.snobot2017.vision.messages;

import org.json.simple.JSONObject;

public class HeartbeatMessage
{
    private JSONObject mJson;

    @SuppressWarnings("unchecked")
    public HeartbeatMessage()
    {
        mJson = new JSONObject();
        mJson.put("type", "heartbeat");
    }

    public JSONObject getJson()
    {
        return mJson;
    }
}
