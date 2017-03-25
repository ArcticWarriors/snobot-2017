package com.snobot2017.vision.messages;

import org.json.simple.JSONObject;

public class RecordingMessage
{
    private JSONObject mJson;

    public RecordingMessage(boolean aRecord)
    {
        mJson = new JSONObject();
        mJson.put("type", "record_images");
        mJson.put("record", aRecord);
    }

    public JSONObject getJson()
    {
        return mJson;
    }
}
