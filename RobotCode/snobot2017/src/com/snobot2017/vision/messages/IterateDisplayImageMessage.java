package com.snobot2017.vision.messages;

import org.json.simple.JSONObject;

public class IterateDisplayImageMessage
{
    private JSONObject mJson;

    @SuppressWarnings("unchecked")
    public IterateDisplayImageMessage()
    {
        mJson = new JSONObject();
        mJson.put("type", "iterate_display_image");
    }

    public JSONObject getJson()
    {
        return mJson;
    }
}
