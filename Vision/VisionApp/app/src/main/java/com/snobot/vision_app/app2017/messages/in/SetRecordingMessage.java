package com.snobot.vision_app.app2017.messages.in;

import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.android.CameraBridgeViewBase;

import java.util.HashMap;
import java.util.Map;

public class SetRecordingMessage
{
    private boolean mRecord;

    public SetRecordingMessage(JSONObject aJson) throws JSONException {
        mRecord = Boolean.parseBoolean((String) aJson.get("record"));
    }

    public boolean shouldRecord()
    {
        return mRecord;
    }
}
