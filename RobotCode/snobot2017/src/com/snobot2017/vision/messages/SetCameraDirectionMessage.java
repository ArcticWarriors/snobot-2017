package com.snobot2017.vision.messages;

import org.json.simple.JSONObject;

import com.snobot2017.vision.VisionAdbServer.CameraFacingDirection;

public class SetCameraDirectionMessage
{
    private JSONObject mJson;
    
    @SuppressWarnings("unchecked")
    public SetCameraDirectionMessage(CameraFacingDirection aDirection)
    {
        mJson = new JSONObject();
        mJson.put("type", "camera_direction");
        mJson.put("direction", aDirection.toString());
    }
    
    public JSONObject getJson()
    {
        return mJson;
    }
}
