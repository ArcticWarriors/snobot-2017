package com.snobot.vision_app.app2017.messages.in;

import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.android.CameraBridgeViewBase;

import java.util.HashMap;
import java.util.Map;

public class CameraDirectionMessage
{
    private static Map<String, Integer> sDIRECTION_MAP;

    static
    {
        sDIRECTION_MAP = new HashMap<>();
        sDIRECTION_MAP.put("Front", CameraBridgeViewBase.CAMERA_ID_FRONT);
        sDIRECTION_MAP.put("Rear", CameraBridgeViewBase.CAMERA_ID_BACK);
    }

    private int cameraDirection;

    public CameraDirectionMessage(JSONObject aJson) throws JSONException {

        String direction = (String) aJson.get("direction");
        cameraDirection = sDIRECTION_MAP.get(direction);
    }

    public int getCameraDirection()
    {
        return cameraDirection;
    }
}
