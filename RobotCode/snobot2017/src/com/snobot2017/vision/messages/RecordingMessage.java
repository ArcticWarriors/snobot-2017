package com.snobot2017.vision.messages;

import org.json.simple.JSONObject;

public class RecordingMessage
{
    private JSONObject mJson;

    @SuppressWarnings("unchecked")
    public RecordingMessage(boolean aRecord, String aName)
    {
        mJson = new JSONObject();
        mJson.put("type", "record_images");
        mJson.put("record", aRecord);
        mJson.put("name", aName);
    }

    public JSONObject getJson()
    {
        return mJson;
    }
}
